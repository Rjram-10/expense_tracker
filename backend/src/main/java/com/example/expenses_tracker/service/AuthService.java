package com.example.expenses_tracker.service;

import com.example.expenses_tracker.dto.LoginResponseDto;
import com.example.expenses_tracker.dto.LoginDto;
import com.example.expenses_tracker.dto.SignUpUserDto;
import com.example.expenses_tracker.model.User;
import com.example.expenses_tracker.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepo userRepo;
    private final PasswordEncoder encoder;


    public LoginResponseDto login(LoginDto loginResponseDto){
        Authentication auth=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginResponseDto.getEmail(),loginResponseDto.getPassword()));

        User user=(User) auth.getPrincipal();

        String token=authUtil.genrateAccessToken(user);
        return new LoginResponseDto(token,user.getUserId());
    }

    public SignUpUserDto save(SignUpUserDto signUpUserDto) {
        User user=userRepo.findByEmail((signUpUserDto.getEmail()));
        if(user!=null) throw new IllegalArgumentException("Already Exists User");

        user=User.builder().
                name(signUpUserDto.getName())
                .departmentId(signUpUserDto.getDepartmentId())
                .email(signUpUserDto.getEmail())
                .role(signUpUserDto.getRole())
                .managerId(signUpUserDto.getManagerId())
                .password(encoder.encode(signUpUserDto.getPassword()))
                .build();

        userRepo.save(user);
        return signUpUserDto;
    }



}
