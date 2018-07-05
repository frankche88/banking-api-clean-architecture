package banking.bundles;

import java.io.InputStream;

import com.google.common.collect.ImmutableList;

import banking.BankingConfiguration;
import banking.accounts.infrastructure.hibernate.repository.BankAccountHibernateRepository;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

public class HbnBundle extends HibernateBundle<BankingConfiguration> {

	
	public HbnBundle() {
		super(ImmutableList.of(), new SessionFactoryFactory());
	}

	@Override
	public PooledDataSourceFactory getDataSourceFactory(BankingConfiguration configuration) {
		return configuration.getDataSourceFactory();

	}
	
	protected void configure(org.hibernate.cfg.Configuration configuration) {
		

		
		InputStream inputBankAccount = BankAccountHibernateRepository.class.getClassLoader().getResourceAsStream("hibernate/bankAccount.hbm.xml");
		
		InputStream inputCustomer = BankAccountHibernateRepository.class.getClassLoader().getResourceAsStream("hibernate/customer.hbm.xml");
		
		InputStream inputRole = BankAccountHibernateRepository.class.getClassLoader().getResourceAsStream("hibernate/UserRoles.hbm.xml");
		
		InputStream inputUser = BankAccountHibernateRepository.class.getClassLoader().getResourceAsStream("hibernate/Users.hbm.xml");
		

		configuration.addInputStream(inputBankAccount);
		configuration.addInputStream(inputCustomer);
		
		configuration.addInputStream(inputUser);
		configuration.addInputStream(inputRole);
		
    }

}
