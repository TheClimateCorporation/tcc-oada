/*
 * Copyright (C) 2014 The Climate Corporation and released under an Apache 2.0 license.
 * You may not use this library except in compliance with the License.
 * You may obtain a copy of the License at:

 * http://www.apache.org/licenses/LICENSE-2.0

 * See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.climate.oada.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.climate.oada.api.IOADAAPI;
import com.climate.oada.api.OADAAPIUtils;
import com.climate.oada.dao.IResourceDAO;
import com.climate.oada.vo.IResource;
import com.climate.oada.vo.impl.FileResource;
import com.climate.oada.vo.impl.LandUnit;

/**
 * JUnit for OADAAPIController.
 */
public final class OADAAPIControllerTest {

    static final Logger LOG = LoggerFactory.getLogger(OADAAPIControllerTest.class);

    private static final String FIELDS_INPUT_1 =
            "{\"name\":\"Hole\",\"farmName\":\"One Crop Farm\",\"clientName\":\"Spade Grower\","
            + "\"acres\":68.43,\"source\":\"Unknown\",\"boundary\":"
            + "\"Polygon((-89.12455178 40.95605095,-89.12457258 40.95957925,-89.13409765 40.95957850,"
            + "-89.13404241 40.95611545,-89.12455178 40.95605095),(-89.12872902 40.95720339,"
            + "-89.12878266 40.95848356,-89.12829987 40.95845926,-89.12689439 40.95800553,"
            + "-89.12512413 40.95811896,-89.12498466 40.95721150,-89.12872902 40.95720339))\"}";

    private static final int FIELDS_INPUT_1_LEN = 1;

    private static final String FIELDS_INPUT_2 =
            "[" + FIELDS_INPUT_1
            + ", {\"name\":\"MultiPart Boundary\",\"farmName\":\"One Crop Farm\","
            + "\"clientName\":\"Spade Grower\",\"acres\":145.81,\"source\":\"Unknown\","
            + "\"boundary\":\"MultiPolygon(((-89.13412351 40.96117997,-89.13415712 40.96336233,"
            + "-89.13544984 40.96336757,-89.13539711 40.96171946,-89.13483709 40.96138782,"
            + "-89.13412351 40.96117997)),((-89.13404406 40.95611546,-89.13412106 40.96106133,"
            + "-89.13490898 40.96129176,-89.13553344 40.96166736,-89.13559528 40.96336776,"
            + "-89.14177981 40.96339144,-89.14173791 40.96055750,-89.14366907 40.96044167,"
            + "-89.14362568 40.95618095,-89.13404406 40.95611546)))\"}]";

    private static final int FIELDS_INPUT_2_LEN = 2;

    private OADAAPIController controller = new OADAAPIController();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private IResourceDAO resourceDAO;
    private IResourceDAO s3DAO;
    private List<LandUnit> dummyLandUnits = new ArrayList<LandUnit>();
    private List<FileResource> dummyFiles = new ArrayList<FileResource>();


    /**
     * Default constructor.
     */
    public OADAAPIControllerTest() {

    }

