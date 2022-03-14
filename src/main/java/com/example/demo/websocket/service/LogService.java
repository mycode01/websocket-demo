package com.example.demo.websocket.service;

import com.example.demo.websocket.controller.dto.UserMsg;
import com.example.demo.websocket.persistence.Message;
import com.example.demo.websocket.persistence.MessageRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class LogService {
  private final MessageRepository repo;

  public LogService(MessageRepository repo) {
    this.repo = repo;
  }

  @Async
  public void log(UserMsg msg) {
    repo.save(new Message(msg.getChannelId(), msg.getSender(), msg.getMessage()));
  }
}
