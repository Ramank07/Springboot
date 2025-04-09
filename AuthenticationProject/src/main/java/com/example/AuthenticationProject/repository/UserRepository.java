package com.example.AuthenticationProject.repository;

//package com.example.registration.repository;

import com.example.AuthenticationProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationToken(String token);
    boolean existsByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);
}
