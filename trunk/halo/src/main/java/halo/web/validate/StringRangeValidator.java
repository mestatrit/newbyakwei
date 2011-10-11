package halo.web.validate;

/**
 * 字符串长度的范围验证
 * 
 * @author akwei
 */
public class StringRangeValidator implements ObjectValidator {

	private int minLen;

	private int maxLen;

	@Override
	public boolean validate(Object obj) {
		if (obj == null) {
			return false;
		}
		String v = obj.toString().trim();
		if (v.length() >= this.minLen && v.length() <= this.maxLen) {
			return true;
		}
		return false;
	}
}