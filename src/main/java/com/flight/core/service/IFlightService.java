package com.flight.core.service;

import com.flight.application.DTO.FlightCoordinateRangeFilterRequestDto;
import com.flight.application.DTO.FlightCustomFilterRequestDto;

public interface IFlightService {
    public void startGetLiveFlightDataTask(String sessionId);
    public void stopLiveFlightData(String sessionId);
    public void addOrUpdateLiveFlightFilter(FlightCustomFilterRequestDto FlightCustomFilterRequestDto);
    public void addOrUpdateFlightCoordinateRangeFilter(String pirincipalName, FlightCoordinateRangeFilterRequestDto flightCoordinateRangeFilterDto);
}
