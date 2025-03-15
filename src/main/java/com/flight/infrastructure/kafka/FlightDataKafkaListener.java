package com.flight.infrastructure.kafka;

import com.flight.application.DTO.FlightDataDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class FlightDataKafkaListener {

    private final SimpMessagingTemplate messagingTemplate;

    public FlightDataKafkaListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "live-data", groupId = "session-group")
    public void listenToTopic(ConsumerRecord<String, FlightDataDto> record) {
        // Extract the key and message
        String key = record.key();
        FlightDataDto flightData = record.value();

        // Assume the key is the sessionId
        String sessionId = key != null ? key : "defaultSession";

        // Kafka'dan gelen mesajı, WebSocket üzerinden frontend'e gönderiyoruz
        messagingTemplate.convertAndSendToUser(sessionId,"/topic/liveflight", flightData);
}}
