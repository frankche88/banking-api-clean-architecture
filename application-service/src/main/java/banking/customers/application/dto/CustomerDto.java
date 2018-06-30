package banking.customers.application.dto;

public class CustomerDto {
	
	private String documentNumber;

	private String firstName;
	
	private String lastName;
	
	public CustomerDto() {
		
	}
	
	public CustomerDto(String documentNumber, String firstName, String lastName) {
		super();
		this.documentNumber = documentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	//private Set<BankAccountDto> bankAccountsDto;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
}
