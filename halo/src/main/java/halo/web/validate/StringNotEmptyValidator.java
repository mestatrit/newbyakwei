package halo.web.validate;

/**
 * 字符串不能为空
 * 
 * @author akwei
 */
public class StringNotEmptyValidator implements ObjectValidator {

	@Override
	public boolean validate(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.toString().trim().length() == 0) {
			return false;
		}
		return true;
	}

}
