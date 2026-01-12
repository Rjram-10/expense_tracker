package com.example.expenses_tracker.dto;

import com.example.expenses_tracker.model.type.IncomeSource;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddIncomeDto {
    private Long userId;
    private String incomeDescription;
    private Long amount;
    private IncomeSource source;
    private LocalDate dateOfIncome;
}
