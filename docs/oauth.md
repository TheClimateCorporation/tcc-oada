# Introduction

This document describes OAuth 2.0 support in OADA Reference Implementation. OAuth 2.0 support is built on top of spring security (http://projects.spring.io/spring-security/). We detail all the Spring OAuth 2.0 Configurations. 

## Background

Oauth 2.0 is an open protocol to allow secure authorization in a simple and standard method from web, mobile and desktop applications. It is a simple way to publish and interact with protected data. It’s also a safer and more secure way for server to give access to its protected resources. OAuth protocol enables a user to access protected resources from trusted client through an API.

Here is how OAuth works:

![OAuth Workflow](oauth_diagram.jpg)

# Spring OAuth 2.0 Configuration

We have added all OAuth 2.0 beans in spring-security.xml. The reference implementation uses in memory token store.

## Implementation

### Token Endpoint

Client application will send request to the URL “/oauth/token”. This request will contain the clientId, client password, userName and user password details. One would configure such an endpoint like so in spring security:

```
<http pattern="/oauth/token" create-session="stateless"
		authentication-manager-ref="authenticationManager"
		xmlns="http://www.springframework.org/schema/security" > 
	<intercept-url pattern="/oauth/token" access="IS_AUTHENTICATED_FULLY" />
	<anonymous enabled="false" />
	<http-basic entry-point-ref="clientAuthenticationEntryPoint"/>
	<custom-filter ref="clientCredentialsTokenEndpointFilter" before="BASIC_AUTH_FILTER" /> 
	<access-denied-handler ref="oauthAccessDeniedHandler" />
</http>

<bean id="clientCredentialsTokenEndpointFilter" class="org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter">
    <property name="authenticationManager" ref="authenticationManager"/>
</bean>

<authentication-manager xmlns="http://www.springframework.org/schema/security" alias="authenticationManager">
<authentication-provider user-service-ref="clientDetailsUserService"/>
</authentication-manager>

<bean id="clientDetailsUserService" class="org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService">
    <constructor-arg ref="clientDetails"/>
</bean>

<bean id="clientAuthenticationEntryPoint" class="org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint">
    <property name="realmName" value="springsec/client"/>
    <property name="typeName" value="Basic"/>
</bean>

<bean id="oauthAccessDeniedHandler" class="org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler"/>

```

* The clientCredentialsTokenEndpointFilter filter will extracts client credentials from request & create authentication object in security context
* Then authentication manager extracts the Authentication Object set in the SecurityContext and passes it to the clientDetailsUserService.
* The clientDetailsUserService holds reference to an instance of a custom bean clientDetails. The ClientDetailsServiceImpl class implements the ClientDetailsService interface and implements the method loadClientByClientId. The clientDetailsUserService bean calls this method to authenticate the client.
* If the Client Credentials are valid then authentication manager will update authentication object in the application security context and set the authenticated flag to true.
* The request is then forwarded to the Oauth server for retrieving the access token.
* If the authentication fails the request is forwarded to the clientAuthenticationEntryPoint which displays the appropriate error to the user.

**PLEASE NOTE THAT THIS IS A TYPICAL OAUTH CONFIGURATION FOR TOKEN ENDPOINT. IN OUR FINAL STATE THIS ENDPOINT IS AUTHENTICATED USING SINGLE SIGN ON (SSO) USING SAML. WE DOCUMENT THIS HERE FOR COMPLETENESS**

In SAML + OAuth workflow, we bypass this whole client and user authentication listed above and go directly to samlAuthenticationProvider.

```
  <authentication-manager alias="authenticationManager"
    xmlns="http://www.springframework.org/schema/security">
    <authentication-provider ref="samlAuthenticationProvider"/>
  </authentication-manager>
```

## OAuth Server Configuration

```
 <oauth:authorization-server
    client-details-service-ref="clientDetails" token-services-ref="tokenServices">
    <oauth:authorization-code />
    <oauth:implicit />
    <oauth:refresh-token />
    <oauth:client-credentials />
    <oauth:password authentication-manager-ref="userAuthenticationManager"/>
  </oauth:authorization-server>

  <authentication-manager id="userAuthenticationManager" 
	xmlns="http://www.springframework.org/schema/security">
	<authentication-provider  ref="customUserAuthenticationProvider">
	</authentication-provider>
  </authentication-manager>

  <beans:bean id="customUserAuthenticationProvider"
    class="com.climate.oada.security.oauth.CustomUserAuthenticationProvider">
  </beans:bean>

  <beans:bean id="tokenServices"
    class="com.climate.oada.security.oauth.TokenServicesImpl">
    <beans:property name="tokenStore" ref="tokenStore" />
    <beans:property name="supportRefreshToken" value="true" />
    <beans:property name="accessTokenValiditySeconds"
      value="900000000"></beans:property>
    <beans:property name="clientDetailsService" ref="clientDetails" />
  </beans:bean>

  <beans:bean id="tokenStore"
    class="org.springframework.security.oauth2.provider.token.InMemoryTokenStore" />

  <beans:bean id="oauthAuthenticationEntryPoint"
    class="org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint">
  </beans:bean>

  <oauth:resource-server id="resourceServerFilter"
    resource-id="springsec" token-services-ref="tokenServices" />

  <beans:bean id="accessDecisionManager"
    class="org.springframework.security.access.vote.UnanimousBased" xmlns="http://www.springframework.org/schema/beans">
    <beans:constructor-arg>
      <list>
        <beans:bean
          class="org.springframework.security.oauth2.provider.vote.ScopeVoter" />
        <beans:bean class="org.springframework.security.access.vote.RoleVoter" />
        <beans:bean
          class="org.springframework.security.access.vote.AuthenticatedVoter" />
      </list>
    </beans:constructor-arg>
  </beans:bean>

  <beans:bean id="logoutSuccessHandler" class="com.climate.oada.security.oauth.LogoutImpl">
    <beans:property name="tokenStore" ref="tokenStore"></beans:property>
  </beans:bean>
```

* The Oauth Server receives the request once the client is authenticated using above steps.
* <oauth:authorization-server> - This tag is defined to configure authorization-server of oauth. Server serves requests based on grant type.
* If the Grant type of request is a password, client and user needs to be authenticated by the Oauth server.Since the Client is already authenticated in process mentioned above the Oauth server now checks for the User authentication using the userAuthenticationManager.Then userAuthenticationManager extracts the Authentication Object set in the SecurityContext and passes it to the customUserAuthenticationProvider. The CustomUserAuthenticationProvider class implements the AuthenticationProvider interface and implements the method authenticate to authenticate the user. If the User Credentials are valid then authentication manager will update authentication object in the application security context and set the authenticated flag to true.
* Then tokenServices will then generate the access token using tokenStore. The tokenStore uses InMemoryTokenStore to create access token and wraps authentication from SecurityContext into token. Authorization-server will send the access token in response to user request
* If authentication fails, the request is forwarded to the oauthAuthenticationEntryPoint which displays the appropriate error to the User

In the final state, we use implict grant flow (http://tools.ietf.org/html/rfc6749#section-4.2). Reason being username-password authentication is handled via SSO. Therefore there is "authorization token" returned in the flow. If the user is authenticated over SAML-WebSSO workflow, we can return the OAuth access token to user directly.


## Usage

* A client application will send request to the URL “http://localhost:8080/oauth/token?grant_type=implicit” to retrieve an access token. 
* Client is then pushed into the WebSSO work flow. In our case user needs to login via SSOCircle. Client logs into SSOCircle.SSOCircle redirects the client back to our reference application. To be specific it redirects the client to http://localhost:8080/saml/SSO/alias/defaultAlias. This URL is part of the SP Metadata that we uploaded to SSO Circle.
* Reference application then generates OAuth 2.0 token.
* Client then uses this access token to make calls to /oada/** APIs. 

Each OADA REST API requires an Authorization header with the OAuth token. OAuth Token is provided in the request as "Authorization" header like so:

``` 
User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36
Authorization: Bearer f096f0bf-7414-4291-922f-d29eabd9d5f6kmmxgw9sIAXKsQS20t9JLQ==
Accept: */*
Accept-Encoding: gzip,deflate,sdch
Accept-Language: en-US,en;q=0.8,ms;q=0.6,ta;q=0.4
```
