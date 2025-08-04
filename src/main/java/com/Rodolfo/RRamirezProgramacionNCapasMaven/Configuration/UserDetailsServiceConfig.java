package com.Rodolfo.RRamirezProgramacionNCapasMaven.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager usuario = new JdbcUserDetailsManager(dataSource);
        usuario.setUsersByUsernameQuery("SELECT UserName, Password, IsActivo FROM Usuario WHERE UserName = ?");
        usuario.setAuthoritiesByUsernameQuery(
                "SELECT Usuario.UserName, Rol.Nombre FROM Usuario JOIN Rol ON Usuario.IdRol = Rol.IdRol WHERE Usuario.UserName = ?");
        return usuario;
    }
}
