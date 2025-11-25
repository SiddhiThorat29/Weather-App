package com.example.weather.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "search_logs")
public class SearchLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @Column(name = "searched_at", insertable = false, updatable = false)
    private java.time.LocalDateTime searchedAt;

   // @Column(columnDefinition = "json")
   // private String vendorResponse;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public java.time.LocalDateTime getSearchedAt() { return searchedAt; }
    public void setSearchedAt(java.time.LocalDateTime searchedAt) { this.searchedAt = searchedAt; }

    //public String getVendorResponse() { return vendorResponse; }
   //public void setVendorResponse(String vendorResponse) { this.vendorResponse = vendorResponse; }
}
