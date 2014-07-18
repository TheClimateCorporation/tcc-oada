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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * The CustomUserAuthenticationProvider class implements the
 * AuthenticationProvider interface and implements the method authenticate to
 * authenticate the user.
 *
 */
public final class CustomUserAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserAuthenticationProvider.class);

    /**
     * Default constructor.
     */
    public CustomUserAuthenticationProvider() {

    }

    @Override
    public Authentication authenticate(Authentication authentication) {

        LOG.info("Going to process authentication: " + authentication);
        if (authentication != null
                && authentication.getPrincipal() != null
                && authentication.getCredentials() != null) {

            LOG.info("authentication principal: " + authentication.getPrincipal());
            LOG.info("authentication credentials: " + authentication.getCredentials());

            /*
             * authentication.getPrincipal() <=> userName
             * authentication.getCredentials() <=> password
             */
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            CustomUserPasswordAuthenticationToken auth =
                    new CustomUserPasswordAuthenticationToken(authentication.getPrincipal(),
                            authentication.getCredentials(), grantedAuthorities);
            return auth;
        }
        throw new BadCredentialsException("Invalid User Credentials");
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return true;
    }

}
