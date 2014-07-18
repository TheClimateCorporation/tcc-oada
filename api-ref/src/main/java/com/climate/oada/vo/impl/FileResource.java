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

package com.climate.oada.vo.impl;

import java.net.URL;

import com.climate.oada.vo.IResource;

/**
 * A value object for file resource.
 */
public final class FileResource implements IResource {

    private String fileType = "unknown";
    private URL fileURL;

    /**
     * Default constructor.
     */
    public FileResource() {

    }

    /**
     * Getter for file type.
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Setter for file type.
     * @param ft the fileType to set
     */
    public void setFileType(String ft) {
        this.fileType = ft;
    }

    /**
     * Getter for file url.
     * @return the fileURL
     */
    public URL getFileURL() {
        return fileURL;
    }

    /**
     * Setter for file url.
     * @param url the fileURL to set
     */
    public void setFileURL(URL url) {
        this.fileURL = url;
    }

    @Override
    public void setResourceType(String type) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getResourceType() {
        // TODO Auto-generated method stub
        return null;
    }

}
