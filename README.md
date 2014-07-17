# OADA API Reference Implementation


## Introduction


This open source implementation provides a basic skeleton implementation for OADA REST APIs. This reference implementation is in Java and is built using a combination of:
* Spring MVC 
* Spring Security integrated with Spring SAML and Spring OAuth. 

The goal of this reference implementation is not to be exhaustive in terms of data formats or backend storage systems. It is intended to be a reference implementation that illustrates how OADA Federated Identity could be implemented and on which various parties can build their implementations.

## OADA Federated Identity

### Authentication

[Single Sign On Support via SAML](saml.md)


### Authorization

[OAuth 2.0 Support](oauth.md)


### Tying it all together 

Finally we tie Spring Security with Spring OAuth and Spring SAML. The main interface which provides authentication services in Spring Security is the AuthenticationManager. The bean instance is registered using the "authentication-manager" namespace element. This Authentication Manager is the one that is responsible for the user’s authentication. Within it, there is a declaration of the provider, which holds the users and passwords. In most real world implementations, this provider directs to a JDBC, where all users are stored with their encrypted passwords. In case of WebSSO, we do not have to handle any users and passwords, since we delegate this to the IdP. So, we change the declaration of our server to point to the authentication manager of the SAML SP: this manager has a provider (SAMLAuthenticationProvider or some other class that extends it). This class implements the authenticate() method, that attempts to perform authentication of an Authentication object. 

So in summary we end up with these changes:

```
  <authentication-manager alias="authenticationManager"
    xmlns="http://www.springframework.org/schema/security">
    <authentication-provider ref="samlAuthenticationProvider"/>
  </authentication-manager>

  <http authentication-manager-ref="authenticationManager"
    entry-point-ref="samlEntryPoint">
    <intercept-url pattern="/oauth/**" access="ROLE_USER" />
    <intercept-url pattern="/**" access="IS_AUTHENTICATED_FULLY" />
    <anonymous enabled="false" />
    <custom-filter before="FIRST" ref="metadataGeneratorFilter" />
    <custom-filter after="BASIC_AUTH_FILTER" ref="samlFilter" />
  </http>
```








