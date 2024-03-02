package com.nr.websocket_chat_app.chat;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
  List<Chat> findByChatId(String chatId);
}
