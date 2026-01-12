package com.example.expenses_tracker.repository;

import com.example.expenses_tracker.model.Income;
import com.example.expenses_tracker.model.type.IncomeSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long> {
    List<Income> findByUserUserId(Long userId);
    List<Income> findByUserUserIdAndSource(Long userId, IncomeSource source);
    List<Income> findByUserUserIdAndDateOfIncomeBetween(Long userId, LocalDate start, LocalDate end);
    List<Income> findByUserUserIdAndAmountBetween(Long userId, Long min, Long max);
}
