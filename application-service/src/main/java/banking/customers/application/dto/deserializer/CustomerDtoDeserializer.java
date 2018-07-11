package banking.customers.application.dto.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import banking.common.application.enumeration.RequestBodyType;
import banking.customers.application.dto.CustomerDto;

public class CustomerDtoDeserializer extends JsonDeserializer<CustomerDto> {

	@Override
	public CustomerDto deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		CustomerDto dto = null;
		
		try {
    		ObjectCodec objectCodec = jsonParser.getCodec();
    		
            JsonNode node = objectCodec.readTree(jsonParser);
            
            long id = 0;
            try {
            	id = new Long (node.get("fromAccountNumber").asText());
            } catch(RuntimeException e) {
            	
            }

            String documentNumber = node.get("dni").asText();
            
            String firstName = node.get("firstName").asText();
            
            String lastName = node.get("lastName").asText();
            
            String username = node.get("userName").asText();
            
            String password = node.get("password").asText();
            
            String email = node.get("email").asText();
            
            dto = new CustomerDto(id, documentNumber, firstName, lastName);
            
            dto.setUserData(username, password, email);
            
    	} catch(Exception ex) {
    		dto = new CustomerDto();
    		dto.setRequestBodyType(RequestBodyType.INVALID);
    	}
        return dto;
	}

}
