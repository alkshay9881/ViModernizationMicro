package com.mobicule.authserver.provider;

import com.mobicule.authserver.model.UserDetails;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class CustomUserAdapter extends AbstractUserAdapterFederatedStorage {

    private UserDetails user;

    private final String keycloakId;

    private static final Logger log = LoggerFactory.getLogger(CustomUserAdapter.class);


    public CustomUserAdapter(KeycloakSession session,
                             RealmModel realm,
                             ComponentModel model,
                             UserDetails user) {
        super(session, realm, model);

        log.info("CustomUserAdapter CREATED for mobile: {}", user.getMobNo());
        this.user = user;
        this.keycloakId = StorageId.keycloakId(model, user.getMobNo());

        log.debug("Generated keycloakId: {}", keycloakId);
    }

    // ?? Unique ID for Keycloak (VERY IMPORTANT)
    @Override
    public String getId() {
        return keycloakId;
    }

    // ? Mobile number as username
    @Override
    public String getUsername() {

        return user.getMobNo();
    }

    // ? Prevent username modification (read-only user)
    @Override
    public void setUsername(String username) {
        throw new UnsupportedOperationException("Username is read-only");
    }

    // ? Optional (can return null if not used)
    @Override
    public String getEmail() {
        return null;
    }


    @Override
    public Stream<String> getRequiredActionsStream() {
        return Stream.empty();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isEmailVerified() {
        return true;
    }

    @Override
    public String getFirstName() {
        return "";
    }

    @Override
    public String getLastName() {
        return "";
    }
}