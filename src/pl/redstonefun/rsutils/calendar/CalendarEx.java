package pl.redstonefun.rsutils.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarEx{

	private SimpleDateFormat format;
	private TimeZone warsawTimeZone = TimeZone.getTimeZone("GMT+1:00");
	private Calendar c;
	
	public CalendarEx(){
		c = Calendar.getInstance();
		c.setTimeZone(warsawTimeZone);
		format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setTimeZone(warsawTimeZone);
	}
	
	public void setToNow(){
		c.setTime(new Date());
	}
	
	public boolean isLaterFromNow(){
		if(c.getTime().compareTo(new Date()) < 1){
			return true;
		} else {
			return false;
		}
	}
	
	public void setFromString(String string) throws ParseException{
		c.setTime(format.parse(string));
	}
	
	public String getInString(){
		return format.format(c.getTime());
	}
	
	public void add(String time) throws ParseException { 
		String last = time.substring(time.length()-1);
		int tim = Integer.parseInt(time.substring(0, time.length()-1));
		switch(last){
			case "s":
				c.add(Calendar.SECOND, tim);
				System.out.println("sec");
				break;
			case "m":
				c.add(Calendar.MINUTE, tim);
				break;
			case "h":
				c.add(Calendar.HOUR_OF_DAY, tim);
				break;
			case "d":
				c.add(Calendar.DAY_OF_MONTH, tim);
				break;
			case "w":
				c.add(Calendar.WEEK_OF_MONTH, tim);
				break;
			case "M":
				c.add(Calendar.MONTH, tim);
				break;
			case "y":
				c.add(Calendar.YEAR, tim);
				break;
			default:
				throw new ParseException("Wrong time suffix", time.length());
		}
	}
}
