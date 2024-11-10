package com.flight.infrastructure.kafka;



import com.flight.core.domain.FlightData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlightDataKafkaProducer {

    private final KafkaTemplate<String, FlightData> kafkaTemplate;

    public FlightDataKafkaProducer(KafkaTemplate<String, FlightData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLiveFlightData(FlightData flightData) {
        var a = new FlightData();
        kafkaTemplate.send("live-flight-data", a);
    }

    public void sendHistoricalFlightData(FlightData flightData) {
        kafkaTemplate.send("historical-flight-data", flightData);
    }
}
