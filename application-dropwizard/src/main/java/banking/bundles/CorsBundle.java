package banking.bundles;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.google.common.collect.ImmutableMap;

import banking.BankingConfiguration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CorsBundle implements ConfiguredBundle<BankingConfiguration> {

    @Override
    public void run(BankingConfiguration configuration, Environment environment) throws Exception {

        final FilterRegistration.Dynamic filter = environment.servlets().addFilter(
                CrossOriginFilter.class.getSimpleName(), CrossOriginFilter.class);

        filter.setInitParameters(ImmutableMap.of(
                //CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://localhost:8080,http://localhost:9008, http://cnblogs",
                CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*",
                CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true",
                CrossOriginFilter.ALLOWED_HEADERS_PARAM, "*",
                CrossOriginFilter.ALLOWED_METHODS_PARAM, "*",
                CrossOriginFilter.EXPOSED_HEADERS_PARAM, "*"
        ));
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "*", "/swagger.json");

    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}
