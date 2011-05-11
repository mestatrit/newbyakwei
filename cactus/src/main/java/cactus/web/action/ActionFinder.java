package cactus.web.action;

public interface ActionFinder {

	/**
	 * 查找Action class
	 * 
	 * @param key
	 * @return
	 * @throws NoActionException
	 */
	Action findAction(String key) throws NoActionException;
}
