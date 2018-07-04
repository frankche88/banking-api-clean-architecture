package banking.accounts.application.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import banking.accounts.application.dto.ResponseBankAccountDto;
import banking.accounts.domain.entity.BankAccount;

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

}
