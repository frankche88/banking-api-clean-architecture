package banking.common.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import banking.common.application.dto.ErrorDto;
import banking.common.application.dto.ResponseDto;
import banking.common.application.dto.ResponseOkCommandDto;

@Component()
public class ResponseHandler {
	private Logger LOGGER = LoggerFactory.getLogger(ResponseHandler.class);
	
	public ResponseEntity<Object> getOkCommandResponse(String message) {
		ResponseDto responseDto = new ResponseDto();
		ResponseOkCommandDto responseOkCommandDto = new ResponseOkCommandDto();
		responseOkCommandDto.setHttpStatus(HttpStatus.CREATED.value());
		responseOkCommandDto.setMessage(message);
		responseDto.setResponse(responseOkCommandDto);
		return new ResponseEntity<Object>(responseDto.getResponse(), HttpStatus.CREATED);
    }
	
	public ResponseEntity<Object> getAppCustomErrorResponse(String errorMessages)
    {
		return new ResponseEntity<Object>(new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessages), HttpStatus.BAD_REQUEST);
    }
	
	public ResponseEntity<Object> getAppExceptionResponse(Exception ex)
    {
		LOGGER.error("Error",ex);
        return new ResponseEntity<Object>(new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
