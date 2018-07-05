package banking.oauth;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

public class UserDto implements Principal {
	
	String name;
	
	private Set<String> roles = new HashSet<>();

	public UserDto(String name) {
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

    public int getId() {
	    return (int) (Math.random() * 100);
    }

	public Set<String> getRoles() {
		return roles;
	}

}
