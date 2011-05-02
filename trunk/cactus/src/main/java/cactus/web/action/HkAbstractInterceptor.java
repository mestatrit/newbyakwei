package cactus.web.action;


public class HkAbstractInterceptor implements HkInterceptor {

	public String doAfter(HkActionInvocation invocation) throws Exception {
		return null;
	}

	public String doBefore(HkActionInvocation invocation) throws Exception {
		return invocation.invoke();
	}
}