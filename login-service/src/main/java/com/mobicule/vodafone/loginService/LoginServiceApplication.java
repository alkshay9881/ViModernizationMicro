package com.mobicule.vodafone.loginService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class LoginServiceApplication {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(LoginServiceApplication.class, args);
    }

    @PostConstruct
    public void testConnection() throws Exception {
        log.info("DB Connection: " + dataSource.getConnection());
    }


}
