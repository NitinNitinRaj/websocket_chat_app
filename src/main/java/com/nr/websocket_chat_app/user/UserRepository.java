package com.nr.websocket_chat_app.user;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  List<User> findAllByStatus(Status status);
}
