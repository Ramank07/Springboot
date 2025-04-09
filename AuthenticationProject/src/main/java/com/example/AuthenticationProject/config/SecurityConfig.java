////package com.example.AuthenticationProject.config;
//////
////////package com.example.AuthenticationProject.config;
//////
//////
//////
////////import com.example.AuthenticationProject.auth.security.JwtFilter;
////////import lombok.RequiredArgsConstructor;
////////import org.springframework.context.annotation.Bean;
////////import org.springframework.context.annotation.Configuration;
////////import org.springframework.security.authentication.AuthenticationManager;
////////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////////import org.springframework.security.config.http.SessionCreationPolicy;
////////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////////import org.springframework.security.crypto.password.PasswordEncoder;
////////import org.springframework.security.web.SecurityFilterChain;
////////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////////
////////@Configuration
////////@RequiredArgsConstructor
////////public class SecurityConfig {
////////    private JwtFilter jwtFilter;
////////
////////    @Bean
////////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////////        http
////////                .csrf(csrf -> csrf.disable())
////////                .authorizeHttpRequests(auth -> auth
////////                        .requestMatchers("/api/auth/register", "/api/auth/verify", "/api/auth/login").permitAll()
////////                        .anyRequest().authenticated()
////////                )
////////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
////////
////////        return http.build();
////////    }
////////
////////    @Bean
////////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////////        return authenticationConfiguration.getAuthenticationManager();
////////    }
////////
//////////    @Bean
//////////    public PasswordEncoder passwordEncoder() {
//////////        return new BCryptPasswordEncoder();
//////////    }
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////
//////
////////
////////
////////import com.example.AuthenticationProject.auth.security.JwtFilter;
////////import lombok.RequiredArgsConstructor;
////////import org.springframework.beans.factory.annotation.Autowired;
////////import org.springframework.context.annotation.Bean;
////////import org.springframework.context.annotation.Configuration;
////////import org.springframework.security.authentication.AuthenticationManager;
////////import org.springframework.security.authentication.ProviderManager;
////////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////////import org.springframework.security.config.http.SessionCreationPolicy;
////////import org.springframework.security.core.userdetails.UserDetailsService;
////////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////////import org.springframework.security.crypto.password.PasswordEncoder;
////////import org.springframework.security.web.SecurityFilterChain;
////////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////////
////////@Configuration
////////@RequiredArgsConstructor
////////public class SecurityConfig {
////////
////////    private JwtFilter jwtAuthenticationFilter; // Ensure it's injected
////////    @Autowired
////////    private PasswordEncoder passwordEncoder;  // ✅ Correct (Spring injects the correct bean)
////////
////////    @Bean
////////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////////        http
////////                .csrf(csrf -> csrf.disable())
////////                .authorizeHttpRequests(auth -> auth
////////                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
////////                        .anyRequest().authenticated()
////////                )
////////                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ensure filter is NOT null
////////
////////        return http.build();
////////    }
////////
////////    @Bean
////////    public PasswordEncoder passwordEncoder() {
////////        return new BCryptPasswordEncoder();
////////    }
////////
////////    @Bean
////////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////////        return authenticationConfiguration.getAuthenticationManager();
////////    }
////////
////////
////////
////////
////////
////////
////////}
//////
////
////
////
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import com.example.AuthenticationProject.auth.security.JwtFilter;
////import lombok.RequiredArgsConstructor;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.authentication.AuthenticationManager;
////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.config.http.SessionCreationPolicy;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////
////@Configuration
////@RequiredArgsConstructor
////public class SecurityConfig {
////    private final JwtFilter jwtAuthenticationFilter;
////   // private  JwtFilter jwtAuthenticationFilter;  // ✅ Use constructor-based injection
//////    @Autowired
//////    private PasswordEncoder passwordEncoder;  // ✅ Correct (Spring injects the correct bean)
////
////    private RateLimitFilter rateLimitFilter;
////    @Autowired  // ✅ Injects JwtFilter via constructor
////    public SecurityConfig(JwtFilter jwtAuthenticationFilter) {
////        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
////    }
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        http
//////                .csrf(csrf -> csrf.disable())
//////                .authorizeHttpRequests(auth -> auth
//////                        // In SecurityConfig.java
//////                        .requestMatchers(
//////                                "/api/auth/signup",
//////                                "/api/auth/login",
//////                                "/api/auth/verify",
//////                                "/api/auth/forgot-password",  // Add this
//////                                "/api/auth/reset-password" ,
//////                                "/api/auth/forgot-password",
//////                                "/api/auth/reset-password",  // Allow reset password endpoint
//////                                "/api/auth/reset-password/**",
//////                                "/api/auth/reset-success",
//////                                "/view/auth/**",
//////                                "/api/auth/**"// Add this
//////                        ).permitAll()
//////                        .anyRequest().authenticated()
//////                )
//////                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ensure filter is NOT null
//////
//////        return http.build();
////
////
////        http
////                .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
////                .csrf(csrf -> csrf.disable())
////                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers(
////                                "/api/auth/signup",
////                                "/api/auth/login",
////                                "/api/auth/verify",
////                                "/api/auth/forgot-password",
////                                "/api/auth/reset-password",
////                                "/api/auth/reset-password/**",
////                                "/api/auth/reset-success",
////                                "/view/auth/**",
////                                "/api/auth/**"
////                        ).permitAll()
////                        .anyRequest().authenticated()
////                )
////                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
////
////        return http.build();
////
////
////
////
////    }
////
////
////
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {  // ✅ Define bean, but DO NOT inject it here
////        return new BCryptPasswordEncoder();
////    }
////
////    @Bean
////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
////        return authenticationConfiguration.getAuthenticationManager();
////    }
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////
////}
////
////
////
////
////
////
////
////
////
////
//////package com.example.login_signup.security;
////
//////import org.springframework.beans.factory.annotation.Autowired;
//////import org.springframework.context.annotation.Bean;
//////import org.springframework.context.annotation.Configuration;
//////import org.springframework.security.authentication.AuthenticationManager;
//////import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//////import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//////import org.springframework.security.config.http.SessionCreationPolicy;
//////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//////import org.springframework.security.crypto.password.PasswordEncoder;
//////import org.springframework.security.web.SecurityFilterChain;
//////import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//////import org.springframework.web.servlet.config.annotation.CorsRegistry;
//////import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//////
//////import java.util.List;
//////
//////@Configuration
//////@EnableWebSecurity
//////public class SecurityConfig {
//////
//////    @Autowired
//////    private JwtRequestFilter jwtRequestFilter;
//////
//////    @Bean
//////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//////        return http
//////                .csrf(csrf -> csrf.disable()) // Disable CSRF for API-based authentication
//////                .cors(cors -> cors.configurationSource(request -> {
//////                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
//////                    corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
//////                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
//////                    corsConfig.setAllowedHeaders(List.of("*"));
//////                    return corsConfig;
//////                }))
//////                .authorizeHttpRequests(auth -> auth
//////                        .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/verify" , "/api/auth/forgot-password", "/api/auth/reset-password").permitAll()
//////                        .anyRequest().authenticated()
//////                )
//////                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//////                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//////                .build();
//////    }
//////
//////    @Bean
//////    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//////        return authConfig.getAuthenticationManager();
//////    }
//////
//////    @Bean
//////    public PasswordEncoder passwordEncoder() {
//////        return new BCryptPasswordEncoder();
//////    }
//////
//////    @Bean
//////    public WebMvcConfigurer corsConfigurer() {
//////        return new WebMvcConfigurer() {
//////            @Override
//////            public void addCorsMappings(CorsRegistry registry) {
//////                registry.addMapping("/**")
//////                        .allowedOrigins("http://localhost:3000")
//////                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//////                        .allowedHeaders("*");
//////            }
//////        };
//////    }
//////}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//import com.example.AuthenticationProject.config.RateLimitFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//
//package com.example.AuthenticationProject.config;
//
//import com.example.AuthenticationProject.auth.security.JwtFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    // These MUST be final and properly injected
//    private final JwtFilter jwtFilter;
//    private final RateLimitFilter rateLimitFilter;
//
//
//
//    @Autowired
//    public SecurityConfig(JwtFilter jwtFilter, RateLimitFilter rateLimitFilter) {
//        this.jwtFilter = jwtFilter;
//        this.rateLimitFilter = rateLimitFilter;
//    }
//
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // Add rate limit filter first
//                .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
//                // Then add JWT filter
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/api/auth/**",
//                                "/view/auth/**",
//                                "/api/auth/reset-password/**",
//                                "/api/auth/reset-success"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//            throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//}














package com.example.AuthenticationProject.config;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.AuthenticationProject.auth.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtAuthenticationFilter;
    // private  JwtFilter jwtAuthenticationFilter;  // ✅ Use constructor-based injection
//    @Autowired
//    private PasswordEncoder passwordEncoder;  // ✅ Correct (Spring injects the correct bean)


    @Autowired  // ✅ Injects JwtFilter via constructor
    public SecurityConfig(JwtFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()  // Enable CORS
                .and()
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // In SecurityConfig.java
                        .requestMatchers(
                                "/api/auth/signup",
                                "/api/auth/login",
                                "/api/auth/verify",
                                "/api/auth/forgot-password",  // Add this
                                "/api/auth/reset-password" ,
                                "/api/auth/forgot-password",
                                "/api/auth/reset-password",  // Allow reset password endpoint
                                "/api/auth/reset-password/**",
                                "/api/auth/reset-success",
                                "/view/auth/**",
                                "/api/auth/**"// Add this
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ensure filter is NOT null

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {  // ✅ Define bean, but DO NOT inject it here
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}










//package com.example.login_signup.security;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for API-based authentication
//                .cors(cors -> cors.configurationSource(request -> {
//                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
//                    corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
//                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
//                    corsConfig.setAllowedHeaders(List.of("*"));
//                    return corsConfig;
//                }))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/register", "/api/auth/login", "/api/auth/verify" , "/api/auth/forgot-password", "/api/auth/reset-password").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:3000")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*");
//            }
//        };
//    }
//}
