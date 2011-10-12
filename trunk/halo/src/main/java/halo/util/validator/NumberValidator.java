package halo.util.validator;

import java.math.BigDecimal;

/**
 * 数字验证表达式写法
 * 
 * <pre>
 * number{min=2.6;max=3.9}message
 * 
 * <pre>
 * 
 * @author akwei
 */
public class NumberValidator extends Validator {

	@Override
	public Object exec(String expression, Object message, Object obj) {
		BigDecimal min = null;
		BigDecimal max = null;
		// 解析
		String[] arr = expression.split(";");
		if (arr == null) {
			throw new IllegalExpressionException("illegal expression [ "
					+ expression + " ]");
		}
		try {
			for (String s : arr) {
				if (s.startsWith("min=")) {
					min = new BigDecimal(s.substring(7));
					continue;
				}
				if (s.startsWith("max=")) {
					max = new BigDecimal(s.substring(7));
					continue;
				}
			}
		}
		catch (Exception e) {
			throw new IllegalExpressionException(e);
		}
		if (min == null) {
			min = new BigDecimal(0);
		}
		// 验证数据
		if (obj == null) {
			return message;
		}
		BigDecimal v;
		try {
			v = new BigDecimal(obj.toString());
		}
		catch (Exception e) {
			throw new IllegalExpressionException(e);
		}
		int res = v.compareTo(min);
		if (res == -1) {
			return message;
		}
		if (max != null) {
			res = v.compareTo(max);
			if (res == 1) {
				return message;
			}
		}
		return null;
	}
}
