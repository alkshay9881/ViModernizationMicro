package com.mobicule.authserver.provider;

import com.mobicule.authserver.model.UserDetails;
import com.mobicule.authserver.repository.UserRepository;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialModel;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.*;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CustomUserStorageProvider implements
        UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator,
        UserQueryProvider {

    private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProvider.class);

    private final KeycloakSession session;
    private final ComponentModel model;
    private final UserRepository repository;

    public CustomUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;

        String jdbcUrl = model.getConfig().getFirst("jdbcUrl");
        String dbUser = model.getConfig().getFirst("dbUsername");
        String dbPass = model.getConfig().getFirst("dbPassword");

        log.info("Initializing CustomUserStorageProvider");

        this.repository = new UserRepository(jdbcUrl, dbUser, dbPass);
    }

    // =========================
    // USER LOOKUP
    // =========================

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {

        log.info("getUserByUsername: {}", username);

        UserDetails user = repository.findByMobile(username);

        if (user == null) return null;

        return new CustomUserAdapter(session, realm, model, user);
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {

        log.info("getUserById: {}", id);

        String username = StorageId.externalId(id);

        return getUserByUsername(realm, username);
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        return null; // not supported
    }

    // =========================
    // PASSWORD SUPPORT
    // =========================

    @Override
    public boolean supportsCredentialType(String type) {
        return CredentialModel.PASSWORD.equals(type);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String type) {
        return supportsCredentialType(type);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {

        log.info("Password validation for: {}", user.getUsername());

        if (!supportsCredentialType(input.getType())) {
            return false;
        }

        String enteredPassword = input.getChallengeResponse();

        if (enteredPassword == null) return false;

        UserDetails dbUser = repository.findByMobile(user.getUsername());

        if (dbUser == null) return false;

        String storedPassword = dbUser.getLoginPassword();

        if (storedPassword == null) return false;

        boolean valid = enteredPassword.equals(storedPassword);

        log.info("Login result for {}: {}", user.getUsername(), valid);

        return valid;
    }

    // =========================
    // USER SEARCH (UI + ADMIN CONSOLE)
    // =========================

    @Override
    public Stream<UserModel> searchForUserStream(
            RealmModel realm,
            Map<String, String> params,
            Integer firstResult,
            Integer maxResults) {

        log.info("RAW PARAMS: {}", params);

        String search = null;

        //  Extract search param properly
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();

            if (key.endsWith(".search") || key.endsWith("search")) {
                search = entry.getValue();
                break;
            }
        }

        int first = firstResult == null ? 0 : firstResult;
        int max = maxResults == null ? 50 : maxResults;

        log.info("FINAL SEARCH VALUE: {}", search);

        List<UserDetails> users;

        // ⭐ IMPORTANT LOGIC
        if (search == null || search.trim().isEmpty() || "*".equals(search)) {

            log.info("Calling findAll()");

            users = repository.findAll(first, max);

        } else {

            log.info("Calling searchByMobile()");

            users = repository.searchByMobile(search, first, max);
        }

        log.info("DB RESULT SIZE: {}", users.size());

        return users.stream()
                .map(user -> new CustomUserAdapter(session, realm, model, user));
    }
    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(
            RealmModel realm,
            String attributeName,
            String attributeValue) {

        log.info("searchForUserByUserAttributeStream: {}={}", attributeName, attributeValue);

        if ("mobile".equals(attributeName)) {

            UserDetails user = repository.findByMobile(attributeValue);

            if (user != null) {
                return Stream.of(new CustomUserAdapter(session, realm, model, user));
            }
        }

        return Stream.empty();
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(
            RealmModel realm,
            GroupModel group,
            Integer firstResult,
            Integer maxResults) {

        return Stream.empty();
    }

    // =========================
    // CLOSE
    // =========================

    @Override
    public void close() {
        log.info("CustomUserStorageProvider closed");
    }
}