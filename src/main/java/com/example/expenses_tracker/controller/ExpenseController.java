package com.example.expenses_tracker.controller;

import com.example.expenses_tracker.dto.AddExpenseDto;
import com.example.expenses_tracker.model.Expenses;
import com.example.expenses_tracker.model.type.Category;
import com.example.expenses_tracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ExpenseController {
    private final ExpenseService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expenses>> getExpensesByUser(@PathVariable Long userId){
        return new ResponseEntity<>(service.getAllExpenses(userId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Expenses> add(@RequestBody AddExpenseDto expenseDto){
        return new ResponseEntity<>(service.save(expenseDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId){
        service.deleteExpense(expenseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Expenses>> filterExpenses(
            @RequestParam Long userId,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long minAmount,
            @RequestParam(required = false) Long maxAmount
    ){
        return new ResponseEntity<>(
                service.filterExpenses(userId, category, startDate, endDate, minAmount, maxAmount),
                HttpStatus.OK
        );
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Expenses>> sortExpenses(
            @RequestParam Long userId,
            @RequestParam String sortBy
    ){
        return new ResponseEntity<>(service.sortExpenses(userId, sortBy), HttpStatus.OK);
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<Map<String, Long>> getTotalExpenses(@PathVariable Long userId){
        return new ResponseEntity<>(
                Map.of("totalExpenses", service.getTotalExpenses(userId)),
                HttpStatus.OK
        );
    }
}
