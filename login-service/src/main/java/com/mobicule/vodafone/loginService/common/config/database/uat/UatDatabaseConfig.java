package com.mobicule.vodafone.loginService.common.config.database.uat;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Profile("uat")
public class UatDatabaseConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Bean
    public DataSource dataSource() {

        HikariDataSource ds = new HikariDataSource();

        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);

        //  Pool tuning (UAT )
        ds.setMaximumPoolSize(25);
        ds.setMinimumIdle(8);
        ds.setConnectionTimeout(20000);
        ds.setIdleTimeout(30000);
        ds.setMaxLifetime(1800000);
        ds.setValidationTimeout(5000);

        // Oracle validation
        ds.setConnectionTestQuery("SELECT 1 FROM DUAL");

        //  Oracle RAC failover tuning
        Properties props = new Properties();
        props.setProperty("oracle.net.CONNECT_TIMEOUT", "5000");
        props.setProperty("oracle.jdbc.ReadTimeout", "10000");
        props.setProperty("oracle.net.TCP_KEEPALIVE", "true");

        ds.setDataSourceProperties(props);

        return ds;
    }
}