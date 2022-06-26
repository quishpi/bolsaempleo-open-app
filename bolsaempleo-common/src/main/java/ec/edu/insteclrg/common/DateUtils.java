package ec.edu.insteclrg.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
	static String DATE_FORMAT = "dd/MM/yyyy";
	static String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	static String DATE_FORMAT_TZ = "yyyy-MM-dd'T'HH:mm:ss";

	private DateUtils() {
	}

	public static String convertirGreggorianToDDMMYYYY(String inputDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_TZ);
			Date date = sdf.parse(inputDate);
			DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
			return dateFormat.format(date);
		} catch (ParseException e) {
			throw new AppException(String.format("Ocurrió un error al formatear la fecha [%s]", inputDate));
		}
	}

	public static String convertirTimestampToDate(Timestamp timestamp) {
		if (timestamp == null) {
			return "";
		}
		Date date = new Date(timestamp.getTime());
		DateFormat formattedDate = new SimpleDateFormat(DATE_TIME_FORMAT);
		return formattedDate.format(date);
	}

	public static String getFechaFromDate(Date fecha) {
		if (fecha == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(fecha);
	}

	public static Date getFechaFromStringddMMyyyy(String inputDate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.parse(inputDate);
		} catch (ParseException e) {
			throw new AppException(String.format("Ocurrió un error al formatear la fecha [%s]", inputDate));
		}
	}

	public static String getFechaFromLocalDate(LocalDate inputDate) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(inputDate.atStartOfDay(defaultZoneId).toInstant());
		return DateUtils.getFechaFromDate(date);
	}

}