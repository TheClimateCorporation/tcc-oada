package com.climate.oada.security.oauth;

import java.util.Arrays;

import org.springframework.security.oauth2.provider.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * ClientDetailsServiceImpl class implements the ClientDetailsService interface
 * and implements the method loadClientByClientId. The clientDetailsUserService
 * bean calls this method from to authenticate the client
 *
 */
public final class ClientDetailsServiceImpl implements ClientDetailsService {

    private static final String CLIENT_ID = "client-test";
    private static final String CLIENT_SECRET = "client-secret";
    private static final String[] GRANT_TYPES = {"authorization_code", "implicit"};

    /**
     * Default constructor.
     */
    public ClientDetailsServiceImpl() {

    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(CLIENT_ID);
        clientDetails.setClientSecret(CLIENT_SECRET);
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(GRANT_TYPES));
        return clientDetails;
    }

}
