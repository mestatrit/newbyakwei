package halo.util.validator;

import halo.util.DataUtil;

/**
 * string 表达式写法
 * 
 * <pre>
 * 
 * string{min=4;max=20;empty=true}msg
 * string{min=4;max=20}msg
 * 
 * <pre>
 * @author akwei
 */
public class StringValidator extends Validator {

	@Override
	public Object exec(String expression, Object message, Object obj) {
		int minlen = 0;
		int maxlen = 0;
		boolean emptyCheck = true;
		// 表达式解析
		String[] arr = expression.split(";");
		if (arr == null) {
			throw new IllegalExpressionException("illegal expression [ "
					+ expression + " ]");
		}
		for (String s : arr) {
			if (s.startsWith("minlen=")) {
				minlen = Integer.valueOf(s.substring(7));
				continue;
			}
			if (s.startsWith("maxlen=")) {
				minlen = Integer.valueOf(s.substring(7));
				continue;
			}
			if (s.startsWith("empty=")) {
				emptyCheck = Boolean.valueOf(s.substring(6));
			}
		}
		// 表达式解析完毕
		// 数据验证
		if (emptyCheck) {
			if (DataUtil.isEmpty(obj.toString())) {
				return message;
			}
		}
		if (obj == null) {
			return null;
		}
		String v = obj.toString();
		if (v.length() >= minlen && v.length() <= maxlen) {
			return null;
		}
		return message;
	}
}