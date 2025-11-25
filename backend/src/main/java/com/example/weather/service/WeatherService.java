package com.example.weather.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.weather.model.NormalizedWeather;
import com.example.weather.model.SearchLog;
import com.example.weather.repository.SearchLogRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private final SearchLogRepository repo;

    @Value("${openweather.key}")
    private String openWeatherKey;

    private static final String VENDOR_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherService(SearchLogRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "weather", key = "#city.toLowerCase()")
    public NormalizedWeather fetchWeather(String city) throws Exception {
        UriComponentsBuilder uri = UriComponentsBuilder.fromHttpUrl(VENDOR_URL)
                .queryParam("q", city)
                .queryParam("appid", openWeatherKey)
                .queryParam("units", "metric");

        var resp = rest.getForEntity(uri.toUriString(), String.class);
        if (!resp.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("Vendor error: " + resp.getStatusCodeValue());
        }
        JsonNode vendorJson = mapper.readTree(resp.getBody());

        NormalizedWeather nw = new NormalizedWeather();
        nw.setCity(vendorJson.path("name").asText(city));
        nw.setCountry(vendorJson.path("sys").path("country").asText(null));
        nw.setCoords(mapper.convertValue(vendorJson.path("coord"), Object.class));
        nw.setWeather(mapper.convertValue(
                vendorJson.path("weather").isArray() && vendorJson.path("weather").size() > 0 ?
                        vendorJson.path("weather").get(0) : mapper.createObjectNode(),
                Object.class));
        nw.setTemperature(mapper.convertValue(vendorJson.path("main"), Object.class));
        nw.setWind(mapper.convertValue(vendorJson.path("wind"), Object.class));
        nw.setClouds(mapper.convertValue(vendorJson.path("clouds"), Object.class));
        nw.setVisibility(vendorJson.path("visibility").asInt(0));
        nw.setTimezone(vendorJson.path("timezone").asLong(0));
        if (vendorJson.path("sys").has("sunrise")) nw.setSunrise(vendorJson.path("sys").path("sunrise").asLong() * 1000);
        if (vendorJson.path("sys").has("sunset")) nw.setSunset(vendorJson.path("sys").path("sunset").asLong() * 1000);
        //nw.setVendor(mapper.convertValue(vendorJson, Object.class));
        nw.setFetchedAt(Instant.now().toString());
        nw.setCached(false);

        // log into DB (vendor JSON as string)
        try {
            SearchLog log = new SearchLog();
            log.setCity(city);
            //log.setVendorResponse(resp.getBody());
            repo.save(log);
        } catch (Exception ex) {
            // logging should never break response
            System.err.println("Failed to save search log: " + ex.getMessage());
        }

        return nw;
    }
}
