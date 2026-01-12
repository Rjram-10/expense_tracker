package com.example.expenses_tracker.controller;

import com.example.expenses_tracker.dto.AddIncomeDto;
import com.example.expenses_tracker.model.Income;
import com.example.expenses_tracker.model.type.IncomeSource;
import com.example.expenses_tracker.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/income")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class IncomeController {
    private final IncomeService service;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Income>> getIncomesByUser(@PathVariable Long userId){
        return new ResponseEntity<>(service.getAllIncomes(userId), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Income> add(@RequestBody AddIncomeDto incomeDto){
        return new ResponseEntity<>(service.save(incomeDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long incomeId){
        service.deleteIncome(incomeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Income>> filterIncomes(
            @RequestParam Long userId,
            @RequestParam(required = false) IncomeSource source,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long minAmount,
            @RequestParam(required = false) Long maxAmount
    ){
        return new ResponseEntity<>(
                service.filterIncomes(userId, source, startDate, endDate, minAmount, maxAmount),
                HttpStatus.OK
        );
    }

    @GetMapping("/sort")
    public ResponseEntity<List<Income>> sortIncomes(
            @RequestParam Long userId,
            @RequestParam String sortBy
    ){
        return new ResponseEntity<>(service.sortIncomes(userId, sortBy), HttpStatus.OK);
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<Map<String, Long>> getTotalIncome(@PathVariable Long userId){
        return new ResponseEntity<>(
                Map.of("totalIncome", service.getTotalIncome(userId)),
                HttpStatus.OK
        );
    }
}
