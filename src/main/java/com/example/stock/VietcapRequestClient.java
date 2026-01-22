package com.example.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.example.stock.dtos.VietcapResponseData;


@Service
public class VietcapRequestClient {

    private static final Logger logger = LoggerFactory.getLogger(VietcapRequestClient.class);

    private static final String BASE_URL = "https://trading.vietcap.com.vn/api/price/v1/w/priceboard/tickers/price/group";    
    private final HttpClient httpClient;

    public List<VietcapResponseData> stockData = new ArrayList<>();


    public VietcapRequestClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    public List<VietcapResponseData> getStockData(String group) throws Exception {
        String fullUrl = BASE_URL + "?group=" + group;

        logger.info("Group name: {}", group);

        String body = "{\"group\":\"HOSE\"}";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(fullUrl))
            .timeout(Duration.ofSeconds(10))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("User-Agent", "Mozilla/5.0")
            .header("Origin", "https://trading.vietcap.com.vn")
            .header("Referer", "https://trading.vietcap.com.vn/")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                "Proxy Server Error: HTTP " + response.statusCode() + " - " + response.body()
            );
        }

        String jsonResponse = response.body();

        // logger.info("JSON Response: {}", jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        List<VietcapResponseData> vietcapResponse =
                objectMapper.readValue(jsonResponse, new TypeReference<List<VietcapResponseData>>() {});

        stockData.clear();
        stockData.addAll(vietcapResponse);

        return stockData;
    }
}