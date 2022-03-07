package com.example.demo.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class Channel {
  private String channelId;
  private Set<WebSocketSession> sessions;

  public Channel(String channelId) {
    this.channelId = channelId;
    this.sessions = new HashSet<>();
  }

  public void handleMessage(WebSocketSession session, UserMsg msg){
    sessions.add(session);
    sessions.parallelStream().forEach(e-> {
      try {
        e.sendMessage(new TextMessage(msg.toJsonString()));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
  }

  public String channelId(){
    return channelId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Channel channel = (Channel) o;
    return channelId.equals(channel.channelId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(channelId);
  }
}
