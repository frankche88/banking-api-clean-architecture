package banking.customers.api.controller;

import javax.annotation.security.PermitAll;
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
import banking.common.application.EntityNotFoundResultException;
import banking.common.application.dto.PaggedResponse;
import banking.customers.application.CustomerApplicationService;
import banking.customers.application.dto.CustomerDto;
import banking.customers.application.dto.mapper.CustomerToCustomerDtoMapper;
import banking.customers.domain.entity.Customer;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/api/customers/")
@Api(value = "/api/customers/")
@PermitAll
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
	@ApiOperation(value = "List pagged customers",
		httpMethod = "GET",
	    response = CustomerDto.class,
	    responseContainer = "List")
	public Response listAll(
			@DefaultValue("1") @QueryParam("pageNumber")int pageNumber, 
			@DefaultValue("10") @QueryParam("pageSize")int pageSize) {
		
		try {
		
			PaggedResponse<CustomerDto>  response = customerApplicationService.findAllPaged(pageNumber, pageSize);
			
			if(response == null ) {
				
				return this.responseHandler.getNotFoundObjectResponse("Customers not found");
				
			}
			
			return this.responseHandler.getOkObjectResponse(response);
			
		} catch (EntityNotFoundResultException ex) {	
			
			return this.responseHandler.getNotFoundObjectResponse("Customer not found", ex);

		}  catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@GET
	@Path("{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@ApiOperation(value = "find customer by ID",
		httpMethod = "GET",
	    response = CustomerDto.class)
	public Response getCustomerById(@PathParam("customerId") Long id) throws Exception {
		
		try {
			
			Customer  customer = customerApplicationService.getCustomerById(id);
			if(customer == null) {
				
				return this.responseHandler.getNotFoundObjectResponse("Customer not found");
				
			}
			return this.responseHandler.getOkObjectResponse(customerDtoMapper.mapper(customer));
			
		} catch (EntityNotFoundResultException ex) {	
			
			return this.responseHandler.getNotFoundObjectResponse("Customer not found", ex);

		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@GET
	@Path("/dni/{dni}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@ApiOperation(value = "find customer by dni",
	httpMethod = "GET",
    response = CustomerDto.class)
	public Response getCustomerByDni(@PathParam("dni") String dni) throws Exception {
		
		try {
			
			Customer  customer = customerApplicationService.getCustomerByDni(dni);
			if(customer == null) {
				
				return this.responseHandler.getNotFoundObjectResponse("Customer not found");
				
			}
			return this.responseHandler.getOkObjectResponse(customerDtoMapper.mapper(customer));
			
		} catch (EntityNotFoundResultException ex) {	
			
			return this.responseHandler.getNotFoundObjectResponse("Customer not found", ex);

		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	@ApiOperation(value = "Create customer",
	httpMethod = "POST")
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
	@ApiOperation(value = "Update customer",
	httpMethod = "PUT")
	public Response updateCustomer(CustomerDto customer) throws Exception {
		
		try {
			
			customer = customerApplicationService.update(customer);
			
			if(customer == null) {
				
				return this.responseHandler.getNotFoundObjectResponse("Customer not found");
				
			}
			
			return this.responseHandler.getOkObjectResponse("Customer updated: " + customer.getId());
			
		} catch(IllegalArgumentException ex) {
			
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		
		} catch(Throwable ex) {
		
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
}
