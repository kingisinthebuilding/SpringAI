# SpringAI
A Spring Boot–based AI backend service that integrates OpenAI through Spring AI to enable intelligent text generation and processing. The application exposes REST APIs that allow clients to send prompts and receive AI-generated responses. PostgreSQL is used to store prompts, responses, and interaction history for auditing and analytics.

Key Features:

AI Integration: Uses Spring AI’s OpenAI model interface for chat-based completions.

REST API Layer: Exposes endpoints for prompt submission, history retrieval, and AI result consumption.

Persistence: Stores user requests, generated outputs, and metadata using JPA with PostgreSQL.

Configurable Model Behavior: Supports parameter tuning (model, tokens, temperature, etc.) via external configuration.

Optimized Connection Pooling: Uses HikariCP for efficient DB connection management.

Structured Logging: Custom log patterns for traceability and debugging with correlation IDs.

Secure Configuration: Externalized API keys via environment variables.

Tech Stack:

Backend: Spring Boot 3.5, REST, Spring Validation, Spring AI

Database: PostgreSQL + JPA/Hibernate

AI: OpenAI via Spring AI model

Build: Maven

Language: Java 17+
