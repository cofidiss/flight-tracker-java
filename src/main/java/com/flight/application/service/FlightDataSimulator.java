package com.flight.application.service;

import java.util.*;
import java.util.stream.IntStream;
import com.flight.application.DTO.FlightDataItemDto;
import com.flight.application.DTO.LiveFlightFilterDto;
import org.springframework.stereotype.Component;

@Component
public class FlightDataSimulator {
    private double latitudeMin = -90;
    private double latitudeMax = 90;
    private double longitudeMin = -180;
    private double longitudeMax = 180;
    private String[] categories = {
            "P", "C", "M", "J", "T", "H", "B", "G", "D", "V", "O", "N"
    };

    private Random random = new Random();

    public List<FlightDataItemDto> generateFlights(LiveFlightFilterDto filter, int count) {
        List<FlightDataItemDto> flights = new ArrayList<>();

        if (filter.getLatitudeMax() == null ||
                filter.getLatitudeMin() == null ||
                filter.getLongitudeMax() == null ||
                filter.getLongitudeMin() == null) {
            return flights;
        }

        IntStream.range(0, count).forEach(i -> {
            // Create flight data
            FlightDataItemDto flight = new FlightDataItemDto();
            flight.setFlightId(UUID.randomUUID().toString().substring(0, 8));
            flight.setFlightNumber("FL" + (1000 + random.nextInt(9000)));
            flight.setCallsign("CS" + (1000 + random.nextInt(9000)));

            // Set coordinates according to filter parameters
            flight.setLatitude(getRandomDouble(filter.getLatitudeMin(), filter.getLatitudeMax()));
            flight.setLongitude(getRandomDouble(filter.getLongitudeMin(), filter.getLongitudeMax()));

            // Other data
            flight.setTrack(random.nextInt(360));
            Integer altitudeMin = filter.getAltitudeMin();
            Integer altitudeMax = filter.getAltitudeMax();

            if (altitudeMin == null || altitudeMax == null) {
                // If either value is null, assign a random value (you can define a default range here)
                flight.setAltitude((int) getRandomDouble(1000, 30000));  // Example range 1000 to 30000 feet
            } else {
                // If both values are not null, assign a random value within the given range
                flight.setAltitude((int) getRandomDouble(altitudeMin, altitudeMax));
            }

            // GroundSpeed range check
            Integer groundSpeedMin = filter.getGroundSpeedMin();
            Integer groundSpeedMax = filter.getGroundSpeedMax();

            if (groundSpeedMin == null || groundSpeedMax == null) {
                // If either value is null, assign a random value (you can define a default range here)
                flight.setGroundSpeed((int) getRandomDouble(100, 900));  // Example range 100 to 900 knots
            } else {
                // If both values are not null, assign a random value within the given range
                flight.setGroundSpeed((int) getRandomDouble(groundSpeedMin, groundSpeedMax));
            }

            flight.setHexCode(Integer.toHexString(random.nextInt(0xFFFFFF)).toUpperCase());
            flight.setTimestamp(new Date().toInstant().toString());

            // Set aircraft type based on category
            String typePrefix = filter.getCategory() == null ? categories[random.nextInt(categories.length)] : filter.getCategory();
            flight.setAircraftType(typePrefix + getRandomAircraftType());

            flight.setRegistration("REG" + random.nextInt(9999));
            flight.setOriginIcao(getRandomIcaoCode());
            flight.setDestinationIcao(getRandomIcaoCode());
            flight.setEstimatedArrival(new Date().toInstant().toString());

            // Add flight to the list
            flights.add(flight);
        });

        return flights;
    }

    private double getRandomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    private String getRandomAircraftType() {
        String[] aircraftTypes = {"A320", "B737", "C172", "F16", "G550", "D800", "H145", "Piper", "B767", "E190"};
        return aircraftTypes[random.nextInt(aircraftTypes.length)];
    }

    private String getRandomIcaoCode() {
        String[] codes = {"EGLL", "KJFK", "LFPG", "OMDB", "KSFO", "LTBA", "WSSS", "KLAX", "EDDF", "KATL"};
        return codes[random.nextInt(codes.length)];
    }
}
