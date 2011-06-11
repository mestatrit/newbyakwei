package com.dev3g.cactus.web.action;

public interface ActionFinder {

	/**
	 * 查找Action class
	 * 
	 * @param actionUrl
	 *            除去request.getContextPath部分与除去后缀部分剩下的mappingUri<br/>
	 *            例如：app/user/set/action_list.do <br/>
	 *            actionUrl为/user/set/action
	 * @return
	 * @throws NoActionException
	 */
	Action findAction(String actionUrl) throws NoActionException;
}
