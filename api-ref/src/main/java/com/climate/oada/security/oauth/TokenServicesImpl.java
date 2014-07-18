/*
 * Copyright (C) 2014 The Climate Corporation and released under an Apache 2.0 license.
 * You may not use this library except in compliance with the License.
 * You may obtain a copy of the License at:

 * http://www.apache.org/licenses/LICENSE-2.0

 * See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */

package com.climate.oada.security.oauth;

import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * This class creates an OAuth 2.0 token based on user credentials (via SAML exchange).
 */
public class TokenServicesImpl extends DefaultTokenServices {

    private static final Logger LOG = LoggerFactory.getLogger(TokenServicesImpl.class);

    private static final long SEC_TO_MSEC = 1000L;

    private TokenStore tokenStore;

    /**
     * Default Constructor.
     */
    public TokenServicesImpl() {

    }

    @Override
    public OAuth2AccessToken createAccessToken(
            OAuth2Authentication authentication) throws AuthenticationException {
        LOG.debug("Generating OAuth Token for principal: " + authentication.getName());
        DefaultOAuth2AccessToken result = null;
        String randomUUID = UUID.randomUUID().toString();
        MessageDigest md = null;
        try {
            byte[] emailBytes = authentication.getName().getBytes("UTF-8");
            md = MessageDigest.getInstance("MD5");
            String emailDigest = new String(Base64.encodeBase64(md.digest(emailBytes)));
            String token = randomUUID + emailDigest;
            result = new DefaultOAuth2AccessToken(token);
            int validitySeconds = getAccessTokenValiditySeconds(authentication.getAuthorizationRequest());
            if (validitySeconds > 0) {
                result.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * SEC_TO_MSEC)));
            }
            result.setTokenType("Bearer");
            tokenStore.storeAccessToken(result, authentication);
        } catch (Exception e) {
            LOG.error("Unable to generate OAuth token: " + e.getMessage());
        }
        return result;
    }

    /**
     * Setter for TokenStore.
     * @param s - TokenStore
     */
    public void setTokenStore(TokenStore s) {
        tokenStore = s;
        super.setTokenStore(tokenStore);
    }
}
