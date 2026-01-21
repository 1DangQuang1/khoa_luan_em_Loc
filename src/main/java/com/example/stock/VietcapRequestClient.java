package com.example.stock;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;



public class VietcapRequestClient {

    private static final String BASE_URL = "https://trading.vietcap.com.vn/api/price/v1/w/priceboard/tickers/price/group";

    private final HttpClient httpClient;

    public VietcapRequestClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    /**
     * Call Express API: /api/vietcap?group=HOSE
     */
    public String fetchVietcap(String group) throws Exception {

        String url = BASE_URL + "?group=" + group;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Express API error: HTTP " + response.statusCode()
            );
        }

        return response.body();
    }
}
