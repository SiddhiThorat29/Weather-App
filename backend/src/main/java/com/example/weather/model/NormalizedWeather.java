package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NormalizedWeather {
    private String city;
    private String country;
    private Object coords;
    private Object weather;
    private Object temperature;
    private Integer visibility;
    private Object wind;
    private Object clouds;
    private Long timezone;
    private Long sunrise;
    private Long sunset;
    private String fetchedAt;
    private boolean cached;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Object getCoords() { return coords; }
    public void setCoords(Object coords) { this.coords = coords; }

    public Object getWeather() { return weather; }
    public void setWeather(Object weather) { this.weather = weather; }

    public Object getTemperature() { return temperature; }
    public void setTemperature(Object temperature) { this.temperature = temperature; }

    public Integer getVisibility() { return visibility; }
    public void setVisibility(Integer visibility) { this.visibility = visibility; }

    public Object getWind() { return wind; }
    public void setWind(Object wind) { this.wind = wind; }

    public Object getClouds() { return clouds; }
    public void setClouds(Object clouds) { this.clouds = clouds; }

    public Long getTimezone() { return timezone; }
    public void setTimezone(Long timezone) { this.timezone = timezone; }

    public Long getSunrise() { return sunrise; }
    public void setSunrise(Long sunrise) { this.sunrise = sunrise; }

    public Long getSunset() { return sunset; }
    public void setSunset(Long sunset) { this.sunset = sunset; }

    public String getFetchedAt() { return fetchedAt; }
    public void setFetchedAt(String fetchedAt) { this.fetchedAt = fetchedAt; }

    public boolean isCached() { return cached; }
    public void setCached(boolean cached) { this.cached = cached; }
}
