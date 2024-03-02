package com.nr.websocket_chat_app.chat;

import com.nr.websocket_chat_app.chatroom.ChatRoomService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatRepository chatRepository;
  private final ChatRoomService chatRoomService;

  public Chat save(Chat chat) {
    var chatId = chatRoomService
      .getChatRoomId(chat.getSenderId(), chat.getRecipientId(), true)
      .orElseThrow(); //ToDo exception handling
    chat.setChatId(chatId);
    return chatRepository.save(chat);
  }

  public List<Chat> findChatMessages(String senderId, String recipientId) {
    var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
    return chatId.map(chatRepository::findByChatId).orElse(new ArrayList<>());
  }
}
