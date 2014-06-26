package com.climate.oada.security.oauth;

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
        return clientDetails;
    }

}
