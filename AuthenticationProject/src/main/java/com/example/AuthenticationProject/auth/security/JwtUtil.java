////package com.example.AuthenticationProject.auth.security;
////
////
////
////import io.jsonwebtoken.Claims;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////import org.springframework.stereotype.Component;
////
////import java.util.Base64;
////import java.util.Date;
////
////@Component
////public class JwtUtil {
////    private final String SECRET_KEY = "your-secret-key";  // Replace with a secure key
////    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
////
////    public String generateToken(String username) {
////        String secretKey = "your-base64-encoded-secret"; // Ensure it's correctly encoded
////
////        return Jwts.builder()
////                .setSubject(username)
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
////                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(secretKey)) // Decode before use
////                .compact();
////    }
////
////    public String extractEmail(String token) {
////        return getClaims(token).getSubject();
////    }
////
////    public boolean validateToken(String token) {
////        return getClaims(token).getExpiration().after(new Date());
////    }
////
////    private Claims getClaims(String token) {
////        return Jwts.parser()
////                .setSigningKey(SECRET_KEY)
////                .parseClaimsJws(token)
////                .getBody();
////    }
////}
////
//
//
//
////package com.example.AuthenticationProject.auth.security;
////
////import io.jsonwebtoken.Claims;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////import org.springframework.stereotype.Component;
////
////import java.util.Base64;
////import java.util.Date;
////
////@Component
////public class JwtUtil {
////    private final String SECRET_KEY = Base64.getEncoder().encodeToString("your-secret-key".getBytes()); // ✅ Properly encoded secret key
////    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // ✅ 10 hours validity
////
////    // ✅ Generate JWT Token
////    public String generateToken(String username) {
////        return Jwts.builder()
////                .setSubject(username)
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
////                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // ✅ Use correctly encoded secret key
////                .compact();
////    }
////
////    // ✅ Extract Username (Email) from JWT Token
////    public String extractEmail(String token) {
////        return getClaims(token).getSubject();
////    }
////
////    // ✅ Validate JWT Token
////    public boolean validateToken(String token) {
////        try {
////            return getClaims(token).getExpiration().after(new Date());
////        } catch (Exception e) {
////            return false;
////        }
////    }
////
////    // ✅ Get Claims from Token
////    private Claims getClaims(String token) {
////        return Jwts.parser()
////                .setSigningKey(SECRET_KEY) // ✅ Use the same encoded secret key
////                .parseClaimsJws(token)
////                .getBody();
////    }
////}
//
//
//
//
//
//package com.example.AuthenticationProject.auth.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//   // private final String SECRET_KEY = "your-secret-key"; // Secure and keep it private
//   // private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours validity
//
//    // Generate JWT Token
////    private static final String SECRET_KEY = Base64.getUrlEncoder().encodeToString("your-secret-key".getBytes());
//    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 hours expiry
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    // Extract Username (Email) from JWT Token
//    public String extractEmail(String token) {
//        return getClaims(token).getSubject();
//    }
//
//    // Validate JWT Token
//    public boolean validateToken(String token) {
//        try {
//            return getClaims(token).getExpiration().after(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // Get Claims from Token
//    private Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(Base64.getDecoder().decode(SECRET_KEY)) // Decode before use
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}
//


//package com.example.AuthenticationProject.auth.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//
//import java.util.Base64;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    private final String SECRET_KEY = "your-secret-key"; // Secure and keep it private
//    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours validity
//
//    // Generate JWT Token
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(SECRET_KEY)) // Decode before use
//                .compact();
//    }
//
//    // Extract Username (Email) from JWT Token
//    public String extractEmail(String token) {
//        return getClaims(token).getSubject();
//    }
//
//    // Validate JWT Token
//    public boolean validateToken(String token) {
//        try {
//            return getClaims(token).getExpiration().after(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    // Get Claims from Token
//    private Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(Base64.getDecoder().decode(SECRET_KEY)) // Decode before use
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}





package com.example.AuthenticationProject.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // Use a proper cryptographic key (HS256 requires a 256-bit key)
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY) // Use the Key directly
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Use the same Key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}