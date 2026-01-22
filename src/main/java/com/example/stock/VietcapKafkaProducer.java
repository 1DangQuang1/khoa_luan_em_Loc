package com.example.stock;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.stock.dtos.VietcapResponseData;

@Service
public class VietcapKafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();
    
    public VietcapKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, List<VietcapResponseData> stocks) {
        stocks.forEach(stock -> {
            try {
                String key = stock.getS();
                String value = mapper.writeValueAsString(stock);

                kafkaTemplate.send(topic, key, value);

            } catch (Exception e) {
                throw new RuntimeException("Failed to send stock " + stock.getS(), e);
            }
        });
    }
}
