package com.nr.websocket_chat_app.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public User saveUser(User user) {
    user.setStatus(Status.ONLINE);
    User savedUser = repository.save(user);
    return savedUser;
  }

  public User disconnect(User user) {
    var storedUser = repository.findById(user.getNickName()).orElse(null);
    User updatedUser = null;
    if (storedUser != null) {
      storedUser.setStatus(Status.OFFLINE);
      updatedUser = repository.save(storedUser);
    }
    return updatedUser;
  }

  public List<User> findConnectedUser() {
    return repository.findAllByStatus(Status.ONLINE);
  }
}
