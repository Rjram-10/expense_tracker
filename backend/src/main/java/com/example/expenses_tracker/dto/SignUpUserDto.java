package com.example.expenses_tracker.dto;

import com.example.expenses_tracker.model.type.Role;
import lombok.Data;

@Data
public class SignUpUserDto {
    String name;
    String email;
    String password;
    Role role;
    Long departmentId;
    Long managerId;
}
