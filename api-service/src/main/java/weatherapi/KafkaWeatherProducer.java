package weatherapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaWeatherProducer {
    public static void main(String[] args) {
        SpringApplication.run(KafkaWeatherProducer.class, args);
    }
}
