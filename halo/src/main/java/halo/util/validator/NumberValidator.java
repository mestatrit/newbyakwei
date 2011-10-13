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
public class NumberValidator implements Validator {

	@Override
	public boolean exec(String expression, Object obj) {
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
					min = new BigDecimal(s.substring(4));
					continue;
				}
				if (s.startsWith("max=")) {
					max = new BigDecimal(s.substring(4));
					continue;
				}
			}
		}
		catch (Exception e) {
			throw new IllegalExpressionException(e);
		}
		// 验证数据
		if (obj == null) {
			return false;
		}
		BigDecimal v;
		try {
			v = new BigDecimal(obj.toString());
		}
		catch (Exception e) {
			throw new IllegalExpressionException(e);
		}
		if (min != null) {
			int res = v.compareTo(min);
			if (res == -1) {
				return false;
			}
		}
		if (max != null) {
			int res = v.compareTo(max);
			if (res == 1) {
				return false;
			}
		}
		return true;
	}
}
