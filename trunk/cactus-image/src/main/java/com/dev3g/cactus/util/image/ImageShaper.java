package com.dev3g.cactus.util.image;

import java.io.File;

public interface ImageShaper {

	/**
	 * 裁剪图片并返回新图片文件
	 * 
	 * @param imageParam
	 *            图片处理参数
	 * @param imageRect
	 *            图片起始位置与大小
	 * @param path
	 *            处理后的图片存储路径
	 * @param name
	 *            处理后的图片文件名称
	 * @return 处理后的图片文件
	 * @throws ImageException
	 *             图片处理错误
	 */
	File cutImage(ImageParam imageParam, ImageRect imageRect, String path,
			String name) throws ImageException;

	/**
	 * 缩放图片并返回处理后的图片文件
	 * 
	 * @param imageParam
	 *            图片处理参数
	 * @param imageSize
	 *            图片大小
	 * @param path
	 *            处理后的图片存储路径
	 * @param name
	 *            处理后的图片文件名称
	 * @return 处理后的新图片文件
	 * @throws ImageException
	 *             图片处理错误
	 */
	File scaleImage(ImageParam imageParam, ImageSize imageSize, String path,
			String name) throws ImageException;

	File cutAndScaleImage(ImageParam imageParam, ImageRect imageRect, int size,
			String path, String name) throws ImageException;
}
