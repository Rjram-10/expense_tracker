package com.example.expenses_tracker.dto;

import com.example.expenses_tracker.model.type.Category;
import com.example.expenses_tracker.model.type.Status;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddExpenseDto {
    private Long userId;
    private String expenseDescription;
    private Long departmentId;
    private Long amount;
    private Category category;
    private LocalDate dateOfExpense;
    private String receiptUrl;
    private Status status;
}
