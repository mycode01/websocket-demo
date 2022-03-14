package com.example.demo.websocket.service;

import com.example.demo.websocket.persistence.Channel;
import com.example.demo.websocket.persistence.ChannelRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ChannelService {

  private final IdGenerator idGenerator;
  private final ChannelRepository repo;

  public ChannelService(IdGenerator idGenerator,
      ChannelRepository repo) {
    this.idGenerator = idGenerator;
    this.repo = repo;
  }

  @Transactional
  public Channel createChannel() {
    Channel c = new Channel(idGenerator.next());
    return repo.save(c);
  }

  public Set<String> findAllChannels(){
    return repo.findAll().stream().map(Channel::channelId).collect(Collectors.toSet());
  }
}
