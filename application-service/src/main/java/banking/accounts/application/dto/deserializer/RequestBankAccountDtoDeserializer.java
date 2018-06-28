package banking.accounts.application.dto.deserializer;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import banking.accounts.application.dto.RequestBankAccountDto;
import banking.common.application.enumeration.RequestBodyType;

public class RequestBankAccountDtoDeserializer extends JsonDeserializer<RequestBankAccountDto> {
	
	private Logger logger = LoggerFactory.getLogger(RequestBankAccountDtoDeserializer.class);
	
	@Override
	public RequestBankAccountDto deserialize(JsonParser jsonParser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		RequestBankAccountDto requestBankTransferDto = null;
		try {
			
			
			
    		ObjectCodec objectCodec = jsonParser.getCodec();
            JsonNode node = objectCodec.readTree(jsonParser);
            
            long id = new Long(node.get("id").asText());
            long customerId = new Long(node.get("customerId").asText());
            String accountNumber = node.get("number").asText();
            BigDecimal balance = new BigDecimal(node.get("balance").asText());
            boolean isLocked = new Boolean(node.get("isLocked").asText());
            
            requestBankTransferDto = new RequestBankAccountDto(id, accountNumber, balance, isLocked, customerId, RequestBodyType.VALID);
    	} catch(Exception ex) {
    		
    		logger.error("RequestBankAccountDtoDeserializer", ex);
    		requestBankTransferDto = new RequestBankAccountDto(RequestBodyType.INVALID);
    	}
        return requestBankTransferDto;
	}	
}
