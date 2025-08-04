package com.Rodolfo.RRamirezProgramacionNCapasMaven.Configuration;

import com.Rodolfo.RRamirezProgramacionNCapasMaven.DAO.UsuarioJPADAOImplementation;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.ML.Result;
import com.Rodolfo.RRamirezProgramacionNCapasMaven.JPA.Usuario;
import jakarta.servlet.http.Cookie;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var handler = new XorCsrfTokenRequestAttributeHandler();
        handler.setCsrfRequestAttributeName(null);

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(config -> config
                .requestMatchers(HttpMethod.GET, "/usuario/CargaMasiva/**").hasAnyAuthority("Soporte Tecnico", "Administrador")
                .requestMatchers(HttpMethod.POST, "/usuario/CargaMasiva/**").hasAnyAuthority("Soporte Tecnico", "Administrador")
                .requestMatchers(HttpMethod.GET, "/usuario/**").hasAnyAuthority("Administrador", "Usuario", "Soporte Tecnico")
                .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAuthority("Administrador")
                .requestMatchers(HttpMethod.PATCH, "/usuario/**").hasAuthority("Administrador")
                .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasAuthority("Administrador")
                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(accessDenied -> accessDenied.accessDeniedHandler(handle()))
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
        return (request, response, exception)
                -> response.sendRedirect(request.getContextPath() + "/accessDenied");
    }

    @Bean
    public AuthenticationSuccessHandler role() {
        return (request, response, authentication) -> {
            String jwt = jwtTokenProvider.generateToken(authentication.getName());

            response.setHeader("Authorization", "Bearer " + jwt); // opción 1 (sólo lectura JS)

            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            if (roles.contains("Administrador")) {
                response.sendRedirect("/usuario");
            } else if (roles.contains("Soporte Tecnico")) {
                response.sendRedirect("/usuario/CargaMasiva");
            } else if (roles.contains("Usuario")) {
                Result result = usuarioJPADAOImplementation.GetIdByUserName(authentication.getName());
                Usuario usuario = (Usuario) result.object;
                response.sendRedirect("/usuario/form/" + usuario.getIdUsuario());
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
