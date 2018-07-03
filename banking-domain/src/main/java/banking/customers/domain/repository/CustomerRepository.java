package banking.customers.domain.repository;

import java.util.List;

import banking.customers.domain.entity.Customer;

public interface CustomerRepository {

	Customer findById(long id);
	
	List<Customer> findAllPaginated(int pageNumber, int pageSize);

	long countAll();

	Customer findByDni(String dni);

	void save(Customer customer);
}
