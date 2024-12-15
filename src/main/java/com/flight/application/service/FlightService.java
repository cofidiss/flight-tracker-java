package com.flight.application.service;

import com.flight.core.domain.FlightData;
import com.flight.core.service.IFlightService;
import com.flight.infrastructure.kafka.FlightDataKafkaProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlightService implements IFlightService {

    private final FlightDataKafkaProducer kafkaProducer;

    public FlightService(FlightDataKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
    @Override
    public void getLiveFlightData(String sessionId) {
        new Thread(() -> {
            try {
                while (true) {
                    var flightData = fetchLiveFlightData();
                    kafkaProducer.sendLiveFlightData(sessionId,flightData);
                    Thread.sleep(1000);  // 1 saniyede bir veri gönder
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();


    }



    public FlightData fetchLiveFlightData() {
        FlightData flightData = new FlightData();
        flightData.setFlightId("sessionId");
        return  flightData;

    }
    public FlightData fetchHistoricalFlightData() {
        FlightData flightData = new FlightData();
        flightData.setFlightId("sessionId");
        return  flightData;

    }

    @Override
    public void fetchHistoricalFlightData(String sessionId, LocalDateTime startDate) {
        new Thread(() -> {
            try {
                while (true) {
                    var flightData = fetchHistoricalFlightData();
                    kafkaProducer.sendHistoricalFlightData(sessionId,flightData);
                    Thread.sleep(1000);  // 1 saniyede bir veri gönder
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();


    }
}