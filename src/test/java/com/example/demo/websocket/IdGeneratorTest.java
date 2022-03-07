package com.example.demo.websocket;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IdGeneratorTest {

  @Test
  public void test() {
    IdGenerator generator = new IdGenerator();

    Set<String> idSet = new HashSet<>();

    for (int i = 0; i < 100000; i++) {
      idSet.add(generator.next());
    }

    assertTrue(idSet.size() == 100000);
  }

}