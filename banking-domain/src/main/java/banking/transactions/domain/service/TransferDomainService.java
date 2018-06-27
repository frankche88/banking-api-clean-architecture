package banking.transactions.domain.service;

import static banking.common.application.Messages.*;
import java.math.BigDecimal;

import javax.inject.Named;

import banking.accounts.domain.entity.BankAccount;
import banking.common.application.Notification;

@Named
public class TransferDomainService {
	public void performTransfer(BankAccount originAccount, BankAccount destinationAccount, BigDecimal amount)
			throws IllegalArgumentException {
		Notification notification = this.validation(originAccount, destinationAccount, amount);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
		originAccount.withdrawMoney(amount);
		destinationAccount.depositMoney(amount);
	}
	
	private Notification validation(BankAccount originAccount, BankAccount destinationAccount, BigDecimal amount) {
        Notification notification = new Notification();
        this.validateAmount(notification, amount);
        this.validateBankAccounts(notification, originAccount, destinationAccount);
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
    
    private void validateBankAccounts(Notification notification, BankAccount originAccount, BankAccount destinationAccount) {
        if (originAccount == null || destinationAccount == null) {
            notification.addError(INVALID_BANK_ACCOUNT_SPECIFICATIONS);
            return;
        }
        if (originAccount.getNumber().equals(destinationAccount.getNumber())) {
            notification.addError(SAME_BANK_ACCOUNTS);
        }
    }
}
