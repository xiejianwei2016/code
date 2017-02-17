package code.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 日期工具
 * 
 * @author 谢建伟
 * @history <TABLE id="HistoryTable" border="1">
 *          <TR>
 *          <TD>时间</TD>
 *          <TD>描述</TD>
 *          <TD>作者</TD>
 *          </TR>
 *          <TR>
 *          <TD>2017-02-16</TD>
 *          <TD>创建初始版本</TD>
 *          <TD>谢建伟</TD>
 *          </TR>
 *          </TABLE>
 */
public class DateUtils{

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); // 日期格式

	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");// 时间格式

	/**
	 * 获取yyyyMMdd格式的日期字符串
	 * @return
	 */
	public static String getCurrentDate() {
		return dateFormat.format(new Date());
	}
	
	/**
	 * 获取hhmmss格式的时间字符串
	 * @return
	 */
	public static String getCurrentTime() {
		return timeFormat.format(new Date());
	}
	
	/**
	 * 获取月份加减日期
	 * @return
	 */
	public static String getMonthDate(int month) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		//格式化对象
		Calendar calendar = Calendar.getInstance();
		//日历对象
		calendar.setTime(date);
		//设置当前日期
		calendar.add(Calendar.MONTH, month);//月份减一
		return format.format(calendar.getTime());
	}
	
	/**
	 * 将日期字符串格式化为yyyy-MM-dd格式
	 * @param date
	 * @return
	 */
	public static String dateFormat(String date){
		if(StringUtils.isEmpty(date)){
			return date;
		}else{
			StringBuffer datetemp = new StringBuffer("");
			if(date.length() == 6){
				datetemp.append(date.substring(0,4)).append("-")
					.append(date.substring(4,6));
			}else if(date.length() == 8){
				datetemp.append(date.substring(0,4)).append("-")
					.append(date.substring(4,6)).append("-")
					.append(date.substring(6,8));
			}else{
				datetemp.append(date);
			}
			return datetemp.toString();
		}
	}
	
	/**
	 * 将时间字符串格式化为HH:mm:ss格式
	 * @param time
	 * @return
	 */
	public static String timeFormat(String time){
		if(StringUtils.isEmpty(time)){
			return time;
		}else{
			StringBuffer timetemp = new StringBuffer("");
			timetemp.append(time.substring(0,2)).append(":")
				.append(time.substring(2,4)).append(":")
				.append(time.substring(4,6));
			return timetemp.toString();
		}
	}
	
	/**
	 * 获取起始日期 
	 * 日期格式：yyyyMMdd
	 * @param month 最近三个月：-3，最近六个月 -6
	 * @return
	 */
	public static String getQsrq(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_MONTH, 1); //把当前的日期设置为这个月的第一天
        calendar.add(Calendar.MONTH, month);
        return dateFormat.format(calendar.getTime());
	}
	
	/**
     * 两个string类型的日期比较大小
     * @param date1 例如：20150512
     * @param date2 例如：20160512
     * @return 
     * @throws ParseException 
     */
    public static int compareDate(String date1, String date2) throws ParseException {
        Date dt1 = dateFormat.parse(date1);
        Date dt2 = dateFormat.parse(date2);
        if (dt1.getTime() > dt2.getTime()) {
            return 1;
        } else if (dt1.getTime() < dt2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }
    
}
