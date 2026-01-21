package com.ai.serviceImpl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import com.ai.common.MessageConstant;
import com.ai.common.ResponseBean;
import com.ai.model.AiChatLog;
import com.ai.repository.AiChatLogRepository;
import com.ai.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class ChatServiceImpl implements ChatService {

	private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

	private final ChatClient chatClient;
	private final AiChatLogRepository aiChatLogRepository;

	public ChatServiceImpl(ChatClient.Builder builder, AiChatLogRepository aiChatLogRepository) {
		if (builder == null) {
			logger.error("ChatClient.Builder cannot be null during initialization.");
			throw new IllegalArgumentException("ChatClient.Builder cannot be null.");
		}

		try {
			this.chatClient = builder.defaultOptions(OpenAiChatOptions.builder().temperature(0.3).maxTokens(200).build()).build();
			this.aiChatLogRepository = aiChatLogRepository;

			logger.info("ChatClient successfully initialized.");
		} catch (Exception e) {
			logger.error("Failed to initialize ChatClient: {}", e.getMessage(), e);
			throw new RuntimeException("ChatClient initialization failed.", e);
		}
	}

	@Override
	public ResponseBean chat(String query) {
		ResponseBean resBean = new ResponseBean();

		if (query == null || query.trim().isEmpty()) {
			logger.warn("Invalid 'query' parameter: null or empty.");

			resBean.setStatusCode(MessageConstant.VALIDATION_ERROR_CODE);
			resBean.setStatusDesc(MessageConstant.VALIDATION_ERROR_DESC);
			resBean.setError("Query cannot be null or empty.");

			// Persist validation failure log
			aiChatLogRepository.save(new AiChatLog(query, "Invalid request", "VALIDATION_ERROR", "OpenAI"));
			return resBean;
		}

		try {
			logger.info("Processing chat request. Query: {}", query);

			Prompt prompt = new Prompt(query.trim());
			String content = chatClient.prompt(prompt).system(MessageConstant.SYSTEM_PROMPT).call().content();

			if (content == null || content.trim().isEmpty()) {
				logger.warn("AI returned empty content for query.");

				resBean.setStatusCode(MessageConstant.NO_DATA_CODE);
				resBean.setStatusDesc(MessageConstant.NO_DATA_DESC);
				resBean.setData(Map.of("response", "No response generated"));

				// Persist no data log
				aiChatLogRepository.save(new AiChatLog(query, "EMPTY_RESPONSE", "NO_DATA", "OpenAI"));
				return resBean;
			}

			resBean.setStatusCode(MessageConstant.SUCCESS_CODE);
			resBean.setStatusDesc(MessageConstant.SUCCESS_DESC);
			resBean.setData(Map.of("response", content));

			logger.info("Successfully generated AI response.");

			// Persist success log
			aiChatLogRepository.save(new AiChatLog(query, content, "SUCCESS", "OpenAI"));

			return resBean;

		} catch (Exception e) {
			logger.error("Exception during processing: {}", e.toString(), e);

			resBean.setStatusCode(MessageConstant.TECHNICAL_ERROR_CODE);
			resBean.setStatusDesc(MessageConstant.TECHNICAL_ERROR_DESC);
			resBean.setError("Internal error occurred. Please try again.");
			resBean.setData(Map.of("response", "Sorry, we couldn't process your request."));

			// Persist failure log
			aiChatLogRepository.save(new AiChatLog(query, e.getMessage(), "FAILED", "OpenAI"));

			return resBean;
		}
	}

	@Override
	public ResponseBean getChatHistory() {
		ResponseBean resBean = new ResponseBean();

		logger.debug("Entering getChatHistory()");

		try {
			List<AiChatLog> logs = aiChatLogRepository.findAll();

			if (logs == null || logs.isEmpty()) {
				logger.warn("No chat history found.");

				resBean.setStatusCode(MessageConstant.NO_DATA_CODE);
				resBean.setStatusDesc(MessageConstant.NO_DATA_DESC);
				resBean.setData(Map.of("history", "[]"));

				logger.debug("Exiting getChatHistory() with NO_DATA status.");
				return resBean;
			}
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

			// Convert to JSON string
			String jsonHistory = mapper.writeValueAsString(logs);

			resBean.setStatusCode(MessageConstant.SUCCESS_CODE);
			resBean.setStatusDesc(MessageConstant.SUCCESS_DESC);
			resBean.setData(Map.of("history", jsonHistory));
			
			logger.info("Chat History: {}", jsonHistory);
			logger.info("Chat history fetched successfully. Total logs: {}", logs.size());
			logger.debug("Exiting getChatHistory() with SUCCESS status.");
			return resBean;
		} catch (Exception e) {
			logger.error("Exception while fetching chat history: {}", e.getMessage(), e);

			resBean.setStatusCode(MessageConstant.TECHNICAL_ERROR_CODE);
			resBean.setStatusDesc(MessageConstant.TECHNICAL_ERROR_DESC);
			resBean.setError("Unable to fetch chat history due to internal error.");
			resBean.setData(null);

			logger.debug("Exiting getChatHistory() with TECHNICAL_ERROR status.");
			return resBean;
		}
	}
}