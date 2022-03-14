package com.example.demo.websocket.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMsg {

  String channelId;
  String sender;
  UserState state;
  String message;

  public String getMessage() {
    return state.message(message);
  }

  @Override
  public String toString() {
    return "UserMsg{" +
        "channelId='" + channelId + '\'' +
        ", sender='" + sender + '\'' +
        ", state='" + state.name() + '\'' +
        ", message='" + message + '\'' +
        '}';
  }
}
