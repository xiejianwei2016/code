package code.ftp;


import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;

public class FtpUploadController {
	@Resource
	private UploadBusiness uploadBusiness;
	
	@Resource
	private FtpConfigBusiness ftpConfigBusiness;
	/**
	 * 上传到ftp服务器
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/uploadPDF.app")
	public void uploadToFtpServer(HttpServletResponse response, HttpServletRequest request) {
		/**************************上传文件到FTP***********************/
		try {
			//获取Web项目的全路径  request.getServletContext().getRealPath("/")
//			String path = request.getServletContext().getRealPath("/") + File.separator + "pdfs" + File.separator;
			String filePath = "d://abc.pdf"; //待上传文件的绝对路径
			File localFile = new File(filePath);
			uploadBusiness.uploadToFtpServer(localFile);
			if(localFile.exists()) {
				localFile.delete(); 
			}
		} catch (UploadFtpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**************************上传文件到FTP***********************/
	}
}
