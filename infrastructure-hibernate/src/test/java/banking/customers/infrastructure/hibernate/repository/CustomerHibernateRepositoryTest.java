package banking.customers.infrastructure.hibernate.repository;

import static junit.framework.TestCase.assertNotNull;

import java.util.List;

import org.junit.Test;

import banking.commons.infrastructure.hibernate.repository.JPAHibernateTest;
import banking.customers.domain.entity.Customer;

public class CustomerHibernateRepositoryTest extends JPAHibernateTest {
	
	CustomerHibernateRepository customerRepository = new CustomerHibernateRepository();
	
	//@Test
    public void testFindAllPaginated_success() {
		
		sessionFactory.getCurrentSession().beginTransaction();
		
		customerRepository.setSessionFactory(sessionFactory);
		
		List<Customer> book = customerRepository.findAllPaginated(1, 10);
		
        assertNotNull(book);
        
        sessionFactory.getCurrentSession().getTransaction().commit();
    }
	
	//@Test
    public void testFindById_success() throws Exception {
		
		sessionFactory.getCurrentSession().beginTransaction();
		
		customerRepository.setSessionFactory(sessionFactory);
		
		Customer book = customerRepository.findById(1);
		
        assertNotNull(book);
        
        sessionFactory.getCurrentSession().getTransaction().commit();
    }

}
