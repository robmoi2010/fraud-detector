package com.goglotek.frauddetector.datastoreservice.configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Map;

public class AuthProvider implements AuthenticationProvider {
    public static class Builder {
        private RegisteredClientRepository clientRepo;
        private PasswordEncoder passwordEncoder;

        public Builder withClientRepository(RegisteredClientRepository repo) {
            this.clientRepo = repo;
            return this;
        }

        public Builder withPasswordEncoder(PasswordEncoder encoder) {
            this.passwordEncoder = encoder;
            return this;
        }

        public AuthProvider build() {
            return new AuthProvider(clientRepo, passwordEncoder);
        }
    }

    private RegisteredClientRepository clientRepo;
    private PasswordEncoder passwordEncoder;

    public AuthProvider(RegisteredClientRepository repo, PasswordEncoder encoder) {
        this.clientRepo = repo;
        this.passwordEncoder = encoder;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RegisteredClient client = clientRepo.findByClientId(authentication.getName());
        String secret = authentication.getCredentials().toString();
        boolean matches = passwordEncoder.matches(secret, client.getClientSecret());
        return new OAuth2ClientAuthenticationToken(
                client.getClientId(),
                ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
                null,
                Map.of()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
