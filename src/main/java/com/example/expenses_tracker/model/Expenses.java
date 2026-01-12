package com.example.expenses_tracker.model;

import com.example.expenses_tracker.model.type.Category;
import com.example.expenses_tracker.model.type.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Expenses {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long expenseId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    String expenseDescription;
    Long departmentId;
    Long amount;
    @Enumerated(EnumType.STRING)
    Category category;
    LocalDate dateOfExpense;
    String receiptUrl;
    @Enumerated(EnumType.STRING)
    Status status;






}
