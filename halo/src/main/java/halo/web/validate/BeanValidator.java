package halo.web.validate;

import halo.util.ClassInfo;
import halo.util.ClassInfoFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 数据验证框架。对类中的属性进行校验，并返回校验消息，可单步校验，也可集合校验。<br/>
 * 校验功能包括是否是数字，数字范围，字符范围，空字符串校验，日期范围校验，日期有效性校验<br/>
 * 目前只支持数据校验，不支持与数据库相关的数据校验
 * 
 * @author akwei
 * @param <T>
 */
public abstract class BeanValidator<T> {

	/**
	 * 要验证的对象
	 */
	private T t;

	/**
	 * 保存要验证的数据，验证按照添加顺序执行
	 */
	private LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

	/**
	 * @param t
	 *            要验证的对象
	 */
	public BeanValidator(T t) {
		super();
		this.t = t;
	}

	/**
	 * 添加验证
	 * 
	 * @param field
	 * @param expression
	 */
	public void add(String field, String expression) {
		map.put(field, expression);
	}

	/**
	 * 当验证通过返回null。验证不通过返回自定义的Object
	 * 
	 * @return
	 */
	public ValidateMsg validate() {
		@SuppressWarnings("unchecked")
		ClassInfo<T> classInfo = (ClassInfo<T>) ClassInfoFactory.getClassInfo(t
				.getClass());
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> e : set) {
		}
		return null;
	}

	/**
	 * 当验证通过返回空list。验证不通过返回自定义的信息集合
	 * 
	 * @return
	 */
	public abstract List<ValidateMsg> validateBatch();
}