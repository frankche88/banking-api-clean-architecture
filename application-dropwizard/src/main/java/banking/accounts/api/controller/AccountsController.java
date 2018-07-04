package banking.accounts.api.controller;

import javax.inject.Inject;
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
import javax.ws.rs.core.Response;

import banking.accounts.application.BankAccountService;
import banking.accounts.application.dto.RequestBankAccountDto;
import banking.accounts.application.dto.ResponseBankAccountDto;
import banking.accounts.application.dto.mapper.BankAccountDtoMapper;
import banking.accounts.domain.entity.BankAccount;
import banking.common.api.controller.ResponseHandler;
import banking.common.application.dto.PaggedResponse;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/api/bankAccounts/")
public class AccountsController {

	@Inject
	BankAccountService bankAccountService;

	@Inject
	ResponseHandler responseHandler;

	@Inject
	BankAccountDtoMapper bankAccountDtoMapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response listAll(@DefaultValue("1") @QueryParam("pageNumber") int pageNumber,
			@DefaultValue("10") @QueryParam("pageSize") int pageSize) {
		try {

			PaggedResponse<ResponseBankAccountDto> response = bankAccountService.findAllPaged(pageNumber, pageSize);

			if (response == null) {

				return this.responseHandler.getNotFoundObjectResponse("Bank Accounts not found");

			}

			return this.responseHandler.getOkObjectResponse(response);

		} catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}

	@GET
	@Path("{bankAccountId}")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response findById(@PathParam("bankAccountId") long id) {
		try {

			BankAccount bankAccount = bankAccountService.getBankAccountById(id);
			if (bankAccount == null) {

				return this.responseHandler.getNotFoundObjectResponse("Bank Account not found");

			}
			return this.responseHandler.getOkObjectResponse(bankAccountDtoMapper.mapper(bankAccount));

		} catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}

	@GET
	@Path("/newNumber")
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response newNumber() {
		try {

			BankAccount bankAccount = new BankAccount();

	
			return this.responseHandler.getOkObjectResponse("{\"accountNumber\": \"" + bankAccount.generateNumber() + "\"}");

		} catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response addBankAccount(RequestBankAccountDto bankAccountDto) {
		try {

			bankAccountService.save(bankAccountDto);

			return this.responseHandler.getOkObjectResponse("Bank account saved: " + bankAccountDto.getId());

		} catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}

	@PUT
	@Path("{ID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response updateBankAccount(@PathParam("ID") long id, RequestBankAccountDto bankAccountDto) {

		try {

			BankAccount customer = bankAccountService.update(bankAccountDto);

			if (customer == null) {

				return this.responseHandler.getNotFoundObjectResponse("Bank account not found");

			}

			return this.responseHandler.getOkObjectResponse("Bank account updated: " + customer.getId());

		} catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}

}
