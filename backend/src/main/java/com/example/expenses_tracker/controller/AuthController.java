package com.example.expenses_tracker.controller;

import com.example.expenses_tracker.dto.LoginResponseDto;
import com.example.expenses_tracker.dto.SignUpUserDto;
import com.example.expenses_tracker.dto.LoginDto;
import com.example.expenses_tracker.model.User;
import com.example.expenses_tracker.model.type.Role;
import com.example.expenses_tracker.repository.UserRepo;
import com.example.expenses_tracker.service.AuthService;
import com.example.expenses_tracker.service.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthUtil authUtil;
    private final UserRepo userRepo;


    @PostMapping("/signup")
    public ResponseEntity<SignUpUserDto> signup(@RequestBody SignUpUserDto signUpUserDto){
        return new ResponseEntity<>(authService.save(signUpUserDto), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> signin(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(authService.login(loginDto),HttpStatus.OK);
    }

    @GetMapping("/oauth-success")
    public void oauthSuccess(OAuth2AuthenticationToken token, jakarta.servlet.http.HttpServletResponse response) throws java.io.IOException {
        try {
            if (token == null || token.getPrincipal() == null) {
                response.sendRedirect("http://localhost:3000/login?error=oauth_failed");
                return;
            }
            
            Map<String, Object> attributes = token.getPrincipal().getAttributes();
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
                        .password("")
                        .build();
                user = userRepo.save(user);
            }
            
            String jwt = authUtil.genrateAccessToken(user);
            String redirectUrl = String.format("http://localhost:3000/auth/callback?token=%s&userId=%s", 
                    jwt, user.getUserId());
            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            response.sendRedirect("http://localhost:3000/login?error=oauth_failed");
        }
    }
    
    @GetMapping("/oauth2/authorization/google")
    public void googleLogin() {
        // This endpoint will be handled by Spring Security OAuth2
    }

}
