package com.gtcafe.mqlab01.producer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gtcafe.mqlab01.producer.MessageProducer;
import com.gtcafe.mqlab01.producer.model.Event;
import com.gtcafe.mqlab01.producer.model.Message;

@RestController
public class RootController {

  @Autowired
  MessageProducer _producer;

  @PostMapping(value = "/send-message", produces = { "application/json" })
  public ResponseEntity<String> sendMessage(
      @RequestBody Message message) {

    String eventId = UUID.randomUUID().toString();
    message.setId(eventId);

    Event event = new Event();
    event.setEventId(eventId);
    event.setMessage(message);

    _producer.send(event);

    return ResponseEntity.ok(String.format("sent, eventId: [%s], message: [%s]", eventId, message));
  }

}
