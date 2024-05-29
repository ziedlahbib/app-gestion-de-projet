package com.example.appgestiondeprojet.config;

import com.example.appgestiondeprojet.jwt.AuthEntryPointJwt;
import com.example.appgestiondeprojet.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.appgestiondeprojet.services.UserDetailsServiceImpl;
import com.example.appgestiondeprojet.config.CustomAccessDeniedHandler;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//(securedEnabled = true,
//jsr250Enabled = true,
//prePostEnabled = true) // by default
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    private CustomAccessDeniedHandler CustomAccessDeniedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(CustomAccessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/signup-superadmin").permitAll()
                                .requestMatchers("/auth/signup").hasAnyAuthority("ROLE_SUPERADMIN")
                                .requestMatchers("/auth/signin").permitAll()
                                .requestMatchers("/user/exist-userbyusername/**").permitAll()
                                .requestMatchers("/user/exist-userbyemail/**").permitAll()
                                .requestMatchers("/forgot").permitAll()
                                .requestMatchers("/reset/**").permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/tache/get-taches").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
