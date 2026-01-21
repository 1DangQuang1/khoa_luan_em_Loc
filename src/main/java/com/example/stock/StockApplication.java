package com.example.stock;

import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class StockApplication {

	public static void main(String[] args) throws Exception {
		VietcapRequestClient client = new VietcapRequestClient();
		String response = client.fetchVietcap("HOSE");
		System.out.println(response);
	}

}
