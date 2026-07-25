package com.flight.infrastructure.mapper;

import com.flight.application.DTO.FlightApiResponseDto;
import com.flight.application.DTO.FlightDataDto;
import com.flight.application.DTO.FlightDataItemDto;
import com.flight.application.DTO.FlightItemApiResponseDto;
import com.flight.application.util.CustomUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


                @Mapper(componentModel = "spring",uses = CustomUtil.class)
                public interface IFlightDataMapper {

                    @Mapping(target = "flightId", source = "fr24Id")
                    @Mapping(target = "latitude", source = "lat")
                    @Mapping(target = "longitude", source = "lon")
                    @Mapping(target = "altitude", source = "alt")
                    @Mapping(target = "groundSpeed", source = "gspeed")
                    @Mapping(target = "track", source = "track")
                    @Mapping(target = "timestamp", source = "timestamp")
                    @Mapping(target = "flightNumber", source = "flight")
                    @Mapping(target = "callsign", source = "callsign")
                    @Mapping(target = "hexCode", source = "hex")
                    @Mapping(target = "aircraftType", source = "type")
                    @Mapping(target = "registration", source = "reg")
                    @Mapping(target = "originIcao", source = "origIcao")
                    @Mapping(target = "destinationIcao", source = "destIata")
                    @Mapping(target = "estimatedArrival", source = "eta")
                    FlightDataItemDto toFlightDataItem(FlightItemApiResponseDto flightItem);
                    // FlightItemApiResponse listesini FlightData listesine dönüştürme
                    FlightDataDto toFlightData(FlightApiResponseDto flightItems);
                }
