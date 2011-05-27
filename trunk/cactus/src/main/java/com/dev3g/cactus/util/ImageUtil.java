package com.dev3g.cactus.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	private ImageUtil() {//
	}

	public static void test() throws Exception {
		// 1.jpg是你的 主图片的路径
		InputStream is = new FileInputStream("1.jpg");
		// 通过JPEG图象流创建JPEG数据流解码器
		JPEGImageDecoder jpegDecoder = JPEGCodec.createJPEGDecoder(is);
		// 解码当前JPEG数据流，返回BufferedImage对象
		BufferedImage buffImg = jpegDecoder.decodeAsBufferedImage();
		// 得到画笔对象
		Graphics g = buffImg.getGraphics();
		// 创建你要附加的图象。
		// 2.jpg是你的小图片的路径
		// ImageIcon imgIcon = new ImageIcon("2.jpg");
		// 得到Image对象。
		// Image img = imgIcon.getImage();
		// 将小图片绘到大图片上。
		// 5,300 .表示你的小图片在大图片上的位置。
		// g.drawImage(img, 5, 330, null);
		// 设置颜色。
		g.setColor(Color.BLACK);
		// 最后一个参数用来设置字体的大小
		Font f = new Font("宋体", Font.BOLD, 30);
		g.setFont(f);
		// 10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
		g.drawString("默哀555555。。。。。。。", 10, 30);
		g.dispose();
		OutputStream os = new FileOutputStream("union.jpg");
		// 创键编码器，用于编码内存中的图象数据。
		JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
		en.encode(buffImg);
		is.close();
		os.close();
		System.out.println("合成结束。。。。。。。。");
	}

	public static void convertCharToImageFile() throws Exception {
		int bgColor = 255;
		int fontColor = 0;
		// 文字
		String s = "你好，用友宝箱";
		// 图片
		int width = 110, height = 28;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(new Color(bgColor, bgColor, bgColor));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font(null, Font.PLAIN, 14));
		g.drawRect(0, 0, width - 1, height - 1);
		g.setColor(new Color(fontColor, fontColor, fontColor));
		g.drawString(s, 6, 19);
		g.dispose();
		ImageIO.write(image, "JPEG", new File("d:/1.jpg"));
		System.out.println("ok");
	}

	public static void main(String[] args) throws Exception {
		// ImageUtil.convertCharToImageFile();
		ImageUtil.convertCharToImageFile();
	}
}