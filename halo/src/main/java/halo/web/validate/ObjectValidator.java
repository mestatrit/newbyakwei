package halo.web.validate;

/**
 * 数据验证器
 * 
 * @author akwei
 */
public interface ObjectValidator {

	/**
	 * 对数据进行验证，并返回验证结果
	 * 
	 * @param obj 待验证的数据
	 * @return true:表示验证通过,false:表示验证失败返回验证信息
	 */
	boolean validate(Object obj);
}