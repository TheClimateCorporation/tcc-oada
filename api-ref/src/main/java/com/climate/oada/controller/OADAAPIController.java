package com.climate.oada.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.climate.oada.api.IOADAAPI;
import com.climate.oada.vo.IPermission;
import com.climate.oada.vo.IResource;


/**
 * OADA API Controller.
 */
@Controller
public final class OADAAPIController implements IOADAAPI {

    /**
     * Default constructor.
     */
    public OADAAPIController() {

    }

    @Override
    public List<IResource> getResources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> updateResources() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResource(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResource(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResourceData(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResourceData(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource getResourceMeta(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResource updateResourceMeta(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getResourceFormats(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> updateResourceFormats(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> getResourceParents(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> updateResourceParents(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> getResourceChildren(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IResource> upateResourceChildren(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IPermission> getResourcePermissions(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IPermission> updateResourcePermissions(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getResourceSyncs(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String updateResourceSyncs(String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }


}
