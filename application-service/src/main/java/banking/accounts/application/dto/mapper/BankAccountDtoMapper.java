package banking.accounts.application.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import banking.accounts.application.dto.RequestBankAccountDto;
import banking.accounts.application.dto.ResponseBankAccountDto;
import banking.accounts.domain.entity.BankAccount;
import banking.customers.domain.entity.Customer;
import banking.transactions.application.dto.ResponseBankTransferDto;

@Named
@Singleton
public class BankAccountDtoMapper {

	public List<ResponseBankAccountDto> mapper(List<BankAccount> bankAccounts) {
		
		List<ResponseBankAccountDto> lstDto = new ArrayList<>();
		
		for (BankAccount bankAccount : bankAccounts) {
			
			lstDto.add(mapper(bankAccount));
		}
		
		return lstDto;
	}

	public ResponseBankAccountDto mapper(BankAccount bankAccount) {

		ResponseBankAccountDto dto = null;
		
		long id = bankAccount.getId();
		
		String number = bankAccount.getNumber();

		boolean isLocked = false;
		
		if(bankAccount.getCustomer() != null) {
			long customerId = bankAccount.getCustomer().getId();
			String customerFullName = bankAccount.getCustomer().getFullName();
			String customerDni = bankAccount.getCustomer().getDocumentNumber();
			dto = new ResponseBankAccountDto(id, number, customerId, customerFullName, customerDni, isLocked);
		} else {
			dto = new ResponseBankAccountDto(id, number, 0, "", "", isLocked);
		} 
		
		return dto;
	}

	public BankAccount reverseMapper(RequestBankAccountDto dto) {
		
		Customer customer = new Customer();
		
		customer.setId(dto.getCustomerId());
		
		BankAccount bankAccount = new BankAccount();
		
		bankAccount.setIsLocked(dto.isLocked());
		
		bankAccount.setCustomer(customer);
		
		bankAccount.setNumber(dto.getNumber());
		
		bankAccount.setBalance(dto.getBalance());
		
		return bankAccount;
	}

	// el unico cambio que se puede hacer por este medio es el estado
	public BankAccount merge(BankAccount bankAccount, RequestBankAccountDto dto) {
		
		bankAccount.setIsLocked(dto.isLocked());
		
		return bankAccount;
	}

	public List<ResponseBankTransferDto> mapperResponse(List<BankAccount> bankAccounts) {
		List<ResponseBankTransferDto> lstDto = new ArrayList<>();
		
		for (BankAccount bankAccount : bankAccounts) {
			
			lstDto.add(mapperResponse(bankAccount));
		}
		
		return lstDto;
	}

	public ResponseBankTransferDto mapperResponse(BankAccount bankAccount) {
		ResponseBankTransferDto dto = new ResponseBankTransferDto(bankAccount.getId(), bankAccount.getNumber(), bankAccount.getBalance());
		return dto;
	}

}
