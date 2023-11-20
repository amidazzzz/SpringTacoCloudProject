package com.amida.tacocloud.config;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.amida.tacocloud.model.User;
import com.amida.tacocloud.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User ‘" + username + "’ not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/design"), new AntPathRequestMatcher("/orders")).access("hasRole('USER')")
                                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                )
                .build();
    }
}
