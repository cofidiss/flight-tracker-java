package com.flight.infrastructure.kafka;


import com.flight.application.DTO.FlightDataDto;
import com.flight.application.DTO.FlightDataItemDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightDataKafkaProducer {

    private final KafkaTemplate<String, FlightDataDto> kafkaTemplate;
    private final KafkaTemplate<String, FlightDataItemDto> kafkaTemplate2;
    public FlightDataKafkaProducer(KafkaTemplate<String, FlightDataDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate2 = null;
    }

    public void sendLiveFlightData(String key, FlightDataDto flights) {
        if(flights == null)
            return;
        kafkaTemplate.send("live-data", flights);  // Kafka topic'ine veri gönderiliyor

    }

    public void sendHistoricalFlightData(String key, FlightDataDto flightData) {
        kafkaTemplate.send("historical-data",key, flightData);  // Kafka topic'ine veri gönderiliyor
    } }


