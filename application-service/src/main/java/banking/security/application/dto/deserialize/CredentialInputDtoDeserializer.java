package banking.security.application.dto.deserialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import banking.common.application.enumeration.RequestBodyType;
import banking.security.application.dto.CredentialInputDto;

public class CredentialInputDtoDeserializer extends JsonDeserializer<CredentialInputDto> {
	
	@Override
	public CredentialInputDto deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		CredentialInputDto dto = null;
		
		try {
    		ObjectCodec objectCodec = jsonParser.getCodec();
    		
            JsonNode node = objectCodec.readTree(jsonParser);
            
            String userName = node.get("userName").asText();
            
            String password = node.get("password").asText();
            
            dto = new CredentialInputDto(userName, password);
            
            
            
    	} catch(Exception ex) {
    		dto = new CredentialInputDto(RequestBodyType.INVALID);
    	}
        return dto;
	}

}
