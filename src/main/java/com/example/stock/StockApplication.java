package com.example.stock;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.stock.dtos.VietcapResponseData;

@SpringBootApplication(scanBasePackages = "com.example.stock")
public class StockApplication implements CommandLineRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(StockApplication.class);

    private final VietcapRequestClient client;
    private final VietcapKafkaProducer producer;

    @Value("${application.stock.kafka_topic}")
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
    public void run(String... args) {
        logger.info("Stock polling started...");

        while (true) {
            try {
                List<VietcapResponseData> response =
                        client.getStockData("HOSE");

                logger.info("Received {} stock items", response.size());

                producer.send(kafkaTopic, response);

                // sleep 2 seconds
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Polling interrupted, shutting down...");
                break;

            } catch (Exception e) {
                logger.error("Error while fetching or pushing stock data", e);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
