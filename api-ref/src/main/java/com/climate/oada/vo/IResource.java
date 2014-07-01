package com.climate.oada.vo;

/**
 * A base interface for all OADA resources.
 */
public interface IResource {

    /**
     * Setter for resource type.
     * @param type of resource (mimetype etc)
     */
    void setResourceType(String type);

    /**
     * Getter for resource type.
     * @return String
     */
    String getResourceType();

}
