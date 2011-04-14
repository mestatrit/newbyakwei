package svr;

import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.Slide;
import iwant.bean.enumtype.ActiveType;
import iwant.svr.OptStatus;
import iwant.svr.PptSvr;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hk.frame.util.DataUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/app-ds.xml", "/app-dao.xml", "/app-svr.xml" })
@Transactional
public class PptSvrTest {

	@Resource
	private PptSvr pptSvr;

	MainPpt mainPpt0;

	MainPpt mainPpt1;

	Ppt ppt0;

	Ppt ppt1;

	Slide slide0;

	Slide slide1;

	int catid = 2;

	@Before
	public void init() {
		// *************mainppt*************//
		// data 0
		mainPpt0 = new MainPpt();
		mainPpt0.setActive_flag(ActiveType.ACTIVE.getValue());
		mainPpt0.setCatid(catid);
		mainPpt0.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		mainPpt0.setName("ppt 0");
		mainPpt0.setPic_path("");
		mainPpt0.setProjectid(9);
		this.pptSvr.createMainPpt(mainPpt0);
		// data 1
		mainPpt1 = new MainPpt();
		mainPpt1.setActive_flag(ActiveType.ACTIVE.getValue());
		mainPpt1.setCatid(catid);
		mainPpt1.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		mainPpt1.setName("ppt 1");
		mainPpt1.setPic_path("");
		mainPpt1.setProjectid(9);
		this.pptSvr.createMainPpt(mainPpt1);
		// *************slide*************//
		// data 0
		this.slide0 = new Slide();
		this.slide0.setDescr("slide desc 3");
		this.slide0.setPptid(3);
		this.slide0.setProjectid(5);
		this.slide0.setSubtitle("sub 3");
		this.slide0.setTitle("title 3");
		this.slide0.setPic_path("");
		this.pptSvr.createSlide(this.slide0, new File("d:/test/test7.jpg"));
		// data 1
		this.slide1 = new Slide();
		this.slide1.setDescr("slide desc 4");
		this.slide1.setPptid(3);
		this.slide1.setProjectid(5);
		this.slide1.setSubtitle("sub 4");
		this.slide1.setTitle("title 4");
		this.slide1.setPic_path("");
		this.pptSvr.createSlide(this.slide1, new File("d:/test/test7.jpg"));
		// *************ppt*************//
		// data 0
		this.ppt0 = new Ppt();
		ppt0.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		ppt0.setName("ppt 0");
		ppt0.setPic_path("");
		ppt0.setProjectid(9);
		this.pptSvr.createPpt(ppt0);
		// data 1
		this.ppt1 = new Ppt();
		ppt1.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		ppt1.setName("ppt 0");
		ppt1.setPic_path("");
		ppt1.setProjectid(9);
		this.pptSvr.createPpt(ppt1);
	}

	@Test
	public void createMainPpt() {
		MainPpt mainPpt = new MainPpt();
		mainPpt.setActive_flag(ActiveType.ACTIVE.getValue());
		mainPpt.setCatid(catid);
		mainPpt.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		mainPpt.setName("ppt 3");
		mainPpt.setPic_path("");
		mainPpt.setProjectid(9);
		this.pptSvr.createMainPpt(mainPpt);
	}

	@Test
	public void createSlide() {
		Slide slide = new Slide();
		slide.setDescr("slide desc 3");
		slide.setPptid(3);
		slide.setProjectid(5);
		slide.setSubtitle("sub 3");
		slide.setTitle("title 3");
		slide.setPic_path("");
		OptStatus optStatus = this.pptSvr.createSlide(slide, new File(
				"d:/test/test7.jpg"));
		Assert.assertEquals(true, optStatus.isSuccess());
	}

	@Test
	public void updateSlide() {
		Slide slide = this.pptSvr.getSlide(this.slide0.getSlideid());
		Assert.assertNotNull(slide);
		slide.setDescr("slide desc dldld");
		slide.setOrder_flag(99);
		slide.setPic_path("dd");
		// slide.setPptid(90);
		slide.setProjectid(99);
		slide.setSubtitle("dkkk");
		slide.setTitle("tititi");
		OptStatus optStatus = this.pptSvr.updateSlide(slide, new File(
				"d:/test/test7.jpg"));
		Assert.assertEquals(true, optStatus.isSuccess());
		Slide dbSlide = this.pptSvr.getSlide(this.slide0.getSlideid());
		this.assertSlideData(slide, dbSlide);
	}

	@Test
	public void getMainPptListOrderedByCatid() {
		List<MainPpt> list = this.pptSvr.getMainPptListOrderedByCatid(2,
				ActiveType.ACTIVE, 0, -1);
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void createPpt() {
		Ppt ppt = new Ppt();
		ppt.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		ppt.setName("ppt 0");
		ppt.setPic_path("");
		ppt.setProjectid(9);
		this.pptSvr.createPpt(ppt);
		Ppt dbPpt = this.pptSvr.getPpt(ppt.getPptid());
		this.assertPptData(ppt, dbPpt);
	}

	@Test
	public void updatePpt() {
		Ppt ppt = this.pptSvr.getPpt(this.ppt0.getPptid());
		ppt.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		ppt.setName("ppt 0");
		ppt.setPic_path("");
		ppt.setProjectid(9);
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
	}

	@Test
	public void getSlideListByProjectid() {
	}

	@Test
	public void deleteSlide() {
	}

	@Test
	public void deletePptByProjectid() {
		this.pptSvr.deletePptByProjectid(this.ppt0.getProjectid());
		Ppt ppt = this.pptSvr.getPpt(this.ppt0.getPptid());
		Assert.assertNull(ppt);
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
		Assert.assertEquals(expected.getPic_path(), actual.getPic_path());
		Assert.assertEquals(expected.getPptid(), actual.getPptid());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
		Assert.assertEquals(expected.getCreatetime().getTime(), actual
				.getCreatetime().getTime());
	}
}