package banking.customers.application.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import banking.customers.application.dto.CustomerDto;
import banking.customers.application.dto.UpdateCustomerDto;
import banking.customers.domain.entity.Customer;
import banking.security.domain.entity.Roles;
import banking.security.domain.entity.User;
import banking.security.domain.entity.UserRole;

@Named
@Singleton
public class CustomerToCustomerDtoMapper {

	public CustomerDto mapper(Customer customer) {

		CustomerDto dto = new CustomerDto(customer.getId(), customer.getDocumentNumber(), customer.getFirstName(),
				customer.getLastName());
		
		if(customer.getUser() != null) {
		    
		    String password = "";
		    
		    String username = customer.getUser().getUsername();
		    
		    String email = customer.getUser().getEmail();
		    
		    dto.setUserData(username, password, email);
		}

		return dto;
	}

	public Customer reverseMapper(CustomerDto dto) {

		Customer customer = new Customer();

		customer.setId(dto.getId());

		customer.setDocumentNumber(dto.getDni());

		customer.setFirstName(dto.getFirstName());

		customer.setLastName(dto.getLastName());
		
		customer.setActive(dto.isActive());

		return customer;
	}

	public List<CustomerDto> mapper(List<Customer> customers) {

		List<CustomerDto> lstCustomer = new ArrayList<>();

		for (Customer customer : customers) {

			lstCustomer.add(mapper(customer));

		}

		return lstCustomer;
	}

	public Customer mergeDtoToCustomer(UpdateCustomerDto dto, Customer customer) {

		if (dto.getId() == 0) {

			customer.setId(dto.getId());
		}

		if (dto.getFirstName() != null) {
			customer.setFirstName(dto.getFirstName());
		}

		if (dto.getLastName() != null) {
			customer.setLastName(dto.getLastName());
		}
		
		if (dto.getActive() != null) {
			customer.setActive(dto.getActive());
		}

		return customer;
	}

	public User userMapper(CustomerDto dto) {
		
		String username = dto.getUserName();
		
		String password = dto.getPassword();
		
		String email = dto.getEmail();
		
		User user = new User(username, password, email, true);
		
		UserRole role = new UserRole(user, Roles.ROLE_MEMBER.toString());
		
		user.addUserRole(role);
		
		
		return user;
	}

}
