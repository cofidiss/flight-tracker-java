package com.flight.core.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LiveFlightFilter {
    // Getters and Setters
    private double latitudeMin;
    private double latitudeMax;
    private double longitudeMin;
    private double longitudeMax;
    private String[] categories;
}
