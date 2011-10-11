package halo.web.validate;

/**
 * 验证表达式解析器
 * 
 * @author akwei
 */
public class ValidatorParser {

	/**
	 * 表达式写法<br/>
	 * string:min=4;max=20;&& notempty;msg <br/>
	 * string:min=4;max=20;|| empty;msg <br/>
	 * email:min=4;max=20;|| empty;msg <br/>
	 * number:min=2.6;max=3.9;&& notempty;msg <br/>
	 * 
	 * @param expression
	 * @return
	 */
	public static ObjectValidator parse(String expression) {
		return null;
	}
}