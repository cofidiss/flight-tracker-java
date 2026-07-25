package com.flight.application.service;

import com.flight.application.DTO.FlightDataDto;
import com.flight.application.DTO.FlightDataItemDto;
import com.flight.application.DTO.LiveFlightFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class FlightWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;



    public void broadcastFlight(
            List<FlightDataItemDto> flights,ConcurrentHashMap<String, LiveFlightFilterDto> userFilters
    ) {

        userFilters.entrySet()
                .parallelStream()
                .forEach(entry -> {

                    String userId = entry.getKey();
                    LiveFlightFilterDto filter = entry.getValue();

                    // Kullanıcının filtresine uyan uçuşları bul
                    List<FlightDataItemDto> filteredFlights =
                            flights.parallelStream()
                                    .filter(flight ->
                                            matchesFilter(
                                                    flight,
                                                    filter
                                            )
                                    )
                                    .toList();

                    // Kullanıcıya sadece sonuç varsa gönder
                    if (!filteredFlights.isEmpty()) {
                        var flightData = new FlightDataDto();
                        flightData.setData(filteredFlights);
                        messagingTemplate.convertAndSendToUser(
                                userId,
                                "/topic/liveflight",
                                flightData
                        );
                    }
                });
    }

    private boolean matchesFilter(
            FlightDataItemDto flight,
            LiveFlightFilterDto filter
    ) {

        if (filter.getLatitudeMin() != null &&
                flight.getLatitude() < filter.getLatitudeMin()) {
            return false;
        }

        if (filter.getLatitudeMax() != null &&
                flight.getLatitude() > filter.getLatitudeMax()) {
            return false;
        }

        if (filter.getLongitudeMin() != null &&
                flight.getLongitude() < filter.getLongitudeMin()) {
            return false;
        }

        if (filter.getLongitudeMax() != null &&
                flight.getLongitude() > filter.getLongitudeMax()) {
            return false;
        }

        if (filter.getAltitudeMin() != null &&
                flight.getAltitude() < filter.getAltitudeMin()) {
            return false;
        }

        if (filter.getAltitudeMax() != null &&
                flight.getAltitude() > filter.getAltitudeMax()) {
            return false;
        }

        if (filter.getGroundSpeedMin() != null &&
                flight.getGroundSpeed() < filter.getGroundSpeedMin()) {
            return false;
        }

        if (filter.getGroundSpeedMax() != null &&
                flight.getGroundSpeed() > filter.getGroundSpeedMax()) {
            return false;
        }

        if (filter.getCategory() != null &&
                !flight.getAircraftType().equals(filter.getCategory())) {
            return false;
        }
        return true;
    }

}
