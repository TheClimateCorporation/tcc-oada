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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.InMemoryTokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * The LogoutImpl class implements LogoutSuccessHandler interface and implements
 * onLogoutSuccess method to remove access token of the user from tokenStore.
 *
 */
public final class LogoutImpl implements LogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LogoutImpl.class);

    private InMemoryTokenStore tokenStore;

    /**
     * Default constructor.
     */
    public LogoutImpl() {

    }

    /**
     * Getter for token store.
     *
     * @return TokenStore
     */
    public InMemoryTokenStore getTokenStore() {
        return tokenStore;
    }

    /**
     * Setter for token store.
     *
     * @param store
     *            - Token store.
     */
    public void setTokenStore(InMemoryTokenStore store) {
        this.tokenStore = store;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Authentication authentication) throws IOException, ServletException {

        this.removeAccess(httpServletRequest);
        httpServletResponse.getOutputStream().write("Logged Out".getBytes());
    }

    /**
     * Remove access to user's token on logout.
     *
     * @param httpServletRequest
     *            - Servlet request.
     */
    private void removeAccess(HttpServletRequest httpServletRequest) {
        if (httpServletRequest != null) {
            String bearerAndToken = httpServletRequest
                    .getHeader("Authorization");
            LOG.info("bearerAndToken:" + bearerAndToken);

            if (StringUtils.isNotBlank(bearerAndToken)
                    && bearerAndToken.length() >= OAuthUtils.OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE
                            .length()
                    && bearerAndToken
                            .contains(OAuthUtils.OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE)) {
                String extractedToken = bearerAndToken
                        .substring(
                                bearerAndToken
                                        .indexOf(OAuthUtils.OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE)
                                        + OAuthUtils.OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE
                                                .length()).trim();

                if (this.getTokenStore() != null
                        && StringUtils.isNotBlank(extractedToken)
                        && extractedToken.length() > 0) {
                    DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(
                            extractedToken);
                    this.getTokenStore().removeAccessToken(
                            oAuth2AccessToken);
                    LOG.info("Access OAuth token removed "
                            + oAuth2AccessToken);
                }
            } else {
                throw new IllegalArgumentException("OAuth Token missing");
            }
        }
    }
}
