package com.flight.application.service;

import java.util.*;
import java.util.stream.IntStream;

import com.flight.application.DTO.FlightDataItemDto;
import org.springframework.stereotype.Component;

@Component
public class FlightDataSimulator {

    // Dünya koordinat sınırları
    private static final double LATITUDE_MIN = -90.0;
    private static final double LATITUDE_MAX = 90.0;

    private static final double LONGITUDE_MIN = -180.0;
    private static final double LONGITUDE_MAX = 180.0;

    // Uçuş verisi aralıkları
    private static final int ALTITUDE_MIN = 1000;
    private static final int ALTITUDE_MAX = 45000;

    private static final int GROUND_SPEED_MIN = 100;
    private static final int GROUND_SPEED_MAX = 900;

    private static final int TRACK_MIN = 0;
    private static final int TRACK_MAX = 360;

    // Flight number
    private static final int FLIGHT_NUMBER_MIN = 1000;
    private static final int FLIGHT_NUMBER_MAX = 9999;

    // Callsign
    private static final int CALLSIGN_MIN = 1000;
    private static final int CALLSIGN_MAX = 9999;

    // Registration
    private static final int REGISTRATION_MIN = 1000;
    private static final int REGISTRATION_MAX = 9999;

    private static final String[] CATEGORIES = {
            "P", "H"
    };

    private static final String[] AIRCRAFT_TYPES = {
            "A320",
            "B737",
            "C172",
            "F16",
            "G550",
            "D800",
            "H145",
            "Piper",
            "B767",
            "E190"
    };

    private static final String[] ICAO_CODES = {
            "EGLL",
            "KJFK",
            "LFPG",
            "OMDB",
            "KSFO",
            "LTBA",
            "WSSS",
            "KLAX",
            "EDDF",
            "KATL"
    };

    private final Random random = new Random();

    public List<FlightDataItemDto> generateFlights(int count) {

        List<FlightDataItemDto> flights = new ArrayList<>();

        IntStream.range(0, count).forEach(i -> {

            FlightDataItemDto flight = new FlightDataItemDto();

            // Kimlik bilgileri
            flight.setFlightId(
                    UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
            );

            flight.setFlightNumber(
                    "FL" + randomInt(
                            FLIGHT_NUMBER_MIN,
                            FLIGHT_NUMBER_MAX
                    )
            );

            flight.setCallsign(
                    "CS" + randomInt(
                            CALLSIGN_MIN,
                            CALLSIGN_MAX
                    )
            );

            // Dünya üzerinde rastgele koordinat
            flight.setLatitude(
                    randomDouble(
                            LATITUDE_MIN,
                            LATITUDE_MAX
                    )
            );

            flight.setLongitude(
                    randomDouble(
                            LONGITUDE_MIN,
                            LONGITUDE_MAX
                    )
            );

            // Uçuş bilgileri
            flight.setTrack(
                    randomInt(
                            TRACK_MIN,
                            TRACK_MAX - 1
                    )
            );

            flight.setAltitude(
                    randomInt(
                            ALTITUDE_MIN,
                            ALTITUDE_MAX
                    )
            );

            flight.setGroundSpeed(
                    randomInt(
                            GROUND_SPEED_MIN,
                            GROUND_SPEED_MAX
                    )
            );

            // ICAO Hex Code
            flight.setHexCode(
                    String.format(
                            "%06X",
                            random.nextInt(0x1000000)
                    )
            );

            flight.setTimestamp(
                    new Date()
                            .toInstant()
                            .toString()
            );

            // Uçak tipi
            String category =
                    CATEGORIES[
                            random.nextInt(CATEGORIES.length)
                            ];

            flight.setAircraftType(
                    category
            );

            // Registration
            flight.setRegistration(
                    "REG" + randomInt(
                            REGISTRATION_MIN,
                            REGISTRATION_MAX
                    )
            );

            // Kalkış / varış
            flight.setOriginIcao(
                    getRandomIcaoCode()
            );

            flight.setDestinationIcao(
                    getRandomIcaoCode()
            );

            flight.setEstimatedArrival(
                    new Date()
                            .toInstant()
                            .toString()
            );

            flights.add(flight);
        });

        return flights;
    }

    private double randomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    private int randomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    private String getRandomAircraftType() {
        return AIRCRAFT_TYPES[
                random.nextInt(AIRCRAFT_TYPES.length)
                ];
    }

    private String getRandomIcaoCode() {
        return ICAO_CODES[
                random.nextInt(ICAO_CODES.length)
                ];
    }
}
