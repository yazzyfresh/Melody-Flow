package com.melodyflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TicketmasterEventRequest {

    @NotBlank
    private String externalId;

    @NotBlank
    private String name;

    @NotNull
    private LocalDateTime eventDate;

    private String ticketUrl;

    @NotBlank
    private String artistName;

    private String artistExternalId;

    @NotBlank
    private String venueName;

    @NotBlank
    private String venueCity;

    private String venueState;

    private String venueAddress;

    private String venueExternalId;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistExternalId() {
        return artistExternalId;
    }

    public void setArtistExternalId(String artistExternalId) {
        this.artistExternalId = artistExternalId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    public String getVenueState() {
        return venueState;
    }

    public void setVenueState(String venueState) {
        this.venueState = venueState;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getVenueExternalId() {
        return venueExternalId;
    }

    public void setVenueExternalId(String venueExternalId) {
        this.venueExternalId = venueExternalId;
    }
}