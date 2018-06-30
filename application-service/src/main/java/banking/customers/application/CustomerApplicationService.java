package banking.customers.application;

import javax.inject.Inject;
import javax.inject.Named;

import banking.common.application.Notification;
import banking.customers.domain.entity.Customer;
import banking.customers.domain.repository.CustomerRepository;

@Named
public class CustomerApplicationService {
	
	@Inject
	private CustomerRepository customerRepository;

	
    public Customer getCustomerById(long id) throws Exception {
        Notification notification = this.validation(id);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        Customer customer = this.customerRepository.findById(id);
        //Customer customer = this.customerRepository.findOne(id);
        return customer;
    }

    private Notification validation(long customerId) {
        Notification notification = new Notification();
        if (customerId <= 0) {
            notification.addError("Customer id must be greater than zero.");
        }
        return notification;
    }	
}
