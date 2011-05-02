package cactus.web.action;


public interface HkInterceptor {

	String doBefore(HkActionInvocation invocation) throws Exception;

	String doAfter(HkActionInvocation invocation) throws Exception;
}