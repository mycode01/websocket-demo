package com.example.demo.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {

  private final IdGenerator idGenerator;
  private final Map<String, Channel> channelMap;

  public MessageService(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
    this.channelMap = new HashMap<>();
  }

  public Channel createChannel() {
    Channel c = new Channel(idGenerator.next());
    channelMap.put(c.channelId(), c);
    return c;
  }

  public void findAndSendMessage(InternalMsg msg) {
    Channel c = findChannel(msg.getChannelId());

    c.handleMessage(msg.getSession(), msg.getMsg());
  }

  public Channel findChannel(String channelId){
    return Optional.ofNullable(channelMap.get(channelId)).orElseThrow(RuntimeException::new);
  }
}
