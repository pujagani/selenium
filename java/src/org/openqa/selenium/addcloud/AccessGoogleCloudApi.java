package org.openqa.selenium.addcloud;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URL;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
public class AccessGoogleCloudApi {
  private static final Logger LOG = Logger.getLogger(AccessGoogleCloudApi.class.getName());
  public static String getPresignedUrl(String bucketName, String keyName) {
    try {
      if (keyName == null || keyName.equalsIgnoreCase("")) {
        return null;
      }
      Storage storage = StorageOptions.getDefaultInstance().getService();
      BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, keyName).build();
      URL url = storage.signUrl(blobInfo, 86400000 * 7, TimeUnit.MINUTES);
      return url.toString();
    } catch (Exception e) {
      LOG.warning("Error generating presigned url for GCS bucket: " + e.getMessage());
      return "";
    }
  }
  public static String uploadFileToBucket(String bucketName, String filePath, String keyName) {
    try {
      Storage storage = StorageOptions.getDefaultInstance().getService();
      Bucket bucket = storage.get(bucketName);

      Blob blob = bucket.create(keyName, Files.readAllBytes(Paths.get(filePath)));
      LOG.info("File uploaded successfully to S3 bucket!");
      return getPresignedUrl(bucketName, keyName);
    } catch (Exception e) {
      LOG.warning("Error uploading file to GCS Bucket: " + e.getMessage());
      return "";
    }
  }
}