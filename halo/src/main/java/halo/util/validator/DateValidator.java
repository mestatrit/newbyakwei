package halo.util.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator implements Validator {

	private final static String fmt = "yyyy-MM-dd HH:mm:ss";

	@Override
	public boolean exec(String subExpr, Object obj) {
		String s_min = null;
		String s_max = null;
		Date min = null;
		Date max = null;
		// 解析
		String[] arr = subExpr.split(";");
		if (arr == null) {
			throw new IllegalExpressionException("illegal expression [ "
					+ subExpr + " ]");
		}
		try {
			for (String s : arr) {
				if (s.startsWith("min=")) {
					s_min = s.substring(4);
					continue;
				}
				if (s.startsWith("max=")) {
					s_max = s.substring(4);
					continue;
				}
			}
		}
		catch (Exception e) {
			throw new IllegalExpressionException(e);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			if (s_min != null) {
				min = sdf.parse(s_min);
			}
			if (s_max != null) {
				max = sdf.parse(s_max);
			}
		}
		catch (ParseException e) {
			throw new IllegalExpressionException(e);
		}
		// 解析完成
		// 开始验证数据
		Date value = (Date) obj;
		if (min != null && value.getTime() < min.getTime()) {
			return false;
		}
		if (max != null && value.getTime() > max.getTime()) {
			return false;
		}
		return true;
	}
}