package com.example.demo.websocket.controller;

import com.example.demo.websocket.controller.dto.UserMsg;
import com.example.demo.websocket.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MessageHandler {

  private final RabbitTemplate msgTemplate;
  private final LogService logService;

  public MessageHandler(RabbitTemplate msgTemplate,
      LogService logService) {
    this.msgTemplate = msgTemplate;
    this.logService = logService;
  }

  @MessageMapping("msg.{channelId}")
  public void message(@DestinationVariable String channelId, UserMsg msg) {
    log.info("in sender : {}", channelId);
    log.info("in sender : {}", msg.toString());

    msgTemplate.convertAndSend("channel.exchange", "msg." + channelId, msg);
//    msgTemplate.convertAndSend("amq.topic", "msg." + channelId, msg);
    logService.log(msg);
  }

  @RabbitListener(queues = "channel.queue", containerFactory = "containerFactory")
  public void log(UserMsg msg) {
    log.info("in Listener : {}", msg.toString());
  }
}
