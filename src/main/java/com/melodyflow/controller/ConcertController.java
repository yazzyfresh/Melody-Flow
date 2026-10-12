
package com.melodyflow.controller;

import com.melodyflow.dto.TicketmasterEventRequest;
import com.melodyflow.entity.Concert;
import com.melodyflow.service.ConcertService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final ConcertService concertService;

    public ConcertController(ConcertService concertService) {
        this.concertService = concertService;
    }

    @GetMapping
    public ResponseEntity<List<Concert>> getAllConcerts() {
        return ResponseEntity.ok(concertService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Long id) {
        return concertService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/from-ticketmaster")
    public ResponseEntity<Concert> saveTicketmasterEvent(
            @Valid @RequestBody TicketmasterEventRequest request
    ) {
        Concert concert = concertService.saveTicketmasterEvent(request);
        return ResponseEntity.ok(concert);
    }
}

