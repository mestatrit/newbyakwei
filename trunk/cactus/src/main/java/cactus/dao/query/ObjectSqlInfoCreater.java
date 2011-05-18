package cactus.dao.query;

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

import cactus.dao.annotation.Table;
import cactus.util.HkUtil;

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

	private List<String> tableScanPathList;

	public void setTableScanPathList(List<String> tableScanPathList) {
		this.tableScanPathList = tableScanPathList;
	}

	public void setTableCnfList(List<TableCnf> tableCnfList) throws Exception {
		this.tableCnfList = tableCnfList;
	}

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

	@SuppressWarnings("unchecked")
	public <T> RowMapper<T> getRowMapper(Class<T> clazz) {
		ObjectSqlInfo<T> o = (ObjectSqlInfo<T>) this.objectSqlInfoMap.get(clazz
				.getName());
		if (o != null) {
			return o.getRowMapper();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterPropertiesSet() throws Exception {
		ObjectSqlInfo<?> objectSqlInfo = null;
		List<TableCnf> list = this.getTableCnfFromScanPathList();
		if (this.tableCnfList == null) {
			this.tableCnfList = new ArrayList<TableCnf>();
		}
		this.tableCnfList.addAll(list);
		for (TableCnf cnf : this.tableCnfList) {
			objectSqlInfo = new ObjectSqlInfo(cnf);
			objectSqlInfoMap.put(cnf.getClassName(), objectSqlInfo);
		}
	}

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
			log.info("load tablebean [ " + tableCnf.getClassName()
					+ " ] with helper [ "
					+ tableCnf.getDbPartitionHelper().getClass().getName()
					+ " ]");
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
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
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