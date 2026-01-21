package com.ai.service;

import com.ai.common.ResponseBean;

public interface ChatService {

	ResponseBean chat(String q);

	ResponseBean getChatHistory();

}