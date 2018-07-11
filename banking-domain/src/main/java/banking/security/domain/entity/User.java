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
	private String email;
	private boolean enabled = true;
	private long customerId = 0;
	private Set<UserRole> userRole = new HashSet<UserRole>(0);

	public User() {
	}

	public User(String username, String password, String email, boolean enabled) {

		this.username = username;

		this.password = BCrypt.hashpw(password, BCrypt.gensalt(15));

		this.enabled = enabled;

		this.email = email;
	}

	public User(String username, String password, String email,

		boolean enabled, Set<UserRole> userRole) {

		this.username = username;

		this.password = BCrypt.hashpw(password, BCrypt.gensalt(15));

		this.enabled = enabled;

		this.userRole = userRole;

		this.email = email;
	}

	public boolean verifyIdentity(String plainPassword) {

		return BCrypt.checkpw(plainPassword, password);
	}

	public String generateJWT(String jwtEncodedKey) {

		byte[] decodedKey = Base64.getDecoder().decode(jwtEncodedKey);

		Key secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

		String role = "ROLE_USER";

		if (userRole != null && userRole.size() > 0) {
			role = userRole.iterator().next().getRole();
		}

		String accessToken = Jwts.builder().setSubject(username).claim("customerId", customerId)
				.claim("userName", username).claim("role", role).signWith(SignatureAlgorithm.HS512, secretKey)
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

		this.password = password;
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

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", enabled=" + enabled + ", customerId="
				+ customerId + ", userRole=" + userRole + "]";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
