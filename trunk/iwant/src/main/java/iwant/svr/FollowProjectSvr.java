package iwant.svr;

import iwant.bean.FollowProject;
import iwant.bean.ProjectFans;

import java.util.List;

public interface FollowProjectSvr {

	/**
	 * 用户订阅项目，如果已经订阅过，将不会创建订阅数据，返回已经存在的数据
	 * 
	 * @param userid
	 *            订阅者id
	 * @param projectid
	 *            订阅的项目id
	 * @return 对象实体:订阅成功
	 */
	FollowProject createFollow(long userid, long projectid);

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