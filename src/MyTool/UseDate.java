package MyTool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author XQ
 *	nowTimeString返回当前时间的字符串显示
 *	ruleTimeString返回一个按自己规定格式的当前时间的字符串
 * 	ruleTimeStringWithDate返回自己需要的时间按自己规定格式转换后返回的字符串
 *  backBaseRuleTimeWithDate返回传入日期转变后的字符串
 *  backDateWithString返回一个传入值的字符串转变为时间的date日期
 */
public class UseDate {
	
	/**
	 * 返回当前时间的字符串显示
	 * @return
	 */
	public static String nowTimeString() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = dateFormat.format(date);
		return timeStr;
	}
	
	/**
	 * 返回一个按自己规定格式的当前时间的字符串
	 * @param formatString
	 * @return
	 */
	public static String ruleTimeString(String formatString) {
		Date date =new Date();
		DateFormat dateFormat = new SimpleDateFormat(formatString);
		String timeStr = dateFormat.format(date);
		return timeStr;
	}
	
	/**
	 * 返回自己需要的时间按自己规定格式转换后返回的字符串
	 * @param date
	 * @param formatString
	 * @return
	 */
	public static String ruleTimeStringWithDate(Date date,String formatString) {
		DateFormat dateFormat = new SimpleDateFormat(formatString);
		String timeStr = dateFormat.format(date);
		return timeStr;
	}
	
	/**
	 * 返回传入日期转变后的字符串
	 * @param date
	 * @return
	 */
	public static String backBaseRuleTimeWithDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = dateFormat.format(date);
		return timeStr;
	}
	
	/**
	 * 返回一个传入值的字符串转变为时间的date日期
	 * @param dateString
	 * @return
	 */
	public static Date backDateWithString(String dateString) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		try {
			parse = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return parse;
	}
}