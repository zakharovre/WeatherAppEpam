package weatherapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import weatherapi.dto.ForecastDto;
import weatherapi.dto.WeatherDto;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class WeatherService {

    @Value("${weatherApiUrl}")
    private String url;
    private final String TOPIC = "weather";
    private ObjectMapper objectMapper;
    private KafkaTemplate<String,WeatherDto> kafkaTemplate;
    private RestTemplate restTemplate = new RestTemplate();

    public WeatherService(ObjectMapper objectMapper, KafkaTemplate<String, WeatherDto> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public WeatherDto getWeather() {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return convert(response);
    }

    @Scheduled(fixedRate = 60000)
    private void sendWeatherToKafka(){
        kafkaTemplate.send(TOPIC, getWeather());
    }

    private WeatherDto convert(ResponseEntity<String> response) {
        List<ForecastDto> forecastDtos = new ArrayList<>();
        WeatherDto weatherDto;
        JsonNode forecastWeatherRoot;
        try {
            forecastWeatherRoot = objectMapper.readTree(response.getBody()).findPath("daily");
            for (int i = 0; i < 7; i++) {
                JsonNode node = forecastWeatherRoot.get(i);
                forecastDtos.add(new ForecastDto(LocalDateTime.ofInstant(Instant.ofEpochSecond((node.path("dt").asLong())), TimeZone.getDefault().toZoneId()),
                        node.path("temp").path("day").asDouble(),
                        node.path("temp").path("night").asDouble(),
                        node.path("pressure").asInt(),
                        node.path("humidity").asInt(),
                        node.path("wind_speed").asInt(),
                        node.path("wind_deg").asInt(),
                        node.path("weather").get(0).path("main").asText(),
                        node.path("weather").get(0).path("description").asText()));
            }
            forecastWeatherRoot = objectMapper.readTree(response.getBody()).findPath("current");
            weatherDto = new WeatherDto(forecastWeatherRoot.path("temp").asDouble(),
                    forecastWeatherRoot.path("pressure").asInt(),
                    forecastWeatherRoot.path("humidity").asInt(),
                    forecastWeatherRoot.path("wind_speed").asInt(),
                    forecastWeatherRoot.path("wind_deg").asInt(),
                    forecastWeatherRoot.path("weather").get(0).path("main").asText(),
                    forecastWeatherRoot.path("weather").get(0).path("description").asText(),
                    forecastDtos);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
        return weatherDto;
    }
}
