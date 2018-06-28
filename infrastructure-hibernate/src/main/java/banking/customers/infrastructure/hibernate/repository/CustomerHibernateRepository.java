package banking.customers.infrastructure.hibernate.repository;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;
import banking.customers.domain.entity.Customer;
import banking.customers.domain.repository.CustomerRepository;

@Named
@Singleton
public class CustomerHibernateRepository extends BaseHibernateRepository<Customer> implements CustomerRepository {
	
	
	@Override
	public List<Customer> findAllPaginated(int pageNumber, int pageSize) {
		
		
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		
		
		CriteriaQuery<Long> indexCriteria = criteriaBuilder.createQuery(Long.class);
		
		Root<Customer> fromIndex = indexCriteria.from(Customer.class);
		
		indexCriteria.multiselect(fromIndex.get("id"));
		
		TypedQuery<Long> indexQuery = getSession().createQuery(indexCriteria);
		
		indexQuery.setFirstResult(pageNumber - 1);
		indexQuery.setMaxResults(pageSize);
		
		List<Long> indexPaged = indexQuery.getResultList();
		
		// paged result
		CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
		
		Root<Customer> from = criteriaQuery.from(Customer.class);
		
		CriteriaQuery<Customer> select = criteriaQuery.select(from);
		
		Predicate condition = criteriaBuilder.in(from.get("number")).in(indexPaged);
		
		

		TypedQuery<Customer> typedQuery = getSession().createQuery(select.where(condition));

		

		return typedQuery.getResultList();
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