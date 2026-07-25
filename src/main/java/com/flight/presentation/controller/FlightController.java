package com.flight.presentation.controller;

import com.flight.application.DTO.FlightCoordinateRangeFilterRequestDto;
import com.flight.application.DTO.FlightCustomFilterRequestDto;
import com.flight.application.interfaces.IFlightService;
import com.flight.core.domain.RequiresPermission;
import com.flight.core.domain.UserRole;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    private final SimpMessagingTemplate messagingTemplate;
    private final IFlightService flightService;

    public FlightController(SimpMessagingTemplate messagingTemplate, IFlightService flightService) {
        this.messagingTemplate = messagingTemplate;
        this.flightService = flightService;
    }

    @MessageMapping("/live")
    public void getLiveFlightData(Principal principal, @Payload FlightCoordinateRangeFilterRequestDto flightCoordinateRangeFilterDto) {
        var principalName = principal.getName();
        flightService.addOrUpdateFlightCoordinateRangeFilter(principalName, flightCoordinateRangeFilterDto);
    }

    @RequiresPermission(value = UserRole.PremiumUser)
    @PostMapping("/live/filter")
    public void filterLiveFlightData(@RequestBody FlightCustomFilterRequestDto filter) {
        flightService.addOrUpdateLiveFlightFilter(filter);
    }

    @MessageMapping("/getSessionId")
    public void getSessionId(SimpMessageHeaderAccessor headerAccessor, Principal principal) {
        messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/getSessionId", principal.getName());
    }

}
