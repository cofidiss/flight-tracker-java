package com.flight.infrastructure.config;

import com.flight.application.interfaces.IFlightService;
import org.springframework.context.event.EventListener;
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
