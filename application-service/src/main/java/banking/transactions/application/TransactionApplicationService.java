package banking.transactions.application;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import banking.accounts.application.dto.mapper.BankAccountDtoMapper;
import banking.accounts.domain.entity.BankAccount;
import banking.accounts.domain.repository.BankAccountRepository;
import banking.common.application.Notification;
import banking.common.application.enumeration.RequestBodyType;
import banking.customers.domain.entity.Customer;
import banking.customers.domain.repository.CustomerRepository;
import banking.transactions.application.dto.RequestBankTransferDto;
import banking.transactions.application.dto.ResponseBankTransferDto;
import banking.transactions.domain.service.TransferDomainService;

@Named
public class TransactionApplicationService {
	@Inject
	private BankAccountRepository bankAccountRepository;
	
	@Inject
	private CustomerRepository customerRepository;

	@Inject
	private TransferDomainService transferDomainService;
	
	@Inject
	BankAccountDtoMapper bankAccountDtoMapper;
	

	@Transactional
	public List<ResponseBankTransferDto> findAcounstByUser(long userId) {
		Notification notification = this.validation(userId);
		if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
		Customer  customer = customerRepository.findById(userId);
		
		List<BankAccount> bankAccounts = new ArrayList<>(customer.getBankAccounts());
		
		List<ResponseBankTransferDto> bankAccountsDto = bankAccountDtoMapper.mapperResponse(bankAccounts);
		
		return bankAccountsDto;
	}

	@Transactional
	public BankAccount getBankAccountById(long id) {
		Notification notification = this.validation(id);
		if (notification.hasErrors()) {
			throw new IllegalArgumentException(notification.errorMessage());
		}
		BankAccount customer = this.bankAccountRepository.findById(id);
		return customer;
	}

	@Transactional
	public void performTransfer(RequestBankTransferDto requestBankTransferDto) throws Exception {
		Notification notification = this.validation(requestBankTransferDto);
        if (notification.hasErrors()) {
            throw new IllegalArgumentException(notification.errorMessage());
        }
		BankAccount originAccount = this.bankAccountRepository.findByNumberLocked(requestBankTransferDto.getFromAccountNumber());
		BankAccount destinationAccount = this.bankAccountRepository.findByNumberLocked(requestBankTransferDto.getToAccountNumber());
		this.transferDomainService.performTransfer(originAccount, destinationAccount, requestBankTransferDto.getAmount());
		this.bankAccountRepository.save(originAccount);
		this.bankAccountRepository.save(destinationAccount);
	}
	
	private Notification validation(RequestBankTransferDto requestBankTransferDto) {
		Notification notification = new Notification();
		if (requestBankTransferDto == null || requestBankTransferDto.getRequestBodyType() == RequestBodyType.INVALID) {
			notification.addError("Invalid JSON data in request body.");
		}
		return notification;
	}
	


	private Notification validation(long userId) {
		Notification notification = new Notification();
		if (userId == 0 ) {
			notification.addError("User id not valid.");
		}
		return notification;
	}
}
