package com.example.expenses_tracker.service;

import com.example.expenses_tracker.dto.SignUpUserDto;
import com.example.expenses_tracker.dto.LoginDto;
import com.example.expenses_tracker.model.User;
import com.example.expenses_tracker.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo repo;
    public List<User> getUser() {
        return repo.findAll();
    }

}
