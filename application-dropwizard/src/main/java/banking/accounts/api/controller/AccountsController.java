package banking.accounts.api.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import banking.accounts.application.dto.BankAccountDto;



@Path("/api/accounts/")
public class AccountsController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BankAccountDto> listAll(
			@DefaultValue("1") @QueryParam("pageNumber")int pageNumber, 
			@DefaultValue("10") @QueryParam("pageSize")int pageSize) {
		return null;
	}
	
	@GET
	@Path("{ID}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<BankAccountDto> findById(@PathParam("ID") long id) {
		return null;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<BankAccountDto> addBankAccount(BankAccountDto bankAccountDto) {
		return null;
	}
	
	@PUT
	@Path("{ID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<BankAccountDto> updateBankAccount(@PathParam("ID") long id, BankAccountDto bankAccountDto) {
		return null;
	}
	
	
	

	
}
