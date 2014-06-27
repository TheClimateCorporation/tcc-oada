OADA API Reference Implementation
=================================

This open source implementation provides a basic skeleton implementation for OADA REST APIs. This reference implementation is in Java and is built using a combination of Spring MVC and Spring Security. The goal of this reference implementation is not to be exhaustive in terms of data formats or backend storage systems. It is intended to be a reference implementation on which various parties can build their implementations.

OAUTH 2.0 Support
-----------------

/oauth/token - This API authenticates over basic authentication and returns OAuth 2.0 bearer token. This request requires:
client_id=client-test&client_secret=client-secret&username=test&password=test&grant_type=password

Each OADA REST API requires an Authorization header with the OAuth token.

Fields
------

File Upload
-----------
