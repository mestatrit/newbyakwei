package svr;

import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.Slide;
import iwant.bean.enumtype.ActiveType;
import iwant.svr.exception.ImageProcessException;
import iwant.svr.exception.PptNotFoundException;
import iwant.svr.exception.ProjectNotFoundException;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import cactus.util.DateUtil;
import cactus.util.image.jmagick.PicRect;

public class PptSvrTest extends BaseSvrTest {

	@Test
	public void createMainPpt() throws ProjectNotFoundException {
		MainPpt mainPpt = new MainPpt();
		mainPpt.setActive_flag(ActiveType.ACTIVE.getValue());
		mainPpt.setCatid(this.category.getCatid());
		mainPpt.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		mainPpt.setName("ppt 3");
		mainPpt.setPic_path("");
		mainPpt.setProjectid(this.project0.getProjectid());
		this.pptSvr.createMainPpt(mainPpt);
	}

	@Test
	public void createSlide() throws PptNotFoundException, ImageProcessException {
		Slide slide = new Slide();
		slide.setDescr("slide desc 3");
		slide.setPptid(this.ppt0.getPptid());
		slide.setSubtitle("sub 3");
		slide.setTitle("title 3");
		slide.setPic_path("");
		this.pptSvr.createSlide(slide, new File(filePath), null);
	}

	@Test
	public void updateSlide() throws PptNotFoundException, ImageProcessException {
		Slide slide = this.pptSvr.getSlide(this.slide0.getSlideid());
		Assert.assertNotNull(slide);
		slide.setDescr("slide desc dldld");
		slide.setOrder_flag(99);
		slide.setPic_path("dd");
		slide.setSubtitle("dkkk");
		slide.setTitle("tititi");
		this.pptSvr.updateSlide(slide, new File(filePath), null);
		Slide dbSlide = this.pptSvr.getSlide(this.slide0.getSlideid());
		this.assertSlideData(slide, dbSlide);
	}

	@Test
	public void createPpt() throws ProjectNotFoundException {
		Ppt ppt = new Ppt();
		ppt.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		ppt.setName("ppt 0");
		ppt.setPic_path("");
		ppt.setProjectid(this.project0.getProjectid());
		this.pptSvr.createPpt(ppt);
		Ppt dbPpt = this.pptSvr.getPpt(ppt.getPptid());
		this.assertPptData(ppt, dbPpt);
	}

	@Test
	public void updatePpt() throws ProjectNotFoundException {
		Ppt ppt = this.pptSvr.getPpt(this.ppt0.getPptid());
		ppt.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		ppt.setName("ppt 0");
		ppt.setPic_path("");
		ppt.setProjectid(this.project0.getProjectid());
		this.pptSvr.updatePpt(ppt);
		Ppt dbPpt = this.pptSvr.getPpt(ppt.getPptid());
		this.assertPptData(ppt, dbPpt);
	}

	@Test
	public void deletePpt() {
		this.pptSvr.deletePpt(this.mainPpt0.getPptid());
		Ppt ppt = this.pptSvr.getPpt(this.mainPpt0.getPptid());
		Assert.assertNull(ppt);
	}

	@Test
	public void getPpt() {
		Ppt ppt = this.pptSvr.getPpt(this.ppt0.getPptid());
		this.assertPptData(this.ppt0, ppt);
	}

	@Test
	public void getSlideListByPptid() {
		List<Slide> list = this.pptSvr.getSlideListByPptidOrdered(this.ppt0
				.getPptid());
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void getSlideListByProjectid() {
		List<Slide> list = this.pptSvr.getSlideListByProjectid(this.project0
				.getProjectid(), 0, 100);
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void deleteSlide() {
		this.pptSvr.deleteSlide(this.slide0);
		Slide slide = this.pptSvr.getSlide(this.slide0.getSlideid());
		Assert.assertNull(slide);
	}

	@Test
	public void deletePptByProjectid() {
		this.pptSvr.deletePptByProjectid(this.ppt0.getProjectid());
		Ppt ppt = this.pptSvr.getPpt(this.ppt0.getPptid());
		Assert.assertNull(ppt);
		List<Ppt> list = this.pptSvr.getPptListByProjectid(this.ppt0
				.getProjectid(), 0, 100);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void getPptMapInId() {
		List<Long> idList = new ArrayList<Long>();
		idList.add(this.ppt0.getPptid());
		idList.add(this.ppt1.getPptid());
		Map<Long, Ppt> map = this.pptSvr.getPptMapInId(idList);
		Ppt ppt = map.get(this.ppt0.getPptid());
		this.assertPptData(this.ppt0, ppt);
		ppt = map.get(this.ppt1.getPptid());
		this.assertPptData(this.ppt1, ppt);
	}

	@Test
	public void getMainPpt() {
		MainPpt mainPpt = this.pptSvr.getMainPpt(this.mainPpt0.getPptid());
		Assert.assertNotNull(mainPpt);
		this.assertMainPptData(this.mainPpt0, mainPpt);
	}

	@Test
	public void updateSldePic0() {
		PicRect picRect = new PicRect(0, 0, 70, 70);
		this.pptSvr.updateSldePic1(this.slide0.getSlideid(), picRect);
	}

	private void assertSlideData(Slide expected, Slide actual) {
		Assert.assertEquals(expected.getDescr(), actual.getDescr());
		Assert.assertEquals(expected.getPic_path(), actual.getPic_path());
		Assert.assertEquals(expected.getPptid(), actual.getPptid());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
		Assert.assertEquals(expected.getSlideid(), actual.getSlideid());
		Assert.assertEquals(expected.getSubtitle(), actual.getSubtitle());
		Assert.assertEquals(expected.getTitle(), actual.getTitle());
		Assert.assertEquals(expected.getOrder_flag(), actual.getOrder_flag());
	}

	private void assertMainPptData(MainPpt expected, MainPpt actual) {
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getOrder_flag(), actual.getOrder_flag());
		Assert.assertEquals(expected.getPic_path(), actual.getPic_path());
		Assert.assertEquals(expected.getPptid(), actual.getPptid());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
		Assert.assertEquals(expected.getActive_flag(), actual.getActive_flag());
		Assert.assertEquals(expected.getCatid(), actual.getCatid());
		Assert.assertEquals(expected.getCreatetime().getTime(), actual
				.getCreatetime().getTime());
	}

	private void assertPptData(Ppt expected, Ppt actual) {
		Assert.assertEquals(expected.getName(), actual.getName());
		// Assert.assertEquals(expected.getPic_path(), actual.getPic_path());
		Assert.assertEquals(expected.getPptid(), actual.getPptid());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
		Assert.assertEquals(expected.getCreatetime().getTime(), actual
				.getCreatetime().getTime());
	}
}