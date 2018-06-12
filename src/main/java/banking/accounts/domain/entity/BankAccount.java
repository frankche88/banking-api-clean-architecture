package banking.accounts.domain.entity;

import java.math.BigDecimal;

import banking.common.application.Notification;
import banking.customers.domain.entity.Customer;

public class BankAccount {
	private long id;
    private String number;
    private BigDecimal balance;
    private boolean isLocked;
    private Customer customer;

    public BankAccount() {
    }

    public void lock() {
        if (!this.isLocked) {
            this.isLocked = true;
        }
    }

    public void unLock() {
        if (this.isLocked) {
            this.isLocked = false;
        }
    }

    public boolean hasIdentity() {
        return !this.number.trim().equals("");
    }

    public void withdrawMoney(BigDecimal amount) {
    	Notification notification = this.withdrawValidation(amount);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        this.balance = this.balance.subtract(amount);
    }

    public void depositMoney(BigDecimal amount) {
    	Notification notification = this.depositValidation(amount);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
        this.balance = this.balance.add(amount);
    }
    
    public Notification withdrawValidation(BigDecimal amount) {
    	Notification notification = new Notification();
        this.validateAmount(notification, amount);
        this.validateBankAccount(notification);
        this.validateBalance(notification, amount);
        return notification;
    }
    
    public Notification depositValidation(BigDecimal amount) {
        Notification notification = new Notification();
        this.validateAmount(notification, amount);
        this.validateBankAccount(notification);
        return notification;
    }
    
    private void validateAmount(Notification notification, BigDecimal amount) {
        if (amount == null) {
            notification.addError("amount is missing");
            return;
        }
        if (amount.signum() <= 0) {
            notification.addError("The amount must be greater than zero");
        }
    }
    
    private void validateBankAccount(Notification notification) {
        if (!this.hasIdentity()) {
            notification.addError("The account has no identity");
        }
        if (this.isLocked) {
        	notification.addError("The account is locked");
        }
    }
    
    private void validateBalance(Notification notification, BigDecimal amount) {
        if (this.balance == null) {
            notification.addError("balance cannot be null");
        }
        if (!this.canBeWithdrawed(amount)) {
        	notification.addError("Cannot withdraw in the account, amount is greater than balance");
        }
    }

    public boolean canBeWithdrawed(BigDecimal amount) {
        return !this.isLocked && (this.balance.compareTo(amount) >= 0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean getIsLocked() {
        return isLocked;
    }
    
    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
