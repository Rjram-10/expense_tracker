package com.example.expenses_tracker.repository;

import com.example.expenses_tracker.model.Expenses;
import com.example.expenses_tracker.model.type.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expenses, Long> {
    List<Expenses> findByUserUserId(Long userId);
    List<Expenses> findByUserUserIdAndCategory(Long userId, Category category);
    List<Expenses> findByUserUserIdAndDateOfExpenseBetween(Long userId, LocalDate start, LocalDate end);
    List<Expenses> findByUserUserIdAndAmountBetween(Long userId, Long min, Long max);
}
