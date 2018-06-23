/*
 *  Copyright (C) 2015  oauth2-dropwizard project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package banking.application.oauth2.auth;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banking.OAuth2Config;
import banking.application.oauth2.User;
import banking.application.oauth2.apifest.AccessToken;
import banking.application.oauth2.apifest.ApifestApiPath;
import banking.application.oauth2.apifest.TokenValidationResponse;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

/**
 * Takes the credentials extracted from the request by the SecurityProvider
 * and authenticates the principal to the oauth2 server.
 *
 * @author Edouard De Oliveira
 */
public class OAuth2Authenticator implements Authenticator<OAuth2Credentials, User> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2Authenticator.class);
    private Client client;
    private String validationUrl;

    public OAuth2Authenticator(OAuth2Config cfg, Client client) {
        this.validationUrl = cfg.getUrl() + ApifestApiPath.TOKENS_VALIDATION.getPath();
        this.client = client;
    }

    private TokenValidationResponse validateToken(AccessToken token) throws AuthenticationException {
        WebTarget target = client.target(validationUrl + token.getToken());
        Response response = target.request().accept(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != HttpStatus.OK_200 && response.getStatus() != HttpStatus.UNAUTHORIZED_401) {
            log.debug("Error {} : invalid token {}", token.getToken(), response.getStatus());
            throw new AuthenticationException("Invalid credentials");
        }

        return response.readEntity(TokenValidationResponse.class);
    }

    public Optional<User> authenticate(OAuth2Credentials credentials) throws AuthenticationException {
        try {
            AccessToken token;
            if (credentials instanceof OAuth2CookieCredentials) {
                token = ((OAuth2CookieCredentials) credentials).getToken();
            } else {
                token = new AccessToken();
                token.setToken(((OAuth2HeaderCredentials) credentials).getToken());
            }

            TokenValidationResponse tvr = validateToken(token);
            if (!tvr.isValid())
                return Optional.empty();

            String username = tvr.getUserId();
            if (credentials instanceof OAuth2CookieCredentials)
                username = ((OAuth2CookieCredentials) credentials).getUsername();

            return Optional.of(new User(username, token));
        } catch (Exception ex) {
            log.error("Exception during authentication", ex);
            throw new AuthenticationException("Invalid credentials");
        }
    }
}