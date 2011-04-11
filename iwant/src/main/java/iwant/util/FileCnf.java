package iwant.util;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import com.hk.frame.util.P;

public class FileCnf {

	private String tmpPhotoPath;

	private String directory;

	private String root_path;

	public String suburl;

	/**
	 * 文件最大大小
	 */
	private int fileMaxSize;

	/**
	 * 获得存储到数据库的文件路径
	 * 
	 * @param name
	 *            文件名称(不包括扩展名)
	 * @return
	 *         2010-11-7
	 */
	public String getFileSaveToDbPath(String name) {
		Calendar createDate = Calendar.getInstance();
		String year = String.valueOf(createDate.get(Calendar.YEAR));
		String month = String.valueOf(createDate.get(Calendar.MONTH) + 1);
		String date = String.valueOf(createDate.get(Calendar.DAY_OF_MONTH));
		return directory + year + "/" + month + "/" + date + "/"
				+ createDate.get(Calendar.HOUR_OF_DAY) + "/" + name + "/";
	}

	/**
	 * 获取文件存储的路径
	 * 
	 * @param path
	 *            文件的数据库存储路径
	 * @return
	 *         2010-11-7
	 */
	public String getFilePath(String path) {
		return this.root_path + path;
	}

	/**
	 * 获取图片url地址片段(到目录部分为止)
	 * 
	 * @param path
	 *            数据库存储的图片路径
	 * @return
	 *         2010-11-7
	 */
	public String getPicUrlPart(String path) {
		return this.suburl + path;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getRoot_path() {
		return root_path;
	}

	public void setRoot_path(String rootPath) {
		root_path = rootPath;
	}

	public void setSuburl(String suburl) {
		this.suburl = suburl;
	}

	public String getSuburl() {
		return suburl;
	}

	public int getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(int fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	private static char[] syschar = new char[] { 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9' };

	public static String createFileName() {
		String tmp = System.currentTimeMillis() + "";
		Random r = null;
		for (int i = 0; i < 6; i++) {
			r = new Random();
			int idx = r.nextInt(syschar.length);
			tmp += syschar[idx];
		}
		return tmp;
	}

	public static void delPhotoFile(String path) {
		File file = new File(path);
		File parent = file.getParentFile();
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
		files = parent.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Random random = new Random();
			P.println(random.nextInt(3));
		}
	}

	public String getTmpPhotoPath() {
		return tmpPhotoPath;
	}

	public void setTmpPhotoPath(String tmpPhotoPath) {
		this.tmpPhotoPath = tmpPhotoPath;
	}

	/**
	 * 获取图片文件
	 * 
	 * @param filepath
	 *            文件路径
	 * @return
	 */
	public static File getFile(String filepath) {
		return new File(filepath);
	}
}