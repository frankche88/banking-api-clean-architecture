package banking.customers.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import banking.common.application.dto.ResponseDto;
import banking.customers.application.dto.CustomerDto;

@Component()
public class ResponseHandlerCustomer {

    public ResponseEntity<Object> getCustomer(CustomerDto customerDto) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponse(customerDto);
        return new ResponseEntity<Object>(responseDto.getResponse(), HttpStatus.OK);
    }

}
