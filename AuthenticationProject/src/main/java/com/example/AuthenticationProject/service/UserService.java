package com.example.AuthenticationProject.service;

import com.example.AuthenticationProject.model.User;
import com.example.AuthenticationProject.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user) throws Exception {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email is already registered");
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Generate verification token and set user as not enabled
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setEnabled(false);

        // Save the user
        User savedUser = userRepository.save(user);

        // Send verification email
        sendVerificationEmail(savedUser);

        return savedUser;
    }

    private void sendVerificationEmail(User user) {
        String senderEmail = "your_email@gmail.com";
        String senderName = "Your Company Name";
        try {
            String subject = "Email Verification";
            String mailContent = "<p>Dear " + user.getName() + ",</p>";
            mailContent += "<p>Thank you for registering. Please click the link below to verify your email:</p>";
            String verifyURL = "http://localhost:8082/api/auth/verify?token=" + user.getVerificationToken();
            mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
            mailContent += "<p>Thank you,<br>The Team</p>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            try {
                InternetAddress senderAddress = new InternetAddress(senderEmail);
                helper.setFrom(senderAddress.toString(), senderName);
            } catch (AddressException e) {
                System.err.println("Invalid sender email address: " + senderEmail);
                return; // Exit if email is invalid
            } catch (UnsupportedEncodingException e) {
                System.err.println("Encoding issue with sender name: " + senderName);
                return; // Exit if encoding error occurs
            }

            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public String verifyUser(String token) {
        Optional<User> optionalUser = userRepository.findByVerificationToken(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setVerificationToken(null); // token no longer needed after verification
            userRepository.save(user);
            return "User verified successfully.";
        } else {
            return "Invalid verification token.";
        }
    }




    // In UserService.java

    public void generateResetToken(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found with email: " + email));

        // Generate token
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setResetPasswordExpires(new Date(System.currentTimeMillis() + 3600000)); // 1 hour expiry
        userRepository.save(user);

        // Send email
        sendResetEmail(user);
    }

    private void sendResetEmail(User user) {
//        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + user.getResetPasswordToken();

        String resetUrl = "http://localhost:8082/api/auth/reset-password?token=" + user.getResetPasswordToken();

        String emailContent = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetUrl + "\">Reset Password</a></p>"
                + "<p>This link will expire in 1 hour.</p>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setTo(user.getEmail());
            helper.setSubject("Password Reset Request");
            helper.setText(emailContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset email", e);
        }
    }

//    public void resetPassword(String token, String newPassword) throws Exception {
//        User user = userRepository.findByResetPasswordToken(token)
//                .orElseThrow(() -> new Exception("Invalid token"));
//
//        if (user.getResetPasswordExpires().before(new Date())) {
//            throw new Exception("Token has expired");
//        }
//
//        if (!newPassword.equals(user.getPassword())) {
//            user.setPassword(passwordEncoder.encode(newPassword));
//            user.setResetPasswordToken(null);
//            user.setResetPasswordExpires(null);
//            userRepository.save(user);
//        } else {
//            throw new Exception("New password cannot be the same as old password");
//        }
//    }
public void resetPassword(String token, String newPassword) {
    // 1. Find user by token
    User user = userRepository.findByResetPasswordToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid password reset token"));

    // 2. Check token expiration
    if (user.getResetPasswordExpires().before(new Date())) {
        throw new RuntimeException("Password reset token has expired");
    }

    // 3. Verify new password isn't same as current (using passwordEncoder)
    if (passwordEncoder.matches(newPassword, user.getPassword())) {
        throw new RuntimeException("New password cannot be the same as current password");
    }

    // 4. Update password and clear reset token
    user.setPassword(passwordEncoder.encode(newPassword));
    user.setResetPasswordToken(null);
    user.setResetPasswordExpires(null);
    userRepository.save(user);
}



}
