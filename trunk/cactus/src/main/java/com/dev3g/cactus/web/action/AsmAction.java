package com.dev3g.cactus.web.action;

/**
 * 通过asm创建并使用的代理action,主要工作是为了避免使用反射来进行方法调用
 * 
 * @author akwei
 */
public interface AsmAction extends Action {

	/**
	 * 注入父类
	 * 
	 * @param action
	 */
	void setBase(Action action);
}
