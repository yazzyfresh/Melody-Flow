
package com.melodyflow.repository;

import com.melodyflow.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByConcertPlanId(Long concertPlanId);

    List<Expense> findByConcertPlanIdOrderByExpenseDateAsc(Long concertPlanId);
}
