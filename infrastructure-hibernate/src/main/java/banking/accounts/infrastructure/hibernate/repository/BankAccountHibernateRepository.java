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
	public BankAccount findByNumber(String accountNumber) throws Exception {

		TypedQuery<BankAccount> query = findByNumberQuery(accountNumber);

		return query.getSingleResult();

		// Criteria criteria = getSession().createCriteria(BankAccount.class);
		// criteria.add(Restrictions.eq("number", accountNumber));
		// return (BankAccount) criteria.uniqueResult();
	}

	@Override
	public BankAccount findByNumberLocked(String accountNumber) throws Exception {

		TypedQuery<BankAccount> query = findByNumberQuery(accountNumber);

		query.setLockMode(LockModeType.PESSIMISTIC_WRITE);

		return query.getSingleResult();

		// List<BankAccount> results = query.getResultList();

		// Criteria criteria = getSession().createCriteria(BankAccount.class);
		// criteria.add(Restrictions.eq("number", accountNumber));
		// criteria.setLockMode(LockMode.PESSIMISTIC_WRITE);
		// return (BankAccount) criteria.uniqueResult();

	}

	private TypedQuery<BankAccount> findByNumberQuery(String accountNumber) {

		CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();

		CriteriaQuery<BankAccount> criteriaQuery = criteriaBuilder.createQuery(BankAccount.class);

		Root<BankAccount> from = criteriaQuery.from(BankAccount.class);

		Predicate condition = criteriaBuilder.equal(from.get("number"), accountNumber);

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
		
		indexQuery.setFirstResult(pageNumber - 1);
		indexQuery.setMaxResults(pageSize);
		
		List<Long> indexPaged = indexQuery.getResultList();
		
		// paged result
		CriteriaQuery<BankAccount> criteriaQuery = criteriaBuilder.createQuery(BankAccount.class);
		
		Root<BankAccount> from = criteriaQuery.from(BankAccount.class);
		
		CriteriaQuery<BankAccount> select = criteriaQuery.select(from);
		
		Predicate condition = criteriaBuilder.in(from.get("number")).in(indexPaged);
		
		

		TypedQuery<BankAccount> typedQuery = getSession().createQuery(select.where(condition));

		

		return typedQuery.getResultList();
	}

}
