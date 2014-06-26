package com.climate.oada.security.oauth;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

/**
 * OAuthUtils.
 */
public final class OAuthUtils extends OAuth2Utils {

    public static final String OAUTH_HEADER_VALUE_BEARER = DefaultOAuth2AccessToken.BEARER_TYPE;
    public static final String OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE = DefaultOAuth2AccessToken.BEARER_TYPE + " ";


    public static final String HTTP_200 = "HTTP/1.1 200 OK\n";
    public static final String BYE_MESSAGE = "Bye!";
    public static final String HTTP_200_BYE_MESSAGE = HTTP_200 + "\n" + BYE_MESSAGE;
    public static final String WRONG_USER_CREDENTIALS = "Wrong user credentials";

    /**
     * Default private constructor.
     */
    private OAuthUtils() {

    }

}
