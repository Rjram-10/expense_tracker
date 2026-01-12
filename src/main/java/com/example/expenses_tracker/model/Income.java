package com.example.expenses_tracker.model;

import com.example.expenses_tracker.model.type.IncomeSource;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Income {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long incomeId;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    
    String incomeDescription;
    Long amount;
    
    @Enumerated(EnumType.STRING)
    IncomeSource source;
    
    LocalDate dateOfIncome;
}
