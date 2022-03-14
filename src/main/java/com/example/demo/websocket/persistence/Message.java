package com.example.demo.websocket.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Message {
  @Id @GeneratedValue
  Long id;

  String channelId;

  String sender;

  @Column(columnDefinition = "TEXT")
  String message;

  public Message(String channelId, String sender, String message) {
    this.channelId = channelId;
    this.sender = sender;
    this.message = message;
  }
}
