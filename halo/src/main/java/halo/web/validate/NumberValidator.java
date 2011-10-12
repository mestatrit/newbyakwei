package halo.web.validate;

import java.math.BigDecimal;

public class NumberValidator implements ObjectValidator {

	private BigDecimal min;

	private BigDecimal max;

	public NumberValidator(BigDecimal min, BigDecimal max) {
		super();
		this.min = min;
		this.max = max;
	}

	@Override
	public boolean validate(Object obj) {
		if (obj == null) {
			return false;
		}
		BigDecimal v = new BigDecimal(obj.toString());
		int res = v.compareTo(min);
		if (res == -1) {
			return false;
		}
		res = v.compareTo(max);
		if (res == 1) {
			return false;
		}
		return true;
	}
}
