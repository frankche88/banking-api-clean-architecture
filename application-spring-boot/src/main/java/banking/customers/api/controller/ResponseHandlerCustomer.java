package banking.customers.api.controller;

import banking.common.application.dto.ResponseDto;
import banking.customers.domain.entity.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component()
public class ResponseHandlerCustomer {

    public ResponseEntity<Object> getCustomer(Customer customer) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponse(customer);
        return new ResponseEntity<Object>(responseDto.getResponse(), HttpStatus.OK);
    }

}
