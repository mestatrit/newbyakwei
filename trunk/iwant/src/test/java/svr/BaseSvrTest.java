package svr;

import iwant.bean.Category;
import iwant.bean.City;
import iwant.bean.Country;
import iwant.bean.District;
import iwant.bean.FollowProject;
import iwant.bean.MainPpt;
import iwant.bean.Notice;
import iwant.bean.Ppt;
import iwant.bean.PptTimeline;
import iwant.bean.Project;
import iwant.bean.Province;
import iwant.bean.Slide;
import iwant.bean.User;
import iwant.bean.UserNotice;
import iwant.bean.enumtype.ActiveType;
import iwant.bean.enumtype.GenderType;
import iwant.bean.enumtype.ReadFlagType;
import iwant.svr.CategorySvr;
import iwant.svr.FollowProjectSvr;
import iwant.svr.NoticeSvr;
import iwant.svr.PptSvr;
import iwant.svr.PptTimelineSvr;
import iwant.svr.ProjectSvr;
import iwant.svr.UserSvr;
import iwant.svr.ZoneSvr;
import iwant.svr.exception.DuplicateCityNameException;
import iwant.svr.exception.DuplicateDistrictNameException;
import iwant.svr.exception.DuplicateProvinceNameException;
import iwant.svr.exception.ImageProcessException;
import iwant.svr.exception.CategoryNotFoundException;
import iwant.svr.exception.DistrictNotFoundException;
import iwant.svr.exception.PptNotFoundException;
import iwant.svr.exception.ProjectNotFoundException;
import iwant.svr.exception.ProvinceNotFoundException;
import iwant.svr.exception.UserNotFoundException;

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
	UserSvr userSvr;

	@Resource
	NoticeSvr noticeSvr;

	@Resource
	ProjectSvr projectSvr;

	@Resource
	CategorySvr categorySvr;

	@Resource
	FollowProjectSvr followProjectSvr;

	@Resource
	ZoneSvr zoneSvr;

	@Resource
	PptTimelineSvr pptTimelineSvr;

	@Resource
	PptSvr pptSvr;

	Project project0;

	Project project1;

	Category category;

	Country country;

	Province province;

	City city;

	User user0;

	User user1;

	District district;

	String filePath = "c:/test/05.jpg";

	MainPpt mainPpt0;

	MainPpt mainPpt1;

	Ppt ppt0;

	Ppt ppt1;

	Slide slide0;

	Slide slide1;

	PptTimeline pptTimeline0;

	PptTimeline pptTimeline1;

	Category category1;

	Category category2;

	FollowProject followProject0;

	FollowProject followProject1;

	Notice notice0;

	Notice notice1;

	UserNotice userNotice0;

	UserNotice userNotice1;

	@Before
	public void init() throws CategoryNotFoundException,
			DistrictNotFoundException, DuplicateProvinceNameException,
			DuplicateCityNameException, ProvinceNotFoundException,
			DuplicateDistrictNameException, ProjectNotFoundException,
			PptNotFoundException, ImageProcessException, UserNotFoundException {
		// data 1
		category1 = new Category();
		category1.setName("akwei");
		this.categorySvr.createCategory(category1);
		// data 2
		category2 = new Category();
		category2.setName("akweiwei");
		this.categorySvr.createCategory(category2);
		this.user0 = new User();
		this.user0.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		this.user0.setDevice_token("a");
		this.user0.setEmail("");
		this.user0.setGender(GenderType.MALE.getValue());
		this.user0.setMobile("");
		this.user0.setName("");
		this.userSvr.createUser(this.user0);
		this.user1 = new User();
		this.user1.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		this.user1.setDevice_token("b");
		this.user1.setEmail("");
		this.user1.setGender(GenderType.MALE.getValue());
		this.user1.setMobile("");
		this.user1.setName("");
		this.userSvr.createUser(this.user1);
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
		project1 = new Project();
		project1.setActive_flag(ActiveType.ACTIVE.getValue());
		project1.setAddr("kakah");
		project1.setCatid(this.category.getCatid());
		project1.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		project1.setDescr("bbb");
		project1.setFans_num(10);
		project1.setName("project 1");
		project1.setOrder_flag(100);
		project1.setTel("125");
		project1.setDid(this.district.getDid());
		this.projectSvr.createProject(project1);
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
		this.slide0.setTitle("title 3");
		this.slide0.setPic_path("");
		this.pptSvr.createSlide(this.slide0, new File(filePath), null);
		// data 1
		this.slide1 = new Slide();
		this.slide1.setDescr("slide desc 4");
		this.slide1.setPptid(this.ppt1.getPptid());
		this.slide1.setProjectid(this.ppt1.getProjectid());
		this.slide1.setTitle("title 4");
		this.slide1.setPic_path("");
		this.pptSvr.createSlide(this.slide1, new File(filePath), null);
		// data 0
		this.pptTimeline0 = new PptTimeline();
		this.pptTimeline0.setCreatetime(DateUtil
				.createNoMillisecondTime(new Date()));
		this.pptTimeline0.setPptid(this.ppt0.getPptid());
		this.pptTimeline0.setRead_flag(ReadFlagType.NOTREAD.getValue());
		this.pptTimeline0.setReadtime(DateUtil
				.createNoMillisecondTime(new Date()));
		this.pptTimeline0.setUserid(this.user0.getUserid());
		this.pptTimelineSvr.createPptTimeline(this.pptTimeline0);
		// data 1
		this.pptTimeline1 = new PptTimeline();
		this.pptTimeline1.setCreatetime(DateUtil
				.createNoMillisecondTime(new Date()));
		this.pptTimeline1.setPptid(this.ppt0.getPptid());
		this.pptTimeline1.setRead_flag(ReadFlagType.NOTREAD.getValue());
		this.pptTimeline1.setReadtime(DateUtil
				.createNoMillisecondTime(new Date()));
		this.pptTimeline1.setUserid(this.user1.getUserid());
		this.pptTimelineSvr.createPptTimeline(this.pptTimeline1);
		// data 0
		this.notice0 = new Notice();
		this.notice0.setContent("notice 0");
		this.notice0.setProjectid(this.project0.getProjectid());
		this.notice0
				.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		this.noticeSvr.createNotice(this.notice0);
		// data 1
		this.notice1 = new Notice();
		this.notice1.setContent("notice 1");
		this.notice1.setProjectid(this.project1.getProjectid());
		this.notice1
				.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		this.noticeSvr.createNotice(this.notice1);
		// usernotice
		this.userNotice0 = this.noticeSvr.createUserNotice(this.notice0
				.getNoticeid(), this.user0.getUserid());
		this.userNotice1 = this.noticeSvr.createUserNotice(this.notice1
				.getNoticeid(), this.user1.getUserid());
		followProject0 = this.followProjectSvr.createFollow(this.user0
				.getUserid(), this.project0.getProjectid());
		followProject1 = this.followProjectSvr.createFollow(this.user1
				.getUserid(), this.project0.getProjectid());
	}

	@Test
	public void testBegin() {
	}
}
