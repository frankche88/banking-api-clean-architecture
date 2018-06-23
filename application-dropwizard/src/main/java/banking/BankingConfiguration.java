package banking;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.cache.CacheBuilderSpec;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jetty.ConnectorFactory;

public class BankingConfiguration  extends Configuration {
	
	@Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
	
	
	//@NotNull
    //@JsonProperty
//    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @JsonProperty("clientConfig")
    private ConnectorFactory clientConfig;

    //@NotNull
    //@JsonProperty
    private OAuth2Config oauth2Config;
    
    
    @JsonProperty
    private CacheBuilderSpec cacheSpec;
    
    
    
    public CacheBuilderSpec getCacheSpec() {
        return cacheSpec;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return new JerseyClientConfiguration();
    }
    
    
    
	
	
	@JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    


    public OAuth2Config getOauth2Config() {
        return oauth2Config;
    }

    public ConnectorFactory getClientConfig() {
        return clientConfig;
    }

}
