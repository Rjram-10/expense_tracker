package com.example.expenses_tracker.controller;

import com.example.expenses_tracker.dto.SignUpUserDto;
import com.example.expenses_tracker.model.User;
import com.example.expenses_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/alluser")
    public ResponseEntity<List<User>> getUser(){
        return new ResponseEntity<>(userService.getUser(),HttpStatus.OK);
    }


}
