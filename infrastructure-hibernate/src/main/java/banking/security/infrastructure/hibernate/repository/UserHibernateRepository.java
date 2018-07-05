package banking.security.infrastructure.hibernate.repository;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import banking.common.application.EntityNotFoundResultException;
import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;
import banking.security.domain.entity.User;
import banking.security.domain.repository.UserRepository;

@Named
@Singleton
public class UserHibernateRepository extends BaseHibernateRepository<User>  implements UserRepository {

	@Override
	public User findByUserName(String username) {
		
		try {
		
			CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
	
			CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
	
			Root<User> from = criteriaQuery.from(User.class);
	
			CriteriaQuery<User> select = criteriaQuery.select(from);
	
			Predicate condition = criteriaBuilder.equal(from.get("username"), username);
	
			TypedQuery<User> typedQuery = getSession().createQuery(select.where(condition));
	
			return typedQuery.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			
			throw new EntityNotFoundResultException(e.getMessage(), e);
		}
	}

}
