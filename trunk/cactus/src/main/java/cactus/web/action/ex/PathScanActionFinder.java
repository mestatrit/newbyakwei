package cactus.web.action.ex;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import cactus.util.HkUtil;
import cactus.web.action.Action;
import cactus.web.action.ActionFinder;
import cactus.web.action.NoActionException;

/**
 * 根据Action文件路径进行查找Action，并产生Action对象
 * 
 * @author akwei
 */
public class PathScanActionFinder implements ActionFinder {

	private String basePath;

	private String subName = "action.class";

	/**
	 * 应用程序的绝对路径
	 */
	private String app_ab_path;

	public PathScanActionFinder() {
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

	@SuppressWarnings("unchecked")
	@Override
	public Action findAction(String actionUrl) throws NoActionException {
		Action obj = actionMap.get(actionUrl);
		if (obj != null) {
			return obj;
		}
		String dirpath = null;
		String filename = null;
		int lastsepartoridx = actionUrl.lastIndexOf('/');
		if (lastsepartoridx == -1) {
			dirpath = "/";
			filename = actionUrl;
		}
		else {
			dirpath = actionUrl.substring(0, lastsepartoridx + 1);
			filename = actionUrl.substring(lastsepartoridx + 1);
		}
		File file = this.searchActionFile(dirpath, filename);
		if (file == null) {
			throw new NoActionException("no action [ " + actionUrl + " ]");
		}
		String actionClassName = this.basePath + dirpath.replaceAll("/", ".")
				+ this.getFileShortName(file);
		try {
			Class<Action> clazz = (Class<Action>) Thread.currentThread()
					.getContextClassLoader().loadClass(actionClassName);
			Action action = clazz.getConstructor().newInstance();
			this.processObjectAutowiredField(action);
			this.actionMap.put(actionUrl, action);
			return action;
		}
		catch (ClassNotFoundException e) {
			throw new NoActionException("no action [ " + actionUrl + " ]");
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getFileShortName(File file) {
		String name = file.getName();
		int idx = name.indexOf('.');
		String shortName = name.substring(0, idx);
		return shortName;
	}

	/**
	 * 搜索指定目录，找到action匹配的class文件
	 * 
	 * @param dirPath
	 *            文件相对目录
	 * @param actionName
	 * @return
	 */
	private File searchActionFile(String dirPath, String actionName) {
		File file = new File(this.app_ab_path
				+ this.basePath.replaceAll("\\.", "/") + dirPath);
		if (!file.isDirectory()) {
			return null;// 没有找到此目录
		}
		File[] files = file.listFiles(this.fileFilter);
		for (File o : files) {
			if (o.getName().toLowerCase().equals(actionName + "action.class")) {
				return o;
			}
		}
		return null;
	}

	/**
	 * 由于通过anntion lazy 注入的bean autowired的属性无值，特此注入
	 * 
	 * @param obj
	 *            2010-6-28
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void processObjectAutowiredField(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(obj) == null) {
				if (field.getAnnotation(Autowired.class) != null) {
					Class<?> clazz = field.getType();
					try {
						Object fieldObj = HkUtil.getWebApplicationContext()
								.getBean(clazz);
						field.set(obj, fieldObj);
					}
					catch (BeansException e) {
					}
				}
			}
		}
	}

	private FileFilter fileFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().toLowerCase().endsWith(subName);
		}
	};
}