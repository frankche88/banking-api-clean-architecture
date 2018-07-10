package banking.transactions.application.dto;

import java.math.BigDecimal;

import banking.common.application.dto.RequestBaseDto;

public class ResponseBankTransferDto extends RequestBaseDto {
	
	long id;
	private String number;
	private BigDecimal balance;
	
	
	
	public ResponseBankTransferDto(long id, String number, BigDecimal balance) {
		super();
		this.id = id;
		this.number = number;
		this.balance = balance;
	}
	
	
	public long getId() {
		return id;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getAccountNumber() {
		return number;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	

}
