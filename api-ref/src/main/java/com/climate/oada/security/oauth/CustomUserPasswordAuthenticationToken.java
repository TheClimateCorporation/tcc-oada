package com.climate.oada.security.oauth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * User name password authentication token.
 */
public final class CustomUserPasswordAuthenticationToken extends
        AbstractAuthenticationToken {

    /**
     * Generated serial UID.
     */
    private static final long serialVersionUID = 1335329508055313606L;
    private final Object principal;
    private Object credentials;
    private int loginId;

    /**
     * Constructor.
     *
     * @param p - Principal
     * @param c - User credentials.
     */
    public CustomUserPasswordAuthenticationToken(Object p, Object c) {
        super(null);
        this.principal = p;
        this.credentials = c;
        setAuthenticated(false);
    }

    /**
     * Constructor with overload for authorities.
     * @param p - Principal.
     * @param c - Credentials.
     * @param auths - Authorities.
     */
    public CustomUserPasswordAuthenticationToken(Object p, Object c,
            Collection<? extends GrantedAuthority> auths) {
        super(auths);
        this.principal = p;
        this.credentials = c;
        super.setAuthenticated(true);
    }

    /**
     * Getter for credentials.
     * @return Object - Credentials
     */
    public Object getCredentials() {
        return this.credentials;
    }

    /**
     * Getter for principal.
     * @return Object - Principal.
     */
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     * Set authenticated.
     * @param isAuth - is authenticated
     */
    public void setAuthenticated(boolean isAuth) {
        if (isAuth) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    /**
     * Erase credentials.
     */
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    /**
     * Getter for login id.
     * @return int
     */
    public int getLoginId() {
        return loginId;
    }

    /**
     * Getter for login id.
     * @param id - Login Id
     */
    public void setLoginId(int id) {
        this.loginId = id;
    }

}
