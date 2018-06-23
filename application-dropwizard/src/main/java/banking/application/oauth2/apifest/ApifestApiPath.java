package banking.application.oauth2.apifest;

public enum ApifestApiPath {
    TOKENS("/tokens"), TOKENS_VALIDATION("/tokens/validate?token=");

    private String path;

    ApifestApiPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
