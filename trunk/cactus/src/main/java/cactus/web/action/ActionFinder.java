package cactus.web.action;

public interface ActionFinder {

	/**
	 * 查找Action class
	 * 
	 * @param key
	 *            除去request.getContextPath部分与除去后缀部分剩下的mappingUri<br/>
	 *            例如：app/user/set/action_list.do <br/>
	 *            key为/user/set/action_list
	 * @return
	 * @throws NoActionException
	 */
	Action findAction(String key) throws NoActionException;
}
