package banking.guice.module;

import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;

import banking.accounts.domain.repository.BankAccountRepository;
import banking.accounts.infrastructure.hibernate.repository.BankAccountHibernateRepository;
import banking.bundles.HbnBundle;
import banking.customers.domain.repository.CustomerRepository;
import banking.customers.infrastructure.hibernate.repository.CustomerHibernateRepository;
import banking.security.domain.repository.UserRepository;
import banking.security.infrastructure.hibernate.repository.UserHibernateRepository;

public class HbnModule extends AbstractModule {

	private final HbnBundle hbnBundle;

    public HbnModule(HbnBundle hbnBundle) {
        this.hbnBundle = hbnBundle;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(hbnBundle.getSessionFactory());
        
        bind(BankAccountRepository.class).to(BankAccountHibernateRepository.class);
        
        bind(CustomerRepository.class).to(CustomerHibernateRepository.class);
        
        bind(UserRepository.class).to(UserHibernateRepository.class);
    }

}
