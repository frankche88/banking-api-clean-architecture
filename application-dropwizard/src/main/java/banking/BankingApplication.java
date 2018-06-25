package banking;

import banking.application.hibernate.HbnBundle;
import banking.application.hibernate.HbnModule;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class BankingApplication extends Application<BankingConfiguration> {

	public static void main(String[] args) throws Exception {
		new BankingApplication().run(args);
	}


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

		final HbnBundle hibernate = new HbnBundle();
        // register hbn bundle before guice to make sure factory initialized before guice context start
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig("banking")
                .modules(new HbnModule(hibernate))
                .build());

	}

	@Override
	public void run(BankingConfiguration configuration, Environment environment) throws Exception {

		//environment.jersey().register(BankTransferController.class);
		// environment.jersey().register(new ViewResource());

	}
}
