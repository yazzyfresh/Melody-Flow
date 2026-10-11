
package com.melodyflow.controller;

import com.melodyflow.entity.ConcertPlan;
import com.melodyflow.entity.Expense;
import com.melodyflow.repository.ConcertPlanRepository;
import com.melodyflow.repository.ExpenseRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final ConcertPlanRepository concertPlanRepository;

    public ExpenseController(
            ExpenseRepository expenseRepository,
            ConcertPlanRepository concertPlanRepository) {
        this.expenseRepository = expenseRepository;
        this.concertPlanRepository = concertPlanRepository;
    }

    @GetMapping("/plan/{planId}")
    public List<ExpenseResponse> getExpensesByPlan(@PathVariable Long planId) {
        return expenseRepository
                .findByConcertPlanIdOrderByExpenseDateAsc(planId)
                .stream()
                .map(ExpenseResponse::from)
                .toList();
    }

    @PostMapping("/plan/{planId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponse createExpense(
            @PathVariable Long planId,
            @Valid @RequestBody Expense expense) {

        ConcertPlan concertPlan = concertPlanRepository.findById(planId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Concert plan not found"));

        expense.setConcertPlan(concertPlan);
        expense.setId(null);

        if (expense.getCreatedAt() == null) {
            expense.setCreatedAt(LocalDateTime.now());
        }

        Expense savedExpense = expenseRepository.save(expense);

        return ExpenseResponse.from(savedExpense);
    }

    @DeleteMapping("/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long expenseId) {
        if (!expenseRepository.existsById(expenseId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Expense not found");
        }

        expenseRepository.deleteById(expenseId);
    }

    public record ExpenseResponse(
            Long id,
            String category,
            String description,
            BigDecimal amount,
            LocalDate expenseDate,
            Long concertPlanId
    ) {
        public static ExpenseResponse from(Expense expense) {
            return new ExpenseResponse(
                    expense.getId(),
                    expense.getCategory(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getExpenseDate(),
                    expense.getConcertPlan().getId()
            );
        }
    }
}
