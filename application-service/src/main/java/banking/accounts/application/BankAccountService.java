package banking.accounts.application;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import banking.accounts.application.dto.ResponseBankAccountDto;
import banking.accounts.application.dto.mapper.BankAccountDtoMapper;
import banking.accounts.domain.entity.BankAccount;
import banking.accounts.domain.repository.BankAccountRepository;
import banking.common.application.Notification;
import banking.common.application.dto.PaggedResponse;

@Named
public class BankAccountService {
	
	@Inject
	private BankAccountRepository bankAccountRepository;
	
	@Inject
	BankAccountDtoMapper bankAccountDtoMapper;
	
	
	@Transactional
	public BankAccount getBankAccountById(long id) throws Exception {
		Notification notification = this.validation(id);
		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		BankAccount customer = this.bankAccountRepository.findById(id);
		return customer;
	}

	

	@Transactional
	public PaggedResponse<ResponseBankAccountDto> findAllPaged(int pageNumber, int pageSize) throws Exception {
		Notification notification = this.validation(pageNumber, pageSize);
		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		List<BankAccount> bankAccounts = this.bankAccountRepository.findAllPaginated(pageNumber, pageSize);

		if (bankAccounts == null || bankAccounts.size() == 0) {
			return null;
		}

		long totalRecords = this.bankAccountRepository.countBankAccount();

		PaggedResponse<ResponseBankAccountDto> response = new PaggedResponse<ResponseBankAccountDto>(bankAccountDtoMapper.mapper(bankAccounts),
				pageNumber, pageSize, totalRecords);

		return response;
	}

	private Notification validation(int pageNumber, int pageSize) {
		Notification notification = new Notification();
		if (pageNumber <= 0) {
			notification.addError("Page must be greater than zero.");
		}
		if (pageSize <= 0) {
			notification.addError("Page size must be greater than zero.");
			return notification;
		}
		return notification;
	}

	private Notification validation(long bankAccountId) {
		Notification notification = new Notification();
		if (bankAccountId <= 0) {
			notification.addError("bankAccount id must be greater than zero.");
		}
		return notification;
	}

}
