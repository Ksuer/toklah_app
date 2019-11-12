package com.toklahBackend.payload;

public class UploadFileResponse {

	private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private int userId;
    
    public UploadFileResponse(String fileName, /*String fileDownloadUri,*/ String fileType, int userId) {
        this.fileName = fileName;
        // this.fileDownloadUri = fileDownloadUri;
         this.fileType = fileType;
        this.userId=  userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
    
}
