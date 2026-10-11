
package com.melodyflow.service.ticketmaster;


import tools.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class TicketmasterService {

    private final RestClient restClient;
    private final String apiKey;

    public TicketmasterService(
            @Value("${ticketmaster.api.base-url}") String baseUrl,
            @Value("${ticketmaster.api.key}") String apiKey) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        this.apiKey = apiKey;
    }

    public JsonNode searchEvents(String city) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/events.json")
                        .queryParam("apikey", apiKey)
                        .queryParam("city", city)
                        .queryParam("classificationName", "music")
                        .queryParam("sort", "date,asc")
                        .queryParam("size", 20)
                        .build())
                .retrieve()
                .body(JsonNode.class);
    }
}


