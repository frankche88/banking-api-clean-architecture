package banking;

import com.hubspot.dropwizard.guice.GuiceBundle;

import banking.transactions.api.controller.BankTransferController;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BankingApplication extends Application<BankingConfiguration> {

	private GuiceBundle<BankingConfiguration> guiceBundle;

	public static void main(String[] args) throws Exception {
		new BankingApplication().run(args);
	}

	private final HibernateBundle<BankingConfiguration> hibernateBundle = new HibernateBundle<BankingConfiguration>(
			null) {
		@Override
		public DataSourceFactory getDataSourceFactory(BankingConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public String getName() {
		return "Banking-api";
	}

	@Override
	public void initialize(Bootstrap<BankingConfiguration> bootstrap) {

		bootstrap.addBundle(new MigrationsBundle<BankingConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(BankingConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});

		bootstrap.addBundle(hibernateBundle);

		guiceBundle = GuiceBundle.<BankingConfiguration>newBuilder()
				.addModule(new BankingModule())
				.setConfigClass(BankingConfiguration.class).build();

		bootstrap.addBundle(guiceBundle);

	}

	@Override
	public void run(BankingConfiguration configuration, Environment environment) throws Exception {

		environment.jersey().register(BankTransferController.class);
		// environment.jersey().register(new ViewResource());

	}
}
