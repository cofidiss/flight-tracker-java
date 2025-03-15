package com.flight.application.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightItemApiResponseDto {

    @JsonProperty("fr24_id")
    private String fr24Id;

    @JsonProperty("flight")
    private String flight;

    @JsonProperty("callsign")
    private String callsign;

    @JsonProperty("lat")
    private double lat;

    @JsonProperty("lon")
    private double lon;

    @JsonProperty("track")
    private int track;

    @JsonProperty("alt")
    private int alt;

    @JsonProperty("gspeed")
    private int gspeed;

    @JsonProperty("vspeed")
    private int vspeed;

    @JsonProperty("squawk")
    private int squawk;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("source")
    private String source;

    @JsonProperty("hex")
    private String hex;

    @JsonProperty("type")
    private String type;

    @JsonProperty("reg")
    private String reg;

    @JsonProperty("painted_as")
    private String paintedAs;

    @JsonProperty("operating_as")
    private String operatingAs;

    @JsonProperty("orig_iata")
    private String origIata;

    @JsonProperty("orig_icao")
    private String origIcao;

    @JsonProperty("dest_iata")
    private String destIata;

    @JsonProperty("dest_icao")
    private String destIcao;

    @JsonProperty("eta")
    private String eta;

}
