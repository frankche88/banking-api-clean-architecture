package banking.customers.application.dto.mapper;

import javax.inject.Named;
import javax.inject.Singleton;

import banking.customers.application.dto.CustomerDto;
import banking.customers.domain.entity.Customer;

@Named
@Singleton
public class CustomerToCustomerDtoMapper {

	public CustomerDto mapper(Customer customer) {
		
		
		CustomerDto dto = new CustomerDto(customer.getDocumentNumber(), customer.getFirstName(), customer.getLastName());

		return dto;
	}

}
