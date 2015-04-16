package com.alnie.tc.po;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * 文件上传实体.
* CopyRright (c)2011: 
* Project: 
* Comments: 
* Author： Alnie
* Create Date： Nov 6, 2011
* Version: V1.0.0
 */
public class UploadInfo {

	private List<File> file;

	private List<String> fileFileName;

	private List<String> fileContentType;
	
	private List<String> fileStoreName;
	
	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public List<String> getFileStoreName() {
		if(this.fileStoreName==null){
			List fileStoreName=new ArrayList();
			for (int i = 0; i < getFile().size(); ++i) {
				//long key = System.nanoTime();// 新的文件名称不包含扩展名
				String key = UUID.randomUUID().toString();
				String fileoldname = this.fileFileName.get(i);// 旧的文件名
				String extendname = fileoldname.substring(fileoldname.lastIndexOf("."), fileoldname.length());//扩展名
				String filenewname =  key + extendname;
				fileStoreName.add(filenewname);
			}
			this.setFileStoreName(fileStoreName);
		}
		return this.fileStoreName;
	}

	public void setFileStoreName(List<String> fileStoreName) {
		this.fileStoreName = fileStoreName;
	}
}
