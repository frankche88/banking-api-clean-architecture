package banking.oauth;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public class UserDto implements Principal {
	
	private String name;
	
	private long customerId;
	
	private Set<String> roles = new HashSet<>();

	public UserDto(String name, long customerId) {
		this.customerId = customerId;
		this.name = name;
	}

	public UserDto(String name, Set<String> roles) {
	    this.name = name;
    	this.roles = roles;
    }
	
	public void addRole(String role) {
		this.roles.add(role);
    }

    public String getName() {
	    return name;
    }

    public long getId() {
	    return customerId;
    }

	public Set<String> getRoles() {
		return roles;
	}

}
