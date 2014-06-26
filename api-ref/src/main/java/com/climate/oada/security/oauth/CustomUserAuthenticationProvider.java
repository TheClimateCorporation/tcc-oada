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
