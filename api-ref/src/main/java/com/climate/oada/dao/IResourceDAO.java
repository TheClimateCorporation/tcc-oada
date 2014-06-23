package com.climate.oada.dao;

import java.util.List;

import com.climate.oada.vo.impl.LandUnit;

/**
 * Interface for Resource DAO. Abstracts actual storage details.
 */
public interface IResourceDAO {

    /**
     * Insert a given LandUnit.
     *
     * @param lu - A land unit value object.
     * @return boolean
     */
    boolean insert(LandUnit lu);

    /**
     * Retrieve user's fields.
     *
     * @param userId - user's id.
     * @return list of land units.
     */
    List<LandUnit> getLandUnits(Long userId);
}
