package com.example.demo.websocket.controller;

import com.example.demo.websocket.service.ChannelService;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/channels")
public class ChannelController {

  private final ChannelService service;

  public ChannelController(ChannelService service) {
    this.service = service;
  }

  @PostMapping("/create")
  public String create() {
    return service.createChannel().channelId();
  }

  @GetMapping
  public Set<String> channels() {
    return service.findAllChannels();
  }

  @Controller
  public static class DefaultController {

    @GetMapping("/index")
    public String index() {
      return "index";
    }

    @GetMapping("/detail/{channelId}")
    public ModelAndView detail(@PathVariable String channelId) {
      ModelAndView mv = new ModelAndView();
      mv.setViewName("detail");
      mv.addObject("channelId", channelId);
      return mv;
    }
  }
}

