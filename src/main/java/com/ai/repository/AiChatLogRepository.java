package com.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.model.AiChatLog;

@Repository
public interface AiChatLogRepository extends JpaRepository<AiChatLog, Long> {
	
}