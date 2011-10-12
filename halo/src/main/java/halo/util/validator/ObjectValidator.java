package halo.util.validator;

import java.util.List;

/**
 * 对象数据验证器
 * 
 * @author akwei
 */
public interface ObjectValidator {

	<T> Object exec(T t);

	<T> List<Object> execBatch(T t);
}
