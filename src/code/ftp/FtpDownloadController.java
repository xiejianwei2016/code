package code.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 从ftp服务器下载 
 *
 * @author 谢建伟
 * @history
 * <TABLE id="HistoryTable" border="1">
 * 	<TR><TD>时间</TD><TD>描述</TD><TD>作者</TD></TR>
 *	<TR><TD>2016-12-07</TD><TD>创建初始版本</TD><TD>谢建伟</TD></TR>
 * </TABLE>
 */
@Controller
public class FtpDownloadController {
	@Resource
	private UploadBusiness uploadBusiness;
	
	@Resource
	private FtpConfigBusiness ftpConfigBusiness;
	
	/**
	 * 从ftp服务器下载
	 * 
	 * @param response, request
	 * 
	 */
	@RequestMapping("/downloadPDF.do")
	public void downloadFromFtpServer(HttpServletResponse response, HttpServletRequest request) {
		String fileName = ServletRequestUtils.getStringParameter(request, "fileName", null);
		String realName = "沟通的艺术.pdf";
		FTPClient ftpClient = new FTPClient();
		InputStream in = null;
		OutputStream out = null;
		try {
			String serverFileBasPath = ftpConfigBusiness.getValueByKey(SysVars.FTP_FILE_PATH);
			System.out.println("download ftpfilepath>>>>>>> : " + serverFileBasPath);
			String filePath = serverFileBasPath + "/" + fileName;
			if(uploadBusiness.checkDownloadFileExist(serverFileBasPath+"/", fileName)) {
				ftpClient = uploadBusiness.connect();
				in = ftpClient.retrieveFileStream(filePath);
				response.setContentType("application/x-download");
				//realName 指定下载的文件名称，可以是中文
				response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(realName,"UTF-8"));
				out = response.getOutputStream();
				byte[] b = new byte[1024];   
			    int i = 0;   
			    while((i = in.read(b)) > 0) {   
			    	out.write(b, 0, i);  
			    }
			    System.out.println(fileName + "download from FtpServer success...");
			} else {
			    System.out.println(fileName + "doesn't exist...");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			uploadBusiness.closeConnect(ftpClient);
		}
	}
	
}
