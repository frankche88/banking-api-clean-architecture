package banking.customers.infrastructure.hibernate.repository;

import org.springframework.stereotype.Repository;

import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;
import banking.customers.domain.entity.Customer;
import banking.customers.domain.repository.CustomerRepository;

@Repository
public class CustomerHibernateRepository extends BaseHibernateRepository<Customer> implements CustomerRepository {

}