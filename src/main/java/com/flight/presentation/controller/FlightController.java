package com.flight.presentation.controller;

import com.flight.application.service.FlightService;
import com.flight.core.domain.FlightData;
import com.flight.infrastructure.kafka.FlightDataKafkaProducer;
import com.flight.infrastructure.persistence.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller

public class FlightController {
    private final SimpMessagingTemplate messagingTemplate;
    private final FlightService flightService;
    private final FlightDataKafkaProducer flightDataKafkaProducer;
    private final IUserRepository userRepository;

    public FlightController(FlightDataKafkaProducer flightDataKafkaProducer, SimpMessagingTemplate messagingTemplate, FlightService flightService, IUserRepository userRepository) {

        this.flightDataKafkaProducer = flightDataKafkaProducer;
        this.messagingTemplate = messagingTemplate;
        this.flightService = flightService;
        this.userRepository = userRepository;
    }

    @MessageMapping("/live")
    public void getLiveFlightData(@Payload String sessionId) {
        var a = userRepository.findAll ();
        flightService.getLiveFlightData(sessionId);
    }

    @GetMapping("/historical")
    public String getHistoricalFlightData() {
        return "asd";
        // Belirtilen saat kadar geçmiş veriyi Kafka'dan çekip SignalR ile gönderim
    }
}