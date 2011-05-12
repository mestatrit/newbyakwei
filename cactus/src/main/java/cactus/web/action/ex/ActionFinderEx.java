package cactus.web.action.ex;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cactus.web.action.Action;
import cactus.web.action.ActionFinder;
import cactus.web.action.NoActionException;

/**
 * 根据Action文件路径进行查找Action，并产生Action对象
 * 
 * @author akwei
 */
public class ActionFinderEx implements ActionFinder {

	private String basePath;

	private List<String> scanPathList;

	/**
	 * 应用程序的绝对路径
	 */
	private String app_ab_path;

	public ActionFinderEx() {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("");
		this.app_ab_path = url.getPath();
	}

	private final Map<String, Action> actionMap = new HashMap<String, Action>();

	/**
	 * 设置查询Action的根目录，例如app.web
	 * 
	 * @param basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setScanPathList(List<String> scanPathList) {
		this.scanPathList = scanPathList;
	}

	@Override
	public Action findAction(String key) throws NoActionException {
		// TODO Auto-generated method stub
		return null;
	}
	// private File searchActionFile(String dirPath, String actionName) {
	// File file = new File(this.app_ab_path + dirPath);
	// if (!file.isDirectory()) {
	// return null;// 没有找到此目录
	// }
	// }
}