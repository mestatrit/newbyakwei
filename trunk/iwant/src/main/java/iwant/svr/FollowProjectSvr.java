package iwant.svr;

import iwant.bean.FollowProject;
import iwant.bean.ProjectFans;
import iwant.svr.exception.FollowProjectAlreadyExistException;
import iwant.svr.exception.ProjectNotFoundException;
import iwant.svr.exception.UserNotFoundException;

import java.util.List;

public interface FollowProjectSvr {

	/**
	 * @param userid
	 *            订阅者id
	 * @param projectid
	 *            订阅的项目id
	 * @return 订阅成功的数据
	 * @throws UserNotFoundException
	 *             用户不存在
	 * @throws ProjectNotFoundException
	 *             项目不存在
	 * @throws FollowProjectAlreadyExistException
	 *             已经订阅过
	 */
	FollowProject createFollow(long userid, long projectid)
			throws UserNotFoundException, ProjectNotFoundException,
			FollowProjectAlreadyExistException;

	/**
	 * 取消订阅
	 * 
	 * @param userid
	 *            订阅者id
	 * @param projectid
	 *            订阅的项目id
	 */
	void deleteFollow(long userid, long projectid);

	/**
	 * 获取用户订阅的项目信息
	 * 
	 * @param userid
	 * @param projectid
	 * @return
	 */
	FollowProject getFollowProjectByUseridAndProjectid(long userid,
			long projectid);

	/**
	 * 获取用户订阅的 项目信息
	 * 
	 * @param userid
	 * @param begin
	 * @param size
	 * @param buildProject
	 * @return
	 */
	List<FollowProject> getFollowProjectListByUserid(long userid, int begin,
			int size, boolean buildProject);

	void deleteFollowProjectByProjectid(long projectid);

	List<ProjectFans> getProjectFansListByProjectid(long projectid, int begin,
			int size);
}