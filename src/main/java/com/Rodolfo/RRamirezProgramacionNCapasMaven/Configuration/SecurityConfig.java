package com.Rodolfo.RRamirezProgramacionNCapasMaven.Configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Set;
import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(config -> config
                .requestMatchers("/usuario/CargaMasiva").hasRole("ADMINISTRADOR")
                .requestMatchers(HttpMethod.GET, "/usuario/**").hasAnyRole("ANALISTA", "PROGRAMADOR")
                .requestMatchers("/usuario/**").hasRole("PROGRAMADOR")
                .anyRequest()
                .authenticated())
                .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(role())
                .permitAll())
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                );

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationSuccessHandler role() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            Set<String> roles;
            roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (roles.contains("ROLE_PROGRAMADOR")) {
                response.sendRedirect("/usuario");
            } else if (roles.contains("ROLE_ADMINISTRADOR")) {
                response.sendRedirect("/usuario/CargaMasiva");
            } else if (roles.contains("ROLE_ANALISTA")) {
                response.sendRedirect("/usuario");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails programador = User.builder()
                .username("Rodolfo")
                .password(passwordEncoder().encode("password123"))
                .roles("PROGRAMADOR")
                .build();

        UserDetails administrador = User.builder()
                .username("Gabriel")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMINISTRADOR")
                .build();

        UserDetails analista = User.builder()
                .username("Pedro")
                .password(passwordEncoder().encode("qwerty123"))
                .roles("ANALISTA")
                .build();

        return new InMemoryUserDetailsManager(programador, administrador, analista);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
