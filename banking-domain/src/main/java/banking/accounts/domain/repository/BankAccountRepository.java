package banking.accounts.domain.repository;

import java.util.List;

import banking.accounts.domain.entity.BankAccount;

public interface BankAccountRepository {
	
	BankAccount findByNumber(String accountNumber) throws Exception;
	
	BankAccount findByNumberLocked(String accountNumber) throws Exception;
	
	void save(BankAccount bankAccount);
	
	long countBankAccount();
	
	List<BankAccount> findAllPaginated(int pageNumber, int pageSize);

	BankAccount findById(long id);
	
}
