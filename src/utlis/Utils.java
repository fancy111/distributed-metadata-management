package utlis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ZZZZ");
		return sdf.format(date);
	}
	
	public String getCurrentTime() {
		return dateToString(new Date());
	}
}
