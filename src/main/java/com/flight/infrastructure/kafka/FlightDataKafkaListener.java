package com.flight.infrastructure.kafka;


import com.flight.core.domain.FlightData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FlightDataKafkaListener {


    @KafkaListener(topics = "live-flight-data", groupId = "flightGroup")
    public void listenLiveFlightData(ConsumerRecord<String, FlightData> record) {
        FlightData flightData = record.value();
        System.out.println("Received flight data: " + flightData);

        // Canlı veri için SignalR/SseEmitter ile gönderim
    }

    @KafkaListener(topics = "historical-flight-data", groupId = "flightGroup")
    public void listenHistoricalFlightData(FlightData flightData) {
        // Geçmiş veri için SignalR/SseEmitter ile gönderim
    }
}
