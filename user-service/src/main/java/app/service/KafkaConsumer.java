package app.service;

import app.dtos.WeatherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private WeatherService weatherService;

    public KafkaConsumer(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @KafkaListener(topics = "weather", groupId = "weather",
            containerFactory = "weatherKafkaListenerFactory")
    public void consumeJson(WeatherDto weatherDto) {
        logger.info(weatherDto.toString());
        weatherService.sendAllUsers(weatherDto);
    }
}
