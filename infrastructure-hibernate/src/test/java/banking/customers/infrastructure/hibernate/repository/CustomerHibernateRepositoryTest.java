package banking.customers.infrastructure.hibernate.repository;

import static junit.framework.TestCase.assertNotNull;

import java.util.List;

import org.junit.Test;

import banking.commons.infrastructure.hibernate.repository.JPAHibernateTest;
import banking.customers.domain.entity.Customer;

public class CustomerHibernateRepositoryTest extends JPAHibernateTest {
	
	CustomerHibernateRepository customerRepository = new CustomerHibernateRepository();
	
	@Test
    public void testGetObjectById_success() {
		
		customerRepository.setSessionFactory(this.sessionFactory);
		List<Customer> book = customerRepository.findAllPaginated(1, 10);
        assertNotNull(book);
    }

}
