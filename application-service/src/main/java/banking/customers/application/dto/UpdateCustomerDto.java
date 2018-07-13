package banking.customers.application.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import banking.common.application.dto.RequestBaseDto;
import banking.common.application.enumeration.RequestBodyType;
import banking.customers.application.dto.deserializer.UpdateCustomerDtoDeserializer;

@JsonDeserialize(using = UpdateCustomerDtoDeserializer.class)
public class UpdateCustomerDto extends RequestBaseDto {
	
	private long id = 0;
	
	private String firstName;
	
	private String lastName;
	
	private Boolean active = true;
	
	
	

	public UpdateCustomerDto(long id, String firstName, String lastName, Boolean active) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		setRequestBodyType(RequestBodyType.VALID);
	}

	public UpdateCustomerDto(RequestBodyType invalid) {
		setRequestBodyType(invalid);
	}
	
	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Boolean isActive() {
		return active;
	}
	
	

}
