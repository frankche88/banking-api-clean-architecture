package banking.customers.api.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.common.api.controller.ResponseHandler;
import banking.customers.application.CustomerApplicationService;
import banking.customers.application.dto.mapper.CustomerToCustomerDtoMapper;
import banking.customers.domain.entity.Customer;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/api/customers/")
public class CustomerController {
	
	@Inject
	CustomerApplicationService customerApplicationService;

	
	@Inject
	ResponseHandler  responseHandler;
	
	@Inject
	CustomerToCustomerDtoMapper customerDtoMapper;
	
	@GET
	@Path("{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response getCustomerById(@PathParam("customerId") Long id) throws Exception {
		
		try {
			
			Customer  customer = customerApplicationService.getCustomerById(id);
			return this.responseHandler.getOkObjectResponse(customerDtoMapper.mapper(customer));
			
		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Exception ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}  
}
