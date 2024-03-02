package com.nr.websocket_chat_app.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @MessageMapping("/user.addUser") //will map User creation message from client to addUser()
  @SendTo("/user/topic") //to inform all the connected user that new user has joined - will add this message to 'topic' queue
  public User addUser(@Payload User user) {
    return service.saveUser(user);
  }

  @MessageMapping("/user.disconnectUser")
  @SendTo("/user/topic")
  public User disconnectUser(@Payload User user) {
    return service.disconnect(user);
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> findAllConnectedUser() {
    return ResponseEntity.ok(service.findConnectedUser());
  }
}
