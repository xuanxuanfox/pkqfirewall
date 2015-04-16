package com.alnie.tc.system.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.alnie.tc.po.UploadInfo;

public class UploadUtil {

	private String uploadDir="/upload";
	private String uploadEncoding="GBK";
	private boolean useStoreName=false;
	private UploadInfo ufInfo;
	public UploadUtil(){};
	public UploadUtil(UploadInfo ufInfo)throws Exception {
		this.ufInfo=ufInfo;
	}
	public String Upload()throws Exception {
		if(this.getUfInfo()!=null){
			String root = ServletActionContext.getServletContext().getRealPath(uploadDir);
			root = root.replace('\\', '/');
			
			List<String> fileNameList=this.getUfInfo().getFileFileName();
			//是否用新文件存放
			if(useStoreName){
				fileNameList=this.getUfInfo().getFileStoreName();
			}
			
			for (int i = 0; i < getUfInfo().getFile().size(); ++i) {
				File file=getUfInfo().getFile().get(i);
				//if(file.get)
				InputStream is = new FileInputStream(file);
				String fileName=fileNameList.get(i);
				//fileName=new String(fileName.getBytes("UTF-8"),"GB2312");  
				fileName = java.net.URLDecoder.decode(fileName,this.getUploadEncoding());
				File destFile = new File(root,fileName);
				OutputStream os = new FileOutputStream(destFile);
				byte[] buffer = new byte[400];
				int length = 0;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				is.close();
				os.close();
			}
		}
		return "操作成功！";
	}
	public UploadInfo getUfInfo() {
		return ufInfo;
	}
	public void setUfInfo(UploadInfo ufInfo) {
		this.ufInfo = ufInfo;
	}
	public String getUploadDir() {
		return uploadDir;
	}
	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	public boolean isUseStoreName() {
		return useStoreName;
	}
	public void setUseStoreName(boolean useStoreName) {
		this.useStoreName = useStoreName;
	}
	public String getUploadEncoding() {
		return uploadEncoding;
	}
	public void setUploadEncoding(String uploadEncoding) {
		this.uploadEncoding = uploadEncoding;
	}
}
