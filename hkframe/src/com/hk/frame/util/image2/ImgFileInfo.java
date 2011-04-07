package com.hk.frame.util.image2;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImgFileInfo {

	public static final String IMGTYPE_JPEG = "jpeg";

	public static final String IMGTYPE_GIF = "gif";

	public static final String IMGTYPE_BMP = "bmp";

	public static final String IMGTYPE_PNG = "png";

	private int width;

	private int height;

	private String imgType;

	private long imgFileSize;

	private File file;

	public File getFile() {
		return file;
	}

	private ImgFileInfo() {
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public long getImgFileSize() {
		return imgFileSize;
	}

	public long getImgFileSizeKB() {
		return new BigDecimal(this.imgFileSize).divide(new BigDecimal(1024), 1,
				BigDecimal.ROUND_HALF_UP).longValue();
	}

	public long getImgFileSizeMB() {
		return new BigDecimal(this.imgFileSize).divide(
				new BigDecimal(1024 * 1024), 1, BigDecimal.ROUND_HALF_UP)
				.longValue();
	}

	public static ImgFileInfo getImageFileInfo(File file) {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
			if (readers.hasNext()) {
				ImgFileInfo imgFileInfo = new ImgFileInfo();
				ImageReader reader = readers.next();
				reader.setInput(iis);
				imgFileInfo.setImgType(reader.getFormatName().toLowerCase());
				imgFileInfo.setWidth(reader.getWidth(0));
				imgFileInfo.setHeight(reader.getHeight(0));
				imgFileInfo.file = file;
				imgFileInfo.imgFileSize = file.length();
				return imgFileInfo;
			}
			return null;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (iis != null) {
				try {
					iis.close();
				}
				catch (IOException e) {
					throw new RuntimeException(e);
				}
				iis = null;
			}
		}
	}
}