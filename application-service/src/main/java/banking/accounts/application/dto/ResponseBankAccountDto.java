package banking.accounts.application.dto;

public class ResponseBankAccountDto {
	
	
	private long id;
	private String number;
	private long customerId;
	private String customerFullName;
	private String customerDni;
	private boolean isLocked = true;
	
	
	
	public ResponseBankAccountDto(long id, String number, long customerId, String customerFullName, String customerDni,
			boolean isLocked) {
		super();
		this.id = id;
		this.number = number;
		this.customerId = customerId;
		this.customerFullName = customerFullName;
		this.customerDni = customerDni;
		this.isLocked = isLocked;
	}
	
	
	public long getId() {
		return id;
	}
	public String getNumber() {
		return number;
	}
	public long getCustomerId() {
		return customerId;
	}
	public String getCustomerFullName() {
		return customerFullName;
	}
	public String getCustomerDni() {
		return customerDni;
	}
	public boolean isLocked() {
		return isLocked;
	}
	
	

}
