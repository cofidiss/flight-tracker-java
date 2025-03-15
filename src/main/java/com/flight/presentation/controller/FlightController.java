package com.flight.presentation.controller;


import com.flight.application.DTO.FlightCoordinateRangeFilterRequestDto;
import com.flight.application.DTO.FlightCustomFilterRequestDto;
import com.flight.application.service.FlightService;
import com.flight.core.domain.FlightData;
import com.flight.core.domain.LiveFlightFilter;
import com.flight.core.domain.RequiresPermission;

import com.flight.core.domain.UserRole;
import com.flight.core.service.IFlightService;
import com.flight.infrastructure.kafka.FlightDataKafkaProducer;
import com.flight.infrastructure.persistence.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/flight")
public class FlightController {
    private final SimpMessagingTemplate messagingTemplate;
    private final IFlightService flightService;
    private final FlightDataKafkaProducer flightDataKafkaProducer;
    private final IUserRepository userRepository;

    public FlightController(FlightDataKafkaProducer flightDataKafkaProducer, SimpMessagingTemplate messagingTemplate, IFlightService flightService, IUserRepository userRepository) {

        this.flightDataKafkaProducer = flightDataKafkaProducer;
        this.messagingTemplate = messagingTemplate;
        this.flightService = flightService;
        this.userRepository = userRepository;
    }

    @MessageMapping("/live")
    public void getLiveFlightData(SimpMessageHeaderAccessor headerAccessor, Principal principal, @Payload FlightCoordinateRangeFilterRequestDto flightCoordinateRangeFilterDto) {

        var principalName = principal.getName();
        messagingTemplate.convertAndSendToUser(principalName,"/topic/flight", "flightData");
        flightService.addOrUpdateFlightCoordinateRangeFilter(principalName,flightCoordinateRangeFilterDto);
        flightService.startGetLiveFlightDataTask(principalName);
    }


    @RequiresPermission(value= UserRole.PremiumUser)
    @PostMapping("/live/filter")
    public void filterLiveFlightData( @RequestBody FlightCustomFilterRequestDto filter) {

        flightService.addOrUpdateLiveFlightFilter(filter);

    }


    @MessageMapping("/getSessionId")
    public void getSessionId(SimpMessageHeaderAccessor headerAccessor, Principal principal) {

        messagingTemplate.convertAndSendToUser(principal.getName(),"/topic/getSessionId", principal.getName());

    }
    @RequiresPermission(value= UserRole.Admin)
    @GetMapping("/historical")
    public String getHistoricalFlightData() {
        return "asd";
        // Belirtilen saat kadar geçmiş veriyi Kafka'dan çekip SignalR ile gönderim
    }
}