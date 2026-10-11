package com.melodyflow.service;

import com.melodyflow.dto.SavedConcertRequest;
import com.melodyflow.entity.Concert;
import com.melodyflow.entity.SavedConcert;
import com.melodyflow.entity.User;
import com.melodyflow.repository.ConcertRepository;
import com.melodyflow.repository.SavedConcertRepository;
import com.melodyflow.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SavedConcertService {

    private final SavedConcertRepository savedConcertRepository;
    private final ConcertRepository concertRepository;
    private final UserRepository userRepository;

    public SavedConcertService(
            SavedConcertRepository savedConcertRepository,
            ConcertRepository concertRepository,
            UserRepository userRepository
    ) {
        this.savedConcertRepository = savedConcertRepository;
        this.concertRepository = concertRepository;
        this.userRepository = userRepository;
    }

    public List<SavedConcert> findAll() {
        return savedConcertRepository.findAll();
    }

    public Optional<SavedConcert> findById(Long id) {
        return savedConcertRepository.findById(id);
    }

    @Transactional
    public SavedConcert saveConcert(SavedConcertRequest request) {

        Long concertId = request.getConcertId();

        Concert concert = concertRepository.findById(concertId)
                .orElseThrow(() ->
                        new RuntimeException("Concert not found with ID: " + concertId)
                );

        // Temporary user ID for development.
        // We will replace this with the authenticated user's ID
        // when JWT authentication is implemented.
        Long userId = 1L;

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + userId)
                );

        if (savedConcertRepository.existsByUserIdAndConcertId(userId, concertId)) {
            throw new RuntimeException("Concert is already saved by this user");
        }

        SavedConcert savedConcert = new SavedConcert();

        savedConcert.setUser(user);
        savedConcert.setConcert(concert);
        savedConcert.setStatus("SAVED");

        return savedConcertRepository.save(savedConcert);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!savedConcertRepository.existsById(id)) {
            throw new RuntimeException("Saved concert not found with ID: " + id);
        }

        savedConcertRepository.deleteById(id);
    }
}