package app.config;

import app.dtos.WeatherDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaListenerConfiguration {

    @Value("${kafkaServerIp}")
    private String kafkaServerIp;

    @Bean
    public ConsumerFactory<String, WeatherDto> weatherConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerIp);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "weather");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(WeatherDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WeatherDto> weatherKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, WeatherDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(weatherConsumerFactory());
        return factory;
    }
}
