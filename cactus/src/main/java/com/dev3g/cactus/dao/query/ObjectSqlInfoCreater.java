package com.dev3g.cactus.dao.query;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.RowMapper;

import com.dev3g.cactus.dao.annotation.Table;
import com.dev3g.cactus.dao.partition.DbPartitionHelper;
import com.dev3g.cactus.util.HkUtil;

/**
 * 初始化类与数据表的对应关系配置信息，存储类与数据表的映射对象的数据
 * 
 * @author akwei
 */
public class ObjectSqlInfoCreater implements InitializingBean {

	private final Log log = LogFactory.getLog(ObjectSqlInfoCreater.class);

	/**
	 * class名称为key
	 */
	private final Map<String, ObjectSqlInfo<?>> objectSqlInfoMap = new HashMap<String, ObjectSqlInfo<?>>();

	private List<TableCnf> tableCnfList;

	/**
	 * 存放需要扫描的路径
	 */
	private List<String> tableScanPathList;

	/**
	 * 存放定义的className
	 */
	private List<String> classNameList;

	public void setClassNameList(List<String> classNameList) {
		this.classNameList = classNameList;
	}

	public void setTableScanPathList(List<String> tableScanPathList) {
		this.tableScanPathList = tableScanPathList;
	}

	public void setTableCnfList(List<TableCnf> tableCnfList) throws Exception {
		this.tableCnfList = tableCnfList;
	}

	/**
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> ObjectSqlInfo<T> getObjectSqlInfo(Class<T> clazz) {
		ObjectSqlInfo<T> o = (ObjectSqlInfo<T>) this.objectSqlInfoMap.get(clazz
				.getName());
		if (o == null) {
			throw new RuntimeException("no ObjectSqlInfo for [ "
					+ clazz.getName() + " ]");
		}
		return o;
	}

	/**
	 * 通过clazz获得sql查询集合的组装对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		ObjectSqlInfo<T> o = (ObjectSqlInfo<T>) this.objectSqlInfoMap.get(clazz
				.getName());
		if (o != null) {
			return o.getRowMapper();
		}
		return null;
	}

	/**
	 * 通过定义的className来获取className对应的TableCnf，并存放到集合中
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	private List<TableCnf> getTableCnfFromClassNameList()
			throws ClassNotFoundException {
		List<TableCnf> list = new ArrayList<TableCnf>();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		Class<?> clazz = null;
		if (this.classNameList != null) {
			for (String className : this.classNameList) {
				clazz = classLoader.loadClass(className);
				TableCnf tableCnf = this.analyzeClass(clazz);
				if (tableCnf != null) {
					list.add(tableCnf);
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		ObjectSqlInfo<?> objectSqlInfo = null;
		if (this.tableCnfList == null) {
			this.tableCnfList = new ArrayList<TableCnf>();
		}
		this.tableCnfList.addAll(this.getTableCnfFromScanPathList());
		this.tableCnfList.addAll(this.getTableCnfFromClassNameList());
		for (TableCnf cnf : this.tableCnfList) {
			log.info("load tablebean [ " + cnf.getClassName()
					+ " ] with helper [ "
					+ cnf.getDbPartitionHelper().getClass().getName() + " ]");
			objectSqlInfo = new ObjectSqlInfo(cnf);
			objectSqlInfoMap.put(cnf.getClassName(), objectSqlInfo);
		}
	}

	/**
	 * 通过扫描指定路径，来获得含有@{@link Table}信息的类，并生成对应的TableCnf对象存入集合
	 * 
	 * @return
	 */
	private List<TableCnf> getTableCnfFromScanPathList() {
		List<TableCnf> list = new ArrayList<TableCnf>();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		URL url = classLoader.getResource("");
		String app_ab_path = url.getPath();
		if (this.tableScanPathList == null) {
			return list;
		}
		for (String scanPath : this.tableScanPathList) {
			list.addAll(this.scanPath(scanPath, app_ab_path));
		}
		return list;
	}

	/**
	 * 扫描classpath下的指定package中的class，不做深度调用，目前只支持classpath目录寻找，不支持jar文件中寻找
	 * 
	 * @param scanPath
	 * @param app_ab_path
	 * @return
	 */
	private List<TableCnf> scanPath(String scanPath, String app_ab_path) {
		List<TableCnf> list = new ArrayList<TableCnf>();
		File file = new File(app_ab_path + scanPath.replaceAll("\\.", "/"));
		if (!file.isDirectory()) {
			return list;
		}
		File[] files = file.listFiles(this.fileFilter);
		for (File f : files) {
			TableCnf tableCnf = this.analyzeFile(scanPath, f);
			list.add(tableCnf);
		}
		return list;
	}

	private TableCnf analyzeFile(String scanPath, File f) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String className = scanPath + "."
				+ f.getName().replaceFirst("\\.class", "");
		try {
			Class<?> clazz = classLoader.loadClass(className);
			return this.analyzeClass(clazz);
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private TableCnf analyzeClass(Class<?> clazz) {
		String className = clazz.getName();
		Table table = clazz.getAnnotation(Table.class);
		if (table != null) {
			TableCnf tableCnf = new TableCnf();
			tableCnf.setClassName(className);
			tableCnf.setDbPartitionHelper(this
					.getDbPartitionHelperFromTableAnnotation(table));
			return tableCnf;
		}
		return null;
	}

	private DbPartitionHelper getDbPartitionHelperFromTableAnnotation(
			Table table) {
		DbPartitionHelper dbPartitionHelper = null;
		if (!table.partitionid().equals("")) {
			dbPartitionHelper = (DbPartitionHelper) HkUtil.getBean(table
					.partitionid());
			if (dbPartitionHelper == null) {
				throw new RuntimeException("can not found spring bean id [ "
						+ table.partitionid() + " ]");
			}
		}
		else {
			try {
				dbPartitionHelper = (DbPartitionHelper) table.partitionClass()
						.getConstructor().newInstance();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return dbPartitionHelper;
	}

	private FileFilter fileFilter = new FileFilter() {

		private String subName = ".class";

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().toLowerCase().endsWith(subName);
		}
	};
}