package banking.common.application.dto;

public class ErrorDto {
	private String message;
	
	private String statusCode;
	
	public ErrorDto() {
	}
	
	public ErrorDto(int statusCode, String message) {
		this.message = message;
		this.setStatusCode(String.valueOf(statusCode));
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
