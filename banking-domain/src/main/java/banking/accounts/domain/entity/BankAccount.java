package banking.accounts.domain.entity;

import static banking.common.application.Messages.ACCOUNT_LOCKED;
import static banking.common.application.Messages.ACCOUNT_NO_IDENTITY;
import static banking.common.application.Messages.AMOUNT_MISSING;
import static banking.common.application.Messages.AMOUNT_MUST_GREATER_ZERO;
import static banking.common.application.Messages.BALANCE_NULL;
import static banking.common.application.Messages.WITHDRAW_THE_AMOUNT_GREATER_THAN_BALANCE;

import java.math.BigDecimal;
import java.util.UUID;

import banking.common.application.Notification;
import banking.customers.domain.entity.Customer;

public class BankAccount {
	private long id = 0;
    private String number;
    private BigDecimal balance = null;
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
    
    public String generateNumber() {
    	return UUID.randomUUID().toString();
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
            notification.addError(AMOUNT_MISSING);
            return;
        }
        if (amount.signum() <= 0) {
            notification.addError(AMOUNT_MUST_GREATER_ZERO);
        }
    }
    
    private void validateBankAccount(Notification notification) {
        if (!this.hasIdentity()) {
            notification.addError(ACCOUNT_NO_IDENTITY);
        }
        if (this.isLocked) {
        	notification.addError(ACCOUNT_LOCKED);
        }
    }
    
    private void validateBalance(Notification notification, BigDecimal amount) {
        if (this.balance == null) {
            notification.addError(BALANCE_NULL);
        }
        if (!this.canBeWithdrawed(amount)) {
        	notification.addError(WITHDRAW_THE_AMOUNT_GREATER_THAN_BALANCE);
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
