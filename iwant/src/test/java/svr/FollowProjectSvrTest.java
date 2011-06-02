package svr;

import iwant.bean.FollowProject;
import iwant.bean.ProjectFans;
import iwant.svr.exception.FollowProjectAlreadyExistException;
import iwant.svr.exception.ProjectNotFoundException;
import iwant.svr.exception.UserNotFoundException;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FollowProjectSvrTest extends BaseSvrTest {

	private void assertData(FollowProject expected, FollowProject actual) {
		Assert.assertEquals(expected.getUserid(), actual.getUserid());
		Assert.assertEquals(expected.getProjectid(), actual.getProjectid());
	}

	@Test
	public void createFollow() throws UserNotFoundException,
			ProjectNotFoundException {
		FollowProject followProject = this.followProjectSvr
				.getFollowProjectByUseridAndProjectid(this.user0.getUserid(),
						this.project0.getProjectid());
		Assert.assertNotNull(followProject);
		this.assertData(this.followProject0, followProject);
		FollowProject followProject3;
		try {
			followProject3 = this.followProjectSvr.createFollow(this.user0
					.getUserid(), this.project0.getProjectid());
			Assert.assertEquals(followProject.getSysid(), followProject3
					.getSysid());
		}
		catch (FollowProjectAlreadyExistException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void deleteFollow() {
		this.followProjectSvr.deleteFollow(this.user0.getUserid(),
				this.project0.getProjectid());
		FollowProject followProject = this.followProjectSvr
				.getFollowProjectByUseridAndProjectid(this.user0.getUserid(),
						this.project0.getProjectid());
		Assert.assertNull(followProject);
	}

	@Test
	public void deleteFollowProjectByProjectid() {
		this.followProjectSvr.deleteFollowProjectByProjectid(this.project0
				.getProjectid());
		List<ProjectFans> list = this.followProjectSvr
				.getProjectFansListByProjectid(2, 0, 100);
		Assert.assertEquals(0, list.size());
	}
}