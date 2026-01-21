package com.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

	@Bean
	public ChatClient chatClient(ChatClient.Builder builder) {
		return builder.defaultSystem("You are a helpful coding assistant. You are an expert in coding.")
				.defaultOptions(
						OpenAiChatOptions.builder().model("gpt-3.5-turbo").temperature(0.3).maxTokens(200).build())
				.defaultAdvisors(new SimpleLoggerAdvisor())
				.build();
	}
}