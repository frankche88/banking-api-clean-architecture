package banking.bundles;

import banking.BankingConfiguration;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class SwitchableSwaggerBundle extends SwaggerBundle<BankingConfiguration> {

    @Override
    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(BankingConfiguration configuration) {
        return configuration.getSwaggerBundleConfiguration();
    }

    @Override
    public void run(BankingConfiguration configuration, Environment environment) throws Exception {
        super.run(configuration, environment);
    }
}
