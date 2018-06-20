package banking.transactions.api.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import banking.common.api.controller.ResponseHandler;
import banking.transactions.application.TransactionApplicationService;
import banking.transactions.application.dto.RequestBankTransferDto;

@Path("api/")
public class BankTransferController {
	
	@Autowired
	TransactionApplicationService transactionApplicationService;
	
	@Autowired
	ResponseHandler responseHandler;

	@POST
	@Path("transfers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	@Path("transfers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response hola(RequestBankTransferDto requestBankTransferDto) throws Exception {
		try {
			//transactionApplicationService.performTransfer(requestBankTransferDto);
			return this.responseHandler.getOkCommandResponse("Transfer done!");
		} catch(IllegalArgumentException ex) {
			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());
		} catch(Exception ex) {
			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}
	
}
