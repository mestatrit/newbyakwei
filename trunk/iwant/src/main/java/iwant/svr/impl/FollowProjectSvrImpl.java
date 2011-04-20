package iwant.svr.impl;

import iwant.bean.FollowProject;
import iwant.bean.ProjectFans;
import iwant.dao.FollowProjectDao;
import iwant.dao.ProjectFansDao;
import iwant.svr.FollowProjectSvr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.NumberUtil;

public class FollowProjectSvrImpl implements FollowProjectSvr {

	@Autowired
	private FollowProjectDao followProjectDao;

	@Autowired
	private ProjectFansDao projectFansDao;

	@Override
	public FollowProject createFollow(long userid, long projectid) {
		FollowProject o = this.followProjectDao.getByUseridAndProjectid(userid,
				projectid);
		if (o == null) {
			o = new FollowProject();
			o.setUserid(userid);
			o.setProjectid(projectid);
			o.setSysid(NumberUtil.getLong(this.followProjectDao.save(o)));
		}
		ProjectFans projectFans = this.projectFansDao.getByUseridAndProjectid(
				userid, projectid);
		if (projectFans == null) {
			projectFans = new ProjectFans();
			projectFans.setUserid(userid);
			projectFans.setProjectid(projectid);
			projectFans.setSysid(NumberUtil.getLong(this.projectFansDao
					.save(projectFans)));
		}
		return o;
	}

	@Override
	public void deleteFollow(long userid, long projectid) {
		this.followProjectDao.delete(null, "userid=? and projectid=?",
				new Object[] { userid, projectid });
	}

	@Override
	public FollowProject getFollowProjectByUseridAndProjectid(long userid,
			long projectid) {
		return this.followProjectDao.getObject(null,
				"userid=? and projectid=?", new Object[] { userid, projectid });
	}

	@Override
	public List<FollowProject> getFollowProjectListByUserid(long userid,
			int begin, int size, boolean buildProject) {
		List<FollowProject> list = this.followProjectDao.getList(null,
				"userid=?", new Object[] { userid }, "sysid desc", begin, size);
		return list;
	}

	@Override
	public void deleteFollowProjectByProjectid(long projectid) {
		this.projectFansDao.deleteByProjectid(projectid);
		this.followProjectDao.deleteByProjectid(projectid);
	}

	@Override
	public List<ProjectFans> getProjectFansListByProjectid(long projectid,
			int begin, int size) {
		return this.projectFansDao.getListByProjectid(projectid, begin, size);
	}
}