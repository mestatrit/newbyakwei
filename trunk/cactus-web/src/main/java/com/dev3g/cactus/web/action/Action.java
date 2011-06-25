package com.dev3g.cactus.web.action;

/**
 * 所有控制器类型需要实现此接口，默认执行接口的execute方法，例如http://localhost:8080/appctx/hello，
 * 就是执行HelloAction的execute方法 如果要执行其他方法，则只需要写method(HkRequest req, HkResponse
 * resp) throws
 * Exception;，例如http://localhost:8080/appctx/hello_test，就是执行HelloAction中的test
 * (HkRequest req, HkResponse resp)方法
 * 
 * @author akwei
 */
public interface Action {

	/**
	 * 默认的接口方法
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	String execute(HkRequest req, HkResponse resp) throws Exception;
}