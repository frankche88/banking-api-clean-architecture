package banking.customers.domain.entity;

import java.util.Set;

import banking.accounts.domain.entity.BankAccount;

public class Customer {
	private long id;
    private String firstName;
    private String lastName;
    private Set<BankAccount> bankAccounts;
    private String documentNumber;

    public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Customer() {
    }

    public String getFullName() {
        return String.format("%s, %s", this.lastName, this.firstName);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
