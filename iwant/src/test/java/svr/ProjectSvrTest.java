package svr;

import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.bean.enumtype.ActiveType;
import iwant.svr.OptStatus;
import iwant.svr.ProjectSvr;

import java.io.File;
import java.util.Date;

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
public class ProjectSvrTest {

	@Resource
	private ProjectSvr projectSvr;

	Project project0;

	@Before
	public void init() {
		project0 = new Project();
		project0.setActive_flag(ActiveType.ACTIVE.getValue());
		project0.setAddr("kakah");
		project0.setCatid(5);
		project0.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		project0.setDescr("bbb");
		project0.setFans_num(10);
		project0.setName("project 1");
		project0.setOrder_flag(100);
		project0.setTel("125");
		project0.setPic_path("");
		this.projectSvr.createProject(project0, new File("d:/test/test7.jpg"));
	}

	@Test
	public void createProject() {
		Project project = new Project();
		project.setActive_flag(ActiveType.ACTIVE.getValue());
		project.setAddr("kakah");
		project.setCatid(5);
		project.setCreatetime(DataUtil.createNoMillisecondTime(new Date()));
		project.setDescr("bbb");
		project.setFans_num(10);
		project.setName("project 1");
		project.setOrder_flag(100);
		project.setTel("125");
		project.setPic_path("");
		OptStatus optStatus = this.projectSvr.createProject(project, new File(
				"d:/test/test7.jpg"));
		Assert.assertEquals(true, optStatus.isSuccess());
	}

	@Test
	public void updateProject() {
		Project project = this.projectSvr.getProject(this.project0
				.getProjectid());
		Assert.assertNotNull(project);
	}

	private void assertProjectData(Project expected, Project actual) {
		Assert.assertEquals(expected.getAddr(), actual.getAddr());
		Assert.assertEquals(expected.getDescr(), actual.getDescr());
		Assert.assertEquals(new Double(expected.getMarkerx()), new Double(
				actual.getMarkerx()));
		Assert.assertEquals(new Double(expected.getMarkery()), new Double(
				actual.getMarkery()));
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getOrder_flag(), actual.getOrder_flag());
		Assert.assertEquals(expected.getPic_path(), actual.getPic_path());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
		Assert.assertEquals(expected.getTel(), actual.getTel());
		Assert.assertEquals(expected.getActive_flag(), actual.getActive_flag());
		Assert.assertEquals(expected.getCatid(), actual.getCatid());
		Assert.assertEquals(expected.getCreatetime().getTime(), actual
				.getCreatetime().getTime());
		Assert.assertEquals(expected.getFans_num(), actual.getFans_num());
	}

	@Test
	public void getProject() {
		Project project = this.projectSvr.getProject(this.project0
				.getProjectid());
		this.assertProjectData(this.project0, project);
	}

	@Test
	public void deleteProject() {
		this.projectSvr.deleteProject(this.project0.getProjectid());
		Project project = this.projectSvr.getProject(this.project0
				.getProjectid());
		Assert.assertNull(project);
		ProjectRecycle projectRecycle = this.projectSvr
				.getProjectRecycle(this.project0.getProjectid());
		Assert.assertNotNull(projectRecycle);
		Assert.assertEquals(this.project0.getProjectid(), projectRecycle
				.getProjectid());
	}
}