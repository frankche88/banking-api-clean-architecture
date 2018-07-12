package banking.customers.application.dto.deserializer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import banking.common.application.enumeration.RequestBodyType;
import banking.customers.application.dto.UpdateCustomerDto;

public class UpdateCustomerDtoDeserializer extends JsonDeserializer<UpdateCustomerDto> {
	
	Logger logger = LoggerFactory.getLogger(UpdateCustomerDtoDeserializer.class);

	@Override
	public UpdateCustomerDto deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		UpdateCustomerDto dto = null;
		
		try {
    		ObjectCodec objectCodec = jsonParser.getCodec();
    		
            JsonNode node = objectCodec.readTree(jsonParser);
            
            long id = 0;

            id = new Long (node.get("id").asText());
            
            String firstName = node.get("firstName").asText();
            
            String lastName = node.get("lastName").asText();
            
            Boolean active = node.get("active").asBoolean();
            
            dto = new UpdateCustomerDto(id, firstName, lastName, active);
            
            
    	} catch(Exception ex) {
    		logger.error(ex.getMessage(), ex);
    		dto = new UpdateCustomerDto(RequestBodyType.INVALID);
    	}
        return dto;
	}

}
