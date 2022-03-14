package com.example.demo.websocket.controller.dto;


public enum UserState {
  JOIN {
    @Override
    String message(String msg) {
      return "joining";
    }
  },
  TRANSMIT {
    @Override
    String message(String msg) {
      return msg;
    }
  },
  LEAVE {
    @Override
    String message(String msg) {
      return "leaves";
    }
  };

  abstract String message(String msg);
}
