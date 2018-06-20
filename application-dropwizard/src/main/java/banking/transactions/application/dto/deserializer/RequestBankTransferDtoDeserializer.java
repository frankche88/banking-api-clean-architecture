package banking.transactions.application.dto.deserializer;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import banking.common.application.enumeration.RequestBodyType;
import banking.transactions.application.dto.RequestBankTransferDto;

public class RequestBankTransferDtoDeserializer extends JsonDeserializer<RequestBankTransferDto> {
	@Override
	public RequestBankTransferDto deserialize(JsonParser jsonParser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		RequestBankTransferDto requestBankTransferDto = null;
		try {
    		ObjectCodec objectCodec = jsonParser.getCodec();
            JsonNode node = objectCodec.readTree(jsonParser);
            String fromAccountNumber = node.get("fromAccountNumber").asText();
            String toAccountNumber = node.get("toAccountNumber").asText();
            BigDecimal amount = new BigDecimal(node.get("amount").asText());
            requestBankTransferDto = new RequestBankTransferDto(fromAccountNumber, toAccountNumber, amount, RequestBodyType.VALID);
    	} catch(Exception ex) {
    		requestBankTransferDto = new RequestBankTransferDto("", "", null, RequestBodyType.INVALID);
    	}
        return requestBankTransferDto;
	}	
}
