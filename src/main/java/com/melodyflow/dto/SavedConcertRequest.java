package com.melodyflow.dto;

import jakarta.validation.constraints.NotNull;

public class SavedConcertRequest {

    @NotNull(message = "Concert ID is required")
    private Long concertId;

    public SavedConcertRequest() {
    }

    public Long getConcertId() {
        return concertId;
    }

    public void setConcertId(Long concertId) {
        this.concertId = concertId;
    }
}