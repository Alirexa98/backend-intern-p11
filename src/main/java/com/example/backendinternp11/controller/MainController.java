package com.example.backendinternp11.controller;

import com.example.backendinternp11.service.KafkaTestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MainController {

  private KafkaTestService kafkaTestService;


  @PostMapping("/")
  public ResponseEntity start() {
    kafkaTestService.sendMessage();
    return ResponseEntity.ok().build();
  }

}
