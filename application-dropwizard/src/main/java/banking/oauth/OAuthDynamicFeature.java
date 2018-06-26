package banking.oauth;



import java.security.Key;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Environment;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Provider
public class OAuthDynamicFeature extends AuthDynamicFeature {

    @Inject
    public OAuthDynamicFeature(OAuthAuthenticator authenticator, 
                                UserAuthorizer authorizer, 
                                Environment environment) {
        super(new OAuthCredentialAuthFilter.Builder<User>()
                .setAuthenticator(authenticator)
                .setAuthorizer(authorizer)
                .setPrefix("Bearer")
                .buildAuthFilter());
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    // classes below may be external (internal for simplicity)

	@Singleton
	public static class OAuthAuthenticator implements Authenticator<String, User> {

		@Override
		public Optional<User> authenticate(String credentials) throws AuthenticationException{
			// buscar usuario en BD
			try {
			
				// get base64 encoded version of the key
				String encodedKey = "k8zgjphoSZl4aTtNKiOXMQ==";
				
				// decode the base64 encoded string
				byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
				// rebuild key using SecretKeySpec
				Key secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
			
			
				System.out.println("OAuthDynamicFeature.OAuthAuthenticator.authenticate()" + credentials);
				Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(credentials);
				
				String username = jwsClaims.getBody().getSubject();

			
			return Optional.of(username != null ? new User(username) : null);
			
			} catch (SignatureException e) {

			    throw new AuthenticationException("acceso negado");
			}
		}
	}

    @Singleton
    public static class UserAuthorizer implements Authorizer<User> {
        @Override
        public boolean authorize(User user, String role) {
        	System.out.println("OAuthDynamicFeature.UserAuthorizer.authorize() role: " + role);
            return user.getName().equals("good-guy") && role.equals("ADMIN");
        }
    }   
}
