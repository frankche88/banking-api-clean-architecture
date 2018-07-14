package banking.customers.infrastructure.hibernate.repository;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import banking.common.application.EntityNotFoundResultException;
import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;
import banking.customers.domain.entity.Customer;
import banking.customers.domain.repository.CustomerRepository;
import banking.security.domain.entity.User;

@Named
@Singleton
public class CustomerHibernateRepository extends BaseHibernateRepository<Customer> implements CustomerRepository {

	@Override
	public Customer findById(long id) {
		
		try {

			CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		
			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
		
			Root<Customer> from = criteriaQuery.from(Customer.class);
		
			CriteriaQuery<Customer> select = criteriaQuery.select(from);
		
			Predicate condition = criteriaBuilder.equal(from.get("id"), id);
		
			TypedQuery<Customer> typedQuery = getSession().createQuery(select.where(condition));
			
			//return typedQuery.getSingleResult();
			
			Customer customer = typedQuery.getSingleResult();
			
			User user = findUserByIdCustomer(id);
			
			customer.setUser(user);
			
			return customer;
			
		} catch (javax.persistence.NoResultException e) {
			
			throw new EntityNotFoundResultException(e.getMessage(), e);
		}
	}
	
	
	
    public User findUserByIdCustomer(long idCustomer) {
        
        try {

            CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        
            Root<User> from = criteriaQuery.from(User.class);
        
            CriteriaQuery<User> select = criteriaQuery.select(from);
        
            Predicate condition = criteriaBuilder.equal(from.get("customerId"), idCustomer);
        
            TypedQuery<User> typedQuery = getSession().createQuery(select.where(condition));
            
            
        
            return typedQuery.getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            
            throw new EntityNotFoundResultException(e.getMessage(), e);
        }
    }
	

	@Override
	public Customer findByDni(String dni) {
		try {
			CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
	
			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
	
			Root<Customer> from = criteriaQuery.from(Customer.class);
	
			CriteriaQuery<Customer> select = criteriaQuery.select(from);
	
			Predicate condition = criteriaBuilder.equal(from.get("documentNumber"), dni);
	
			TypedQuery<Customer> typedQuery = getSession().createQuery(select.where(condition));
	
			return typedQuery.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			
			throw new EntityNotFoundResultException(e.getMessage(), e);
		}
	}

	@Override
	public List<Customer> findAllPaginated(int pageNumber, int pageSize) {
		
		try {

			CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
	
			CriteriaQuery<Long> indexCriteria = criteriaBuilder.createQuery(Long.class);
	
			Root<Customer> fromIndex = indexCriteria.from(Customer.class);
	
			indexCriteria.multiselect(fromIndex.get("id"));
	
			TypedQuery<Long> indexQuery = getSession().createQuery(indexCriteria);
	
			indexQuery.setFirstResult((pageNumber - 1) * pageSize);
			indexQuery.setMaxResults(pageSize);
	
			List<Long> indexPaged = indexQuery.getResultList();
			
			if(indexPaged == null || indexPaged.size()<=0 ) {
				
				throw new EntityNotFoundResultException("Customers not found");
				
			}
	
			// paged result
			CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
	
			Root<Customer> from = criteriaQuery.from(Customer.class);
	
			CriteriaQuery<Customer> select = criteriaQuery.select(from);
	
			Predicate condition = from.get("id").in(indexPaged);
	
			TypedQuery<Customer> typedQuery = getSession().createQuery(select.where(condition));
	
			return typedQuery.getResultList();
		} catch (javax.persistence.NoResultException e) {
			
			throw new EntityNotFoundResultException(e.getMessage(), e);
		}
	}

	@Override
	public long countAll() {

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		TypedQuery<Long> query = countAllQuery(criteriaBuilder);

		Long count = query.getSingleResult();

		return count;
	}

	private TypedQuery<Long> countAllQuery(CriteriaBuilder criteriaBuilder) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		countQuery.select(criteriaBuilder.count(countQuery.from(Customer.class)));

		TypedQuery<Long> query = getSession().createQuery(countQuery);
		return query;
	}

}