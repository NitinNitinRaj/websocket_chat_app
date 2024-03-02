package com.nr.websocket_chat_app.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;

  public void saveUser(User user) {
    user.setStatus(Status.ONLINE);
    repository.save(user);
  }

  public void disconnect(User user) {
    var storedUser = repository.findById(user.getNickName()).orElse(null);
    if (storedUser != null) {
      storedUser.setStatus(Status.OFFLINE);
      repository.save(storedUser);
    }
  }

  public List<User> findConnectedUser() {
    return repository.findAllByStatus(Status.ONLINE);
  }
}
