package com.flight.application.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FlightApiResponseDto {
    private List<FlightItemApiResponseDto> data;

}