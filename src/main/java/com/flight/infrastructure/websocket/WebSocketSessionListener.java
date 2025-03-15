package com.flight.infrastructure.websocket;


import com.flight.application.service.FlightService;
import com.flight.core.service.IFlightService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketSessionListener {

    private final IFlightService flightService;

    public WebSocketSessionListener(IFlightService flightService) {
        this.flightService = flightService;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        System.out.println("WebSocket bağlantısı kapandı. Session ID: " + sessionId);

        // Session için veri çekmeyi durdur
        flightService.stopLiveFlightData(sessionId);
    }
}
