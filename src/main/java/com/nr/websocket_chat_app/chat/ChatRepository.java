package com.nr.websocket_chat_app.chat;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {}
