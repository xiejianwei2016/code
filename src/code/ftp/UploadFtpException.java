package code.ftp;

/**
 * 上传ftp异常类
 *
 * @author 谢建伟
 * @history
 * <TABLE id="HistoryTable" border="1">
 * 	<TR><TD>时间</TD><TD>描述</TD><TD>作者</TD></TR>
 *	<TR><TD>2016-12-07</TD><TD>创建初始版本</TD><TD>谢建伟</TD></TR>
 * </TABLE>
 */
public class UploadFtpException extends RuntimeException {

	private static final long serialVersionUID = -6282755065110379084L;
	
	public static final int CONNECT_ERROR = 0;
	
	public static final int FILE_ERROR = 1;
	
	public static final int MKDIR_ERROR = 2;
	
	public UploadFtpException(int errorModel, String message) {
		super(model(errorModel) + message);
	}

	protected static String model(int errorCode) {
		String string = "";
		switch (errorCode) {
		case CONNECT_ERROR:
			string="ftp服务器连接失败";
			break;
		case FILE_ERROR:
			string="文件上传失败，文件队列中文件丢失，请重新申请上传";
			break;
		case MKDIR_ERROR:
			string="创建目录失败，请确保您有足够权限";
			break;
		default:
			break;
		}
		return string;
	}
	
	public UploadFtpException(int errorCode, Throwable cause) {
		super(model(errorCode), cause);
	}

	public UploadFtpException(int errorCode, String message, Throwable cause) {
		super(model(errorCode) + message, cause);
	}

}
