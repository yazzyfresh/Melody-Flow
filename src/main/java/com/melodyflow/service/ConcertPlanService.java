package com.melodyflow.service;

import com.melodyflow.entity.ConcertPlan;
import com.melodyflow.repository.ConcertPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertPlanService {

    private final ConcertPlanRepository concertPlanRepository;

    public ConcertPlanService(ConcertPlanRepository concertPlanRepository) {
        this.concertPlanRepository = concertPlanRepository;
    }

    public List<ConcertPlan> findAll() {
        return concertPlanRepository.findAll();
    }

    public Optional<ConcertPlan> findById(Long id) {
        return concertPlanRepository.findById(id);
    }

    public Optional<ConcertPlan> findBySavedConcertId(Long savedConcertId) {
        return concertPlanRepository.findBySavedConcertId(savedConcertId);
    }

    public ConcertPlan save(ConcertPlan concertPlan) {
        return concertPlanRepository.save(concertPlan);
    }

    public void deleteById(Long id) {
        concertPlanRepository.deleteById(id);
    }


}
