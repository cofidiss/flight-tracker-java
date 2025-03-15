package com.flight.application.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightDataItemDto {


    private String flightId;          // "fr24Id" mapped to "flightId"
    private double latitude;          // "lat" mapped to "latitude"
    private double longitude;         // "lon" mapped to "longitude"
    private int altitude;             // "alt" mapped to "altitude"
    private int groundSpeed;          // "gspeed" mapped to "groundSpeed"
    private int track;                // "track" mapped to "track"
    private String timestamp;  // "timestamp" mapped to "timestamp"
    private String flightNumber;      // "flight" mapped to "flightNumber"
    private String callsign;          // "callsign" mapped to "callsign"
    private String hexCode;           // "hex" mapped to "hexCode"
    private String aircraftType;      // "type" mapped to "aircraftType"
    private String registration;      // "reg" mapped to "registration"
    private String originIcao;        // "origIcao" mapped to "originIcao"
    private String destinationIcao;   // "destIata" mapped to "destinationIcao"
    private String estimatedArrival;
}
