package com.ai.controller;

import com.ai.common.MessageConstant;
import com.ai.common.ResponseBean;
import com.ai.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/AI")
@Validated
public class ChatController {

	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	private final ChatService chatService;

	public ChatController(ChatService chatService) {
		if (chatService == null) {
			logger.error("ChatService cannot be null in ChatController constructor.");
			throw new IllegalArgumentException("ChatService cannot be null.");
		}
		this.chatService = chatService;
		logger.info("ChatController initialized successfully.");
	}

	@PostMapping("/ask")
	public ResponseEntity<ResponseBean> chat(@RequestParam(value = "Query", required = true) @NotBlank(message = "Query cannot be blank.") String q) {

		logger.info("Incoming AI request. Query length: {}", q.length());
		ResponseBean resBean = chatService.chat(q);
		HttpStatus httpStatus;

		switch (resBean.getStatusCode()) {
		case MessageConstant.SUCCESS_CODE:
			httpStatus = HttpStatus.OK;
			break;
		case MessageConstant.VALIDATION_ERROR_CODE:
			httpStatus = HttpStatus.BAD_REQUEST;
			break;
		case MessageConstant.NO_DATA_CODE:
			httpStatus = HttpStatus.NO_CONTENT;
			break;
		case MessageConstant.TECHNICAL_ERROR_CODE:
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		default:
			httpStatus = HttpStatus.OK;
		}

		logger.info("Sending response with HTTP status: {}", httpStatus.value());
		return new ResponseEntity<>(resBean, httpStatus);
	}

	@GetMapping("/chat-history")
	public ResponseEntity<ResponseBean> getChatHistory() {
		logger.info("Chat History request.");

		ResponseBean resBean = chatService.getChatHistory();
		HttpStatus httpStatus;

		switch (resBean.getStatusCode()) {
		case MessageConstant.SUCCESS_CODE:
			httpStatus = HttpStatus.OK;
			break;
		case MessageConstant.VALIDATION_ERROR_CODE:
			httpStatus = HttpStatus.BAD_REQUEST;
			break;
		case MessageConstant.NO_DATA_CODE:
			httpStatus = HttpStatus.NO_CONTENT;
			break;
		case MessageConstant.TECHNICAL_ERROR_CODE:
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			break;
		default:
			httpStatus = HttpStatus.OK;
		}

		logger.info("Sending response with HTTP status: {}", httpStatus.value());
		return new ResponseEntity<>(resBean, httpStatus);
	}
}