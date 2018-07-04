package banking.accounts.infrastructure.hibernate.repository;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

//import org.hibernate.Criteria;
//import org.hibernate.LockMode;
//import org.hibernate.criterion.Restrictions;

import banking.accounts.domain.entity.BankAccount;
import banking.accounts.domain.repository.BankAccountRepository;
import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;

@Named
@Singleton
public class BankAccountHibernateRepository extends BaseHibernateRepository<BankAccount>
		implements BankAccountRepository {

	@Override
	public BankAccount findById(long id) {
		
		TypedQuery<BankAccount> query = findByField("id", id);

		return query.getSingleResult();
	}

	@Override
	public BankAccount findByNumber(String accountNumber) throws Exception {

		TypedQuery<BankAccount> query = findByField("number", accountNumber);

		return query.getSingleResult();
	}

	@Override
	public BankAccount findByNumberLocked(String accountNumber) throws Exception {

		TypedQuery<BankAccount> query = findByField("number", accountNumber);

		query.setLockMode(LockModeType.PESSIMISTIC_WRITE);

		return query.getSingleResult();

	}

	private TypedQuery<BankAccount> findByField(String field, Object valueField) {

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<BankAccount> criteriaQuery = criteriaBuilder.createQuery(BankAccount.class);

		Root<BankAccount> from = criteriaQuery.from(BankAccount.class);

		Predicate condition = criteriaBuilder.equal(from.get(field), valueField);

		criteriaQuery.select(from).where(condition);

		TypedQuery<BankAccount> query = getSession().createQuery(criteriaQuery);
		
		return query;
	}

	public void save(BankAccount bankAccount) {
		super.save(bankAccount);
	}

	@Override
	public long countBankAccount() {

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		TypedQuery<Long> query = countAllQuery(criteriaBuilder);

		Long count = query.getSingleResult();

		return count;
	}

	private TypedQuery<Long> countAllQuery(CriteriaBuilder criteriaBuilder) {
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		
		countQuery.select(criteriaBuilder.count(countQuery.from(BankAccount.class)));
		
		TypedQuery<Long> query = getSession().createQuery(countQuery);
		return query;
	}

	@Override
	public List<BankAccount> findAllPaginated(int pageNumber, int pageSize) {
		
		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
		
		
		CriteriaQuery<Long> indexCriteria = criteriaBuilder.createQuery(Long.class);
		
		Root<BankAccount> fromIndex = indexCriteria.from(BankAccount.class);
		
		indexCriteria.multiselect(fromIndex.get("id"));
		
		TypedQuery<Long> indexQuery = getSession().createQuery(indexCriteria);
		
		indexQuery.setFirstResult((pageNumber - 1) * pageSize);
		indexQuery.setMaxResults(pageSize);
		
		List<Long> indexPaged = indexQuery.getResultList();
		
		// paged result
		CriteriaQuery<BankAccount> criteriaQuery = criteriaBuilder.createQuery(BankAccount.class);
		
		Root<BankAccount> from = criteriaQuery.from(BankAccount.class);
		
		CriteriaQuery<BankAccount> select = criteriaQuery.select(from);
		
		Predicate condition = from.get("id").in(indexPaged);
		
		

		TypedQuery<BankAccount> typedQuery = getSession().createQuery(select.where(condition));

		

		return typedQuery.getResultList();
	}

}
