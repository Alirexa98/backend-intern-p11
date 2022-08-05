package com.example.backendinternp11.service;

import com.example.backendinternp11.entity.SimpleEntity;
import com.example.backendinternp11.repository.SimpleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;

@Service
@EnableKafka
@AllArgsConstructor
public class KafkaTestService {

  private SimpleRepository simpleRepository;
  private KafkaTemplate<String, String> kafkaTemplate;
  private Executor executor;

  private ObjectMapper mapper;

  public void sendMessage() {
    executor.execute(
        () -> {
          try (var linesStream = Files.lines(Paths.get("src/main/data/data.tsv"))) {
            linesStream.skip(1).forEach(
                (line) -> {
                  var fields = line.split("\t");
                  try {
                    kafkaTemplate.send(
                        "backend_kafka",
                      mapper.writeValueAsString(
                          new SimpleEntity(
                              fields[0],
                              fields[1],
                              fields[2],
                              fields[3],
                              fields[4]
                          )
                      )
                    );
                  } catch (JsonProcessingException e) {
                    System.out.println(e);
                  }
                }
            );
            System.out.println("Producer Finished");
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
    );
  }

  @KafkaListener(topics = "backend_kafka", groupId = "test_group")
  public void listener1(String message) {
    insertEntity(message);
  }

  @KafkaListener(topics = "backend_kafka", groupId = "test_group")
  public void listener2(String message) {
    insertEntity(message);
  }

  private void insertEntity(String jsonEntity) {
    try {
      SimpleEntity entity = mapper.readValue(jsonEntity, SimpleEntity.class);
      simpleRepository.save(entity);
    } catch (JsonProcessingException e) {
      System.out.println(e);
    }
  }

}
