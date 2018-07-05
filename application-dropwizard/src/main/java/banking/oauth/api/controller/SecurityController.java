package banking.oauth.api.controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.common.api.controller.ResponseHandler;
import banking.common.application.EntityNotFoundResultException;
import banking.security.application.UserApplicationService;
import banking.security.application.dto.CredentialInputDto;
import banking.security.application.dto.JwTokenOutputDto;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/api")
public class SecurityController {

	@Inject
	ResponseHandler responseHandler;

	@Inject
	UserApplicationService userApplicationService;

	@POST
	@Path("/security")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response authenticateOATH(@FormParam("grant_type") String grantType, @FormParam("password") String password,
			@FormParam("username") String username) throws Exception {

		try {

			JwTokenOutputDto response = userApplicationService.verifyUser(username, password);

			if (response == null) {

				return this.responseHandler.getNotFoundObjectResponse("User not found");

			}

			return this.responseHandler.getOkObjectResponse(response);

		} catch (EntityNotFoundResultException ex) {	
			
			return this.responseHandler.getNotFoundObjectResponse("User not found", ex);

		} catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}

	@POST
	@Path("/auth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@UnitOfWork
	public Response authenticate(CredentialInputDto dto) throws Exception {

		try {

			JwTokenOutputDto response = userApplicationService.verifyUser(dto);

			if (response == null) {

				return this.responseHandler.getNotFoundObjectResponse("User not found");

			}

			return this.responseHandler.getOkObjectResponse(response);

		} catch (EntityNotFoundResultException ex) {	
			
			return this.responseHandler.getNotFoundObjectResponse("User not found", ex);

		} catch (IllegalArgumentException ex) {

			return this.responseHandler.getAppCustomErrorResponse(ex.getMessage());

		} catch (Throwable ex) {

			return this.responseHandler.getAppExceptionResponse(ex);
		}
	}

}
