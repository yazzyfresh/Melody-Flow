
package com.melodyflow.service;

import com.melodyflow.dto.TicketmasterEventRequest;
import com.melodyflow.entity.Artist;
import com.melodyflow.entity.Concert;
import com.melodyflow.entity.Venue;
import com.melodyflow.repository.ArtistRepository;
import com.melodyflow.repository.ConcertRepository;
import com.melodyflow.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertService {

    private final ConcertRepository concertRepository;
    private final ArtistRepository artistRepository;
    private final VenueRepository venueRepository;

    public ConcertService(
            ConcertRepository concertRepository,
            ArtistRepository artistRepository,
            VenueRepository venueRepository
    ) {
        this.concertRepository = concertRepository;
        this.artistRepository = artistRepository;
        this.venueRepository = venueRepository;
    }

    public List<Concert> findAll() {
        return concertRepository.findAll();
    }

    public Optional<Concert> findById(Long id) {
        return concertRepository.findById(id);
    }

    public Optional<Concert> findByExternalId(String externalId) {
        return concertRepository.findByExternalId(externalId);
    }

    public Concert save(Concert concert) {
        return concertRepository.save(concert);
    }

    public void deleteById(Long id) {
        concertRepository.deleteById(id);
    }

    public Concert saveTicketmasterEvent(TicketmasterEventRequest request) {
        // Check whether this concert already exists.
        Optional<Concert> existingConcert =
                concertRepository.findByExternalId(request.getExternalId());

        if (existingConcert.isPresent()) {
            return existingConcert.get();
        }

        // Find or create the artist.
        Artist artist = artistRepository
                .findByExternalId(request.getArtistExternalId())
                .orElseGet(() -> {
                    Artist newArtist = new Artist(
                            request.getArtistName(),
                            null,
                            request.getArtistExternalId()
                    );
                    return artistRepository.save(newArtist);
                });

        // Find or create the venue.
        Venue venue = venueRepository
                .findByExternalId(request.getVenueExternalId())
                .orElseGet(() -> {
                    Venue newVenue = new Venue(
                            request.getVenueName(),
                            request.getVenueCity(),
                            request.getVenueState(),
                            request.getVenueAddress(),
                            request.getVenueExternalId()
                    );
                    return venueRepository.save(newVenue);
                });

        // Create the concert and connect its artist and venue.
        Concert concert = new Concert();
        concert.setExternalId(request.getExternalId());
        concert.setName(request.getName());
        concert.setEventDate(request.getEventDate());
        concert.setTicketUrl(request.getTicketUrl());
        concert.setArtist(artist);
        concert.setVenue(venue);

        return concertRepository.save(concert);
    }
}
