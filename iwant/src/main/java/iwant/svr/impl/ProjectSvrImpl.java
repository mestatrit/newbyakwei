package iwant.svr.impl;

import halo.util.NumberUtil;
import iwant.bean.District;
import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.bean.ProjectidCreator;
import iwant.bean.Slide;
import iwant.dao.ProjectDao;
import iwant.dao.ProjectRecycleDao;
import iwant.dao.ProjectSearchCdn;
import iwant.dao.ProjectidCreatorDao;
import iwant.dao.SlideDao;
import iwant.svr.ProjectSvr;
import iwant.svr.ZoneSvr;
import iwant.svr.exception.DistrictNotFoundException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("projectSvr")
public class ProjectSvrImpl implements ProjectSvr {

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectRecycleDao projectRecycleDao;

	@Autowired
	private ProjectidCreatorDao projectidCreatorDao;

	@Autowired
	private ZoneSvr zoneSvr;

	@Autowired
	private SlideDao slideDao;

	@Override
	public void createProject(Project project) throws DistrictNotFoundException {
		District district = this.zoneSvr.getDistrict(project.getDid());
		if (district == null) {
			throw new DistrictNotFoundException();
		}
		project.setCityid(district.getCityid());
		long projectid = NumberUtil.getLong(this.projectidCreatorDao
				.save(new ProjectidCreator()));
		project.setProjectid(projectid);
		this.projectDao.save(project);
	}

	@Override
	public void deleteProject(long projectid) {
		Project project = this.getProject(projectid);
		if (project == null) {
			return;
		}
		ProjectRecycle projectRecycle = new ProjectRecycle();
		projectRecycle.setProjectid(projectid);
		this.projectRecycleDao.save(projectRecycle);
		this.projectDao.deleteById(projectid);
	}

	@Override
	public Project getProject(long projectid) {
		return this.projectDao.getById(projectid);
	}

	@Override
	public void updateProject(Project project) throws DistrictNotFoundException {
		District district = this.zoneSvr.getDistrict(project.getDid());
		if (district == null) {
			throw new DistrictNotFoundException();
		}
		project.setCityid(district.getCityid());
		this.projectDao.update(project);
	}

	@Override
	public ProjectRecycle getProjectRecycle(long projectid) {
		return this.projectRecycleDao.getById(projectid);
	}

	@Override
	public List<Project> getProjectListByCdn(ProjectSearchCdn projectSearchCdn,
			int begin, int size) {
		return this.projectDao.getListByCdn(projectSearchCdn, begin, size);
	}

	public List<ProjectRecycle> getProjectRecycleList(int begin, int size) {
		return this.projectRecycleDao.getList(begin, size);
	}

	@Override
	public void deleteProjectRecycle(ProjectRecycle projectRecycle) {
		this.projectRecycleDao.deleteById(projectRecycle.getProjectid());
	}

	@Override
	public List<Project> getProjectListByDid(int did, int begin, int size) {
		return this.projectDao.getListByDid(did, begin, size);
	}

	@Override
	public void changeProjectOrder_flg(long projectid, long chg_projectid) {
		Project project = this.getProject(projectid);
		Project chg_Project = this.getProject(chg_projectid);
		if (project != null && chg_Project != null) {
			this.projectDao.updateOrder_flg(chg_projectid,
					project.getOrder_flg());
			this.projectDao.updateOrder_flg(projectid,
					chg_Project.getOrder_flg());
		}
	}

	@Override
	public void tempupdate() {
		int begin = 0;
		int size = 100;
		List<Project> list = this.projectDao.getList(null, null,
				"projectid desc", begin, size);
		while (!list.isEmpty()) {
			for (Project project : list) {
				List<Slide> slist = this.slideDao.getListByProjectid(
						project.getProjectid(), 0, 1);
				if (!slist.isEmpty()) {
					project.setPath(slist.get(0).getPic_path());
					this.projectDao.update(project);
				}
			}
			begin = begin + size;
			list = this.projectDao.getList(null, null, "projectid desc", begin,
					size);
		}
	}

	@Override
	public void updateProjectOrder_flg(long projectid, int order_flg) {
		this.projectDao.updateOrder_flg(projectid, order_flg);
	}
}