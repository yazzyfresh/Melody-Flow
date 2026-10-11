
package com.melodyflow.controller;

import com.melodyflow.dto.ConcertPlanRequest;
import com.melodyflow.entity.ConcertPlan;
import com.melodyflow.entity.SavedConcert;
import com.melodyflow.repository.ConcertPlanRepository;
import com.melodyflow.repository.SavedConcertRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/concert-plans")
@CrossOrigin(origins = "http://localhost:5173")
public class ConcertPlanController {

    private final ConcertPlanRepository concertPlanRepository;
    private final SavedConcertRepository savedConcertRepository;

    public ConcertPlanController(
            ConcertPlanRepository concertPlanRepository,
            SavedConcertRepository savedConcertRepository) {
        this.concertPlanRepository = concertPlanRepository;
        this.savedConcertRepository = savedConcertRepository;
    }

    @GetMapping
    public List<ConcertPlanResponse> getAllPlans() {
        return concertPlanRepository.findAll()
                .stream()
                .map(ConcertPlanResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ConcertPlanResponse getPlanById(@PathVariable Long id) {
        ConcertPlan plan = concertPlanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Concert plan not found"));

        return ConcertPlanResponse.from(plan);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConcertPlanResponse createPlan(
            @Valid @RequestBody ConcertPlanRequest request) {

        SavedConcert savedConcert =
                savedConcertRepository.findById(request.getSavedConcertId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Saved concert not found"));

        if (concertPlanRepository.findBySavedConcertId(savedConcert.getId())
                .isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "A plan already exists for this saved concert");
        }

        ConcertPlan plan = new ConcertPlan(savedConcert, request.getBudget());
        ConcertPlan savedPlan = concertPlanRepository.save(plan);

        return ConcertPlanResponse.from(savedPlan);
    }

    @PutMapping("/{id}")
    public ConcertPlanResponse updatePlanBudget(
            @PathVariable Long id,
            @Valid @RequestBody ConcertPlanRequest request) {

        ConcertPlan plan = concertPlanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Concert plan not found"));

        plan.setBudget(request.getBudget());
        plan.setUpdatedAt(LocalDateTime.now());

        ConcertPlan updatedPlan = concertPlanRepository.save(plan);

        return ConcertPlanResponse.from(updatedPlan);
    }

    public record ConcertPlanResponse(
            Long id,
            Long savedConcertId,
            BigDecimal budget,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {

        public static ConcertPlanResponse from(ConcertPlan plan) {
            return new ConcertPlanResponse(
                    plan.getId(),
                    plan.getSavedConcert().getId(),
                    plan.getBudget(),
                    plan.getCreatedAt(),
                    plan.getUpdatedAt()
            );
        }
    }
}
