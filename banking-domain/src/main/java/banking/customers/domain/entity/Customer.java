package banking.customers.domain.entity;

import java.util.Set;

import banking.accounts.domain.entity.BankAccount;
import banking.security.domain.entity.User;

public class Customer {
	private long id;
    private String firstName;
    private String lastName;
    private Set<BankAccount> bankAccounts;
    private String documentNumber;
    private boolean active = true;
    
    private User user;
    

    public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", bankAccounts="
                + bankAccounts + ", documentNumber=" + documentNumber + ", active=" + active + ", user=" + user + "]";
    }
    
    
}
