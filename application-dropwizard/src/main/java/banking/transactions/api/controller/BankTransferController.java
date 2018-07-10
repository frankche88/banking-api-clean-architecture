package banking.transactions.api.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.accounts.application.dto.mapper.BankAccountDtoMapper;
import banking.accounts.domain.entity.BankAccount;
import banking.common.api.controller.ResponseHandler;
import banking.common.application.EntityNotFoundResultException;
import banking.oauth.UserDto;
import banking.transactions.application.TransactionApplicationService;
import banking.transactions.application.dto.RequestBankTransferDto;
import banking.transactions.application.dto.ResponseBankTransferDto;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;

@Path("/api")
@PermitAll
@Api(value = "/api/bankTransfers")
public class BankTransferController {
	
	@Inject
	TransactionApplicationService transactionApplicationService;

	
	@Inject
	ResponseHandler responseHandler;

	@Inject
	BankAccountDtoMapper bankAccountDtoMapper;

	@POST
	@Path("/bankTransfers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response performTransfer(RequestBankTransferDto requestBankTransferDto) throws Exception {
		try {
			transactionApplicationService.performTransfer(requestBankTransferDto);
			return this.responseHandler.getOkCommandResponse("Transfer done!");
		} catch(IllegalArgumentException ex) {
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		} catch(Exception ex) {
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@GET
	@Path("/bankTransfers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response getAccountByUserLogged(@Auth UserDto user) throws Exception {
		try {
			long userId = user.getId();
			 
			
			List<ResponseBankTransferDto> bankAcounts = transactionApplicationService.findAcounstByUser(userId);

			if (bankAcounts == null) {

				return this.responseHandler.getNotFoundObjectResponse("Bank Accounts not found");

			}

			return this.responseHandler.getOkObjectResponse(bankAcounts);
			
		} catch (EntityNotFoundResultException ex) {	
			
			return this.responseHandler.getNotFoundObjectResponse("Bank Accounts not found");

		} catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
	@GET
	@Path("/bankTransfers/{bankAccountId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response getAccountById(@PathParam("bankAccountId") long id) throws Exception {
		
		try {

			BankAccount bankAccount = transactionApplicationService.getBankAccountById(id);
			
			return this.responseHandler.getOkObjectResponse(bankAccountDtoMapper.mapperResponse(bankAccount));

		} catch (EntityNotFoundResultException ex) {	
			
			return this.responseHandler.getNotFoundObjectResponse("Bank Accounts not found", ex);

		}  catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
}
