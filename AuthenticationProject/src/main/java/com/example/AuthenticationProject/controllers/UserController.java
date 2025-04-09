package com.example.AuthenticationProject.controllers;

import ch.qos.logback.core.model.Model;
import com.example.AuthenticationProject.auth.dto.ForgotPasswordRequest;
import com.example.AuthenticationProject.auth.dto.LoginRequest;
import com.example.AuthenticationProject.auth.dto.ResetPasswordRequest;
import com.example.AuthenticationProject.auth.security.JwtUtil;
import com.example.AuthenticationProject.repository.UserRepository;
import com.example.AuthenticationProject.service.RateLimitService;
import io.github.bucket4j.Bucket;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;




import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


//package com.example.registration.controller;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.AuthenticationProject.model.User;
import com.example.AuthenticationProject.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PasswordEncoder passwordEncoder;  // ✅ Correct (Spring will inject the correct bean)

    @Autowired
    private  UserRepository userRepository;  // Declare userRepository

//    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  JwtUtil jwtUtil;



//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
//        try {
//            userService.registerUser(user);
//            return ResponseEntity.ok("User registered successfully. Please check your email to verify your account.");
//        } catch (MessagingException e) {
//            return ResponseEntity.badRequest().body("User registered but failed to send verification email: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "User registered successfully. Please check your email to verify your account."));
        } catch (MessagingException e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", "User registered but failed to send verification email: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        String result = userService.verifyUser(token);
        if ("User verified successfully.".equals(result)) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }




//
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
//        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
////        System.out.println("i am in the login controller");
////        System.out.println("user is preent : "+userOptional.isPresent());
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//
//            System.out.println("User"+user);
//
////            System.out.println("user is preent1 : "+userOptional.isPresent());
//            // Check if user is verified
//            if (!user.isEnabled()) {
//                return ResponseEntity.badRequest().body("Please verify your email before logging in.");
//            }
//
//            // Verify password
//
//            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//                System.out.println("user is preent1 : "+userOptional.isPresent());
//                String token = jwtUtil.generateToken(user.getEmail());
//                System.out.println("token: " + token);
//                System.out.println("user is preent0: "+userOptional.isPresent());
//              //  return ResponseEntity.ok(token);
//                return ResponseEntity.ok(Collections.singletonMap("token", token));
//            } else {
//                System.out.println("i am in the invalid emai or password");
//                return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Invalid email or password"));
//            }
//        } else {
//            System.out.println("user is not pressent in db");
//            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "User not found, Please Register First"));
//        }
//    }
//

//Login limiter added

@Autowired
private RateLimitService rateLimitService; // ✅ Added for failed login rate limiting
@PostMapping("/login")
public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
    Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

    if (userOptional.isPresent()) {
        User user = userOptional.get();

        // ✅ Ensure user has verified email before login
        if (!user.isEnabled()) {
            return ResponseEntity.badRequest().body("Please verify your email before logging in.");
        }

        // ✅ Check if the password is correct
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // ✅ Successful login – no rate limit action needed
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            // ✅ Wrong password – apply rate limiting using Bucket4j
            Bucket bucket = rateLimitService.resolveBucket(request.getEmail()); // using email as key
            if (bucket.tryConsume(1)) {
                // ✅ Allow this failed attempt, return bad request
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Invalid email or password"));
            } else {
                // ✅ Rate limit exceeded – ask user to wait
                return ResponseEntity.status(429) // HTTP 429 Too Many Requests
                        .body(Collections.singletonMap("message",
                                "Too many failed attempts. Please wait 2 minutes before trying again."));
            }
        }
    } else {
        // ✅ No user found – do not apply rate limit here (optional: you can apply based on IP)
        return ResponseEntity.badRequest()
                .body(Collections.singletonMap("message", "User not found, Please Register First"));
    }
}






    // login rate limiter - 2




    // In UserController.java

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            userService.generateResetToken(request.getEmail());
            System.out.println("i am in forgot-password");
            return ResponseEntity.ok(Collections.singletonMap("message", "Password reset link sent to your email"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
//        try {
//            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
//                return ResponseEntity.badRequest().body("Passwords don't match");
//            }
//
//            userService.resetPassword(request.getToken(), request.getNewPassword());
//            return ResponseEntity.ok("Password reset successfully");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//
//
//
//
//
//
//
//    @GetMapping("/reset-password")
//    public String showResetForm(@RequestParam String token, Model model) {
//        // Verify token is valid (but don't require auth)
//        if (!userRepository.findByResetPasswordToken(token).isPresent()) {
//            throw new RuntimeException("Invalid token");
//        }
//        model.addAttribute("token", token);
//        return "reset-password";
//    }
//
//    @PostMapping("/reset-password")
//    public ModelAndView handleResetPassword(
//            @RequestParam String token,
//            @RequestParam String newPassword,
//            @RequestParam String confirmPassword) {
//
//        if (!newPassword.equals(confirmPassword)) {
//            throw new RuntimeException("Passwords do not match");
//        }
//
//        userService.resetPassword(token, newPassword);
//        return new ModelAndView("redirect:/api/auth/reset-success");
//    }
//
//    @GetMapping("/reset-success")
//    public String showResetSuccess() {
//        return "reset-success";
//    }









}

