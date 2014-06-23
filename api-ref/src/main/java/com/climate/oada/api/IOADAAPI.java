package com.climate.oada.api;

import com.climate.oada.vo.IPermission;
import com.climate.oada.vo.IResource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Interface class for all the OADA APIs.
 */
@RequestMapping("/oada")
public interface IOADAAPI {

    String OADA_FIELDS_CONTENT_TYPE = "application/vnd.oada.fields+json";

    /**
     * OADA's /resources API 's responsibilities include: Storing all data:
     * binary files, JSON documents, etc. Storing user defined metadata about
     * the resource. Transform and represent data in multiple formats. Organize
     * the data in a parent/child structure (think Google Drive). Share data
     * with other users.
     *
     * This REST API specifically returns a list of resources.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return JSON representation of resources.
     */
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResources(HttpServletRequest request,
            HttpServletResponse response);

    /**
     * OADA's /resources API 's responsibilities include: Storing all data:
     * binary files, JSON documents, etc. Storing user defined metadata about
     * the resource. Transform and represent data in multiple formats. Organize
     * the data in a parent/child structure (think Google Drive). Share data
     * with other users.
     *
     * This REST API specifically updates resources via POST.
     *
     * @param resources JSON body
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return JSON representation of resources.
     */
    @RequestMapping(value = "/resources", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> updateResources(@RequestBody String resources, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns a specific resource identified by a "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}", method = RequestMethod.GET)
    @ResponseBody
    IResource getResource(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates a specific resource identified by a "resourceId" and returns the
     * same.
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResource(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns actual data (binary or other wise) associated with a specific
     * resource identified by a "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/data", method = RequestMethod.GET)
    @ResponseBody
    IResource getResourceData(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates actual data (binary or other wise) associated with a specific
     * resource identified by a "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/data", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResourceData(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns meta data associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/meta", method = RequestMethod.GET)
    @ResponseBody
    IResource getResourceMeta(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates meta data associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/meta", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResourceMeta(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns formats associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/formats", method = RequestMethod.GET)
    @ResponseBody
    List<String> getResourceFormats(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates formats associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/formats", method = RequestMethod.POST)
    @ResponseBody
    List<String> updateResourceFormats(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns parents associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/parents", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResourceParents(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates parents associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/parents", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> updateResourceParents(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns children associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/children", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResourceChildren(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates children associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/children", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> upateResourceChildren(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns permissions associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/permissions", method = RequestMethod.GET)
    @ResponseBody
    List<IPermission> getResourcePermissions(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Updates permissions associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/permissions", method = RequestMethod.POST)
    @ResponseBody
    List<IPermission> updateResourcePermissions(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns syncs performed with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/syncs", method = RequestMethod.GET)
    @ResponseBody
    String getResourceSyncs(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * Returns syncs performed with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     *
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/syncs", method = RequestMethod.POST)
    @ResponseBody
    String updateResourceSyncs(@PathVariable String resourceId, HttpServletRequest request,
            HttpServletResponse response);

}
