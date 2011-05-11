package svr;

import iwant.bean.Category;
import iwant.bean.City;
import iwant.bean.Country;
import iwant.bean.District;
import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.Project;
import iwant.bean.Province;
import iwant.bean.Slide;
import iwant.bean.enumtype.ActiveType;
import iwant.svr.CategorySvr;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;
import iwant.svr.ZoneSvr;
import iwant.svr.exception.DuplicateCityNameException;
import iwant.svr.exception.DuplicateDistrictNameException;
import iwant.svr.exception.DuplicateProvinceNameException;
import iwant.svr.exception.ImageProcessException;
import iwant.svr.exception.NoCategoryExistException;
import iwant.svr.exception.NoDistrictExistException;
import iwant.svr.exception.NoPptExistException;
import iwant.svr.exception.NoProjectExistException;
import iwant.svr.exception.NoProvinceExistException;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cactus.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/test-app-ds.xml", "/app-dao.xml", "/app-svr.xml" })
@Transactional
public class BaseSvrTest {

	@Resource
	ProjectSvr projectSvr;

	Project project0;

	@Resource
	CategorySvr categorySvr;

	@Resource
	ZoneSvr zoneSvr;

	@Resource
	PptSvr pptSvr;

	Category category;

	Country country;

	Province province;

	City city;

	District district;

	String filePath = "/Users/fire9/test/test.png";

	MainPpt mainPpt0;

	MainPpt mainPpt1;

	Ppt ppt0;

	Ppt ppt1;

	Slide slide0;

	Slide slide1;

	@Before
	public void init() throws NoCategoryExistException,
			NoDistrictExistException, DuplicateProvinceNameException,
			DuplicateCityNameException, NoProvinceExistException,
			DuplicateDistrictNameException, NoProjectExistException,
			NoPptExistException, ImageProcessException {
		category = new Category();
		category.setName("abc");
		this.categorySvr.createCategory(category);
		this.country = new Country();
		this.country.setName("abcc");
		this.zoneSvr.createCountry(country);
		this.province = new Province();
		this.province.setCountryid(this.country.getCountryid());
		this.province.setName("pro_abcc");
		this.zoneSvr.createProvince(province);
		this.city = new City();
		this.city.setProvinceid(this.province.getProvinceid());
		this.city.setName("city_pro_abcc");
		this.zoneSvr.createCity(city);
		this.district = new District();
		this.district.setCityid(this.city.getCityid());
		this.district.setName("district_city_pro_abcc");
		this.zoneSvr.createDistrict(district);
		project0 = new Project();
		project0.setActive_flag(ActiveType.ACTIVE.getValue());
		project0.setAddr("kakah");
		project0.setCatid(this.category.getCatid());
		project0.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		project0.setDescr("bbb");
		project0.setFans_num(10);
		project0.setName("project 1");
		project0.setOrder_flag(100);
		project0.setTel("125");
		project0.setDid(this.district.getDid());
		this.projectSvr.createProject(project0);
		// *************mainppt*************//
		// data 0
		mainPpt0 = new MainPpt();
		mainPpt0.setActive_flag(ActiveType.ACTIVE.getValue());
		mainPpt0.setCatid(this.category.getCatid());
		mainPpt0.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		mainPpt0.setName("ppt 0");
		mainPpt0.setPic_path("");
		mainPpt0.setProjectid(this.project0.getProjectid());
		this.pptSvr.createMainPpt(mainPpt0);
		// data 1
		mainPpt1 = new MainPpt();
		mainPpt1.setActive_flag(ActiveType.ACTIVE.getValue());
		mainPpt1.setCatid(this.category.getCatid());
		mainPpt1.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		mainPpt1.setName("ppt 1");
		mainPpt1.setPic_path("");
		mainPpt1.setProjectid(this.project0.getProjectid());
		this.pptSvr.createMainPpt(mainPpt1);
		// *************ppt*************//
		// data 0
		this.ppt0 = new Ppt();
		ppt0.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		ppt0.setName("ppt 0");
		ppt0.setPic_path("");
		ppt0.setProjectid(this.project0.getProjectid());
		this.pptSvr.createPpt(ppt0);
		// data 1
		this.ppt1 = new Ppt();
		ppt1.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		ppt1.setName("ppt 0");
		ppt1.setPic_path("");
		ppt1.setProjectid(this.project0.getProjectid());
		this.pptSvr.createPpt(ppt1);
		// *************slide*************//
		// data 0
		this.slide0 = new Slide();
		this.slide0.setDescr("slide desc 3");
		this.slide0.setPptid(this.ppt0.getPptid());
		this.slide0.setProjectid(this.ppt0.getProjectid());
		this.slide0.setSubtitle("sub 3");
		this.slide0.setTitle("title 3");
		this.slide0.setPic_path("");
		this.pptSvr.createSlide(this.slide0, new File(filePath), null);
		// data 1
		this.slide1 = new Slide();
		this.slide1.setDescr("slide desc 4");
		this.slide1.setPptid(this.ppt1.getPptid());
		this.slide1.setProjectid(this.ppt1.getProjectid());
		this.slide1.setSubtitle("sub 4");
		this.slide1.setTitle("title 4");
		this.slide1.setPic_path("");
		this.pptSvr.createSlide(this.slide1, new File(filePath), null);
	}

	@Test
	public void testBegin() {
	}
}
