package com.example.stock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.stock.dtos.VietcapResponseData;

@SpringBootApplication(scanBasePackages = "com.example.stock")
public class StockApplication implements CommandLineRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(StockApplication.class);

    private final VietcapRequestClient client;
    private final VietcapKafkaProducer producer;

    @Value("${application.stock.kafka-topic}")
    private String kafkaTopic;

    public StockApplication(
            VietcapRequestClient client,
            VietcapKafkaProducer producer) {
        this.client = client;
        this.producer = producer;
    }

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<VietcapResponseData> response = client.getStockData("HOSE");

        logger.info("Received {} stock items", response.size());

        producer.send(kafkaTopic, response);
    }
}
