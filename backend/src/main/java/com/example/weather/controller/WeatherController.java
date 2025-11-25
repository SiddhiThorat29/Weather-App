package com.example.weather.controller;

import com.example.weather.model.NormalizedWeather;
import com.example.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherService svc;

    public WeatherController(WeatherService svc) {
        this.svc = svc;
    }

    @GetMapping("/weather")
    public ResponseEntity<?> getWeather(@RequestParam(name = "city") String city) {
        if (city == null || city.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", "Missing city query parameter"));
        }
        try {
            NormalizedWeather data = svc.fetchWeather(city.trim());
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            String msg = e.getMessage();
            return ResponseEntity.status(502).body(java.util.Map.of("error", "Failed to fetch weather", "details", msg));
        }
    }
}
