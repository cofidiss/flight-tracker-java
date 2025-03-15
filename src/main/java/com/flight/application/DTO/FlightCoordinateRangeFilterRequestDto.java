package com.flight.application.DTO;

import lombok.Data;

@Data
public class FlightCoordinateRangeFilterRequestDto {
    private double latitudeMin;
    private double latitudeMax;
    private double longitudeMin;
    private double longitudeMax;
}
