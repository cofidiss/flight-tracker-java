package com.flight.core.domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FlightData {
List<FlightDataItem> data;
public FlightData (){

    data = new ArrayList<FlightDataItem>() ;

}
}
