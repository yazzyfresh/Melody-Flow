package com.melodyflow.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ConcertPlanRequest {

    @NotNull(message = "Saved concert ID is required")
    private Long savedConcertId;

    @NotNull(message = "Budget is required")
    @DecimalMin(value = "0.00", message = "Budget cannot be negative")
    private BigDecimal budget;

    public ConcertPlanRequest() {
    }

    public Long getSavedConcertId() {
        return savedConcertId;
    }

    public void setSavedConcertId(Long savedConcertId) {
        this.savedConcertId = savedConcertId;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}