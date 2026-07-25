package com.flight.application.interfaces;

import com.flight.application.DTO.FlightCoordinateRangeFilterRequestDto;
import com.flight.application.DTO.FlightCustomFilterRequestDto;
import com.flight.application.DTO.FlightDataItemDto;

import java.util.List;

public interface IFlightService {
    public void startGetLiveFlightDataTask(String sessionId);
    public void stopLiveFlightData(String sessionId);
    public void addOrUpdateLiveFlightFilter(FlightCustomFilterRequestDto FlightCustomFilterRequestDto);
    public void addOrUpdateFlightCoordinateRangeFilter(String pirincipalName, FlightCoordinateRangeFilterRequestDto flightCoordinateRangeFilterDto);

    public void flightReceieved(List<FlightDataItemDto> flights);
}
