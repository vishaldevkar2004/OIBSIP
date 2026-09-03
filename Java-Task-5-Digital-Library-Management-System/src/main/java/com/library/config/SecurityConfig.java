package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomAuthenticationProvider authenticationProvider;
    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(
            CustomAuthenticationProvider authenticationProvider,
            CustomAuthenticationSuccessHandler successHandler) {

        this.authenticationProvider = authenticationProvider;
        this.successHandler = successHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authenticationProvider(authenticationProvider)

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/",
                    "/register",
                    "/login",
                    "/about",
                    "/css/**",
                    "/js/**",
                    "/images/**"
                ).permitAll()

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                .requestMatchers("/admin-dashboard")
                .hasRole("ADMIN")

                .requestMatchers("/user-dashboard")
                .hasRole("USER")

                .requestMatchers("/books")
                .permitAll()
                
                .requestMatchers("/issue-book/**")
                .hasAnyRole("USER", "ADMIN")

                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(successHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}