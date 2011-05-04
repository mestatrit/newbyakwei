package cactus.web.action;

/**
 * 拦截器接口
 * 
 * @author 定义拦截器执行方式
 */
public interface HkInterceptor {

	/**
	 * 在action之前进行调用
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	String doBefore(HkActionInvocation invocation) throws Exception;

	/**
	 * 在action之后进行调用
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	String doAfter(HkActionInvocation invocation) throws Exception;
}