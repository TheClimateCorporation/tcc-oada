package com.climate.oada.api;

import com.climate.oada.vo.IPermission;
import com.climate.oada.vo.IResource;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Interface class for all the OADA APIs.
 */
@RequestMapping("/oada")
public interface IOADAAPI {

    String OADA_FIELDS_CONTENT_TYPE = "application/vnd.oada.cff+json";

    /**
     * OADA's /resources API 's responsibilities include: Storing all data:
     * binary files, JSON documents, etc. Storing user defined metadata about
     * the resource. Transform and represent data in multiple formats. Organize
     * the data in a parent/child structure (think Google Drive). Share data
     * with other users.
     *
     * This REST API specifically returns a list of resources.
     *
     * @return JSON representation of resources.
     */
    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResources();

    /**
     * OADA's /resources API 's responsibilities include: Storing all data:
     * binary files, JSON documents, etc. Storing user defined metadata about
     * the resource. Transform and represent data in multiple formats. Organize
     * the data in a parent/child structure (think Google Drive). Share data
     * with other users.
     *
     * This REST API specifically updates resources via POST.
     *
     * @return JSON representation of resources.
     */
    @RequestMapping(value = "/resources", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> updateResources();

    /**
     * Returns a specific resource identified by a "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}", method = RequestMethod.GET)
    @ResponseBody
    IResource getResource(@PathVariable String resourceId);

    /**
     * Updates a specific resource identified by a "resourceId" and returns the
     * same.
     *
     * @param resourceId
     *            - Id for the resource.
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResource(@PathVariable String resourceId);

    /**
     * Returns actual data (binary or other wise) associated with a specific
     * resource identified by a "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/data", method = RequestMethod.GET)
    @ResponseBody
    IResource getResourceData(@PathVariable String resourceId);

    /**
     * Updates actual data (binary or other wise) associated with a specific
     * resource identified by a "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/data", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResourceData(@PathVariable String resourceId);

    /**
     * Returns meta data associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/meta", method = RequestMethod.GET)
    @ResponseBody
    IResource getResourceMeta(@PathVariable String resourceId);

    /**
     * Updates meta data associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return IResource
     */
    @RequestMapping(value = "/resources/{resourceId}/meta", method = RequestMethod.POST)
    @ResponseBody
    IResource updateResourceMeta(@PathVariable String resourceId);

    /**
     * Returns formats associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/formats", method = RequestMethod.GET)
    @ResponseBody
    List<String> getResourceFormats(@PathVariable String resourceId);

    /**
     * Updates formats associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/formats", method = RequestMethod.POST)
    @ResponseBody
    List<String> updateResourceFormats(@PathVariable String resourceId);

    /**
     * Returns parents associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/parents", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResourceParents(@PathVariable String resourceId);

    /**
     * Updates parents associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/parents", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> updateResourceParents(@PathVariable String resourceId);

    /**
     * Returns children associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/children", method = RequestMethod.GET)
    @ResponseBody
    List<IResource> getResourceChildren(@PathVariable String resourceId);

    /**
     * Updates children associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/children", method = RequestMethod.POST)
    @ResponseBody
    List<IResource> upateResourceChildren(@PathVariable String resourceId);

    /**
     * Returns permissions associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/permissions", method = RequestMethod.GET)
    @ResponseBody
    List<IPermission> getResourcePermissions(@PathVariable String resourceId);

    /**
     * Updates permissions associated with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/permissions", method = RequestMethod.POST)
    @ResponseBody
    List<IPermission> updateResourcePermissions(@PathVariable String resourceId);

    /**
     * Returns syncs performed with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/syncs", method = RequestMethod.GET)
    @ResponseBody
    String getResourceSyncs(@PathVariable String resourceId);

    /**
     * Returns syncs performed with a specific resource identified by a
     * "resourceId".
     *
     * @param resourceId
     *            - Id for the resource.
     * @return List of Strings.
     */
    @RequestMapping(value = "/resources/{resourceId}/syncs", method = RequestMethod.POST)
    @ResponseBody
    String updateResourceSyncs(@PathVariable String resourceId);

}
