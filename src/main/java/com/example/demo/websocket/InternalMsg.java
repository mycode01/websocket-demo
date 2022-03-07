package com.example.demo.websocket;

import lombok.Value;
import lombok.experimental.Delegate;
import org.springframework.web.socket.WebSocketSession;

@Value
public class InternalMsg {
  @Delegate
  UserMsg msg;
  WebSocketSession session;
}
