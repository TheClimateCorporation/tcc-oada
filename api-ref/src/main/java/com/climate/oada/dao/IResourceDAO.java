/*
 * Copyright (C) 2014 The Climate Corporation and released under an MIT license.
 * You may not use this library except in compliance with the License.
 * You may obtain a copy of the License at:

 * http://opensource.org/licenses/MIT

 * See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */

package com.climate.oada.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.climate.oada.vo.impl.FileResource;
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

    /**
     * Save a file.
     *
     * @param f - file to upload.
     * @return boolean
     * @throws Exception TODO
     */
    boolean saveFile(MultipartFile f) throws Exception;

    /**
     * Return a list of S3 URLs for given user.
     *
     * @param userId - input user.
     * @param type - file type
     * @return List of URLs.
     */
    List<FileResource> getFileUrls(Long userId, String type);

}
