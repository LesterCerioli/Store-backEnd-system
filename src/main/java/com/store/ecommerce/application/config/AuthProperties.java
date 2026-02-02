package com.store.ecommerce.application.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration properties for Auth / JWT.
 * Binds auth.jwt.secret and auth.clients from application.yml.
 */
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private JwtProperties jwt = new JwtProperties();
    private List<ClientCredentials> clients = List.of();

    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public List<ClientCredentials> getClients() {
        return clients;
    }

    public void setClients(List<ClientCredentials> clients) {
        this.clients = clients;
    }

    /**
     * Returns client credentials as a map: clientId -> secret.
     */
    public Map<String, String> getClientCredentialsMap() {
        Map<String, String> map = new HashMap<>();
        for (ClientCredentials c : clients) {
            if (c.getClientId() != null && c.getSecret() != null) {
                map.put(c.getClientId(), c.getSecret());
            }
        }
        return map;
    }

    public static class JwtProperties {
        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public static class ClientCredentials {
        private String clientId;
        private String secret;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}
