package com.mobicule.vodafone.loginService.common.config.database.local;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;



import javax.sql.DataSource;


@Configuration
@Profile("local")
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@192.168.1.11:1521:orcl");
        dataSource.setUsername("Vodafone_ekyc");
        dataSource.setPassword("welcome123");

        return dataSource;
    }

}
