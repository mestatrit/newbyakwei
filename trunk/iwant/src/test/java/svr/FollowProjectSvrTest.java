package svr;

import iwant.bean.FollowProject;
import iwant.bean.ProjectFans;
import iwant.svr.FollowProjectSvr;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FollowProjectSvrTest extends BaseSvrTest {

	FollowProject followProject0;

	FollowProject followProject1;

	FollowProject followProject2;

	@Resource
	private FollowProjectSvr followProjectSvr;

	@Before
	public void init() {
		long userid = 1;
		long projectid = 2;
		followProject0 = this.followProjectSvr.createFollow(userid, projectid);
		userid = 1;
		projectid = 5;
		followProject1 = this.followProjectSvr.createFollow(userid, projectid);
		userid = 2;
		projectid = 2;
		followProject2 = this.followProjectSvr.createFollow(userid, projectid);
	}

	private void assertData(FollowProject expected, FollowProject actual) {
		Assert.assertEquals(expected.getUserid(), actual.getUserid());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
	}

	@Test
	public void createFollow() {
		long userid = 1;
		long projectid = 2;
		FollowProject followProject = this.followProjectSvr
				.getFollowProjectByUseridAndProjectid(userid, projectid);
		Assert.assertNotNull(followProject);
		this.assertData(this.followProject0, followProject);
		FollowProject followProject3 = this.followProjectSvr.createFollow(
				userid, projectid);
		Assert
				.assertEquals(followProject.getSysid(), followProject3
						.getSysid());
	}

	@Test
	public void deleteFollow() {
		long userid = 1;
		long projectid = 2;
		this.followProjectSvr.deleteFollow(userid, projectid);
		FollowProject followProject = this.followProjectSvr
				.getFollowProjectByUseridAndProjectid(userid, projectid);
		Assert.assertNull(followProject);
	}

	@Test
	public void getFollowProjectListByUserid() {
		long userid = 1;
		List<FollowProject> list = this.followProjectSvr
				.getFollowProjectListByUserid(userid, 0, -1, false);
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void deleteFollowProjectByProjectid() {
		this.followProjectSvr.deleteFollowProjectByProjectid(2);
		List<ProjectFans> list = this.followProjectSvr
				.getProjectFansListByProjectid(2, 0, 100);
		Assert.assertEquals(0, list.size());
	}
}