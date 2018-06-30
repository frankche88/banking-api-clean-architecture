package banking.customers.application.dto.mapper;

import banking.customers.application.dto.CustomerDto;
import banking.customers.domain.entity.Customer;

public class CustomerToCustomerDtoMapper {

	public CustomerDto mapper(Customer customer) {
		
		
		CustomerDto dto = new CustomerDto(customer.getDocumentNumber(), customer.getFirstName(), customer.getLastName());

		return dto;
	}

}
