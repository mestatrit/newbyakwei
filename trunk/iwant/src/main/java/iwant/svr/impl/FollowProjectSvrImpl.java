package iwant.svr.impl;

import halo.util.NumberUtil;
import iwant.bean.FollowProject;
import iwant.bean.Project;
import iwant.bean.ProjectFans;
import iwant.bean.User;
import iwant.dao.FollowProjectDao;
import iwant.dao.ProjectFansDao;
import iwant.svr.FollowProjectSvr;
import iwant.svr.ProjectSvr;
import iwant.svr.UserSvr;
import iwant.svr.exception.FollowProjectAlreadyExistException;
import iwant.svr.exception.ProjectNotFoundException;
import iwant.svr.exception.UserNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("followProjectSvr")
public class FollowProjectSvrImpl implements FollowProjectSvr {

	@Autowired
	private FollowProjectDao followProjectDao;

	@Autowired
	private ProjectFansDao projectFansDao;

	@Autowired
	private UserSvr userSvr;

	@Autowired
	private ProjectSvr projectSvr;

	@Override
	public FollowProject createFollow(long userid, long projectid)
			throws UserNotFoundException, ProjectNotFoundException,
			FollowProjectAlreadyExistException {
		User user = this.userSvr.getUserByUserid(userid);
		if (user == null) {
			throw new UserNotFoundException();
		}
		Project project = this.projectSvr.getProject(projectid);
		if (project == null) {
			throw new ProjectNotFoundException();
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
		FollowProject o = this.followProjectDao.getByUseridAndProjectid(userid,
				projectid);
		if (o != null) {
			throw new FollowProjectAlreadyExistException(
					"already follow [ userid : " + userid + " , projectid : "
							+ projectid + " ]");
		}
		o = new FollowProject();
		o.setUserid(userid);
		o.setProjectid(projectid);
		o.setSysid(NumberUtil.getLong(this.followProjectDao.save(o)));
		return o;
	}

	@Override
	public void deleteFollow(long userid, long projectid) {
		this.followProjectDao.delete(null, "userid=? and projectid=?",
				new Object[] { userid, projectid });
		this.projectFansDao.delete("userid=? and projectid=?", new Object[] {
				userid, projectid });
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