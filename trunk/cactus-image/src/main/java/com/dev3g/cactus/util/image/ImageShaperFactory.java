package com.dev3g.cactus.util.image;

import java.util.HashMap;
import java.util.Map;

import com.dev3g.cactus.util.image.jmagick.JmagickImageShaper;

public class ImageShaperFactory {

	public static final String IMAGESHAPER_JMAGICK = "jmagick";

	private static final Map<String, ImageShaper> map = new HashMap<String, ImageShaper>();
	static {
		map.put(IMAGESHAPER_JMAGICK, new JmagickImageShaper());
	}

	/**
	 * 获得处理图片的类，
	 * 
	 * @param type
	 *            目前图片只支持JMagick，因此参数需要传入jmagick，忽略大小写
	 * @return 图片处理类，null为不支持的类型
	 */
	public static ImageShaper getImageShaper(String type) {
		if (type.equalsIgnoreCase(IMAGESHAPER_JMAGICK)) {
			return map.get(IMAGESHAPER_JMAGICK);
		}
		return null;
	}
}