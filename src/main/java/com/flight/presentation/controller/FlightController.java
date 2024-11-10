package com.flight.presentation.controller;

import com.flight.core.domain.FlightData;
import com.flight.infrastructure.kafka.FlightDataKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightDataKafkaProducer flightDataKafkaProducer;

    public FlightController(FlightDataKafkaProducer flightDataKafkaProducer) {

        this.flightDataKafkaProducer = flightDataKafkaProducer;
    }

    @GetMapping("/live")
    public void getLiveFlightData() {
        flightDataKafkaProducer.sendLiveFlightData(new FlightData());
    }

    @GetMapping("/historical")
    public String getHistoricalFlightData() {
        return "asd";
        // Belirtilen saat kadar geçmiş veriyi Kafka'dan çekip SignalR ile gönderim
    }
}