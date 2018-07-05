package banking.customers.application;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import banking.common.application.Notification;
import banking.common.application.dto.PaggedResponse;
import banking.common.application.enumeration.RequestBodyType;
import banking.customers.application.dto.CustomerDto;
import banking.customers.application.dto.mapper.CustomerToCustomerDtoMapper;
import banking.customers.domain.entity.Customer;
import banking.customers.domain.repository.CustomerRepository;
import banking.security.domain.entity.User;
import banking.security.domain.repository.UserRepository;

@Named
public class CustomerApplicationService {

	@Inject
	private CustomerRepository customerRepository;
	
	@Inject
	private UserRepository userRepository;

	@Inject
	CustomerToCustomerDtoMapper customerDtoMapper;

	@Transactional
	public Customer getCustomerById(long id) throws Exception {
		Notification notification = this.validation(id);
		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		Customer customer = this.customerRepository.findById(id);
		// Customer customer = this.customerRepository.findOne(id);
		return customer;
	}

	@Transactional
	public PaggedResponse<CustomerDto> findAllPaged(int pageNumber, int pageSize) throws Exception {
		Notification notification = this.validation(pageNumber, pageSize);
		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		List<Customer> customers = this.customerRepository.findAllPaginated(pageNumber, pageSize);

		if (customers == null || customers.size() == 0) {
			return null;
		}

		long totalRecords = this.customerRepository.countAll();

		PaggedResponse<CustomerDto> response = new PaggedResponse<CustomerDto>(customerDtoMapper.mapper(customers),
				pageNumber, pageSize, totalRecords);

		return response;
	}

	@Transactional
	public Customer getCustomerByDni(String dni) {
		Notification notification = this.validation(dni);
		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		Customer customer = this.customerRepository.findByDni(dni);
		return customer;
	}

	@Transactional
	public void save(CustomerDto dto) {

		Notification notification = this.validation(dto);

		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}

		Customer customer = customerDtoMapper.reverseMapper(dto);
		
		User user = customerDtoMapper.userMapper(dto);

		this.customerRepository.save(customer);

		user.setCustomerId(customer.getId());

		userRepository.save(user);

		dto.setId(customer.getId());

	}

	public CustomerDto update(CustomerDto dto) {
		Notification notification = this.validation(dto);

		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		

		Customer customer = this.customerRepository.findById(dto.getId());
		
		if(customer == null) {
			
			return null;
		}

		

		customer = customerDtoMapper.mergeDtoToCustomer(dto, customer);

		// todo: save usuario

		this.customerRepository.save(customer);

		dto.setId(customer.getId());
		
		return dto;

	}

	private Notification validation(String dni) {
		Notification notification = new Notification();
		if (StringUtils.isEmpty(dni)) {
			notification.addError("Customer dni must be present.");
		}
		return notification;
	}

	private Notification validation(int pageNumber, int pageSize) {
		Notification notification = new Notification();
		if (pageNumber <= 0) {
			notification.addError("Page must be greater than zero.");
		}
		if (pageSize <= 0) {
			notification.addError("Page size must be greater than zero.");
			return notification;
		}
		return notification;
	}

	private Notification validation(long customerId) {
		Notification notification = new Notification();
		if (customerId <= 0) {
			notification.addError("Customer id must be greater than zero.");
		}
		return notification;
	}

	private Notification validation(CustomerDto dto) {
		Notification notification = new Notification();
		if (dto == null || dto.getRequestBodyType() == RequestBodyType.INVALID) {
			notification.addError("Invalid JSON data in request body.");
		}
		return notification;
	}
}
