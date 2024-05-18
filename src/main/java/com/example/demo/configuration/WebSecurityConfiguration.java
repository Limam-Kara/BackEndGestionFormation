package com.example.demo.configuration;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
    final Filter jwtRequestFilter;
    private final CustomLogoutHandler logoutHandler;

    public WebSecurityConfiguration(Filter jwtRequestFilter, CustomLogoutHandler logoutHandler) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                                auth.requestMatchers("/resetPassword","/forgetPassword", "/login","/thematiques/**","/utilisateurs/**","/**").permitAll()
                                        //.requestMatchers(getAdminlist().toArray(new String[0])).hasAuthority(RoleEnum.ADMINISTRATOR.name())
                                        //.requestMatchers(getCheckoutURLs().toArray(new String[0])).hasAuthority(RoleEnum.CHECKOUT.name())
                                        //.requestMatchers("/AULSH/**")
//                                .hasAnyAuthority("Admin", "Magasinier")
//                                .anyRequest()
                                        //.authenticated()
//                                .anyRequest().authenticated()
                )
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(l->l
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
