package com.example.expenses_tracker.controller;

import com.example.expenses_tracker.service.ExpenseService;
import com.example.expenses_tracker.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pnl")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://192.168.49.2:30060")
public class PnLController {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Long>> getPnL(@PathVariable Long userId){
        Long totalIncome = incomeService.getTotalIncome(userId);
        Long totalExpenses = expenseService.getTotalExpenses(userId);
        Long pnl = totalIncome - totalExpenses;

        return new ResponseEntity<>(
                Map.of(
                        "totalIncome", totalIncome,
                        "totalExpenses", totalExpenses,
                        "pnl", pnl
                ),
                HttpStatus.OK
        );
    }
}
