package com.Rodolfo.RRamirezProgramacionNCapasMaven.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {
    
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource(); // Realiza la instancia para poder utilizar un objeto de tipo DriverManagerDataSource
        
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:orcl");
        dataSource.setUsername("RRamirezProgramacionNCapas");
        dataSource.setPassword("password1");
        
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource); // Retorna un objeto de tipo JdbcTemplate para decirle a la bases de datos que hacer
    }
    
}
