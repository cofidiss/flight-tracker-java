package com.flight.application.service;


import com.flight.application.DTO.FlightCoordinateRangeFilterRequestDto;
import com.flight.application.DTO.FlightCustomFilterRequestDto;
import com.flight.application.mapper.IFlightDataMapper;
import com.flight.core.domain.FlightApiResponse;

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

import java.util.Map;
import java.util.concurrent.*;

@Service
public class FlightService implements IFlightService {

    private final FlightDataKafkaProducer kafkaProducer;
    private final ConcurrentHashMap<String, LiveFlightFilter> liveFlightFilters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Thread> createdFetchLiveThreads = new ConcurrentHashMap<>();
    private final RestTemplate restTemplate;
    private IFlightDataMapper flightDataMapper;
    private Environment environment;
    private final Map<String, ScheduledFuture<?>> sessionTasks = new ConcurrentHashMap<> ();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    FlightDataSimulator flightDataSimulator;
    public FlightService(FlightDataKafkaProducer kafkaProducer,  IFlightDataMapper flightDataMapper,Environment environment, RestTemplate restTemplate,FlightDataSimulator flightDataSimulator) {
        this.kafkaProducer = kafkaProducer;
        this.restTemplate = restTemplate;
        this.flightDataMapper = flightDataMapper;
        this.environment = environment;
        this.flightDataSimulator = flightDataSimulator;
    }

    @Override
    public void startGetLiveFlightDataTask(String principalName) {

        if(sessionTasks.containsKey(principalName)){
        return;
        }

        Runnable task =   () -> {
try {

                    if (!liveFlightFilters.containsKey(principalName)) {
                        System.out.println("Session ID bulunamadı, işlem sonlandırılıyor.");
                        return;
                    }

                    var filter = liveFlightFilters.get(principalName);
                    // var flightData = fetchLiveFlightData(filter);
    var flightData = new FlightData();

    var flights = flightDataSimulator.generateFlights(filter,50);
    flightData.setData(flights);

                   kafkaProducer.sendLiveFlightData(principalName, flightData);
}
catch (Exception ex){
    ex.printStackTrace();

            }
        };
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
        sessionTasks.put(principalName, future);
    }
    public void stopLiveFlightData(String principalName) {
        ScheduledFuture<?> future = sessionTasks.remove(principalName);
        if (future != null) {
            future.cancel(true);
            System.out.println("principalName " + principalName + " için veri yayını durduruldu.");
        }
    }
    public FlightData fetchLiveFlightData(LiveFlightFilter filter) {


        try {

            if (filter.getLatitudeMax() == null ||
                    filter.getLatitudeMin() == null ||
                    filter.getLongitudeMax() == null ||
                    filter.getLongitudeMin() == null){
                return   new FlightData() ;
            }

      var token =             environment.getProperty("flightradar24.token");
            String baseUrl = environment.getProperty("flightradar24.baseUrl");
            String bounds = String.format("%f,%f,%f,%f",
                filter.getLatitudeMax(),     filter.getLatitudeMin(),
                    filter.getLongitudeMin(), filter.getLongitudeMax());

            // URL encode işlemi
            String encodedBounds = URLEncoder.encode(bounds, "UTF-8");

            // Kategoriler kısmı encode edilmesi gereken bir parametre
            String categories = (filter.getCategory() != null)
                    ?  String.join(",", new String[]{ filter.getCategory()})
                    : "";

            // Kategorileri encode et
            String encodedCategories = URLEncoder.encode(categories, "UTF-8");

            // Tam URL'yi oluştur
            String apiUrl = String.format("%s?bounds=%s&categories=%s",
                    baseUrl,
                    encodedBounds,
                    encodedCategories);


            System.out.println("API'ye istek yapılıyor: " + apiUrl);
            RestTemplate restTemplate = new RestTemplate();

            // Başlıkları ayarlama
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            headers.set("Accept-Version", "v1");
            headers.set("Authorization", "Bearer " + token);
            headers.set("accept-version", "v1");

            // Başlıkları ekleyerek bir HttpEntity oluşturma
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // API'den veriyi alma
            var response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, FlightApiResponse.class);
            FlightApiResponse flightApiResponse = response.getBody();

            if (flightApiResponse != null && flightApiResponse.getData() != null) {
                return flightDataMapper.toFlightData(flightApiResponse);

            }
        } catch (Exception e) {
            System.err.println("API isteği başarısız: " + e.getMessage());
        }

        return null; // Boş veri döndür
    }



    public void addOrUpdateLiveFlightFilter(FlightCustomFilterRequestDto filter) {
        if (filter == null)
            return;
        var principal = filter.getPrincipalName();
        if(liveFlightFilters.containsKey(principal) ){
            var presentFilter = liveFlightFilters.get(principal);
            presentFilter.setCategory(filter.getCategory());
            presentFilter.setAltitudeMax(filter.getAltitudeMax());
            presentFilter.setAltitudeMin(filter.getAltitudeMin());
            presentFilter.setGroundSpeedMax(filter.getGroundSpeedMax());
            presentFilter.setGroundSpeedMin(filter.getGroundSpeedMin());
            return;
        }
        var newFilter = new LiveFlightFilter();
        newFilter.setCategory(filter.getCategory());
        newFilter.setAltitudeMax(filter.getAltitudeMax());
        newFilter.setAltitudeMin(filter.getAltitudeMin());
        newFilter.setGroundSpeedMax(filter.getGroundSpeedMax());
        newFilter.setGroundSpeedMin(filter.getGroundSpeedMin());
        liveFlightFilters.put(principal, newFilter);

    }

    @Override
    public void addOrUpdateFlightCoordinateRangeFilter(String principal, FlightCoordinateRangeFilterRequestDto flightCoordinateRangeFilterDto) {
        if (flightCoordinateRangeFilterDto == null)
            return;
        if(liveFlightFilters.containsKey(principal) ){
            var presentFilter = liveFlightFilters.get(principal);
            presentFilter.setLongitudeMax(flightCoordinateRangeFilterDto.getLongitudeMax());
            presentFilter.setLongitudeMin(flightCoordinateRangeFilterDto.getLongitudeMin());
            presentFilter.setLatitudeMax(flightCoordinateRangeFilterDto.getLatitudeMax());
            presentFilter.setLatitudeMin(flightCoordinateRangeFilterDto.getLatitudeMin());
            return;
        }
        var newFilter = new LiveFlightFilter();
        newFilter.setLongitudeMax(flightCoordinateRangeFilterDto.getLongitudeMax());
        newFilter.setLongitudeMin(flightCoordinateRangeFilterDto.getLongitudeMin());
        newFilter.setLatitudeMax(flightCoordinateRangeFilterDto.getLatitudeMax());
        newFilter.setLatitudeMin(flightCoordinateRangeFilterDto.getLatitudeMin());
        liveFlightFilters.put(principal, newFilter);
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
