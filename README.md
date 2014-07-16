# OADA API Reference Implementation


## Introduction


This open source implementation provides a basic skeleton implementation for OADA REST APIs. This reference implementation is in Java and is built using a combination of:
* Spring MVC 
* Spring Security integrated with Spring SAML and Spring OAuth. 

The goal of this reference implementation is not to be exhaustive in terms of data formats or backend storage systems. It is intended to be a reference implementation that illustrates how OADA Federated Identity could be implemented and on which various parties can build their implementations.

## OADA Federated Identity

### Single Sign On (SSO) via Security Assertion Markup Language (SAML)

OADA Identity Federation is made up of all entities that decide to accept identity assertions from one another. For a complete background on OADA Federated Identity see [OADA Federated Identity] (https://github.com/OADA/oada-wiki/wiki/Federated-Identity)

This reference implementation implements SSO using Spring Security SAML Extension with SSO Circle IDP service using SAML 2.0 protocol. Here is an overview of how SAML works in the context of SSO.

![SAML Workflow](saml_diagram.jpg)

We have a working example of Web SSO against a single Identity Provider. This can be extended to multiple identity providers. In the context of SAML this reference application is a "Service Provider" and SSOCircle (http://www.ssocircle.com) is the "Identity Provider". SSOCircle is a free SAML identity provider, which provides us with a very nice way of testing a SAML-based service provider without having to install a SAML identity provider. Here's how we do it.

* We have created a SSOCircle test account (Username: tcc-oada-1 Password: testoada)
* We have uploaded our service provider metadata (more on this later) into https://idp.ssocircle.com/sso/hos/ManageSPMetadata.jsp

We have used Spring SAML Sample App as a reference to perform this integration. For more on this see: http://docs.spring.io/spring-security-saml/docs/1.0.x/reference/html/chapter-quick-start.html#quick-start-sp-metadata

#### Spring SAML Configuration

We have to added all the SAML beans in the saml-spring-security.xml

##### Generating SP Metadata

Modify metadataGeneratorFilter to specify an unique "entityId".

```
<bean id="metadataGeneratorFilter" class="org.springframework.security.saml.metadata.MetadataGeneratorFilter">
	<constructor-arg>
		<bean class="org.springframework.security.saml.metadata.MetadataGenerator">
		<property name="entityId" value="saml-test-app:test-oada-2:sanfrancisco"/>
		<property name="signMetadata" value="false"/>
		</bean>
	</constructor-arg>
</bean>
```

##### Downloading SP Metadata

Once you deploy this reference web application locally, SP metadata can be downloaded via http://localhost:8080/saml/metadata. This XML document gets uploaded to SSOCircle.

##### Sample SAML Requests

###### AuthnRequest

```
<?xml version="1.0" encoding="UTF-8"?>
<saml2p:AuthnRequest
    AssertionConsumerServiceURL="http://localhost:8080/saml/SSO/alias/defaultAlias"
    Destination="https://idp.ssocircle.com:443/sso/SSORedirect/metaAlias/ssocircle"
    ForceAuthn="false" ID="adi8g1ed3397ci3500ji7jd7h6a8d5"
    IsPassive="false" IssueInstant="2014-07-15T22:55:28.218Z"
    ProtocolBinding="urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact"
    Version="2.0" xmlns:saml2p="urn:oasis:names:tc:SAML:2.0:protocol">
    <saml2:Issuer xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion">saml-test-app:test-oada-2:sanfrancisco</saml2:Issuer>
</saml2p:AuthnRequest>
```

###### AuthnResponse

```
<?xml version="1.0" encoding="UTF-8"?>
<soap-env:Envelope xmlns:soap-env="http://schemas.xmlsoap.org/soap/envelope/">
<soap-env:Body>
    <samlp:ArtifactResponse xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol" Destination="http://localhost:8080/saml/SSO/alias/defaultAlias" ID="s2f7c51fad100299b6fac7d09eb7736754a4515966" InResponseTo="a2c33h1854639d1d4506d981e7dac9b" IssueInstant="2014-07-15T22:58:19Z" Version="2.0">
    <saml:Issuer xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion">http://idp.ssocircle.com</saml:Issuer>
    <samlp:Status xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol">
        <samlp:StatusCode xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol" Value="urn:oasis:names:tc:SAML:2.0:status:Success">
        </samlp:StatusCode>
    </samlp:Status>
    <samlp:Response xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol" Destination="http://localhost:8080/saml/SSO/alias/defaultAlias" ID="s2350ae2b11068150795d34ce77fcc7d87fe1abae3" InResponseTo="adi8g1ed3397ci3500ji7jd7h6a8d5" IssueInstant="2014-07-15T22:58:18Z" Version="2.0">
    <saml:Issuer xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion">http://idp.ssocircle.com</saml:Issuer>
    <samlp:Status xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol">
        <samlp:StatusCode xmlns:samlp="urn:oasis:names:tc:SAML:2.0:protocol" Value="urn:oasis:names:tc:SAML:2.0:status:Success">
        </samlp:StatusCode>
    </samlp:Status>
    <saml:Assertion xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion" ID="s2402f5ccf161d45134eee032d3f7ea4386c94203a" IssueInstant="2014-07-15T22:58:18Z" Version="2.0">
        <saml:Issuer>http://idp.ssocircle.com</saml:Issuer>
        <ds:Signature xmlns:ds="http://www.w3.org/2000/09/xmldsig#">
        <ds:SignedInfo>
            <ds:CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
            <ds:SignatureMethod Algorithm="http://www.w3.org/2000/09/xmldsig#rsa-sha1"/>
            <ds:Reference URI="#s2402f5ccf161d45134eee032d3f7ea4386c94203a">
                <ds:Transforms>
                    <ds:Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
                    <ds:Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
                </ds:Transforms>
                <ds:DigestMethod Algorithm="http://www.w3.org/2000/09/xmldsig#sha1"/>
                <ds:DigestValue>E1KFJYacAjMr0+5jH+R0BtrzTaI=</ds:DigestValue>
            </ds:Reference>
        </ds:SignedInfo>
        <ds:SignatureValue>
            Mw18rHZ5zw6Rz+RvKN/i788Dhg4aWGogotwes2kR8EQJ1U4LrGg7/nxWeYeE1cXFdp65T8Kri4OA
            RIhgeeGd+97st15sjyTln5uq6l9pjSnGCS+SgSg62YpjzNDQnPjWGuuiiPbDme9DSyfSt5jZmWkG
            7NAnZ68Z8pZ1H/tENMU=
        </ds:SignatureValue>
        <ds:KeyInfo>
            <ds:X509Data>
                <ds:X509Certificate>
                    MIICjDCCAXSgAwIBAgIFAJRvxcMwDQYJKoZIhvcNAQEEBQAwLjELMAkGA1UEBhMCREUxEjAQBgNV
                    BAoTCVNTT0NpcmNsZTELMAkGA1UEAxMCQ0EwHhcNMTEwNTE3MTk1NzIxWhcNMTYwODE3MTk1NzIx
                    WjBLMQswCQYDVQQGEwJERTESMBAGA1UEChMJU1NPQ2lyY2xlMQwwCgYDVQQLEwNpZHAxGjAYBgNV
                    BAMTEWlkcC5zc29jaXJjbGUuY29tMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbzDRkudC/
                    aC2gMqRVVaLdPJJEwpFB4o71fR5bnNd2ocnnNzJ/W9CoCargzKx+EJ4Nm3vWmX/IZRCFvrvy9C78
                    fP1cmt6Sa091K9luaMAyWn7oC8h/YBXH7rB42tdvWLY4Kl9VJy6UCclvasyrfKx+SR4KU6zCsM62
                    2Kvp5wW67QIDAQABoxgwFjAUBglghkgBhvhCAQEBAf8EBAMCBHAwDQYJKoZIhvcNAQEEBQADggEB
                    AJ0heua7mFO3QszdGu1NblGaTDXtf6Txte0zpYIt+8YUcza2SaZXXvCLb9DvGxW1TJWaZpPGpHz5
                    tLXJbdYQn7xTAnL4yQOKN6uNqUA/aTVgyyUJkWZt2giwEsWUvG0UBMSPS1tp2pV2c6/olIcbdYU6
                    ZecUz6N24sSS7itEBC6nwCVBoHOL8u6MsfxMLDzJIPBI68UZjz3IMKTDUDv6U9DtYmXLc8iMVZBn
                    cYJn9NgNi3ghl9fYPpHcc6QbXeDUjhdzXXUqG+hB6FabGqdTdkIZwoi4gNpyr3kacKRVWJssDgak
                    eL2MoDNqJyQ0fXC6Ze3f79CKy/WjeU5FLwDZR0Q=
                </ds:X509Certificate>
            </ds:X509Data>
        </ds:KeyInfo>
    </ds:Signature>
    <saml:Subject>
        <saml:NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress" NameQualifier="http://idp.ssocircle.com">giri@climate.com</saml:NameID>
        <saml:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:bearer">
            <saml:SubjectConfirmationData InResponseTo="adi8g1ed3397ci3500ji7jd7h6a8d5" NotOnOrAfter="2014-07-15T23:08:18Z" Recipient="http://localhost:8080/saml/SSO/alias/defaultAlias"/>
        </saml:SubjectConfirmation>
    </saml:Subject>
    <saml:Conditions NotBefore="2014-07-15T22:48:18Z" NotOnOrAfter="2014-07-15T23:08:18Z">
        <saml:AudienceRestriction>
            <saml:Audience>saml-test-app:test-oada-2:sanfrancisco</saml:Audience>
        </saml:AudienceRestriction>
    </saml:Conditions>
    <saml:AuthnStatement AuthnInstant="2014-07-15T22:58:18Z" SessionIndex="s2af658adcb50b73f1156ed1f67d1d75956a01a901">
        <saml:AuthnContext>
            <saml:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport</saml:AuthnContextClassRef>
        </saml:AuthnContext>
    </saml:AuthnStatement>
    <saml:AttributeStatement>
        <saml:Attribute Name="EmailAddress">
            <saml:AttributeValue xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">giri@climate.com</saml:AttributeValue>
        </saml:Attribute>
        <saml:Attribute Name="FirstName">
            <saml:AttributeValue xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">Climate</saml:AttributeValue>
        </saml:Attribute>
        <saml:Attribute Name="LastName">
            <saml:AttributeValue xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">Corporation</saml:AttributeValue>
        </saml:Attribute>
    </saml:AttributeStatement>
</saml:Assertion>
</samlp:Response>
</samlp:ArtifactResponse>
</soap-env:Body>
</soap-env:Envelope>

```

###### SAML Assertion

```
<?xml version="1.0" encoding="UTF-8"?>
<saml:Assertion xmlns:saml="urn:oasis:names:tc:SAML:2.0:assertion" ID="s2402f5ccf161d45134eee032d3f7ea4386c94203a" IssueInstant="2014-07-15T22:58:18Z" Version="2.0">
    <saml:Issuer>http://idp.ssocircle.com</saml:Issuer>
    <saml:Subject>
        <saml:NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress" NameQualifier="http://idp.ssocircle.com">giri@climate.com</saml:NameID>
        <saml:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:bearer">
            <saml:SubjectConfirmationData InResponseTo="adi8g1ed3397ci3500ji7jd7h6a8d5" NotOnOrAfter="2014-07-15T23:08:18Z" Recipient="http://localhost:8080/saml/SSO/alias/defaultAlias"/>
        </saml:SubjectConfirmation>
    </saml:Subject>
    <saml:Conditions NotBefore="2014-07-15T22:48:18Z" NotOnOrAfter="2014-07-15T23:08:18Z">
        <saml:AudienceRestriction>
            <saml:Audience>saml-test-app:test-oada-2:sanfrancisco</saml:Audience>
        </saml:AudienceRestriction>
    </saml:Conditions>
    <saml:AuthnStatement AuthnInstant="2014-07-15T22:58:18Z" SessionIndex="s2af658adcb50b73f1156ed1f67d1d75956a01a901">
        <saml:AuthnContext>
            <saml:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport</saml:AuthnContextClassRef>
        </saml:AuthnContext>
    </saml:AuthnStatement>
    <saml:AttributeStatement>
        <saml:Attribute Name="EmailAddress">
            <saml:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">giri@climate.com</saml:AttributeValue>
        </saml:Attribute>
        <saml:Attribute Name="FirstName">
            <saml:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">Climate</saml:AttributeValue>
        </saml:Attribute>
        <saml:Attribute Name="LastName">
            <saml:AttributeValue xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">Corporation</saml:AttributeValue>
        </saml:Attribute>
    </saml:AttributeStatement>
</saml:Assertion>
```


### OAUTH 2.0 Support

[[OAuth 2.0 Support|oauth]]


### Spring Security + Spring SAML + Spring OAuth 

The main interface which provides authentication services in Spring Security is the AuthenticationManager. The bean instance is registered using the "authentication-manager" namespace element. This Authentication Manager is the one that is responsible for the user’s authentication. Within it, there is a declaration of the provider, which holds the users and passwords. In most real world implementations, this provider directs to a JDBC, where all users are stored with their encrypted passwords. In case of WebSSO, we do not have to handle any users and passwords, since we delegate this to the IdP. So, we change the declaration of our server to point to the authentication manager of the SAML SP: this manager has a provider (SAMLAuthenticationProvider or some other class that extends it). This class implements the authenticate() method, that attempts to perform authentication of an Authentication object. 

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








