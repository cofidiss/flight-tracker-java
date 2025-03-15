package com.flight.core.service;


import com.flight.application.DTO.FlightCoordinateRangeFilterRequestDto;
import com.flight.application.DTO.FlightCustomFilterRequestDto;
import com.flight.core.domain.LiveFlightFilter;

import java.time.LocalDateTime;

public interface IFlightService {
    public void startGetLiveFlightDataTask(String sessionId);
    public void fetchHistoricalFlightData(String sessionId, LocalDateTime startDate);
    public void stopLiveFlightData(String sessionId);
    public void addOrUpdateLiveFlightFilter(FlightCustomFilterRequestDto FlightCustomFilterRequestDto);

    public void addOrUpdateFlightCoordinateRangeFilter(String princibalName, FlightCoordinateRangeFilterRequestDto flightCoordinateRangeFilterDto);
}
