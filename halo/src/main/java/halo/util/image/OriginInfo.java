package halo.util.image;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class OriginInfo {

	public static final String IMGTYPE_JPEG = "jpeg";

	public static final String IMGTYPE_GIF = "gif";

	public static final String IMGTYPE_BMP = "bmp";

	public static final String IMGTYPE_PNG = "png";

	private int width;

	private int height;

	private ImageType imageType;

	private File file;

	public OriginInfo(File file) throws IOException {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
			if (readers.hasNext()) {
				ImageReader reader = readers.next();
				reader.setInput(iis);
				this.width = reader.getWidth(0);
				this.height = reader.getHeight(0);
				this.file = file;
				String imgTypeName = reader.getFormatName().toLowerCase();
				if (imgTypeName.equals("jpeg")) {
					this.imageType = ImageType.JPEG;
				}
				else if (imgTypeName.equals("bmp")) {
					this.imageType = ImageType.BMP;
				}
				else if (imgTypeName.equals("png")) {
					this.imageType = ImageType.PNG;
				}
				else if (imgTypeName.equals("gif")) {
					this.imageType = ImageType.GIF;
				}
				else {
					this.imageType = ImageType.JPEG;
				}
			}
		}
		catch (IOException e) {
			throw e;
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ImageType getImageType() {
		return imageType;
	}

	public long getImgFileSize() {
		return this.file.length();
	}

	public File getFile() {
		return file;
	}

	public long getImgFileSizeKB() {
		return new BigDecimal(this.file.length()).divide(new BigDecimal(1024),
				1, BigDecimal.ROUND_HALF_UP).longValue();
	}

	public long getImgFileSizeMB() {
		return new BigDecimal(this.file.length()).divide(
				new BigDecimal(1024 * 1024), 1, BigDecimal.ROUND_HALF_UP)
				.longValue();
	}
}