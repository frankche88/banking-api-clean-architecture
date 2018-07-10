package banking.oauth;

import java.security.Key;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import banking.security.application.UserApplicationService;
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
	
	private static final String JWt_ENCODED_KEY = UserApplicationService.JWt_ENCODED_KEY;

	@Inject
	public OAuthDynamicFeature(OAuthAuthenticator authenticator, UserAuthorizer authorizer, Environment environment) {
		super(new OAuthCredentialAuthFilter.Builder<UserDto>().setAuthenticator(authenticator).setAuthorizer(authorizer)
				.setPrefix("Bearer").buildAuthFilter());
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UserDto.class));
	}

	// classes below may be external (internal for simplicity)

	@Singleton
	public static class OAuthAuthenticator implements Authenticator<String, UserDto> {

		@Override
		public Optional<UserDto> authenticate(String credentials) throws AuthenticationException {
			// buscar usuario en BD
			try {

				// decode the base64 encoded string
				byte[] decodedKey = Base64.getDecoder().decode(JWt_ENCODED_KEY);
				// rebuild key using SecretKeySpec
				Key secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

				Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(credentials);

				String username = jwsClaims.getBody().getSubject();
				
				Long customerId = (Long)jwsClaims.getBody().get("customerId");

				String role = (String) jwsClaims.getBody().get("role");
				
				//{"sub":"client1","customerId":2,"userName":"client1","role":"member"}

				UserDto user = new UserDto(username, customerId.longValue());

				user.addRole(role);

				return Optional.of(username != null ? user : null);

			} catch (SignatureException e) {

				throw new AuthenticationException("Not autenticate");
			}
		}
	}

	@Singleton
	public static class UserAuthorizer implements Authorizer<UserDto> {
		@Override
		public boolean authorize(UserDto user, String role) {
			
			if(user == null) {
				
				return false;
			}
			
			user.getRoles().contains(role);
			
			return user.getRoles().contains(role);
		}
	}
}
