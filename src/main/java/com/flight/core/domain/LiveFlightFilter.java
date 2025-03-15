package com.flight.core.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LiveFlightFilter {
    // Getters and Setters
    private String sessionId;
    private Double latitudeMin;
    private Double latitudeMax;
    private Double longitudeMin;
    private Double longitudeMax;
    private String category;
    private Integer  altitudeMin;
    private Integer  altitudeMax;
    private Integer  groundSpeedMin;
    private Integer  groundSpeedMax;
}
