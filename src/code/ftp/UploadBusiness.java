package code.ftp;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

/**
 * 上传附件到ftp 保存附件信息
 * 
 * @author 谢建伟
 * @history <TABLE id="HistoryTable" border="1">
 *          <TR>
 *          <TD>时间</TD>
 *          <TD>描述</TD>
 *          <TD>作者</TD>
 *          </TR>
 *          <TR>
 *          <TD>2016-12-07</TD>
 *          <TD>创建初始版本</TD>
 *          <TD>谢建伟</TD>
 *          </TR>
 *          </TABLE>
 */
@Service
public class UploadBusiness {
	@Resource
	private FtpConfigBusiness ftpConfigBusiness;

	/**
	 * 连接文件服务器
	 * @throws UploadFtpException 
	 * @throws SocketException 
	 * @throws NumberFormatException 
	 * 
	 * @throws IOException
	 * 
	 */
	public FTPClient connect() throws UploadFtpException {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(ftpConfigBusiness.getValueByKey(SysVars.FTP_WAS_SERVER_URL), 
					Integer.valueOf(ftpConfigBusiness.getValueByKey(SysVars.FTP_WAS_SERVER_PORT)));
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.enterLocalPassiveMode();
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				ftpClient = null;
			} else {
				boolean loginFlag = ftpClient.login(ftpConfigBusiness.getValueByKey(SysVars.FTP_RW_USERNAME),
						ftpConfigBusiness.getValueByKey(SysVars.FTP_RW_PASSWORD));
				if (!loginFlag) {
					ftpClient.logout();
					ftpClient = null;
				} else {
					System.out.println("Login ftp server success...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UploadFtpException(UploadFtpException.CONNECT_ERROR, e);
		} 
		return ftpClient;
	}

	/**
	 * 上传文件到服务器
	 * @param files
	 * @param businessid
	 * @param type
	 * @throws UploadFtpException
	 * @throws IOException
	 */
	public void uploadToFtpServer(File file) throws UploadFtpException, IOException {
		String ftpFilePath = ftpConfigBusiness.getValueByKey(SysVars.FTP_FILE_PATH);
		FTPClient ftpClient = null;
		InputStream in = null;
		ftpClient = connect();
		ftpClient.changeWorkingDirectory(ftpFilePath);
		System.out.println("upload ftpfilepath>>>>>>> : " + ftpClient.printWorkingDirectory());
		try {
			if(file.exists()){
				in = new FileInputStream(file);
				String storeFileName = file.getName();
				boolean uploadFlag = ftpClient.storeFile(storeFileName, in);
				if(uploadFlag) {
					System.out.println(storeFileName + " upload to FtpServer success...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UploadFtpException(UploadFtpException.FILE_ERROR, e);
		}finally{
			closeConnect(ftpClient);
			if(in != null){
				in.close();
				in = null;
			}
		}
	}

	/**
	 * 下载文件之前检查该文件是否存在，如果不存在则提示文件不存在并不允许下载
	 * 
	 * @param request
	 * @param response
	 * @param rel
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean checkDownloadFileExist(String serverFilespath, String fileName)
			throws Exception {
		boolean flag = false;
		FTPClient ftpClient = new FTPClient();
		if (connect() != null) {
			ftpClient = connect();
			FTPFile[] files = ftpClient.listFiles(serverFilespath);
			for (int i = 0; i < files.length; i++) {
				String serverFileName = files[i].getName();
				if (fileName.equals(serverFileName)) {
					flag = true;
				}
			}
		}
		closeConnect(ftpClient);
		return flag;
	}

	/**
	 * 关闭连接
	 * 
	 * @throws IOException
	 * 
	 */
	public void closeConnect(FTPClient ftpClient) {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
