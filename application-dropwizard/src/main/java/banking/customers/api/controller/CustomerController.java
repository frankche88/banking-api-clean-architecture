package banking.customers.api.controller;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.common.api.controller.ResponseHandler;
import banking.common.application.dto.PaggedResponse;
import banking.customers.application.CustomerApplicationService;
import banking.customers.application.dto.CustomerDto;
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
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response listAll(
			@DefaultValue("1") @QueryParam("pageNumber")int pageNumber, 
			@DefaultValue("10") @QueryParam("pageSize")int pageSize) {
		
		try {
		
			PaggedResponse<CustomerDto>  response = customerApplicationService.findAllPaged(pageNumber, pageSize);
			
			if(response == null ) {
				
				return this.responseHandler.getNotFoundObjectResponse("Customers not found");
				
			}
			
			return this.responseHandler.getOkObjectResponse(response);
			
		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@GET
	@Path("{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response getCustomerById(@PathParam("customerId") Long id) throws Exception {
		
		try {
			
			Customer  customer = customerApplicationService.getCustomerById(id);
			if(customer == null) {
				
				return this.responseHandler.getNotFoundObjectResponse("Customer not found");
				
			}
			return this.responseHandler.getOkObjectResponse(customerDtoMapper.mapper(customer));
			
		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@GET
	@Path("{dni}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response getCustomerByDni(@PathParam("dni") String id) throws Exception {
		
		try {
			
			Customer  customer = customerApplicationService.getCustomerByDni(id);
			if(customer == null) {
				
				return this.responseHandler.getNotFoundObjectResponse("Customer not found");
				
			}
			return this.responseHandler.getOkObjectResponse(customerDtoMapper.mapper(customer));
			
		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@POST
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response saveCustomer(CustomerDto customer) throws Exception {
		
		try {
			
			customerApplicationService.save(customer);
			
			return this.responseHandler.getOkObjectResponse("Customer saved: " + customer.getId());
			
		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response updateCustomer(CustomerDto customer) throws Exception {
		
		try {
			
			customer = customerApplicationService.update(customer);
			
			if(customer == null) {
				
				return this.responseHandler.getNotFoundObjectResponse("Customer not found");
				
			}
			
			return this.responseHandler.getOkObjectResponse("Customer saved: " + customer.getId());
			
		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
}
