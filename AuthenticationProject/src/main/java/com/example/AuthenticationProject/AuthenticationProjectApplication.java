package com.example.AuthenticationProject;

import com.example.AuthenticationProject.config.RateLimitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableConfigurationProperties(RateLimitProperties.class)
public class AuthenticationProjectApplication {  // ✅ Class name must match the filename

	public static void main(String[] args) {SpringApplication.run(AuthenticationProjectApplication.class, args);  // ✅ Use correct class name
	System.out.println("Server is running...");
	}



	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins("http://localhost:3000")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*")
						.exposedHeaders("Retry-After", "X-Rate-Limit-Retry-After-Seconds")
						.allowCredentials(true)
						.maxAge(3600);
				;
			}
		};
	}
}
