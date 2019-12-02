package logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ConverteFormatoData {
	
	public static Calendar dateToCalendar(Date data){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(data);
		  return cal;
	}
	
	public static Date calendarToDate(Calendar data){
		  Calendar cal = Calendar.getInstance();
		  return cal.getTime();
	}

	public static java.sql.Date calendarToSQLDate(Calendar data){
		  Calendar cal = Calendar.getInstance();
		  return (java.sql.Date) cal.getTime();
	}

	public static Date stringToDate(String data) throws ParseException{
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		return ((Date)formato.parse(data));
	}

}
