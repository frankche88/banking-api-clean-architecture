package banking.customers.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import banking.common.api.controller.ResponseHandler;
import banking.customers.application.CustomerApplicationService;
import banking.customers.domain.entity.Customer;

@RestController
@RequestMapping("api/customers/")
public class CustomerController {
	@Autowired
	CustomerApplicationService customerApplicationService;

	@Autowired
	ResponseHandlerCustomer responseHandlerCustomer;

	@Autowired
	ResponseHandler  responseHandler;        
	@RequestMapping(method = RequestMethod.GET, path = "customer/{customerId}")
	public ResponseEntity<Object> getCustomerById(@PathVariable("customerId") Long id) throws Exception {
		try {
			Customer  customer = customerApplicationService.getCustomerById(id);
			//Customer  customer = new Customer();//customerApplicationService.getCustomerById(customerId);
//			customer.setId(1);
//			customer.setFirstName("Felipe");
//			customer.setLastName("Llancachagua");
			return this.responseHandlerCustomer.getCustomer(customer);
		} catch(IllegalArgumentException ex) {
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		} catch(Exception ex) {
			return this.responseHandler.getAppExceptionResponse();
		}
	}    

}
