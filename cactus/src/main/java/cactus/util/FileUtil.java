package cactus.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class FileUtil {

	/**
	 * 复制文件
	 * 
	 * @param source
	 *            原文家路径
	 * @param distm
	 *            要复制到的目标路径 2010-5-4
	 */
	public static void copyFile(String source, String dist, String fileName)
			throws IOException {
		copyFile(new File(source), dist, fileName);
	}

	public static void mkdir(String path) {
		File f = new File(path);
		if (!f.exists() || !f.isDirectory()) {
			f.mkdirs();
		}
	}

	public static boolean isFileDirectory(String path) {
		File f = new File(path);
		if (!f.exists()) {
			return false;
		}
		return f.isDirectory();
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 *            原文家路径
	 * @param distm
	 *            要复制到的目标路径 2010-5-4
	 */
	public static void copyFile(File file, String dist, String fileName)
			throws IOException {
		File f = new File(dist);
		if (!f.exists() || !f.isDirectory()) {
			f.mkdirs();
		}
		BufferedOutputStream buffos = null;
		BufferedInputStream buffis = null;
		byte[] by = new byte[1024];
		try {
			buffos = new BufferedOutputStream(new FileOutputStream(dist + "/"
					+ fileName));
			buffis = new BufferedInputStream(new FileInputStream(file));
			int len = -1;
			while ((len = buffis.read(by)) != -1) {
				buffos.write(by, 0, len);
			}
			buffos.flush();
		}
		catch (FileNotFoundException e) {
		}
		finally {
			try {
				if (buffos != null) {
					buffos.close();
				}
			}
			catch (IOException e) {
			}
			try {
				if (buffis != null) {
					buffis.close();
				}
			}
			catch (IOException e) {
			}
		}
	}

	/**
	 * 查看文件大小是否大于指定大小
	 * 
	 * @param file
	 * @param size
	 *            单位为k
	 * @return true:文件大小超过指定大小,false:文件大小在指定大小之内 2010-5-10
	 */
	public static boolean isBigger(File file, long size) {
		long fileSize = file.length();
		BigDecimal decimal = new BigDecimal(fileSize).divide(new BigDecimal(
				1024), 0, BigDecimal.ROUND_HALF_UP);
		long amount = decimal.longValue() - size;
		if (amount > 0) {
			return true;
		}
		return false;
	}

	public static final int FILE_SIZE_TYPE_K = 0;

	public static final int FILE_SIZE_TYPE_M = 1;

	public static long getFileSize(File file, int sizeType) {
		long fileSize = file.length();
		if (sizeType == FILE_SIZE_TYPE_K) {
			BigDecimal decimal = new BigDecimal(fileSize).divide(
					new BigDecimal(1024), 0, BigDecimal.ROUND_HALF_UP);
			return decimal.longValue();
		}
		if (sizeType == FILE_SIZE_TYPE_M) {
			BigDecimal decimal = new BigDecimal(fileSize).divide(
					new BigDecimal(1024 * 1024), 0, BigDecimal.ROUND_HALF_UP);
			return decimal.longValue();
		}
		return fileSize;
	}

	public static void deleteFile(File file) {
		if (file == null) {
			return;
		}
		if (file.exists()) {
			file.delete();
		}
		File parent = file.getParentFile();
		if (parent != null) {
			parent.delete();
		}
	}

	public static void deleteAllFile(String dirPath) {
		File file = new File(dirPath);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] fs = file.listFiles();
				String path = null;
				for (File f : fs) {
					path = f.getAbsolutePath();
					deleteAllFile(path);
				}
				file.delete();
			}
			else {
				file.delete();
			}
		}
	}
}
