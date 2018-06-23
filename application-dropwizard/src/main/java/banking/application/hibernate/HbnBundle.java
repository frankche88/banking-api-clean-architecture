package banking.application.hibernate;

import com.google.common.collect.ImmutableList;

import banking.BankingConfiguration;
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
		
		// incluir xml files
		configuration.addResource("/hibernate/bankAccount.hbm.xml");
		configuration.addResource("/hibernate/custumer.hbm.xml");
		
    }

}
