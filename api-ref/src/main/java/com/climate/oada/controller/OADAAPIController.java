package com.climate.oada.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.climate.oada.api.IOADAAPI;
import com.climate.oada.api.OADAAPIUtils;
import com.climate.oada.dao.IResourceDAO;
import com.climate.oada.vo.IPermission;
import com.climate.oada.vo.IResource;
import com.climate.oada.vo.impl.LandUnit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * OADA API Controller.
 */
@Controller
public class OADAAPIController implements IOADAAPI {

    static final Logger LOG = LoggerFactory.getLogger(OADAAPIController.class);

    static final String JSON_ARRAY_START = "[";
    static final String JSON_ARRAY_END = "]";

    private ObjectMapper mapper = new ObjectMapper();
    private IResourceDAO fieldsResourceDAO;
    private IResourceDAO s3DAO;

    /**
     * Default constructor.
     */
    public OADAAPIController() {
    }

    /**
     * Check if the resource is a file type.
     * @param type of resource.
     * @return boolean
     */
    boolean isFileType(String type) {
        if (IOADAAPI.OADA_PRESCRIPTIONS_CONTENT_TYPE.equalsIgnoreCase(type)
                || IOADAAPI.OADA_AS_PLANTED_CONTENT_TYPE.equalsIgnoreCase(type)
                || IOADAAPI.OADA_AS_HARVESTED_CONTENT_TYPE
                        .equalsIgnoreCase(type)) {
            return true;
        }
        return false;
    }

    @Override
    public List<IResource> getResources(
            @RequestHeader(value = "Authorization") String accessToken,
            @RequestParam(value = "resourceType") String[] resourceTypes,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<IResource> retval = null;
        Long userId = extractUserId(accessToken);
        if (userId != null) {
            List<String> requestedResourceTypes = new ArrayList<String>();
            if (resourceTypes != null) {
                requestedResourceTypes = Arrays.asList(resourceTypes);
            } else {
                requestedResourceTypes.add(IOADAAPI.OADA_FIELDS_CONTENT_TYPE);
                requestedResourceTypes.add(IOADAAPI.OADA_PRESCRIPTIONS_CONTENT_TYPE);
            }
            retval = new ArrayList<IResource>();
            for (String type : requestedResourceTypes) {
                if (IOADAAPI.OADA_FIELDS_CONTENT_TYPE.equalsIgnoreCase(type)) {
                    retval.addAll(getFieldsResourceDAO().getLandUnits(userId));
                } else if (isFileType(type)) {
                    retval.addAll(getS3DAO().getFileUrls(userId, type));
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    OADAAPIUtils.INVALID_ACCESS_TOKEN);
        }
        return retval;
    }

    @Override
    public List<IResource> updateResources(
            @RequestHeader(value = "Authorization") String accessToken,
            String resources, HttpServletRequest request,
            HttpServletResponse response) {
        Long userId = extractUserId(accessToken);
        String inContentType = request.getContentType();
        if (OADA_FIELDS_CONTENT_TYPE.equalsIgnoreCase(inContentType)) {
            /*
             * We assume that fields are nothing but a geospatial boundary
             * (specified in WKT / WKB / GeoJSON) along with some some metadata.
             *
             * Parse json via jackson.
             */
            List<LandUnit> lus = processFields(resources);
            for (LandUnit lu : lus) {
                lu.setUserId(userId);
                getFieldsResourceDAO().insert(lu);
            }
        }
        return null;
    }

    @Override
    public Map<String, String> uploadFile(
            @RequestHeader(value = "Authorization", required = true) String accessToken,
            @RequestParam("file") MultipartFile file) {
        Map<String, String> retval = new HashMap<String, String>();
        try {
            boolean uploadStatus = getS3DAO().saveFile(file);
            if (uploadStatus) {
                retval.put("Uploaded file name", file.getOriginalFilename());
                retval.put("fileSize", new Long(file.getSize()).toString());
            }
        } catch (Exception e) {
            retval.put("Upload failed, reason", e.getMessage());
        }
        return retval;
    }

    @Override
    public IResource getResource(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResource(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResourceData(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResourceData(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResourceMeta(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResourceMeta(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getResourceFormats(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> updateResourceFormats(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> getResourceParents(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> updateResourceParents(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> getResourceChildren(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> upateResourceChildren(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IPermission> getResourcePermissions(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IPermission> updateResourcePermissions(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getResourceSyncs(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String updateResourceSyncs(
            @RequestHeader(value = "Authorization") String accessToken,
            String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Process fields.
     *
     * @param fields
     *            - String as posted.
     *
     * @return Parsed map.
     */
    List<LandUnit> processFields(String fields) {
        List<LandUnit> retval = new ArrayList<LandUnit>();
        try {
            String json = fields;
            if (!fields.startsWith(JSON_ARRAY_START)
                    && !fields.endsWith(JSON_ARRAY_END)) {
                json = JSON_ARRAY_START + fields + JSON_ARRAY_END;
            }
            TypeReference<List<HashMap<String, String>>> typeRef = new TypeReference<List<HashMap<String, String>>>() {
            };
            List<Map<String, String>> fObjs = mapper.readValue(json, typeRef);
            for (Map<String, String> obj : fObjs) {
                LandUnit lu = new LandUnit(obj);
                retval.add(lu);
            }
            /*
             * To do - Set user id and land unit id.
             */
        } catch (Exception e) {
            LOG.error("Unable to parse fields " + e.getMessage());
        }
        return retval;
    }

    /**
     * @return the resourceDAO
     */
    public IResourceDAO getFieldsResourceDAO() {
        return fieldsResourceDAO;
    }

    /**
     * @param dao
     *            - the resourceDAO to set
     */
    public void setFieldsResourceDAO(IResourceDAO dao) {
        this.fieldsResourceDAO = dao;
    }

    /**
     * Getter for S3 DAO.
     *
     * @return the s3DAO
     */
    public IResourceDAO getS3DAO() {
        return s3DAO;
    }

    /**
     * Setter for S3 DAO.
     *
     * @param s3dao
     *            the s3DAO to set
     */
    public void setS3DAO(IResourceDAO s3dao) {
        s3DAO = s3dao;
    }

    /**
     * Extract / map / unravel userid from token.
     *
     * @param token
     *            - auth token.
     * @return Long - userid.
     */
    Long extractUserId(String token) {
        // TODO Unravel or map accessToken to user id.
        return null;
    }

}
