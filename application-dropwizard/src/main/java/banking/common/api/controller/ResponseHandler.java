package banking.common.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import banking.common.application.dto.ErrorDto;
import banking.common.application.dto.ResponseDto;
import banking.common.application.dto.ResponseErrorDto;
import banking.common.application.dto.ResponseOkCommandDto;

public class ResponseHandler {
	
	private Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
	
	public Response getOkCommandResponse(String message) {
		ResponseDto responseDto = new ResponseDto();
		ResponseOkCommandDto responseOkCommandDto = new ResponseOkCommandDto();
		responseOkCommandDto.setHttpStatus(HttpStatus.CREATED_201);
		responseOkCommandDto.setMessage(message);
		responseDto.setResponse(responseOkCommandDto);
		return Response.ok().entity(responseDto.getResponse()).status(HttpStatus.CREATED_201).build();
    }
	
	
	public Response getOkObjectResponse(Object message) {
		
		return Response.ok().entity(message).status(HttpStatus.OK_200).build();
    }
	
	public Response getAppCustomErrorResponse(String errorMessages)
    {
		logger.debug(errorMessages);
		
		ResponseDto responseDto = new ResponseDto();
		String[] errors = errorMessages.split(",");
		List<ErrorDto> errorsDto = new ArrayList<ErrorDto>();
        for (String error : errors) {
            errorsDto.add(new ErrorDto(error));
        }
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(errorsDto);
        responseErrorDto.setHttpStatus(HttpStatus.BAD_REQUEST_400);
        responseDto.setResponse(responseErrorDto);
        return Response.ok().entity(responseDto.getResponse()).status(HttpStatus.BAD_REQUEST_400).build();
    }
	
	public Response getAppExceptionResponse(Throwable e)
    {
		logger.error(e.getMessage(), e);
		
		ResponseDto responseDto = new ResponseDto();
		List<ErrorDto> errorsDto = new ArrayList<ErrorDto>();
        errorsDto.add(new ErrorDto("Server error"));
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(errorsDto);
        responseErrorDto.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
        responseDto.setResponse(responseErrorDto);
        //return new ResponseEntity<Object>(responseDto.getResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        return Response.ok().entity(responseDto.getResponse()).status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
    }
}
