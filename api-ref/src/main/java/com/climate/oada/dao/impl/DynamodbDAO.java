package com.climate.oada.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.climate.oada.dao.IResourceDAO;
import com.climate.oada.vo.impl.FileResource;
import com.climate.oada.vo.impl.LandUnit;

/**
 * ResourceDAO implementation based on AWS DynamoDB.
 */
public final class DynamodbDAO implements IResourceDAO {

    static final Logger LOG = LoggerFactory.getLogger(DynamodbDAO.class);

    static final String LANDUNIT_DYNAMO_DB_TABLE_NAME = "landunit";

    private AmazonDynamoDBClient dynamoDB;

    /**
     * Default constructor.
     */
    public DynamodbDAO() {

    }

    /**
     * Init method to intialize AWS credentials.
     *
     * @throws Exception in case of error.
     */
    public void init() throws Exception {
        AWSCredentials credentials = new PropertiesCredentials(
                AmazonDynamoDB.class.getResourceAsStream("AwsCredentials.properties"));
        dynamoDB = new AmazonDynamoDBClient(credentials);
    }


    @Override
    public boolean insert(LandUnit lu) {
        boolean retval = false;
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put(LandUnit.ID_ATTR_NAME, new AttributeValue(Long.toString(lu.getUnitId())));
        item.put(LandUnit.USER_ID_ATTR_NAME, new AttributeValue(Long.toString(lu.getUserId())));
        item.put(LandUnit.NAME_ATTR_NAME, new AttributeValue(lu.getName()));
        item.put(LandUnit.FARM_NAME_ATTR_NAME, new AttributeValue(lu.getFarmName()));
        item.put(LandUnit.CLIENT_NAME_ATTR_NAME, new AttributeValue(lu.getClientName()));
        item.put(LandUnit.ACRES_ATTR_NAME, new AttributeValue(Float.toString(lu.getAcres())));
        item.put(LandUnit.SOURCE_ATTR_NAME, new AttributeValue(lu.getSource()));
        item.put(LandUnit.OTHER_PROPS_ATTR_NAME, new AttributeValue(lu.getOtherProps()));
        item.put(LandUnit.GEOM_ATTR_NAME, new AttributeValue(lu.getWktBoundary()));
        PutItemRequest putItemRequest = new PutItemRequest(LANDUNIT_DYNAMO_DB_TABLE_NAME, item);
        try {
            PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
            LOG.debug("DDB Insert Result: " + putItemResult);
            retval = true;
        } catch (Exception e) {
            LOG.error("DDB Insert failed " + e.getMessage());
        }
        return retval;
    }


    @Override
    public List<LandUnit> getLandUnits(Long userId) {
        List<LandUnit> retval = new ArrayList<LandUnit>();
        try {
            /*
             * Scan items for movies with user id attribute.
             */
            Map<String, Condition> scanFilter = new HashMap<String, Condition>();
            Condition condition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withN(userId.toString()));
            scanFilter.put(LandUnit.USER_ID_ATTR_NAME, condition);
            ScanRequest scanRequest =
                    new ScanRequest(LANDUNIT_DYNAMO_DB_TABLE_NAME).withScanFilter(scanFilter);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            LOG.debug("DDB Scan Result: " + scanResult);
            retval = mapItemsToLandUnit(scanResult.getItems());
        } catch (Exception e) {
            LOG.error("Unable to retrieve land units from DDB " + e.getMessage());
        }
        return retval;
    }

    /**
     * Maps AWS SDK AttributeValue map to LandUnit value object.
     *
     * @param items - Map of scan result.
     * @return List of LandUnits.
     */
    private List<LandUnit> mapItemsToLandUnit(List<Map<String, AttributeValue>> items) {
        List<LandUnit> retval = new ArrayList<LandUnit>();
        for (Map<String, AttributeValue> item : items) {
            LandUnit lu = new LandUnit();
            lu.setUnitId(new Long(item.get(LandUnit.ID_ATTR_NAME).getS()));
            lu.setUserId(new Long(item.get(LandUnit.USER_ID_ATTR_NAME).getS()));
            lu.setName(item.get(LandUnit.NAME_ATTR_NAME).getS());
            lu.setFarmName(item.get(LandUnit.FARM_NAME_ATTR_NAME).getS());
            lu.setClientName(item.get(LandUnit.CLIENT_NAME_ATTR_NAME).getS());
            lu.setAcres(new Float(item.get(LandUnit.ID_ATTR_NAME).getS()));
            lu.setSource(item.get(LandUnit.SOURCE_ATTR_NAME).getS());
            lu.setOtherProps(item.get(LandUnit.OTHER_PROPS_ATTR_NAME).getS());
            lu.setWktBoundary(item.get(LandUnit.GEOM_ATTR_NAME).getS());
            retval.add(lu);
        }
        return retval;
    }

    @Override
    public boolean saveFile(MultipartFile f) throws Exception {
        throw new UnsupportedOperationException("DynamoDDB DAO does not support file upload");
    }

    @Override
    public List<FileResource> getFileUrls(Long userId, String type) {
        throw new UnsupportedOperationException("DynamoDDB DAO does not support file urls");
    }

}
