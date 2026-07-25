package com.flight.infrastructure.kafka;

import com.flight.application.DTO.FlightDataDto;
import com.flight.application.DTO.FlightDataItemDto;
import com.flight.application.service.FlightService;
import com.flight.application.service.FlightWebSocketService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Component
@RequiredArgsConstructor
public class FlightDataKafkaListener {

    private final FlightService flightService;

    @KafkaListener(
            topics = "live-data",
            groupId = "flight-websocket"
    )
    public void onFlight(
            FlightDataDto flights
    ) {

        flightService.flightReceieved(flights.getData());
    }
}