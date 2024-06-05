package com.hai.stockapp.mining;

import com.hai.stockapp.entity.NseStock;
import com.hai.stockapp.repository.NseStockRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class StockMarket {


    @Autowired
    private  RestTemplate restTemplate;

    @Autowired
    private  NseStockRepository nseStockRepository;

    public  StockMarket()
    {

    }

    public StockMarket(RestTemplate restTemplate, NseStockRepository nseStockRepository) {
        this.restTemplate = restTemplate;
        this.nseStockRepository = nseStockRepository;
    }


    public Map<String, String> getList() {
        String url = "https://indianstockexchange.p.rapidapi.com/index.php?id=%7Bscrip-id%7D";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "RGy3Jqp3JKcEJMLQ3Q4bju4Y5fqGdkxw");
        headers.set("X-RapidAPI-Host", "indianstockexchange.p.rapidapi.com");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String response = responseEntity.getBody();

        // Parse response if necessary
        return null;
    }

    public boolean getAllList() {
        String url = "https://akm-img-a-in.tosshub.com/businesstoday/resource/market-widgets/prod/company-master-23-01-2023.json";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        JSONArray array;
        try {
            array = new JSONArray(responseEntity.getBody());
        } catch (JSONException e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }

        // Save data to database using JPA
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            try {
                if (!jsonObject.getString("nsesymbol").isEmpty()) {
                    NseStock nseStock = new NseStock();
                    nseStock.setNseSymbol(jsonObject.getString("nsesymbol"));
                    nseStock.setCompanyName(jsonObject.getString("companyname"));
                    nseStockRepository.save(nseStock);
                }
            } catch (JSONException e) {
                System.out.println("Error : " + jsonObject.getString("nsesymbol"));
            }
        }
        return true;
    }

    @CircuitBreaker(name="historyAPI",fallbackMethod ="handleFallback")
    public String getAllHistory() {
        String symbol = "ACC";
        String uri = "https://www.alphavante.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + ".BSE&outputsize=full&apikey=FX7M5DS9KCQBT6ZF";
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

            // Parse and process response
            String responseBody = responseEntity.getBody();
            // Process responseBody...
            System.out.println(responseBody);
            return responseBody;
        }catch (RuntimeException e) {
            // Handle RuntimeException and trigger fallback
            System.out.println("Exception occurred: " + e.getMessage());
            throw e;
        }


    }

    public String handleFallback(RuntimeException  throwable) {
        // Fallback logic when circuit breaker is open or any other exception occurs
        System.out.println("Fallback method called due to: " + throwable.getMessage());
        return "User-friendly message or default response";// or any other fallback behavior
    }
}
