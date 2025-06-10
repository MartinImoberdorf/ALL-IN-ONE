package com.app.MyAppBackend.controllers.user;

import com.app.MyAppBackend.model.entities.MyUser;
import com.app.MyAppBackend.model.records.LoginForm;
import com.app.MyAppBackend.repositories.user.MyUserRepository;
import com.app.MyAppBackend.security.jwt.JwtService;
import com.app.MyAppBackend.services.user.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public MyUser createUser(@RequestBody MyUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return myUserRepository.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if(authentication.isAuthenticated()){
            String token = jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        }else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }



}
