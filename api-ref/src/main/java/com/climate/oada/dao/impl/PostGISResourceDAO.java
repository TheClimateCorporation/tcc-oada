package com.climate.oada.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.climate.oada.dao.IResourceDAO;
import com.climate.oada.vo.impl.LandUnit;

/**
 * ResourceDAO implementation based on PostGres+PostGIS.
 */
public final class PostGISResourceDAO implements IResourceDAO {

    static final Logger LOG = LoggerFactory.getLogger(PostGISResourceDAO.class);

    private static final String INSERT_LANDUNIT_SQL = "INSERT INTO landunit "
            + "(user_id, name, farm_name, client_name, acres, source, other_props, geom) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, "
            + "ST_GeomFromText(?))";

    private static final String SELECT_SQL_USER =
            "SELECT id, user_id, name, farm_name, client_name, "
            + "acres, source, other_props, ST_AsEWKT(geom) "
            + "FROM landunit WHERE user_id = ?";

    private static final int ID_INDEX = 1;
    private static final int USER_ID_INDEX = 2;
    private static final int NAME_INDEX = 3;
    private static final int FARM_NAME_INDEX = 4;
    private static final int CLIENT_NAME_INDEX = 5;
    private static final int ACRES_INDEX = 6;
    private static final int SOURCE_INDEX = 7;
    private static final int OTHER_PROPS_INDEX = 8;
    private static final int GEOM_INDEX = 9;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    /**
     * Default constructor.
     */
    public PostGISResourceDAO() {

    }

    /**
     * Insert a land unit.
     *
     * @param lu - A land unit value object.
     * @return boolean
     */
    public boolean insert(LandUnit lu) {
        boolean retval = false;
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(INSERT_LANDUNIT_SQL);
            ps.setLong(ID_INDEX, lu.getUnitId());
            ps.setLong(USER_ID_INDEX, lu.getUserId());
            ps.setString(NAME_INDEX, lu.getName());
            ps.setString(FARM_NAME_INDEX, lu.getFarmName());
            ps.setString(CLIENT_NAME_INDEX, lu.getClientName());
            ps.setFloat(ACRES_INDEX, lu.getAcres());
            ps.setString(SOURCE_INDEX, lu.getSource());
            ps.setString(OTHER_PROPS_INDEX, null);
            ps.setString(GEOM_INDEX, lu.getWktBoundary());
            ps.executeUpdate();
            ps.close();
            retval = true;
        } catch (SQLException e) {
            LOG.error("Landunit insert failed " + lu.toString());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error("JDBC Connection Close Failed " + e.getMessage());
                }
            }
        }
        return retval;

    }

    /**
     * Retrieve user's fields.
     *
     * @param userId - user's id.
     * @return list of land units.
     */
    public List<LandUnit> getLandUnits(Long userId) {
        return getJdbcTemplate().query(SELECT_SQL_USER, new LandUnitRowMapper(), userId.toString());
    }

    /**
     * @return the dataSource
     */
    public DataSource getDataSource() {
        return this.dataSource;
    }

    /**
     * Setter for data source. Used by Spring JDBC.
     *
     * @param ds
     *            - the dataSource to set
     */
    public void setDataSource(DataSource ds) {
        this.dataSource = ds;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * @param template the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    /**
     * Land unit row mapper class.
     */
    public static final class LandUnitRowMapper implements RowMapper<LandUnit> {

        /**
         * Default constructor.
         */
        public LandUnitRowMapper() {

        }

        @Override
        public LandUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
            LandUnit lu = new LandUnit();
            lu.setUnitId(rs.getLong(LandUnit.ID_ATTR_NAME));
            lu.setUserId(rs.getLong(LandUnit.USER_ID_ATTR_NAME));
            lu.setName(rs.getString(LandUnit.NAME_ATTR_NAME));
            lu.setFarmName(rs.getString(LandUnit.FARM_NAME_ATTR_NAME));
            lu.setClientName(rs.getString(LandUnit.CLIENT_NAME_ATTR_NAME));
            lu.setAcres(rs.getFloat(LandUnit.ACRES_ATTR_NAME));
            lu.setSource(rs.getString(LandUnit.SOURCE_ATTR_NAME));
            lu.setOtherProps(rs.getString(LandUnit.OTHER_PROPS_ATTR_NAME));
            lu.setWktBoundary(rs.getString(LandUnit.GEOM_ATTR_NAME));
            return lu;
        }

    }

}
