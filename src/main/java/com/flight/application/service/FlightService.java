package com.flight.application.service;

import com.flight.application.mapper.IFlightDataMapper;
import com.flight.core.domain.FlightApiResponse;
import com.flight.core.domain.FlightItemApiResponse;
import com.flight.core.domain.LiveFlightFilter;
import com.flight.core.domain.FlightData;
import com.flight.core.service.IFlightService;
import com.flight.infrastructure.kafka.FlightDataKafkaProducer;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FlightService implements IFlightService {

    private final FlightDataKafkaProducer kafkaProducer;
    private final ConcurrentHashMap<String, LiveFlightFilter> liveFlightFilters = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate;
    private IFlightDataMapper flightDataMapper;
    private Environment environment;
    public FlightService(FlightDataKafkaProducer kafkaProducer,  IFlightDataMapper flightDataMapper,Environment environment) {
        this.kafkaProducer = kafkaProducer;
        this.restTemplate = new RestTemplate();
        this.flightDataMapper = flightDataMapper;
        this.environment = environment;
    }

    @Override
    public void getLiveFlightData(String sessionId) {
        FlightItemApiResponse a ;

        new Thread(() -> {
            try {
                while (true) {
                    if (!liveFlightFilters.containsKey(sessionId)) {
                        System.out.println("Session ID bulunamadı, işlem sonlandırılıyor.");
                        return;
                    }

                    var filter = liveFlightFilters.get(sessionId);
                    var flightData = fetchLiveFlightData(filter);

                   kafkaProducer.sendLiveFlightData(sessionId, flightData);
                    Thread.sleep(1000);  // 1 saniyede bir veri gönder
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public FlightData fetchLiveFlightData(LiveFlightFilter filter) {


        try {

            String baseUrl = environment.getProperty("flightradar24.baseUrl");
            String bounds = String.format("%f,%f,%f,%f",
                    filter.getLatitudeMin(), filter.getLatitudeMax(),
                    filter.getLongitudeMin(), filter.getLongitudeMax());

            // URL encode işlemi
            String encodedBounds = URLEncoder.encode(bounds, "UTF-8");

            // Kategoriler kısmı encode edilmesi gereken bir parametre
            String categories = (filter.getCategories() != null)
                    ? "&categories=" + String.join(",", filter.getCategories())
                    : "";

            // Kategorileri encode et
            String encodedCategories = URLEncoder.encode(categories, "UTF-8");

            // Tam URL'yi oluştur
            String apiUrl = String.format("%s?bounds=%s%s",
                    baseUrl,
                    encodedBounds,
                    encodedCategories);


            System.out.println("API'ye istek yapılıyor: " + apiUrl);
            RestTemplate restTemplate = new RestTemplate();

            // Başlıkları ayarlama
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            headers.set("Accept-Version", "v1");
            headers.set("Authorization", "Bearer 9e0d3e7c-7d92-4dc9-81b0-406cf584684b|tADUJzKoo96DLVZuWNL8KpGYGPPOkqmzvkDLATQObdb7d86f");

            // Başlıkları ekleyerek bir HttpEntity oluşturma
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // API'den veriyi alma

            FlightApiResponse response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, FlightApiResponse.class).getBody();

            if (response != null && response.getData() != null) {
                return flightDataMapper.toFlightData(response);

            }
        } catch (Exception e) {
            System.err.println("API isteği başarısız: " + e.getMessage());
        }

        return null; // Boş veri döndür
    }



    public void addOrUpdateLiveFlightFilter(String sessionId, LiveFlightFilter filter) {

            liveFlightFilters.put(sessionId, filter);
    }

    public void removeLiveFlightFilter(String sessionId) {
        liveFlightFilters.remove(sessionId);
    }

    @Override
    public void fetchHistoricalFlightData(String sessionId, LocalDateTime startDate) {
        new Thread(() -> {
            try {
                while (true) {
                    var flightData = fetchHistoricalFlightData();
                    kafkaProducer.sendHistoricalFlightData(sessionId, flightData);
                    Thread.sleep(1000);  // 1 saniyede bir veri gönder
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public FlightData fetchHistoricalFlightData() {
        FlightData flightData = new FlightData();

        return flightData;
    }
}
