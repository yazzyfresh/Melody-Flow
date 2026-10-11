package com.melodyflow.controller;

import com.melodyflow.dto.SavedConcertRequest;
import com.melodyflow.entity.SavedConcert;
import com.melodyflow.service.SavedConcertService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-concerts")
public class SavedConcertController {

    private final SavedConcertService savedConcertService;

    public SavedConcertController(SavedConcertService savedConcertService) {
        this.savedConcertService = savedConcertService;
    }

    @GetMapping
    public ResponseEntity<List<SavedConcert>> getAllSavedConcerts() {
        return ResponseEntity.ok(savedConcertService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavedConcert> getSavedConcertById(@PathVariable Long id) {
        return savedConcertService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SavedConcert> saveConcert(
            @Valid @RequestBody SavedConcertRequest request) {

        return ResponseEntity.ok(
                savedConcertService.saveConcert(request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSavedConcert(@PathVariable Long id) {

        savedConcertService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}