package com.nr.websocket_chat_app.chatroom;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

  private final ChatRoomRepository chatRoomRepository;

  public Optional<String> getChatRoomId(
    String senderId,
    String recipientId,
    boolean createNewRoomIfNotExists
  ) {
    return chatRoomRepository
      .findBySenderIdAndRecipientId(senderId, recipientId)
      .map(ChatRoom::getChatId)
      .or(() -> {
        if (createNewRoomIfNotExists) {
          var chatId = createChat(senderId, recipientId);
          return Optional.of(chatId);
        }
        return Optional.empty();
      });
  }

  private String createChat(String senderId, String recipientId) {
    var chatId = String.format("%s_%s", senderId, recipientId); //senderId_recipientId
    ChatRoom senderRecipient = ChatRoom
      .builder()
      .chatId(chatId)
      .recipientID(recipientId)
      .senderId(senderId)
      .build();

    ChatRoom recipientSender = ChatRoom
      .builder()
      .chatId(chatId)
      .recipientID(senderId)
      .senderId(recipientId)
      .build();

    chatRoomRepository.save(recipientSender);
    chatRoomRepository.save(senderRecipient);

    return chatId;
  }
}
