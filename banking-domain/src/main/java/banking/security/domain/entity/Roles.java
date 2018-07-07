package banking.security.domain.entity;

public enum Roles {
	ROLE_ADMIN("admin"), ROLE_MEMBER("member");

	Roles(String role) {
		this.role = role;
	}

	private String role;

	@Override
	public String toString() {
		return role;
	}

}
