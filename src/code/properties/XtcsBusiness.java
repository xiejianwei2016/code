package code.properties;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 系统参数
 * 注意：属性文件中不能有空格，负责会报 java.lang.NullPointerException
 */
public class XtcsBusiness {
	
	public static Map<String, String> csMap = new ConcurrentHashMap<String, String>();

	/**
	 * 根据key值取参数值
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	public static String getValueByKey(String key) throws IOException{
		String value = null;
		if(csMap.containsKey(key)){
			value = csMap.get(key);
		}else{
			ClassPathResource resource = new ClassPathResource("webservice.properties");
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			value = properties.getProperty(key).trim();
			csMap.put(key, value);
		}
		return value;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(XtcsBusiness.getValueByKey("BaiduUrl"));
	}
	
}