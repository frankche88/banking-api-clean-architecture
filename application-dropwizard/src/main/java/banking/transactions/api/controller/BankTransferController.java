package banking.transactions.api.controller;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.common.api.controller.ResponseHandler;
import banking.transactions.application.TransactionApplicationService;
import banking.transactions.application.dto.RequestBankTransferDto;
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
	
}
