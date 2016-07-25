package net.shopin.supply.util;

/**
 * ˵��:  
 *     ͼƬftp�ϴ�������
 * @Probject Name: shopin-back-wms
 * @Path: net.shopin.wms.utilFtpUtil.java
 * @Create By kongm
 * @Create In 2013-9-5 ����10:34:47
 * TODO
 */


import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.fileupload.FileItem;


import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FileTransferClient;

/**
 * @Class Name FtpUtil
 * @Author kongm
 * @Create In 2013-9-5
 */
public class FtpUtil {
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * ˵��: 
	 *     ��ȡ��ǰʱ�������ͼƬ�ϴ�·��
	 * @Methods Name getImagePath
	 * @Create In 2013-9-5 By Administrator
	 * @return String
	 */
	public static String getImagePath(){
		return format.format(new Date());
	}
	
	

	public static void saveBrandLOGOPicToFtp(OutputStream out,String filename,FileItem item){
		FileTransferClient ftp = null;
		
		try {
			ftp = new FileTransferClient();
			ftp.setRemoteHost(SystemConfig.FTP_HOST);
			ftp.setUserName(SystemConfig.FTP_USERNAME);
			ftp.setPassword(SystemConfig.FTP_PASSWORD);
			ftp.setRemotePort(Integer.valueOf(SystemConfig.FTP_PORT));
			ftp.connect();
			/*if (item.getFieldName() == "PicForm"
					|| "PicForm".equals(item.getFieldName())){
				ftp.changeDirectory(SystemConfig.PROBESTDETAIL_PATH);
			}*/
			if (item.getFieldName() == "image1"
					|| "image1".equals(item.getFieldName())){
				ftp.changeDirectory(SystemConfig.BRAND_LOGO_PATH);
			}
			if(!ftp.exists(filename)){
				out = ftp.uploadStream(filename);
				out.write(item.get());
			}
		} catch (FTPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				ftp.disconnect();
			} catch (FTPException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println(getImagePath());
		OutputStream output;
		
		
	}
	
	

}
