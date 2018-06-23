package banking.customers.infrastructure.hibernate.repository;

import javax.inject.Named;

import banking.common.infrastructure.hibernate.repository.BaseHibernateRepository;
import banking.customers.domain.entity.Customer;
import banking.customers.domain.repository.CustomerRepository;

@Named
public class CustomerHibernateRepository extends BaseHibernateRepository<Customer> implements CustomerRepository {

}