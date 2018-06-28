package banking.customers.domain.repository;

import java.util.List;

import banking.customers.domain.entity.Customer;

public interface CustomerRepository {

	List<Customer> findAllPaginated(int pageNumber, int pageSize);

	long countAll();
}
