package test;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.dev3g.cactus.util.image.ImageException;
import com.dev3g.cactus.util.image.ImageParam;
import com.dev3g.cactus.util.image.ImageRect;
import com.dev3g.cactus.util.image.ImageRectMaker;
import com.dev3g.cactus.util.image.ImageShaper;
import com.dev3g.cactus.util.image.ImageShaperFactory;
import com.dev3g.cactus.util.image.ImageSize;
import com.dev3g.cactus.util.image.ImageSizeMaker;
import com.dev3g.cactus.util.image.OriginInfo;

public class JmagickTest {

	String path = "d:/test/create/";

	String originFileName = "d:/test/DSC_3853.jpg";

	String name = "result.jpg";

	@Test
	public void cut() {
		File file = new File(originFileName);
		OriginInfo originInfo = null;
		try {
			originInfo = new OriginInfo(file);
		}
		catch (IOException e) {
			Assert.fail(e.getMessage());
			return;
		}
		ImageParam imageParam = new ImageParam(originInfo);
		imageParam.setQuality(100);
		imageParam.setSharp0(0);
		imageParam.setSharp1(0);
		ImageShaper imageShaper = ImageShaperFactory.getImageShaper("jmagick");
		ImageRect imageRect = new ImageRect(0, 0, 800, 800);
		try {
			imageShaper.cutImage(imageParam, imageRect, path, name);
		}
		catch (ImageException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void make() {
		File file = new File(originFileName);
		OriginInfo originInfo = null;
		try {
			originInfo = new OriginInfo(file);
		}
		catch (IOException e) {
			Assert.fail(e.getMessage());
			return;
		}
		ImageParam imageParam = new ImageParam(originInfo);
		imageParam.setQuality(95);
		imageParam.setSharp0(2);
		imageParam.setSharp1(2);
		ImageShaper imageShaper = ImageShaperFactory.getImageShaper("jmagick");
		ImageSize imageSize = ImageSizeMaker.makeSize(originInfo.getWidth(),
				originInfo.getHeight(), 400);
		try {
			imageShaper.scaleImage(imageParam, imageSize, path, name);
		}
		catch (ImageException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void makeSquare() {
		File file = new File(originFileName);
		OriginInfo originInfo = null;
		try {
			originInfo = new OriginInfo(file);
		}
		catch (IOException e) {
			Assert.fail(e.getMessage());
			return;
		}
		ImageParam imageParam = new ImageParam(originInfo);
		imageParam.setQuality(30);
		imageParam.setSharp0(2);
		imageParam.setSharp1(2);
		ImageShaper imageShaper = ImageShaperFactory.getImageShaper("jmagick");
		try {
			ImageRect imageRect = ImageRectMaker.cutAndMiddleSquare(originInfo
					.getWidth(), originInfo.getHeight(), 200);
			imageShaper
					.cutAndScaleImage(imageParam, imageRect, 200, path, name);
		}
		catch (ImageException e) {
			Assert.fail(e.getMessage());
		}
	}
}
