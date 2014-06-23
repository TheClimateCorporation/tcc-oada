package com.climate.oada.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.climate.oada.api.IOADAAPI;
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
public final class OADAAPIController implements IOADAAPI {

    static final Logger LOG = LoggerFactory.getLogger(OADAAPIController.class);

    static final String JSON_ARRAY_START = "[";
    static final String JSON_ARRAY_END = "]";

    private ObjectMapper mapper = new ObjectMapper();
    private IResourceDAO resourceDAO;

    /**
     * Default constructor.
     */
    public OADAAPIController() {

    }

    @Override
    public List<IResource> getResources(HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> updateResources(String resources,
            HttpServletRequest request, HttpServletResponse response) {
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
                getResourceDAO().insert(lu);
            }
        }
        return null;
    }

    @Override
    public IResource getResource(String resourceId, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResource(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResourceData(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResourceData(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResourceMeta(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResourceMeta(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getResourceFormats(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> updateResourceFormats(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> getResourceParents(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> updateResourceParents(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> getResourceChildren(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> upateResourceChildren(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IPermission> getResourcePermissions(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IPermission> updateResourcePermissions(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getResourceSyncs(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String updateResourceSyncs(String resourceId,
            HttpServletRequest request, HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Process fields.
     *
     * @param fields - String as posted.
     *
     * @return Parsed map.
     */
    List<LandUnit> processFields(String fields) {
        List<LandUnit> retval = new ArrayList<LandUnit>();
        try {
            String json = fields;
            if (!fields.startsWith(JSON_ARRAY_START) && !fields.endsWith(JSON_ARRAY_END)) {
                json = JSON_ARRAY_START + fields + JSON_ARRAY_END;
            }
            TypeReference<List<HashMap<String, String>>> typeRef =
                    new TypeReference<List<HashMap<String, String>>>() { };
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
    public IResourceDAO getResourceDAO() {
        return resourceDAO;
    }

    /**
     * @param dao - the resourceDAO to set
     */
    public void setResourceDAO(IResourceDAO dao) {
        this.resourceDAO = dao;
    }
}
