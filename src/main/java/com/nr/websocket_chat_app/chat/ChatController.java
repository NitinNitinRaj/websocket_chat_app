package com.nr.websocket_chat_app.chat;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {

  private final SimpMessagingTemplate messagingTemplate; //is used to send messages to WebSocket clients from within the server-side
  private final ChatService chatService;

  @MessageMapping("/chat")
  public void processMessage(@Payload Chat chat) {
    Chat savedMsg = chatService.save(chat);
    // nitin/queue/messages
    messagingTemplate.convertAndSendToUser(
      savedMsg.getRecipientId(),
      "/queue/messages",
      ChatNotification
        .builder()
        .senderId(savedMsg.getSenderId())
        .recipientId(savedMsg.getRecipientId())
        .content(savedMsg.getContent())
        .build()
    );
  }

  @GetMapping("/messages/{senderId}/{recipientId}")
  public ResponseEntity<List<Chat>> getChats(
    @PathVariable("senderId") String senderId,
    @PathVariable("recipientId") String recipientId
  ) {
    return ResponseEntity.ok(
      chatService.findChatMessages(senderId, recipientId)
    );
  }
}
