package banking.accounts.application.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import banking.accounts.application.dto.deserializer.RequestBankAccountDtoDeserializer;
import banking.common.application.dto.RequestBaseDto;
import banking.common.application.enumeration.RequestBodyType;

@JsonDeserialize(using = RequestBankAccountDtoDeserializer.class)
public class RequestBankAccountDto extends RequestBaseDto {
	private long id;
	private String number;
	private BigDecimal balance;
	private boolean isLocked;
	private long customerId;
	
	
	
	
	
	private RequestBodyType requestBodyType;
	
	

	public RequestBankAccountDto(long id, String number, BigDecimal balance, boolean isLocked, long customerId, RequestBodyType requestBodyType) {
		super();
		this.id = id;
		this.number = number;
		this.balance = balance;
		this.isLocked = isLocked;
		this.customerId = customerId;
		this.requestBodyType = requestBodyType;
	}

	public RequestBankAccountDto(RequestBodyType requestBodyType) {
		this.requestBodyType = requestBodyType;
	}

	public RequestBodyType getRequestBodyType() {
		return requestBodyType;
	}

	public long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public long getCustomerId() {
		return customerId;
	}

	
}
