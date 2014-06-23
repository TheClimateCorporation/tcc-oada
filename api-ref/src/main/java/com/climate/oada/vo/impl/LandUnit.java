package com.climate.oada.vo.impl;

import com.climate.oada.vo.IResource;

/**
 * A Value object representing a LandUnit. A land unit is defined by a boundary
 * and a bunch of associated properties (key-value pairs).
 *
 * Example:
 * {"name":"Hole","farmName":"One Crop Farm","clientName":"Spade Grower"
 * ,"acres":68.43, "source":"Unknown", "boundary":"Polygon((-89.12455178
 * 40.95605095,-89.12457258 40.95957925,-89.13409765 40.95957850,-89.13404241
 * 40.95611545,-89.12455178 40.95605095), (-89.12872902 40.95720339,-89.12878266
 * 40.95848356,-89.12829987 40.95845926,- 89.12689439 40.95800553,-89.12512413
 * 40.95811896,-89.12498466 40.95721150, -89.12872902 40.95720339))"}
 */
public final class LandUnit implements IResource {

    public static final String ID_ATTR_NAME = "id";
    public static final String USER_ID_ATTR_NAME = "user_id";
    public static final String NAME_ATTR_NAME = "name";
    public static final String FARM_NAME_ATTR_NAME = "farm_name";
    public static final String CLIENT_NAME_ATTR_NAME = "client_name";
    public static final String ACRES_ATTR_NAME = "acres";
    public static final String SOURCE_ATTR_NAME = "source";
    public static final String OTHER_PROPS_ATTR_NAME = "other_props";
    public static final String GEOM_ATTR_NAME = "geom";

    private Long userId;
    private Long unitId;
    private String name;
    private String farmName;
    private String clientName;
    private float acres;
    private String source;
    private String otherProps;
    private String wktBoundary;

    /**
     * Default constructor.
     */
    public LandUnit() {

    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * @param id the userId to set
     */
    public void setUserId(Long id) {
        this.userId = id;
    }

    /**
     * @return the unitId
     */
    public Long getUnitId() {
        return this.unitId;
    }

    /**
     * @param id the unitId to set
     */
    public void setUnitId(Long id) {
        this.unitId = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param n - the name to set
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * @return the farmName
     */
    public String getFarmName() {
        return this.farmName;
    }

    /**
     * @param n - the farmName to set
     */
    public void setFarmName(String n) {
        this.farmName = n;
    }

    /**
     * @return the clientName
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * @param n - the clientName to set
     */
    public void setClientName(String n) {
        this.clientName = n;
    }

    /**
     * @return the acres
     */
    public float getAcres() {
        return this.acres;
    }

    /**
     * @param a - the acres to set
     */
    public void setAcres(float a) {
        this.acres = a;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return this.source;
    }

    /**
     * @param src the source to set
     */
    public void setSource(String src) {
        this.source = src;
    }

    /**
     * @return the wktBoundary
     */
    public String getWktBoundary() {
        return this.wktBoundary;
    }

    /**
     * @param boundary - the wktBoundary to set
     */
    public void setWktBoundary(String boundary) {
        this.wktBoundary = boundary;
    }

    /**
     * @return the otherProps
     */
    public String getOtherProps() {
        return otherProps;
    }

    /**
     * @param props the otherProps to set
     */
    public void setOtherProps(String props) {
        this.otherProps = props;
    }
}
