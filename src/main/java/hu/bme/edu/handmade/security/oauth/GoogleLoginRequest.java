package hu.bme.edu.handmade.security.oauth;

import java.io.Serializable;

public class GoogleLoginRequest implements Serializable {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
