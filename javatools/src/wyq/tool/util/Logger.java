package wyq.tool.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger {

    public static void log(Object o) {
	SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
	Date now = Calendar.getInstance().getTime();
	System.out.println("[" + f.format(now) + "]" + o);
    }

}