    /**
     * Executes before each unit test.
     */
    @Before
    public void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        resourceDAO = Mockito.mock(IResourceDAO.class);
        s3DAO = Mockito.mock(IResourceDAO.class);
        controller.setFieldsResourceDAO(resourceDAO);
        controller.setS3DAO(s3DAO);
    }

    /**
     * Test Jackson parsing of fields.
     */
    @Test
    public void testFieldsParsing1() {
        List<LandUnit> result1 = controller.processFields(FIELDS_INPUT_1);
        assertNotNull(result1);
        assertTrue(result1.size() == FIELDS_INPUT_1_LEN);
        LOG.info(result1.toString());
        List<LandUnit> result2 = controller.processFields(FIELDS_INPUT_2);
        assertNotNull(result2);
        assertTrue(result2.size() == FIELDS_INPUT_2_LEN);
        LOG.info(result2.toString());
    }

    /**
     * Tests uploadResources API.
     */
    @Test
    public void testUpdateResources1() {
        when(request.getContentType()).thenReturn("goo");
        when(resourceDAO.insert(Matchers.any(LandUnit.class))).thenReturn(true);
        controller.updateResources(null, FIELDS_INPUT_2, request, response);
        verify(resourceDAO, never()).insert(Matchers.any(LandUnit.class));
    }

    /**
     * Tests uploadResources API.
     */
    @Test
    public void testUpdateResources2() {
        when(request.getContentType()).thenReturn(IOADAAPI.OADA_FIELDS_CONTENT_TYPE);
        when(resourceDAO.insert(Matchers.any(LandUnit.class))).thenReturn(true);
        controller.updateResources(null, FIELDS_INPUT_2, request, response);
        verify(resourceDAO, times(FIELDS_INPUT_2_LEN)).insert(Matchers.any(LandUnit.class));
    }

    /**
     * Tests get resources API.
     * @throws Exception in case of error.
     */
    @Test
    public void testGetResources1() throws Exception {
        dummyLandUnits.add(new LandUnit());
        dummyFiles.add(new FileResource());
        OADAAPIController spy = spy(controller);
        when(spy.extractUserId(Matchers.anyString())).thenReturn(null);
        when(resourceDAO.getLandUnits(Matchers.anyLong())).thenReturn(dummyLandUnits);
        when(s3DAO.getFileUrls(Matchers.anyLong(), Matchers.anyString())).thenReturn(dummyFiles);
        List<IResource> retval = spy.getResources(null, null, request, response);
        assertNull(retval);
        verify(resourceDAO, never()).getLandUnits(Matchers.anyLong());
        verify(s3DAO, never()).getFileUrls(Matchers.anyLong(), Matchers.anyString());
        verify(response, times(1)).sendError(Matchers.eq(HttpServletResponse.SC_BAD_REQUEST),
                Matchers.eq(OADAAPIUtils.INVALID_ACCESS_TOKEN));
    }

    /**
     * Tests get resources API.
     * @throws Exception in case of error.
     */
    @Test
    public void testGetResources2() throws Exception {
        dummyLandUnits.add(new LandUnit());
        dummyFiles.add(new FileResource());
        OADAAPIController spy = spy(controller);
        when(spy.extractUserId(Matchers.anyString())).thenReturn(new Long(1));
        when(resourceDAO.getLandUnits(Matchers.anyLong())).thenReturn(dummyLandUnits);
        when(s3DAO.getFileUrls(Matchers.anyLong(), Matchers.anyString())).thenReturn(dummyFiles);
        List<IResource> retval = spy.getResources(null, null, request, response);
        assertNotNull(retval);
        assertTrue(retval.size() == (dummyFiles.size() + dummyLandUnits.size()));
        verify(resourceDAO, times(1)).getLandUnits(Matchers.anyLong());
        verify(s3DAO, times(1)).getFileUrls(Matchers.anyLong(), Matchers.anyString());
        verify(response, never()).sendError(Matchers.anyInt(), Matchers.anyString());
    }

    /**
     * Tests get resources API.
     * @throws Exception in case of error.
     */
    @Test
    public void testGetResources3() throws Exception {
        dummyLandUnits.add(new LandUnit());
        dummyLandUnits.add(new LandUnit());
        dummyFiles.add(new FileResource());
        OADAAPIController spy = spy(controller);
        when(spy.extractUserId(Matchers.anyString())).thenReturn(new Long(1));
        when(resourceDAO.getLandUnits(Matchers.anyLong())).thenReturn(dummyLandUnits);
        when(s3DAO.getFileUrls(Matchers.anyLong(), Matchers.anyString())).thenReturn(dummyFiles);
        String[] resourceTypes = new String[1];
        resourceTypes[0] = IOADAAPI.OADA_FIELDS_CONTENT_TYPE;
        List<IResource> retval = spy.getResources(null, resourceTypes, request, response);
        assertNotNull(retval);
        assertTrue(retval.size() == dummyLandUnits.size());
        verify(resourceDAO, times(1)).getLandUnits(Matchers.anyLong());
        verify(s3DAO, never()).getFileUrls(Matchers.anyLong(), Matchers.anyString());
        verify(response, never()).sendError(Matchers.anyInt(), Matchers.anyString());
    }

    /**
     * Tests get resources API.
     * @throws Exception in case of error.
     */
    @Test
    public void testGetResources4() throws Exception {
        dummyLandUnits.add(new LandUnit());
        dummyLandUnits.add(new LandUnit());
        dummyFiles.add(new FileResource());
        OADAAPIController spy = spy(controller);
        when(spy.extractUserId(Matchers.anyString())).thenReturn(new Long(1));
        when(resourceDAO.getLandUnits(Matchers.anyLong())).thenReturn(dummyLandUnits);
        when(s3DAO.getFileUrls(Matchers.anyLong(), Matchers.anyString())).thenReturn(dummyFiles);
        String[] resourceTypes = new String[1];
        resourceTypes[0] = IOADAAPI.OADA_PRESCRIPTIONS_CONTENT_TYPE;
        List<IResource> retval = spy.getResources(null, resourceTypes, request, response);
        assertNotNull(retval);
        assertTrue(retval.size() == dummyFiles.size());
        verify(resourceDAO, never()).getLandUnits(Matchers.anyLong());
        verify(s3DAO, times(1)).getFileUrls(Matchers.anyLong(), Matchers.anyString());
        verify(response, never()).sendError(Matchers.anyInt(), Matchers.anyString());
    }

    /**
     * Tests get resources API.
     * @throws Exception in case of error.
     */
    @Test
    public void testGetResources5() throws Exception {
        dummyLandUnits.add(new LandUnit());
        dummyFiles.add(new FileResource());
        OADAAPIController spy = spy(controller);
        when(spy.extractUserId(Matchers.anyString())).thenReturn(new Long(1));
        when(resourceDAO.getLandUnits(Matchers.anyLong())).thenReturn(dummyLandUnits);
        when(s3DAO.getFileUrls(Matchers.anyLong(), Matchers.anyString())).thenReturn(dummyFiles);
        String[] resourceTypes = new String[2];
        resourceTypes[0] = IOADAAPI.OADA_FIELDS_CONTENT_TYPE;
        resourceTypes[1] = IOADAAPI.OADA_PRESCRIPTIONS_CONTENT_TYPE;
        List<IResource> retval = spy.getResources(null, resourceTypes, request, response);
        assertNotNull(retval);
        assertTrue(retval.size() == (dummyFiles.size() + dummyLandUnits.size()));
        verify(resourceDAO, times(1)).getLandUnits(Matchers.anyLong());
        verify(s3DAO, times(1)).getFileUrls(Matchers.anyLong(), Matchers.anyString());
        verify(response, never()).sendError(Matchers.anyInt(), Matchers.anyString());
    }

}
