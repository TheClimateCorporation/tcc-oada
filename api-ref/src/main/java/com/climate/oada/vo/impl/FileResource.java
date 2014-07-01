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
