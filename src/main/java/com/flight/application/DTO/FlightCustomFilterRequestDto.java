package com.flight.application.DTO;

import lombok.Data;

@Data
public class FlightCustomFilterRequestDto {
    private String principalName;
    private String category;
    private int altitudeMin;
    private int altitudeMax;
    private int groundSpeedMin;
    private int groundSpeedMax;
}
