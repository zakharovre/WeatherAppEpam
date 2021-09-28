package weatherapi.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import weatherapi.dto.WeatherDto;
import weatherapi.service.WeatherService;

@RestController
public class WeatherController {
    private final KafkaTemplate<String, WeatherDto> kafkaTemplate;
    private final WeatherService weatherService;
    private final String TOPIC = "weather";

    public WeatherController(KafkaTemplate<String, WeatherDto> kafkaTemplate, WeatherService weatherService) {
        this.kafkaTemplate = kafkaTemplate;
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherDto getWeather(){
        WeatherDto weatherDto = weatherService.getWeather();
        kafkaTemplate.send(TOPIC, weatherDto);
        return weatherDto;
    }
}
