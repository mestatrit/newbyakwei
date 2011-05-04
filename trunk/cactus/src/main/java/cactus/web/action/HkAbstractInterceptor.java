package cactus.web.action;

/**
 * 拦截器，目前没有大量使用，正在思考如何使用此拦截器
 * 
 * @author akwei
 */
public class HkAbstractInterceptor implements HkInterceptor {

	public String doAfter(HkActionInvocation invocation) throws Exception {
		return null;
	}

	public String doBefore(HkActionInvocation invocation) throws Exception {
		return invocation.invoke();
	}
}