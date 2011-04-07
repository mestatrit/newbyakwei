package com.hk.frame.util.image2;

import java.awt.Rectangle;
import java.io.File;

import magick.CompressionType;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PreviewType;

import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;

public class ImageProcessor {

	static {
		System.setProperty("jmagick.systemclassloader", "no");
	}

	public int maxWidth = 800;

	private ImgFileInfo imgFileInfo;

	private int quality;

	private double sharp0;

	private double sharp1;

	private boolean clearExif;

	public final static byte IMG_SQUARE = 0;

	public final static byte IMG_OBLONG = 1;

	public ImageProcessor(ImgFileInfo imgFileInfo) {
		this.imgFileInfo = ImgFileInfo.getImageFileInfo(imgFileInfo.getFile());
	}

	// 120*120的图片,不裁图，只压缩，按照比例
	public void makeSmall(String newFileName, int size) throws ImageException {
		int width = this.imgFileInfo.getWidth();
		int height = this.imgFileInfo.getHeight();
		try {
			ImageInfo info = this.createImageInfo();
			MagickImage image = new MagickImage(info);
			this.processClearExif(image);
			MagickImage scaled = null;
			double proportion = 0;
			if (width < size && height < size) {
				scaled = image.scaleImage(width, height);
			}
			else {
				if (width == height) {
					scaled = image.scaleImage(size, size);
				}
				else {
					if (width > height) {
						if (width > size) {
							proportion = (double) size / width;
						}
						else {
							proportion = width / (double) size;
						}
						scaled = image.scaleImage(size,
								(int) (height * proportion));
					}
					else {
						if (height > size) {
							proportion = (double) size / height;
						}
						else {
							proportion = height / (double) size;
						}
						scaled = image.scaleImage((int) (width * proportion),
								size);
					}
				}
			}
			scaled = this.processSharp(scaled);
			scaled.setFileName(newFileName);
			scaled.writeImage(info);
		}
		catch (MagickException e) {
			throw new ImageException(e);
		}
	}

	public void makeBig(String newFileName, int size) throws ImageException {
		int width = this.imgFileInfo.getWidth();
		int height = this.imgFileInfo.getHeight();
		try {
			ImageInfo info = this.createImageInfo();
			this.processQuality(info);
			MagickImage image = new MagickImage(info);
			this.processClearExif(image);
			MagickImage scaled = null;
			double proportion = 0;
			if (width == height) {
				if (width > size) {
					proportion = (double) size / width;
					scaled = image.scaleImage((int) (width * proportion),
							(int) (width * proportion));
				}
				else {
					scaled = image.scaleImage(width, height);
				}
			}
			else {
				if (width > height) {
					if (width > size) {
						proportion = (double) size / width;
						scaled = image.scaleImage(size,
								(int) (height * proportion));
					}
					else {
						scaled = image.scaleImage(width, height);
					}
				}
				else {
					if (height > size) {
						proportion = (double) size / height;
						scaled = image.scaleImage((int) (width * proportion),
								size);
					}
					else {
						scaled = image.scaleImage(width, height);
					}
				}
			}
			if (size > maxWidth) {
				scaled = scaled.sharpenImage(1.0, 1.0);
			}
			else {
				scaled = this.processSharp(scaled);
			}
			scaled.setFileName(newFileName);
			scaled.writeImage(info);
		}
		catch (MagickException e) {
			throw new ImageException(e);
		}
	}

	/**
	 * 方图 ,裁减后压缩<br/>
	 * 从上传的大图中裁剪出中间部分，然后对中间部分进行压缩
	 */
	public void makeSquare(String newFileName, int size) throws ImageException {
		int width = this.imgFileInfo.getWidth();
		int height = this.imgFileInfo.getHeight();
		try {
			Rectangle rect = null;
			if (width < size && height < size) {
				rect = new Rectangle(0, 0, size, size);
			}
			else if (width < size || height < size) {
				rect = new Rectangle(0, 0, size, size);
			}
			else {
				if (width < height) {
					int beginPoint = (height - width) / 2;
					rect = new Rectangle(0, beginPoint, width, width);
				}
				else {
					int beginPoint = (width - height) / 2;
					rect = new Rectangle(beginPoint, 0, height, height);
				}
			}
			ImageInfo info = this.createImageInfo();
			this.processQuality(info);
			MagickImage image = new MagickImage(info);
			this.processClearExif(image);
			MagickImage cropped = image.cropImage(rect).scaleImage(size, size);
			cropped = this.processSharp(cropped);
			cropped.setFileName(newFileName);
			cropped.writeImage(info);
		}
		catch (MagickException e) {
			throw new ImageException(e);
		}
	}

	/**
	 * 裁剪选定区域图片
	 * 
	 * @param filePath
	 * @param fileName
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @throws ImageException
	 */
	public void cutImage(String filePath, String fileName, int x1, int y1,
			int x2, int y2) throws ImageException {
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		String newFile = filePath + fileName;
		int new_width = x2 - x1;
		int new_height = y2 - y1;
		try {
			Rectangle rect = new Rectangle(x1, y1, new_width, new_height);
			ImageInfo info = this.createImageInfo();
			info.setQuality(95);
			MagickImage image = new MagickImage(info);
			this.processClearExif(image);
			MagickImage cropped = image.cropImage(rect);
			cropped.setFileName(newFile);
			cropped.writeImage(info);
		}
		catch (MagickException e) {
			throw new ImageException(e);
		}
	}

	/**
	 * @param filePath
	 *            图片目录
	 * @param fileName
	 *            图片名称,包括扩展名
	 * @param type
	 *            图片几何类型
	 * @param size
	 *            图片尺寸
	 * @throws MagickException
	 */
	public void makeImage(String filePath, String fileName, byte type, int size)
			throws ImageException {
		File f = new File(filePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		String newFile = filePath + fileName;
		String img = newFile.substring(0, newFile.lastIndexOf("."));
		// 文件先不加后缀
		if (type == JMagickUtil.IMG_SQUARE) {
			this.makeSquare(img, size);
		}
		else if (type == JMagickUtil.IMG_OBLONG) {
			if (size <= 120) {
				this.makeSmall(img, size);
			}
			else if (size > 120) {
				this.makeBig(img, size);
			}
		}
		File old_file = new File(newFile);
		if (old_file.isFile()) {
			old_file.delete();
		}
		File file = new File(img);
		file.renameTo(new File(newFile));
	}

	private ImageInfo processQuality(ImageInfo info) {
		if (this.quality > 0) {
			try {
				info.setQuality(quality);
			}
			catch (MagickException e) {// 忽略这个错误
			}
		}
		return info;
	}

	private MagickImage processClearExif(MagickImage image) {
		if (!this.clearExif) {
			return image;
		}
		try {
			image.profileImage("*", null);
		}
		catch (Exception e1) {// 忽略这个错误
		}
		return image;
	}

	private MagickImage processSharp(MagickImage image) {
		if (this.sharp0 > 0 || this.sharp1 > 0) {
			try {
				return image.sharpenImage(this.sharp0, this.sharp1);
			}
			catch (MagickException e) {// 忽略这个错误
			}
		}
		return image;
	}

	private ImageInfo createImageInfo() throws MagickException {
		ImageInfo info = new ImageInfo(this.imgFileInfo.getFile()
				.getAbsolutePath());
		info.setCompression(CompressionType.JPEGCompression);
		info.setPreviewType(PreviewType.JPEGPreview);
		this.processQuality(info);
		return info;
	}
}