package com.example.demo.websocket;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

  private final MessageService service;

  public DefaultController(MessageService service) {
    this.service = service;
  }

  @PostMapping("/create")
  public String create(){
    return service.createChannel().channelId();
  }
}
