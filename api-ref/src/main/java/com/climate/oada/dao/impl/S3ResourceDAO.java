package com.climate.oada.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.climate.oada.dao.IResourceDAO;
import com.climate.oada.vo.impl.FileResource;
import com.climate.oada.vo.impl.LandUnit;

/**
 * DAO implementation that handles file upload / download to S3.
 *
 */
public class S3ResourceDAO implements IResourceDAO {

    static final Logger LOG = LoggerFactory.getLogger(S3ResourceDAO.class);

    static final int HOURS_TO_MILLISECONDS = 60 * 60 * 1000;

    @Value("${s3.bucket}")
    private String bucketName;
    @Value("{s3.key}")
    private String keyName;
    @Value("{s3.url.validhours}")
    private String validHours;

    /**
     * Default constructor.
     */
    public S3ResourceDAO() {

    }

    @Override
    public boolean insert(LandUnit lu) {
        throw new UnsupportedOperationException(
                "S3 DAO does not support import fields");
    }

    @Override
    public List<LandUnit> getLandUnits(Long userId) {
        throw new UnsupportedOperationException(
                "S3 DAO does not support export fields");
    }

    /**
     * Save file being uploaded to local.
     *
     * @param file
     *            - MultipartFile
     * @return File - Saved file.
     * @throws Exception
     *             in case of upload errors.
     */
    File saveLocal(MultipartFile file) throws Exception {
        String uploadFileName = file.getOriginalFilename();
        File localfile = new File(uploadFileName);
        LOG.debug("Saving uploaded multipart file " + uploadFileName
                + " to local, file name " + localfile.getAbsolutePath());
        file.transferTo(localfile);
        return localfile;
    }

    /**
     * Log AWSServiceException details.
     *
     * @param ase - exception object.
     */
    private void logAWSServiceException(AmazonServiceException ase) {
        LOG.error("Caught an AmazonServiceException, "
                + "request to Amazon S3 was rejected with an error response");
        LOG.error("Error Message:    " + ase.getMessage());
        LOG.error("HTTP Status Code: " + ase.getStatusCode());
        LOG.error("AWS Error Code:   " + ase.getErrorCode());
        LOG.error("Error Type:       " + ase.getErrorType());
        LOG.error("Request ID:       " + ase.getRequestId());
    }

    /**
     * Log AWS Client exception.
     * @param ace - client exception object.
     */
    private void logAWSClientException(AmazonClientException ace) {
        LOG.error("Caught an AmazonClientException, which "
                + "means the client encountered "
                + "an internal error while trying to "
                + "communicate with S3, "
                + "such as not being able to access the network.");
        LOG.error("Error Message: " + ace.getMessage());

    }

    /**
     * Upload file to S3.
     *
     * @param local
     *            - local file to upload.
     * @return boolean
     */
    boolean uploadS3(File local) {
        boolean retval = false;
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        try {
            LOG.debug("Uploading a new object to S3 from local, file name "
                    + local.getName());
            s3client.putObject(new PutObjectRequest(bucketName, keyName, local));
            retval = true;
        } catch (AmazonServiceException ase) {
            logAWSServiceException(ase);
        } catch (AmazonClientException ace) {
            logAWSClientException(ace);
        }
        return retval;
    }

    @Override
    public boolean saveFile(MultipartFile mpFile) throws Exception {
        if (null != mpFile) {
            File local = saveLocal(mpFile);
            return uploadS3(local);
        }
        return false;
    }

    @Override
    public List<FileResource> getFileUrls(Long userId) {
        List<FileResource> retval = new ArrayList<FileResource>();
        long validfor = new Long(validHours) * HOURS_TO_MILLISECONDS;
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        try {
            LOG.debug("Listing objects for user " + userId);

            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName).withPrefix(userId.toString());
            ObjectListing objectListing;
            do {
                objectListing = s3client.listObjects(listObjectsRequest);
                for (S3ObjectSummary objectSummary : objectListing
                        .getObjectSummaries()) {
                    LOG.debug(" - " + objectSummary.getKey() + "  "
                            + "(size = " + objectSummary.getSize() + ")");

                    Date expiration = new Date();
                    long milliSeconds = expiration.getTime();
                    milliSeconds += validfor;
                    expiration.setTime(milliSeconds);

                    GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                            bucketName, objectSummary.getKey());
                    generatePresignedUrlRequest.setMethod(HttpMethod.GET);
                    generatePresignedUrlRequest.setExpiration(expiration);

                    FileResource res = new FileResource();
                    res.setFileURL(s3client.generatePresignedUrl(generatePresignedUrlRequest));
                    retval.add(res);
                }
                listObjectsRequest.setMarker(objectListing.getNextMarker());
            } while (objectListing.isTruncated());
        } catch (AmazonServiceException ase) {
            logAWSServiceException(ase);
        } catch (AmazonClientException ace) {
            logAWSClientException(ace);
        }
        return retval;
    }
}
