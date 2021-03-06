package iwant.svr;

import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.dao.ProjectSearchCdn;
import iwant.svr.exception.DistrictNotFoundException;

import java.util.List;

public interface ProjectSvr {

	/**
	 * 创建project
	 * 
	 * @param project
	 */
	void createProject(Project project) throws DistrictNotFoundException;

	/**
	 * 更新project
	 * 
	 * @param project
	 */
	void updateProject(Project project) throws DistrictNotFoundException;

	void changeProjectOrder_flg(long projectid, long chg_projectid);

	void updateProjectOrder_flg(long projectid, int order_flg);

	/**
	 * 获得project
	 * 
	 * @param projectid
	 * @return
	 */
	Project getProject(long projectid);

	/**
	 * 删除project,并删除相关信息，由于project相关数据多，先删除project、ppt数据，并把id记入到projectRecycle。
	 * slide异步删除
	 * 
	 * @param projectid
	 * @return
	 */
	void deleteProject(long projectid);

	ProjectRecycle getProjectRecycle(long projectid);

	List<ProjectRecycle> getProjectRecycleList(int begin, int size);

	List<Project> getProjectListByCdn(ProjectSearchCdn projectSearchCdn,
			int begin, int size);

	List<Project> getProjectListByDid(int did, int begin, int size);

	void deleteProjectRecycle(ProjectRecycle projectRecycle);

	void tempupdate();
}