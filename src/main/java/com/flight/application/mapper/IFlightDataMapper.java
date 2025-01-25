package com.flight.application.mapper;

import com.flight.application.util.CustomUtil;
import com.flight.core.domain.FlightApiResponse;
import com.flight.core.domain.FlightData;
import com.flight.core.domain.FlightDataItem;
import com.flight.core.domain.FlightItemApiResponse;
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
    FlightDataItem toFlightDataItem(FlightItemApiResponse flightItem);
    // FlightItemApiResponse listesini FlightData listesine dönüştürme
    FlightData toFlightData(FlightApiResponse flightItems);
}
