package banking.security.domain.entity;

import java.security.Key;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class User {
	
	private String username;
	private String password;
	private boolean enabled = true;
	private long customerId = 0;
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	
	public User() {
	}

	public User(String username, String password, boolean enabled) {
		
		this.username = username;
		this.password = BCrypt.hashpw(password, BCrypt.gensalt(15));
		this.enabled = enabled;
	}

	public User(String username, String password, 
		boolean enabled, Set<UserRole> userRole) {
		this.username = username;
		this.password = BCrypt.hashpw(password, BCrypt.gensalt(15));
		this.enabled = enabled;
		this.userRole = userRole;
	}
	
	public boolean verifyIdentity(String plainPassword) {
		
        return BCrypt.checkpw(plainPassword, password);
    }
	
	
	public String generateJWT(String jwtEncodedKey) {
		

		byte[] decodedKey = Base64.getDecoder().decode(jwtEncodedKey);

		Key secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		
		// todo: add all claims
		
		String accessToken = Jwts.builder()
				  .setSubject(username)
				  .signWith(SignatureAlgorithm.HS512, secretKey)
				  .compact();
		
		return accessToken;
	}
	
	public void addUserRole(UserRole role) {
		this.userRole.add(role);
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		
		this.password = BCrypt.hashpw(password, BCrypt.gensalt(15));
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<UserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	
	

}
