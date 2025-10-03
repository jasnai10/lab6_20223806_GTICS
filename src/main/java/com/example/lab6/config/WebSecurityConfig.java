package com.example.lab6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {

    final DataSource dataSource;

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Query para obtener usuarios (username = correo)
        manager.setUsersByUsernameQuery(
                "SELECT correo AS username, password, true AS enabled FROM usuarios WHERE correo=?"
        );

        // Query para obtener roles
        manager.setAuthoritiesByUsernameQuery(
                "SELECT u.correo AS username, CONCAT('ROLE_', r.nombre) AS authority " +
                        "FROM usuarios u JOIN roles r ON u.rol_id = r.id WHERE u.correo=?"
        );

        return manager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/heroes", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/heroes/nuevo", "/heroes/guardar").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/processLogin")
                        .defaultSuccessUrl("/heroes", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );
        return http.build();
    }
}
