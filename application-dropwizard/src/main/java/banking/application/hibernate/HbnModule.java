package banking.application.hibernate;

import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;

import banking.accounts.infrastructure.hibernate.repository.BankAccountHibernateRepository;

public class HbnModule extends AbstractModule {

	private final HbnBundle hbnBundle;

    public HbnModule(HbnBundle hbnBundle) {
        this.hbnBundle = hbnBundle;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toInstance(hbnBundle.getSessionFactory());
        bind(banking.accounts.domain.repository.BankAccountRepository.class).to(BankAccountHibernateRepository.class);
    }

}
