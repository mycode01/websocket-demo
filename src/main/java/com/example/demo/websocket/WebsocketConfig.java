package com.example.demo.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@EnableWebSocket
@Configuration
public class WebsocketConfig implements WebSocketConfigurer {

  private final MessageHandler messageHandler;

  public WebsocketConfig(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(messageHandler, "/ws/msg").setAllowedOriginPatterns("*")
//        .withSockJS();// sockJs 사용시 ie 11, 10을 지원하지만 설정이 까다로움
    ;
  }

  @Component
  public static class MessageHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final MessageService service;

    public MessageHandler(ObjectMapper objectMapper,
        MessageService service) {
      this.objectMapper = objectMapper;
      this.service = service;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message)
        throws Exception {
      String payload = message.getPayload();
      UserMsg msg = objectMapper.readValue(payload, UserMsg.class);
      log.info("ws payload : {}", msg);

      service.findAndSendMessage(new InternalMsg(msg, session));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
      super.afterConnectionEstablished(session);
      // session 헤더를 사용하지 않으면 과거 메시지 내역은 못보내줌
      log.info("connection opened ");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
        throws Exception {
      super.afterConnectionClosed(session, status);
      log.info("connection closed ");
    }
  }
}
