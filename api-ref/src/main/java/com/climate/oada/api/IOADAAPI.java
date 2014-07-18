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

package com.climate.oada.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.climate.oada.vo.IPermission;
import com.climate.oada.vo.IResource;

/**
 * Interface class for all the OADA APIs.
 */
@RequestMapping("/oada")
public interface IOADAAPI {

    String OADA_FIELDS_CONTENT_TYPE = "application/vnd.oada.fields+json";
    String OADA_PRESCRIPTIONS_CONTENT_TYPE = "application/vnd.oada.prescription";
    String OADA_AS_PLANTED_CONTENT_TYPE = "application/vnd.oada.as-planted";
    String OADA_AS_HARVESTED_CONTENT_TYPE = "application/vnd.oada.as-harvested";

    /**
     * OADA's /resources API 's responsibilities include: Storing all data:
     * binary files, JSON documents, etc. Storing user defined metadata about
     * the resource. Transform and represent data in multiple formats. Organize
     * the data in a parent/child structure (think Google Drive). Share data
     * with other users.
     *
     * This REST API specifically returns a list of resources.
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceTypes - List of resource types to filter on.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return JSON representation of resources.
     * @throws Exception in case of error.
     */
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResources(
            @RequestHeader(value = "Authorization") String accessToken,
            @RequestParam(value = "resourceType", required = false) String[] resourceTypes,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception;

    /**
     * OADA's /resources API 's responsibilities include: Storing all data:
     * binary files, JSON documents, etc. Storing user defined metadata about
     * the resource. Transform and represent data in multiple formats. Organize
     * the data in a parent/child structure (think Google Drive). Share data
     * with other users.
     *
     * This REST API specifically updates resources via POST.
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resources JSON body
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return JSON representation of resources.
     */
    @RequestMapping(value = "/resources", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> updateResources(
            @RequestHeader(value = "Authorization") String accessToken,
            @RequestBody String resources, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * OADA's /resources API 's responsibilities include: Storing all data:
     * binary files, JSON documents, etc. Storing user defined metadata about
     * the resource. Transform and represent data in multiple formats. Organize
     * the data in a parent/child structure (think Google Drive). Share data
     * with other users.
     *
     * This REST API specifically uploads a file resource via POST.
     *
     * @param accessToken - OAuth token.
     * @param file - File to upload.
     * @return ResponseEntity - JSON
     */
    @RequestMapping(value = "/resources", method = RequestMethod.POST,
            consumes = "multipart/form-data", produces = "application/json")
    @ResponseBody
    Map<String, String> uploadFile(
            @RequestHeader(value = "Authorization") String accessToken,
            @RequestParam("file") MultipartFile file);

    /**
     * Returns a specific resource identified by a "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}", method = RequestMethod.GET)
    @ResponseBody
    IResource getResource(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates a specific resource identified by a "resourceId" and returns the
     * same.
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResource(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns actual data (binary or other wise) associated with a specific
     * resource identified by a "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/data", method = RequestMethod.GET)
    @ResponseBody
    IResource getResourceData(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates actual data (binary or other wise) associated with a specific
     * resource identified by a "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/data", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResourceData(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns meta data associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/meta", method = RequestMethod.GET)
    @ResponseBody
    IResource getResourceMeta(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates meta data associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/meta", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResourceMeta(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns formats associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/formats", method = RequestMethod.GET)
    @ResponseBody
    List<String> getResourceFormats(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates formats associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/formats", method = RequestMethod.POST)
    @ResponseBody
    List<String> updateResourceFormats(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns parents associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/parents", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResourceParents(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates parents associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/parents", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> updateResourceParents(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns children associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/children", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResourceChildren(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates children associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/children", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> upateResourceChildren(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns permissions associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/permissions", method = RequestMethod.GET)
    @ResponseBody
    List<IPermission> getResourcePermissions(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates permissions associated with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/permissions", method = RequestMethod.POST)
    @ResponseBody
    List<IPermission> updateResourcePermissions(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns syncs performed with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/syncs", method = RequestMethod.GET)
    @ResponseBody
    String getResourceSyncs(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns syncs performed with a specific resource identified by a
     * "resourceId".
     *
     * @param accessToken - OAuth 2.0 token.
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/syncs", method = RequestMethod.POST)
    @ResponseBody
    String updateResourceSyncs(@RequestHeader(value = "Authorization") String accessToken,
            @PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

}
