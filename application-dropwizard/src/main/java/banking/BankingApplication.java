package banking;

import banking.bundles.CorsBundle;
import banking.bundles.HbnBundle;
import banking.bundles.SwitchableSwaggerBundle;
import banking.guice.module.HbnModule;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
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
		
		bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));

		bootstrap.addBundle(new MigrationsBundle<BankingConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(BankingConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}
		});

		final HbnBundle hibernate = new HbnBundle();
		
		bootstrap.addBundle(new SwitchableSwaggerBundle());
        bootstrap.addBundle(new CorsBundle());
		
		
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
