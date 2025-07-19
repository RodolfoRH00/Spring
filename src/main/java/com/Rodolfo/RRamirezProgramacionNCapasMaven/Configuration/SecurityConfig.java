package com.Rodolfo.RRamirezProgramacionNCapasMaven.Configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        var handler = new XorCsrfTokenRequestAttributeHandler();

        handler.setCsrfRequestAttributeName(null);
        httpSecurity.csrf(csrf -> csrf.disable()
//                .csrfTokenRequestHandler(handler)
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        )
                .authorizeHttpRequests(config -> config
                ////               .requestMatchers("/usuario").hasAnyAuthority("Administrador", "Soporte Tecnico")
                .requestMatchers(HttpMethod.GET, "/usuario/CargaMasiva/**").hasAnyAuthority("Soporte Tecnico", "Administrador")
                .requestMatchers(HttpMethod.POST, "/usuario/CargaMasiva/**").hasAnyAuthority("Soporte Tecnico", "Administrador")
                .requestMatchers("/usuario/form/**").hasAuthority("Administrador")
                //                .requestMatchers(HttpMethod.GET, "/usuario/**").hasAnyAuthority("Administrador", "Usuario", "Soporte Tecnico")
                //                .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAuthority("Administrador")
                //                .requestMatchers(HttpMethod.PATCH, "/usuario/**").hasAuthority("Administrador")
                //                .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasAuthority("Administrador")
                .anyRequest()
                .authenticated())
                .exceptionHandling(accessDenied -> accessDenied
                .accessDeniedHandler(handle()))
                .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(role())
                .permitAll())
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll());

        return httpSecurity.build();
    }

    @Bean
    public AccessDeniedHandler handle() {
        return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) -> {
            response.sendRedirect(request.getContextPath() + "/accessDenied");
        };
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager usuario = new JdbcUserDetailsManager(dataSource);
        usuario.setUsersByUsernameQuery("SELECT UserName, Password, IsActivo FROM Usuario WHERE UserName = ?");
        usuario.setAuthoritiesByUsernameQuery("SELECT Usuario.UserName, Rol.Nombre FROM Usuario " + "JOIN Rol ON Usuario.IdRol = Rol.IdRol WHERE Usuario.UserName = ?");
        return usuario;
    }

    @Bean
    public AuthenticationSuccessHandler role() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            Set<String> roles;
            roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (roles.contains("Administrador")) {
                response.sendRedirect("/usuario");
            } else if (roles.contains("Soporte Tecnico")) {
                response.sendRedirect("/usuario/CargaMasiva");
            } else if (roles.contains("Usuario")) {
                response.sendRedirect("/usuario");
            } else {
                response.sendRedirect("/accessDenied");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
