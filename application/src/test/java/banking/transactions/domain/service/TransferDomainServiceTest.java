package banking.transactions.domain.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import banking.accounts.domain.entity.BankAccount;
import banking.transactions.domain.service.TransferDomainService;

@ContextConfiguration({ "classpath:test-spring-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TransferDomainServiceTest {

    @Autowired
	private TransferDomainService transferDomainService;
    private String originBankAccountNumber = "123-456-001";
    private String destinationBankAccountNumber = "123-456-002";

	@Before
	public void setUp() {
	}

	private BankAccount createAccount(String number, BigDecimal balance) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(balance);
		bankAccount.setNumber(number);
		return bankAccount;
	}

	@Test
	public void performTransferSuccess() throws Exception {
		BankAccount originBankAccount = createAccount(originBankAccountNumber, new BigDecimal(100));
		BankAccount destinationBankAccount = createAccount(destinationBankAccountNumber, new BigDecimal(10));
		transferDomainService.performTransfer(originBankAccount, destinationBankAccount, new BigDecimal(10));
		assertEquals(new BigDecimal(90), originBankAccount.getBalance());
		assertEquals(new BigDecimal(20), destinationBankAccount.getBalance());
	}

	@Test(expected = IllegalArgumentException.class)
	public void performTransferErrorSameAccount() throws Exception {
		BankAccount originBankAccount = createAccount(originBankAccountNumber, new BigDecimal(100));
		BankAccount destinationBankAccount = createAccount(originBankAccountNumber, new BigDecimal(10));
		transferDomainService.performTransfer(originBankAccount, destinationBankAccount, new BigDecimal(10));
	}

	@Test(expected = IllegalArgumentException.class)
	public void performTransferErrorEmptyAccount() throws Exception {
		BankAccount originBankAccount = null;
		BankAccount destinationBankAccount = null;
		transferDomainService.performTransfer(originBankAccount, destinationBankAccount, new BigDecimal(10));

	}

	@Test(expected = IllegalArgumentException.class)
	public void performTransferErrorLockedDestinationAccount() throws Exception {
		BankAccount originBankAccount = createAccount(originBankAccountNumber, new BigDecimal(100));
		BankAccount destinationBankAccount = createAccount(destinationBankAccountNumber, new BigDecimal(10));
		destinationBankAccount.lock();
		transferDomainService.performTransfer(originBankAccount, destinationBankAccount, new BigDecimal(10));
	}

	@Test(expected = IllegalArgumentException.class)
	public void performTransferErrorNoMoneyOriginAccount() throws Exception {
		BankAccount originBankAccount = createAccount(originBankAccountNumber, new BigDecimal(5));
		BankAccount destinationBankAccount = createAccount(destinationBankAccountNumber, new BigDecimal(10));
		transferDomainService.performTransfer(originBankAccount, destinationBankAccount, new BigDecimal(10));
	}

	@Test(expected = IllegalArgumentException.class)
	public void performTransferErrorNegativeTransference() throws Exception {
		BankAccount originBankAccount = createAccount(originBankAccountNumber, new BigDecimal(5));
		BankAccount destinationBankAccount = createAccount(destinationBankAccountNumber, new BigDecimal(10));
		transferDomainService.performTransfer(originBankAccount, destinationBankAccount, new BigDecimal(-10));
	}
}
