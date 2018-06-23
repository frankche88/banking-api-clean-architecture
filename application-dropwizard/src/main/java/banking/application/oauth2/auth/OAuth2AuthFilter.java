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

import io.dropwizard.auth.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banking.application.oauth2.apifest.CookieToken;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A Dropwizard AuthFilter for OAuth2 HTTP authentication.
 *
 * @author Edouard De Oliveira
 */
@Priority(Priorities.AUTHENTICATION)
public final class OAuth2AuthFilter<P extends Principal> extends AuthFilter<OAuth2Credentials, P> {
    private final static Logger log = LoggerFactory.getLogger(OAuth2AuthFilter.class);

    protected final static String AUTH_COOKIE_NAME = "_dw_auth_cookie";
    private final static String BEARER_TYPE = "Bearer";

    private CookieEncrypter cookieEncrypter;

    private OAuth2AuthFilter() {
    }

    /**
     * Builder for {@link OAuth2AuthFilter}.
     * Mandatory parameters to be set :
     * <p>An {@link Authenticator}</p>
     * <p>An {@link CookieEncrypter}</p>
     */
    public static class Builder<P extends Principal, A extends Authenticator<OAuth2Credentials, P>> {
        private String prefix = BEARER_TYPE;
        private CookieEncrypter cookieEncrypter;
        private A authenticator;
        private Authorizer<P> authorizer = new PermitAllAuthorizer();
        private UnauthorizedHandler unauthorizedHandler = new DefaultUnauthorizedHandler();

        public Builder() {
        }

        public OAuth2AuthFilter.Builder<P, A> setCookieEncrypter(CookieEncrypter cookieEncrypter) {
            this.cookieEncrypter = cookieEncrypter;
            return this;
        }

        public OAuth2AuthFilter.Builder<P, A> setAuthenticator(A authenticator) {
            this.authenticator = authenticator;
            return this;
        }

        public OAuth2AuthFilter.Builder<P, A> setPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public OAuth2AuthFilter.Builder<P, A> setAuthorizer(Authorizer<P> authorizer) {
            this.authorizer = authorizer;
            return this;
        }

        public OAuth2AuthFilter.Builder<P, A> setUnauthorizedHandler(UnauthorizedHandler unauthorizedHandler) {
            this.unauthorizedHandler = unauthorizedHandler;
            return this;
        }

        public OAuth2AuthFilter<P> build() {
            Objects.requireNonNull(this.prefix, "Prefix is not set");
            Objects.requireNonNull(this.cookieEncrypter, "CookieEncrypter is not set");
            Objects.requireNonNull(this.authenticator, "Authenticator is not set");
            Objects.requireNonNull(this.authorizer, "Authorizer is not set");
            Objects.requireNonNull(this.unauthorizedHandler, "Unauthorized handler is not set");

            OAuth2AuthFilter<P> filter = new OAuth2AuthFilter<>();
            filter.prefix = this.prefix;
            filter.cookieEncrypter = this.cookieEncrypter;
            filter.authenticator = this.authenticator;
            filter.authorizer = this.authorizer;
            filter.unauthorizedHandler = this.unauthorizedHandler;

            return filter;
        }
    }

    public void filter(final ContainerRequestContext requestContext) {
        try {
            OAuth2Credentials creds = null;

            // Extract credentials
            Map<String, Cookie> map = requestContext.getCookies();
            Cookie cookie = map.get(AUTH_COOKIE_NAME);

            if (cookie != null) {
                CookieToken ct = cookieEncrypter.readCookie(cookie);
                creds = new OAuth2CookieCredentials(ct.getUsername(), ct.getToken());
            } else {
                String authString = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

                if (authString != null && authString.startsWith(prefix)) {
                    String authToken = authString.substring(prefix.length()+1);
                    creds = new OAuth2HeaderCredentials(authToken);
                }
            }

            if (creds == null)
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);

            Optional<P> principal = authenticator.authenticate(creds);

            if (principal.isPresent()) {
                final P userPrincipal = principal.get();
                requestContext.setSecurityContext(new SecurityContext() {
                    public Principal getUserPrincipal() {
                        return userPrincipal;
                    }

                    public boolean isUserInRole(String role) {
                        return authorizer.authorize(userPrincipal, role);
                    }

                    public boolean isSecure() {
                        return requestContext.getSecurityContext().isSecure();
                    }

                    public String getAuthenticationScheme() {
                        return SecurityContext.BASIC_AUTH;
                    }
                });
            }
        } catch (AuthenticationException e) {
            log.warn("Error authenticating credentials", e);
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        } catch (WebApplicationException wex) {
            throw wex;
        } catch (Exception ex) {
            log.error("Exception during cookie extraction", ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}