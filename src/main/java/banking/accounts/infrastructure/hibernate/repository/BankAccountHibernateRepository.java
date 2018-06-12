package banking.accounts.infrastructure.hibernate.repository;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import banking.accounts.domain.entity.BankAccount;
import banking.accounts.domain.repository.BankAccountRepository;
import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;

@Repository
public class BankAccountHibernateRepository extends BaseHibernateRepository<BankAccount>
		implements BankAccountRepository {
	@Override
	public BankAccount findByNumber(String accountNumber) throws Exception {
		Criteria criteria = getSession().createCriteria(BankAccount.class);
		criteria.add(Restrictions.eq("number", accountNumber));
		return (BankAccount) criteria.uniqueResult();
	}

	@Override
	public BankAccount findByNumberLocked(String accountNumber) throws Exception {
		Criteria criteria = getSession().createCriteria(BankAccount.class);
		criteria.add(Restrictions.eq("number", accountNumber));
		criteria.setLockMode(LockMode.PESSIMISTIC_WRITE);
		return (BankAccount) criteria.uniqueResult();
	}
	
	public void save(BankAccount bankAccount) {
		super.save(bankAccount);
	}
}
