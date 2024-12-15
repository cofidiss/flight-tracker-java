package com.flight.core.service;

import java.time.LocalDateTime;

public interface IFlightService {
    public void getLiveFlightData(String sessionId);
    public void fetchHistoricalFlightData(String sessionId, LocalDateTime startDate);
}
