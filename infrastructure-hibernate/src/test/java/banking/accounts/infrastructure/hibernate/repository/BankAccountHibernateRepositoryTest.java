package banking.accounts.infrastructure.hibernate.repository;

import static junit.framework.TestCase.assertNotNull;

import java.util.List;

import org.junit.Test;

import banking.accounts.domain.entity.BankAccount;
import banking.commons.infrastructure.hibernate.repository.JPAHibernateTest;

public class BankAccountHibernateRepositoryTest extends JPAHibernateTest {
	
	BankAccountHibernateRepository customerRepository = new BankAccountHibernateRepository();
	
	@Test
    public void testGetObjectById_success() {
		
		sessionFactory.getCurrentSession().beginTransaction();
		
		customerRepository.setSessionFactory(sessionFactory);
		
		List<BankAccount> book = customerRepository.findAllPaginated(3, 5);
		
        assertNotNull(book);
        
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

}
