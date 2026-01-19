package com.example.expenses_tracker.service;

import com.example.expenses_tracker.dto.AddExpenseDto;
import com.example.expenses_tracker.model.Expenses;
import com.example.expenses_tracker.model.User;
import com.example.expenses_tracker.model.type.Category;
import com.example.expenses_tracker.repository.ExpenseRepo;
import com.example.expenses_tracker.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepo repo;
    private final UserRepo userRepo;
    
    public List<Expenses> getAllExpenses(Long userId) {
        return repo.findByUserUserId(userId);
    }

    public Expenses save(AddExpenseDto expensesDto) {
        User user = userRepo.findById(expensesDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expenses expense = Expenses.builder()
                .user(user)
                .expenseDescription(expensesDto.getExpenseDescription())
                .departmentId(expensesDto.getDepartmentId())
                .dateOfExpense(expensesDto.getDateOfExpense())
                .amount(expensesDto.getAmount())
                .status(expensesDto.getStatus())
                .category(expensesDto.getCategory())
                .receiptUrl(expensesDto.getReceiptUrl())
                .build();
        return repo.save(expense);
    }

    public void deleteExpense(Long expenseId) {
        repo.deleteById(expenseId);
    }

    public List<Expenses> filterExpenses(Long userId, Category category, LocalDate startDate, LocalDate endDate, Long minAmount, Long maxAmount) {
        List<Expenses> expenses = repo.findByUserUserId(userId);

        if (category != null) {
            expenses = expenses.stream()
                    .filter(e -> e.getCategory() == category)
                    .collect(Collectors.toList());
        }

        if (startDate != null && endDate != null) {
            expenses = expenses.stream()
                    .filter(e -> !e.getDateOfExpense().isBefore(startDate) && !e.getDateOfExpense().isAfter(endDate))
                    .collect(Collectors.toList());
        }

        if (minAmount != null) {
            expenses = expenses.stream()
                    .filter(e -> e.getAmount() >= minAmount)
                    .collect(Collectors.toList());
        }

        if (maxAmount != null) {
            expenses = expenses.stream()
                    .filter(e -> e.getAmount() <= maxAmount)
                    .collect(Collectors.toList());
        }

        return expenses;
    }

    public List<Expenses> sortExpenses(Long userId, String sortBy) {
        List<Expenses> expenses = repo.findByUserUserId(userId);

        if ("category".equalsIgnoreCase(sortBy)) {
            expenses.sort(Comparator.comparing(Expenses::getCategory));
        } else if ("amount".equalsIgnoreCase(sortBy)) {
            expenses.sort(Comparator.comparing(Expenses::getAmount).reversed());
        } else if ("date".equalsIgnoreCase(sortBy)) {
            expenses.sort(Comparator.comparing(Expenses::getDateOfExpense).reversed());
        }

        return expenses;
    }

    public Long getTotalExpenses(Long userId) {
        return repo.findByUserUserId(userId).stream()
                .mapToLong(Expenses::getAmount)
                .sum();
    }
}
