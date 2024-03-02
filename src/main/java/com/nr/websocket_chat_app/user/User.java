package com.nr.websocket_chat_app.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document // create a mongo document
public class User {

  @Id
  private String nickName;

  private String fullName;
  private Status status;
}
