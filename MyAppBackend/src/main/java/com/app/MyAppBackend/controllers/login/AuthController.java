package com.app.MyAppBackend.controllers.login;

import com.app.MyAppBackend.model.dtos.RegisterUserDto;
import com.app.MyAppBackend.model.entities.MyUser;
import com.app.MyAppBackend.model.records.LoginForm;
import com.app.MyAppBackend.repositories.user.MyUserRepository;
import com.app.MyAppBackend.security.jwt.JwtService;
import com.app.MyAppBackend.services.user.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MyUserDetailService myUserDetailService;

    @PostMapping("/register/user")
    public ResponseEntity<?> createUser(@RequestBody RegisterUserDto userDto) {
        try {
            // Crear el nuevo usuario
            MyUser user = new MyUser();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole("USER");

            MyUser savedUser = myUserRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
            Map<String, String> response = new HashMap<>();
            return ResponseEntity.ok(Map.of("token", token));

        }else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        MyUser user = myUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return ResponseEntity.ok(user.getUsername());
    }




}
