package code.ftp;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

/**
 * 系统参数
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
public class FtpConfigBusiness {
	
	public static Map<String, String> csMap = new ConcurrentHashMap<String, String>();

	/**
	 * 根据key值取参数值
	 * @param key
	 * @return
	 */
	public String getValueByKey(String key){
		String value = null;
		if(csMap.containsKey(key)){
			value = csMap.get(key);
		}else{
			if(key.equals("ftpPCServerUrl") || key.equals("ftpPCServerPort") || key.equals("ftpWASServerUrl") ||
			    key.equals("ftpWASServerPort") || key.equals("ftpFilePath") || key.equals("ftpRUserName") ||
			    key.equals("ftpRPassword") || key.equals("ftpImgPath") || key.equals("ftpRWUserName") ||
			    key.equals("ftpRWPassword") || key.equals("version") || 
				key.equals("poolminidle") || key.equals("poolmaxidle") || key.equals("poolmaxtotal")){
				ClassPathResource resource = new ClassPathResource("ftpConfig.properties");
				try {
					Properties properties = PropertiesLoaderUtils.loadProperties(resource);
					value = properties.getProperty(key).trim();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		return value;
	}
}