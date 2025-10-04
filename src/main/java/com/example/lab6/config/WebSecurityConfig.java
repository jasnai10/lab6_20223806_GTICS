package com.example.lab6.config;

import com.example.lab6.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig {

    final DataSource dataSource;
    final UsuarioRepository usuarioRepository;

    public WebSecurityConfig(DataSource dataSource, UsuarioRepository usuarioRepository) {
        this.dataSource = dataSource;
        this.usuarioRepository = usuarioRepository;
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

                        .requestMatchers("/juego").hasRole("USUARIO")
                        .requestMatchers("/juego/ranking").authenticated()
                        .requestMatchers("/admin/asignaciones/**").hasRole("ADMIN")

                        .requestMatchers("/reserva/**").hasRole("USUARIO")
                        .requestMatchers("/admin/mesas/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/processLogin")
                        .successHandler((request, response, authentication) -> {
                            DefaultSavedRequest defaultSavedRequest =
                                    (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                            HttpSession session = request.getSession();

                            usuarioRepository.findByCorreo(authentication.getName())
                                    .ifPresent(usuario -> session.setAttribute("usuario", usuario));

                            response.sendRedirect("/heroes");
                        })

                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );
        return http.build();
    }
}
