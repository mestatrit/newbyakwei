package tuxiazi.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PhotoUtil {

	private static final String DATE_FMT = "EEE MMM d HH:mm:ss Z yyyy";

	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT,
			Locale.ENGLISH);

	private static FileCnf fileCnf;
	static {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	}

	/**
	 * 输出标准时间
	 * 
	 * @param date
	 * @return
	 */
	public static String getFmtTime(Date date) {
		return sdf.format(date);
	}

	public void setFileCnf(FileCnf fileCnf) {
		PhotoUtil.fileCnf = fileCnf;
	}

	/**
	 * 60 x 60
	 * 
	 * @param path
	 * @return
	 */
	public static String getP1url(String path) {
		return fileCnf.getDomain() + path + "p_1.jpg";
	}

	/**
	 * 120 x 120
	 * 
	 * @param path
	 * @return
	 */
	public static String getP2url(String path) {
		return fileCnf.getDomain() + path + "p_2.jpg";
	}

	/**
	 * @param path
	 * @return
	 */
	public static String getP3url(String path) {
		return fileCnf.getDomain() + path + "p_3.jpg";
	}

	/**
	 * 480 x 480
	 * 
	 * @param path
	 * @return
	 */
	public static String getP4url(String path) {
		return fileCnf.getDomain() + path + "p_4.jpg";
	}

	/**
	 * @param path
	 * @return
	 */
	public static String getP5url(String path) {
		return fileCnf.getDomain() + path + "p_5.jpg";
	}

	/**
	 * 800 x 800
	 * 
	 * @param path
	 * @return
	 */
	public static String getP6url(String path) {
		return fileCnf.getDomain() + path + "p_6.jpg";
	}
}