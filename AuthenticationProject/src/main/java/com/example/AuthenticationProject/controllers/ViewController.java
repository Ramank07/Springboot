package com.example.AuthenticationProject.controllers;

//public class ViewController package com.example.AuthenticationProject.controllers;

import com.example.AuthenticationProject.repository.UserRepository;
import com.example.AuthenticationProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/auth")
public class ViewController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam String token, Model model) {
        if (!userRepository.findByResetPasswordToken(token).isPresent()) {
            return "redirect:/error?message=Invalid+token";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public ModelAndView handleResetPassword(
            @RequestParam String token,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            return new ModelAndView("redirect:/error?message=Passwords+do+not+match");
        }

        userService.resetPassword(token, newPassword);
        return new ModelAndView("redirect:/api/auth/reset-success");
    }

    @GetMapping("/reset-success")
    public String showResetSuccess() {
        return "reset-success";
    }
}
