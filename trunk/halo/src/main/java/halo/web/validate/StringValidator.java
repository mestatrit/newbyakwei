package halo.web.validate;

import halo.util.DataUtil;

public class StringValidator implements ObjectValidator {

	private int minLen;

	private int maxLen;

	private boolean emptyCheck;

	public StringValidator(int minLen, int maxLen, boolean emptyCheck) {
		super();
		this.minLen = minLen;
		this.maxLen = maxLen;
		this.emptyCheck = emptyCheck;
	}

	public int getMinLen() {
		return minLen;
	}

	public int getMaxLen() {
		return maxLen;
	}

	public boolean isEmptyCheck() {
		return emptyCheck;
	}

	@Override
	public boolean validate(Object obj) {

		if (emptyCheck) {
			if (DataUtil.isEmpty(obj.toString())) {
				return false;
			}
		}
		if (obj == null) {
			return true;
		}
		String v = obj.toString();

		if (v.length() >= this.minLen && v.length() <= this.maxLen) {
			return true;
		}
		return false;
	}

}