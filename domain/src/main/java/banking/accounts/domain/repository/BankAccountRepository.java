package banking.accounts.domain.repository;

import banking.accounts.domain.entity.BankAccount;

public interface BankAccountRepository {
	BankAccount findByNumber(String accountNumber) throws Exception;
	BankAccount findByNumberLocked(String accountNumber) throws Exception;
	void save(BankAccount bankAccount);
}
