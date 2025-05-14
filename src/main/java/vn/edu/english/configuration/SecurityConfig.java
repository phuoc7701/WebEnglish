package vn.edu.english.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Tắt CSRF nếu gọi từ Postman / client JS
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").permitAll() // <-- Cho phép mọi người gọi các API nội bộ
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}

