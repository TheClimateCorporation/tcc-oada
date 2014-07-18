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

package com.climate.oada.security.saml;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

/**
 * The SAMLUserDetailsService is similar to the Spring Security
 * UserDetailsService interface. Annoyingly though, it’s a separate interface,
 * not a sub interface. This means that any implementation of UserDetailsService
 * that we already have will have to be re-implemented for SAML. The
 * SAMLUserDetailsService is optional. If it’s not provided, weill get an
 * instance of OpenSAML NameIDImpl as principal. This is a little cumbersome to
 * work with and is likely to cause cause issues if we're integrating with an
 * existing Spring Security project. Spring Security usually uses an
 * implementation of UserDetails as the principal. Hence this class that maps
 * SAMLCredential to Spring Security UserDetails.
 */
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    private List<String> roles;

    /**
     * Default constructor.
     */
    public SAMLUserDetailsServiceImpl() {

    }

    /**
     * Setter for roles.
     * @param r - user roles.
     */
    public void setRoles(List<String> r) {
        this.roles = r;
    }

    /**
     * Getter for roles.
     * @return list of roles.
     */
    public List<String> getRoles() {
        return this.roles;
    }

    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        String email = credential.getNameID().getValue();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        authorities.add(authority);

        UserDetails userDetails = new User(email, "password", true, true, true,
                true, authorities);

        return userDetails;
    }
}
