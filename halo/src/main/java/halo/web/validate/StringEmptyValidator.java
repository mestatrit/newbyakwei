package halo.web.validate;

public class StringEmptyValidator implements ObjectValidator {

	@Override
	public boolean validate(Object obj) {
		if (obj == null || obj.toString().trim().length() == 0) {
			return true;
		}
		return false;
	}

}