package banking.oauth.api.controller;

import java.security.Key;
import java.util.Base64;

import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.oauth.server.AccessTokenResponse;
import io.dropwizard.hibernate.UnitOfWork;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/")
public class SecurityController {

	@POST
	@Path("/security")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response authenticate(@FormParam("grant_type") String grantType, 
			@FormParam("password") String password,
			@FormParam("username") String username) throws Exception {
		
		// get base64 encoded version of the key
		String encodedKey = "k8zgjphoSZl4aTtNKiOXMQ==";
		
		// decode the base64 encoded string
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		// rebuild key using SecretKeySpec
		Key secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		
		System.out.println("SecurityController.authenticate()" + encodedKey);
		
		//Key key = MacProvider.generateKey();
		
		String accessToken = Jwts.builder()
				  .setSubject("Joe")
				  .signWith(SignatureAlgorithm.HS512, secretKey)
				  .compact();
		System.out.println("SecurityController.authenticate() Bearer " + accessToken);
		
		AccessTokenResponse AccessTokenResponse = new AccessTokenResponse(accessToken, "jwt", 36000, accessToken,
				"frank");
		
		
		return Response.accepted(AccessTokenResponse).build();
	}

}
