/*
 * Copyright (C) 2014 The Climate Corporation and released under an MIT license.
 * You may not use this library except in compliance with the License.
 * You may obtain a copy of the License at:

 * http://opensource.org/licenses/MIT

 * See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */

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
