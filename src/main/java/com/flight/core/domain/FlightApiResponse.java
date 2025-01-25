package com.flight.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FlightApiResponse {
    private List<FlightItemApiResponse> data;

}