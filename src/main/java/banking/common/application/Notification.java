package banking.common.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Notification {
	private List<Error> errors = new ArrayList<>();

	public void addError(String message) {
		addError(message, null);
	}

	public void addError(String message, Exception e) {
		errors.add(new Error(message, e));
	}

	public String errorMessage() {
		return errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
