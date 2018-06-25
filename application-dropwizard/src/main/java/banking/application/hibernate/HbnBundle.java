package banking.application.hibernate;

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
	
	
	//ImmutableList.of();
	
	
	

	@Override
	public PooledDataSourceFactory getDataSourceFactory(BankingConfiguration configuration) {
		return configuration.getDataSourceFactory();

	}
	
	protected void configure(org.hibernate.cfg.Configuration configuration) {
		

		
		InputStream inputBankAccount = BankAccountHibernateRepository.class.getClassLoader().getResourceAsStream("hibernate/bankAccount.hbm.xml");
		
		InputStream inputCustomer = BankAccountHibernateRepository.class.getClassLoader().getResourceAsStream("hibernate/customer.hbm.xml");
		

		
		
		// incluir xml files
		configuration.addInputStream(inputBankAccount);// .addFile(file);// File("hibernate/bankAccount.hbm.xml");
		configuration.addInputStream(inputCustomer);//configuration.addFile("hibernate/custumer.hbm.xml");
		
    }

}
