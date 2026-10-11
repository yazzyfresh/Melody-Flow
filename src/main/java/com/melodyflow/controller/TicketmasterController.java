
package com.melodyflow.controller;

import com.melodyflow.service.ticketmaster.TicketmasterService;
import tools.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticketmaster")
public class TicketmasterController {

    private final TicketmasterService ticketmasterService;

    public TicketmasterController(TicketmasterService ticketmasterService) {
        this.ticketmasterService = ticketmasterService;
    }

    @GetMapping("/events")
    public JsonNode searchEvents(
            @RequestParam(defaultValue = "Tampa") String city) {

        return ticketmasterService.searchEvents(city);
    }
}
