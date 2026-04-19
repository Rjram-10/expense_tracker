package com.example.expenses_tracker.service;

import com.example.expenses_tracker.dto.AddIncomeDto;
import com.example.expenses_tracker.model.Income;
import com.example.expenses_tracker.model.User;
import com.example.expenses_tracker.model.type.IncomeSource;
import com.example.expenses_tracker.repository.IncomeRepo;
import com.example.expenses_tracker.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepo repo;
    private final UserRepo userRepo;

    public List<Income> getAllIncomes(Long userId) {
        return repo.findByUserUserId(userId);
    }

    public Income save(AddIncomeDto incomeDto) {
        User user = userRepo.findById(incomeDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Income income = Income.builder()
                .user(user)
                .incomeDescription(incomeDto.getIncomeDescription())
                .amount(incomeDto.getAmount())
                .source(incomeDto.getSource())
                .dateOfIncome(incomeDto.getDateOfIncome())
                .build();
        return repo.save(income);
    }

    public void deleteIncome(Long incomeId) {
        repo.deleteById(incomeId);
    }

    public List<Income> filterIncomes(Long userId, IncomeSource source, LocalDate startDate, LocalDate endDate, Long minAmount, Long maxAmount) {
        List<Income> incomes = repo.findByUserUserId(userId);

        if (source != null) {
            incomes = incomes.stream()
                    .filter(i -> i.getSource() == source)
                    .collect(Collectors.toList());
        }

        if (startDate != null && endDate != null) {
            incomes = incomes.stream()
                    .filter(i -> !i.getDateOfIncome().isBefore(startDate) && !i.getDateOfIncome().isAfter(endDate))
                    .collect(Collectors.toList());
        }

        if (minAmount != null) {
            incomes = incomes.stream()
                    .filter(i -> i.getAmount() >= minAmount)
                    .collect(Collectors.toList());
        }

        if (maxAmount != null) {
            incomes = incomes.stream()
                    .filter(i -> i.getAmount() <= maxAmount)
                    .collect(Collectors.toList());
        }

        return incomes;
    }

    public List<Income> sortIncomes(Long userId, String sortBy) {
        List<Income> incomes = repo.findByUserUserId(userId);

        if ("source".equalsIgnoreCase(sortBy)) {
            incomes.sort(Comparator.comparing(Income::getSource));
        } else if ("amount".equalsIgnoreCase(sortBy)) {
            incomes.sort(Comparator.comparing(Income::getAmount).reversed());
        } else if ("date".equalsIgnoreCase(sortBy)) {
            incomes.sort(Comparator.comparing(Income::getDateOfIncome).reversed());
        }

        return incomes;
    }

    public Long getTotalIncome(Long userId) {
        return repo.findByUserUserId(userId).stream()
                .mapToLong(Income::getAmount)
                .sum();
    }
}
