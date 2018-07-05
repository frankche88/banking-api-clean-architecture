package banking.customers.application.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import banking.customers.application.dto.CustomerDto;
import banking.customers.domain.entity.Customer;
import banking.security.domain.entity.User;
import banking.security.domain.entity.UserRole;

@Named
@Singleton
public class CustomerToCustomerDtoMapper {

	public CustomerDto mapper(Customer customer) {

		CustomerDto dto = new CustomerDto(customer.getId(), customer.getDocumentNumber(), customer.getFirstName(),
				customer.getLastName());

		return dto;
	}

	public Customer reverseMapper(CustomerDto dto) {

		Customer customer = new Customer();

		customer.setId(dto.getId());

		customer.setDocumentNumber(dto.getDni());

		customer.setFirstName(dto.getFirstName());

		customer.setLastName(dto.getLastName());

		return customer;
	}

	public List<CustomerDto> mapper(List<Customer> customers) {

		List<CustomerDto> lstCustomer = new ArrayList<>();

		for (Customer customer : customers) {

			lstCustomer.add(mapper(customer));

		}

		return lstCustomer;
	}

	public Customer mergeDtoToCustomer(CustomerDto dto, Customer customer) {

		if (dto.getId() == 0) {

			customer.setId(dto.getId());
		}

		if (dto.getDni() != null) {
			customer.setDocumentNumber(dto.getDni());
		}

		if (dto.getFirstName() != null) {
			customer.setFirstName(dto.getFirstName());
		}

		if (dto.getLastName() != null) {
			customer.setLastName(dto.getLastName());
		}

		return customer;
	}

	public User userMapper(CustomerDto dto) {
		String username = dto.getPassword();
		String password = dto.getUserName();
		
		User user = new User(username, password, true);
		
		UserRole role = new UserRole(user, "ROLE_USER");
		
		user.addUserRole(role);
		
		
		return user;
	}

}
