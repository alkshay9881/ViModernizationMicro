package com.mobicule.authserver.provider;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CustomUserStorageProviderFactory
        implements UserStorageProviderFactory<CustomUserStorageProvider> {

    private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProviderFactory.class);

    @Override
    public CustomUserStorageProvider create(KeycloakSession session, ComponentModel model) {

        log.info("Creating CustomUserStorageProvider for realm component: {}", model.getName());

        return new CustomUserStorageProvider(session, model);
    }

    @Override
    public String getId() {
        return "mobile-auth-provider";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {

        log.info("Loading configuration properties for mobile-auth-provider");

        List<ProviderConfigProperty> config = new ArrayList<>();

        ProviderConfigProperty jdbc = new ProviderConfigProperty();
        jdbc.setName("jdbcUrl");
        jdbc.setLabel("JDBC URL");
        jdbc.setType(ProviderConfigProperty.STRING_TYPE);
        jdbc.setHelpText("Enter database JDBC URL");
        config.add(jdbc);

        ProviderConfigProperty user = new ProviderConfigProperty();
        user.setName("dbUsername");
        user.setLabel("DB Username");
        user.setType(ProviderConfigProperty.STRING_TYPE);
        config.add(user);

        ProviderConfigProperty pass = new ProviderConfigProperty();
        pass.setName("dbPassword");
        pass.setLabel("DB Password");
        pass.setType(ProviderConfigProperty.PASSWORD);
        config.add(pass);

        log.debug("Config properties loaded: {}", config.size());

        return config;
    }
}