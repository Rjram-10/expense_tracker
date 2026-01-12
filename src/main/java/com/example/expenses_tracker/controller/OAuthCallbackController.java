package com.example.expenses_tracker.controller;

import com.example.expenses_tracker.dto.LoginResponseDto;
import com.example.expenses_tracker.model.User;
import com.example.expenses_tracker.model.type.Role;
import com.example.expenses_tracker.repository.UserRepo;
import com.example.expenses_tracker.service.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OAuthCallbackController {

    private final AuthUtil authUtil;
    private final UserRepo userRepo;

    @GetMapping("/oauth-user")
    public ResponseEntity<LoginResponseDto> getOAuthUser(@AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        
        // Find or create user
        User user = userRepo.findByEmail(email);
        if (user == null) {
            // Create new user from OAuth
            user = User.builder()
                    .email(email)
                    .name(name != null ? name : email)
                    .role(Role.EMPLOYEE)
                    .departmentId(1L)
                    .password("") // OAuth users don't have passwords
                    .build();
            user = userRepo.save(user);
        }
        
        String jwt = authUtil.genrateAccessToken(user);
        return ResponseEntity.ok(new LoginResponseDto(jwt, user.getUserId()));
    }
}
