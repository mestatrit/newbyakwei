package halo.util.validator;

public abstract class Validator {

	/**
	 * 对数据进行验证，并返回验证结果
	 * 
	 * <pre>
	 * 表达式写法
	 * string{minlen=4;maxlen=20;empty=true}message
	 * string{minlen=4;maxlen=20}message
	 * email{minlen=4;maxlen=20;empty=false}message
	 * number{min=2.6;max=3.9}message
	 * <br/>
	 * @param subExpr 验证规则表达式的子表达式，为{}中的数据
	 * @param message 验证失败的返回信息
	 * @param obj 待验证的数据
	 * @return 返回不为空的数据表示验证失败，返回值表示失败信息 返回值为null表示验证成功
	 */
	public abstract Object exec(String subExpr, Object message, Object obj);

// /**
	// * @param expr 验证表达式
	// * @return
	// */
	// public static Validator parse(String expr) {
	//
	// return null;
	// }
}
