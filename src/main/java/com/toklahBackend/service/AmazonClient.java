package com.toklahBackend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;
	
	private Logger logger = LoggerFactory.getLogger(AmazonClient.class);
	
	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	public String uploadFile(MultipartFile multipartFile) throws IOException {

		String fileUrl = "";
		String userfileUrl = "";
		String eventfileUrl = "";
		String userImage = "user";
		String eventImage = "user";
		
		try {
			File file = convertMultiPartToFile(multipartFile);

			String fileName = generateFileName(multipartFile);
			fileUrl = endpointUrl + "/" + bucketName + "/" +  fileName;
			eventfileUrl = endpointUrl + "/" + bucketName + "/" + eventImage + "/" +  fileName;
			userfileUrl = endpointUrl + "/" + bucketName + "/" + userImage + "/" +  fileName;
			uploadFileTos3bucket(fileName, file);
			
			//fileUrl = endpointUrl + "/" + bucketName + "/" + folderName + "/" +  fileName;
			//uploadFileTos3bucket(fileName, folderName, file);
			file.delete();
		} 
		catch (AmazonServiceException ase) {
	          logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
	          logger.info("Error Message:    " + ase.getMessage());
	          logger.info("HTTP Status Code: " + ase.getStatusCode());
	          logger.info("AWS Error Code:   " + ase.getErrorCode());
	          logger.info("Error Type:       " + ase.getErrorType());
	          logger.info("Request ID:       " + ase.getRequestId());
	          
	          
	            } catch (AmazonClientException ace) {
	              logger.info("Caught an AmazonClientException: ");
	                logger.info("Error Message: " + ace.getMessage());
	            } catch (IOException ioe) {
	              logger.info("IOE Error Message: " + ioe.getMessage());
	              
	            }
		return fileUrl;

	}
	
	/*public List<String> uploadListFile(List<MultipartFile> multipartFile) {
    	List<String> fileUrl = new ArrayList<String>();
        try {
        	for(int i = 0; i < multipartFile.size(); i++) {
            File file = convertMultiPartToFile(multipartFile.get(i));
            String fileName = generateFileName(multipartFile.get(i));
            uploadFileTos3bucket(fileName, file);
            System.out.println(file.getAbsolutePath());
            file.delete();
        	}
        } catch (Exception e) {
           e.printStackTrace();
        }
        return fileUrl;
    }*/

	public String deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
		return "Successfully deleted";
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(
				new PutObjectRequest(bucketName, fileName, file));
				//new PutObjectRequest(bucketName, fileName, folderName, file));
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	
}
