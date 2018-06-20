package banking.common.application.dto;

import banking.common.application.enumeration.RequestBodyType;

public class RequestBaseDto {
	protected RequestBodyType requestBodyType;

	public RequestBodyType getRequestBodyType() {
		return requestBodyType;
	}

	public void setRequestBodyType(RequestBodyType requestBodyType) {
		this.requestBodyType = requestBodyType;
	}
}
