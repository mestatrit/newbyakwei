package iwant.svr.impl;

import halo.util.NumberUtil;
import iwant.bean.District;
import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.bean.ProjectidCreator;
import iwant.dao.ProjectDao;
import iwant.dao.ProjectRecycleDao;
import iwant.dao.ProjectSearchCdn;
import iwant.dao.ProjectidCreatorDao;
import iwant.svr.CategorySvr;
import iwant.svr.ProjectSvr;
import iwant.svr.ZoneSvr;
import iwant.svr.exception.CategoryNotFoundException;
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
	private CategorySvr categorySvr;

	@Override
	public void createProject(Project project)
			throws CategoryNotFoundException, DistrictNotFoundException {
		if (this.categorySvr.getCategory(project.getCatid()) == null) {
			throw new CategoryNotFoundException();
		}
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
	public void updateProject(Project project)
			throws CategoryNotFoundException, DistrictNotFoundException {
		if (this.categorySvr.getCategory(project.getCatid()) == null) {
			throw new CategoryNotFoundException();
		}
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
	public List<Project> getProjectListByCatidAndDid(int catid, int did,
			int begin, int size) {
		return this.projectDao.getListByCatidAndDid(catid, did, begin, size);
	}
}