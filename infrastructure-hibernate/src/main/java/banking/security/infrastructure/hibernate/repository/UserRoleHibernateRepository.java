package banking.security.infrastructure.hibernate.repository;

import javax.inject.Named;
import javax.inject.Singleton;

import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;
import banking.security.domain.entity.UserRole;
import banking.security.domain.repository.UserRoleRepository;

@Named
@Singleton
public class UserRoleHibernateRepository extends BaseHibernateRepository<UserRole>  implements UserRoleRepository {

	

}
