package svr;

import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.bean.enumtype.ActiveType;
import iwant.svr.ProjectSvr;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cactus.util.DateUtil;

public class ProjectSvrTest extends BaseSvrTest {

	@Resource
	private ProjectSvr projectSvr;

	Project project0;

	@Before
	public void init() {
		project0 = new Project();
		project0.setActive_flag(ActiveType.ACTIVE.getValue());
		project0.setAddr("kakah");
		project0.setCatid(5);
		project0.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		project0.setDescr("bbb");
		project0.setFans_num(10);
		project0.setName("project 1");
		project0.setOrder_flag(100);
		project0.setTel("125");
		this.projectSvr.createProject(project0);
	}

	@Test
	public void createProject() {
		Project project = new Project();
		project.setActive_flag(ActiveType.ACTIVE.getValue());
		project.setAddr("kakah");
		project.setCatid(5);
		project.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		project.setDescr("bbb");
		project.setFans_num(10);
		project.setName("project 1");
		project.setOrder_flag(100);
		project.setTel("125");
		this.projectSvr.createProject(project);
		Project actual = this.projectSvr.getProject(project.getProjectid());
		this.assertProjectData(project, actual);
	}

	@Test
	public void updateProject() {
		Project project = this.projectSvr.getProject(this.project0
				.getProjectid());
		Assert.assertNotNull(project);
		project.setName("dsfsdf");
		project.setDescr("dfsdf");
		project.setAddr("rr9900");
		project.setCatid(99);
		this.projectSvr.updateProject(project);
		Project actual = this.projectSvr.getProject(project.getProjectid());
		this.assertProjectData(project, actual);
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