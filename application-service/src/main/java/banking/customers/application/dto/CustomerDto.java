package banking.customers.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import banking.common.application.dto.RequestBaseDto;
import banking.customers.application.dto.deserializer.CustomerDtoDeserializer;

@JsonDeserialize(using = CustomerDtoDeserializer.class)
public class CustomerDto extends RequestBaseDto {	

	private long id = 0;
	
	private String dni;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String userName;
	
	private String password;
	
	private boolean active = true;
	
	
	public CustomerDto() {
		
	}
	
	public CustomerDto(long id, String documentNumber, String firstName, String lastName) {
		super();
		this.id = id;
		this.dni = documentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public CustomerDto(long id, String documentNumber, String firstName, String lastName, boolean active) {
		super();
		this.id = id;
		this.dni = documentNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
	}
	
	public void setUserData(String username, String password, String email) {
		
		this.userName = username;
		
		this.password = password;
		
		this.email = email;
		
	}

	//private Set<BankAccountDto> bankAccountsDto;


	public String getFullName() {
		return String.format("%s, %s", this.lastName, this.firstName);
	}


	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", dni=" + dni + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", userName=" + userName + ", password=" + password + ", active=" + active + "]";
	}

	public long getId() {
		return id;
	}

	public String getDni() {
		return dni;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return active;
	}
	
	
}
