package com.example.demo.websocket.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Channel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  String channelId;

  public Channel(String channelId) {
    this.channelId = channelId;
  }

  public String channelId() {
    return channelId;
  }
}
