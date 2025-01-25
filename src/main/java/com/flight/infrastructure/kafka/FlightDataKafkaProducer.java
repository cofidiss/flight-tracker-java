package com.flight.infrastructure.kafka;



import com.flight.core.domain.FlightData;
import com.flight.core.domain.FlightDataItem;
import io.micrometer.observation.Observation;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlightDataKafkaProducer {

    private final KafkaTemplate<String, FlightData> kafkaTemplate;
    private final KafkaTemplate<String, FlightDataItem> kafkaTemplate2;
    public FlightDataKafkaProducer(KafkaTemplate<String, FlightData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate2 = null;
    }

    public void sendLiveFlightData(String key, FlightData flightData) {

        double randomValue = 1.05 + (1.1 - 1.05) * Math.random();
        flightData.getData().forEach(a -> a.setLongitude(a.getLongitude() * randomValue));

        kafkaTemplate.send("live-data",key, flightData);  // Kafka topic'ine veri gönderiliyor

    }

    public void sendHistoricalFlightData(String key, FlightData flightData) {
        kafkaTemplate.send("historical-data",key, flightData);  // Kafka topic'ine veri gönderiliyor
    } }


