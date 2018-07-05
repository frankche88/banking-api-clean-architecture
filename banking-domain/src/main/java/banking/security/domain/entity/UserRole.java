package banking.security.domain.entity;

public class UserRole {
	
	private Integer userRoleId;
	private User user;
	private String role;
	
	
	public UserRole() {
	}

	public UserRole(User user, String role) {
		this.user = user;
		this.role = role;
	}
	
	
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return role;
	}
	
	

}
