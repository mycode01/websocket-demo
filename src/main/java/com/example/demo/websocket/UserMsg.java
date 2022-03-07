package com.example.demo.websocket;

import lombok.Value;

@Value
public class UserMsg {

  String channelId;
  String sender;
  String message;

  @Override
  public String toString() {
    return "UserMsg{" +
        "channelId='" + channelId + '\'' +
        ", sender='" + sender + '\'' +
        ", message='" + message + '\'' +
        '}';
  }

  public String toJsonString(){
    return "{" +
        "\"channelId\":\"" + channelId + '\"' +
        ", \"sender\":\"" + sender + '\"' +
        ", \"message\":\"" + message + '\"' +
        '}';
  }
}
