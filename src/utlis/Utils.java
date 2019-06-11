package utlis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ZZZZ");
		return sdf.format(date);
	}
	
	public static String getCurrentTime() {
		return dateToString(new Date());
	}
}
