package banking.application.oauth2.apifest;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenValidationResponse implements Serializable {

	private static final long serialVersionUID = 200409976159840368L;

	@JsonProperty("valid")
    private Boolean valid;

    @JsonProperty("scope")
    private String scope = "";

    @JsonProperty("created")
    private Long created;

    @JsonProperty("token")
    private String token = "";

    @JsonProperty("expiresIn")
    private Integer expiresIn;

    @JsonProperty("refreshExpiresIn")
    private String refreshExpiresIn = "";

    @JsonProperty("userId")
    private String userId = "";

    @JsonProperty("refreshToken")
    private String refreshToken = "";

    @JsonProperty("type")
    private String type = "";

    @JsonProperty("clientId")
    private String clientId = "";

    @JsonProperty("codeId")
    private String codeId = "";

    @JsonProperty("details")
    private Map<String, String> details = null;

    public TokenValidationResponse() {
    }

    public Boolean isValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public String getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(String refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }
}
