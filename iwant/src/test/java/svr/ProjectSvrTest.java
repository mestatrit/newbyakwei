package svr;

import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.bean.enumtype.ActiveType;
import iwant.svr.exception.NoCategoryExistException;
import iwant.svr.exception.NoDistrictExistException;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import cactus.util.DateUtil;

public class ProjectSvrTest extends BaseSvrTest {

	@Test
	public void createProject() throws NoCategoryExistException,
			NoDistrictExistException {
		Project project = new Project();
		project.setActive_flag(ActiveType.ACTIVE.getValue());
		project.setAddr("kakah");
		project.setCatid(this.category.getCatid());
		project.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		project.setDescr("bbb");
		project.setFans_num(10);
		project.setName("project 1");
		project.setOrder_flag(100);
		project.setTel("125");
		project.setDid(this.district.getDid());
		this.projectSvr.createProject(project);
		Project actual = this.projectSvr.getProject(project.getProjectid());
		this.assertProjectData(project, actual);
	}

	@Test
	public void updateProject() throws NoCategoryExistException,
			NoDistrictExistException {
		Project project = this.projectSvr.getProject(this.project0
				.getProjectid());
		Assert.assertNotNull(project);
		project.setName("dsfsdf");
		project.setDescr("dfsdf");
		project.setAddr("rr9900");
		project.setCatid(this.category.getCatid());
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
		Assert.assertEquals(expected.getDid(), actual.getDid());
		Assert.assertEquals(expected.getCityid(), actual.getCityid());
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