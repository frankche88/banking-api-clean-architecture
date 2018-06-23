package banking;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Holds the Oauth2 server configuration parameters read from the configuration file.
 *
 * @author Edouard De Oliveira
 */
public class OAuth2Config {

    @NotEmpty
    @JsonProperty
    private String url;

    @NotEmpty
    @JsonProperty
    private String scope;

    @NotEmpty
    @JsonProperty
    private String client_id;

    @NotEmpty
    @JsonProperty
    private String client_secret;

    @NotEmpty
    @JsonProperty
    private String cookieSecretKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String clientId) {
        client_id = clientId;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String clientSecret) {
        client_secret = clientSecret;
    }

    public String getCookieSecretKey() {
        return cookieSecretKey;
    }

    public void setCookieSecretKey(String cookieSecretKey) {
        this.cookieSecretKey = cookieSecretKey;
    }
}
