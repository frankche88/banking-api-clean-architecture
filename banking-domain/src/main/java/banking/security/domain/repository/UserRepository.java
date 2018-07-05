package banking.security.domain.repository;

import banking.security.domain.entity.User;

public interface UserRepository {
	
	public User findByUserName(String username);
	
	public void save(User user);

}
