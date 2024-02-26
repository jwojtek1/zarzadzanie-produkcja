package com.faktoria.zarzadzanieprodukcja.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Zezwalaj na dostęp do konsoli H2 bez uwierzytelniania
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                // Wyłącz zabezpieczenia CSRF i ramki dla ścieżek do konsoli H2
                .csrf().disable()
                .headers().frameOptions().disable();
    }
}