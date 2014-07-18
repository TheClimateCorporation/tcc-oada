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

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

/**
 * OAuthUtils.
 */
public final class OAuthUtils extends OAuth2Utils {

    public static final String OAUTH_HEADER_VALUE_BEARER = DefaultOAuth2AccessToken.BEARER_TYPE;
    public static final String OAUTH_HEADER_VALUE_BEARER_PLUS_SPACE = DefaultOAuth2AccessToken.BEARER_TYPE + " ";


    public static final String HTTP_200 = "HTTP/1.1 200 OK\n";
    public static final String BYE_MESSAGE = "Bye!";
    public static final String HTTP_200_BYE_MESSAGE = HTTP_200 + "\n" + BYE_MESSAGE;
    public static final String WRONG_USER_CREDENTIALS = "Wrong user credentials";

    /**
     * Default private constructor.
     */
    private OAuthUtils() {

    }

}
