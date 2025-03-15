package com.flight.application.DTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FlightDataDto {
List<FlightDataItemDto> data;
public FlightDataDto (){

    data = new ArrayList<FlightDataItemDto>() ;

}
}
