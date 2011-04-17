package iwant.svr.impl;

import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.bean.ProjectidCreator;
import iwant.dao.ProjectDao;
import iwant.dao.ProjectRecycleDao;
import iwant.dao.ProjectSearchCdn;
import iwant.dao.ProjectidCreatorDao;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.NumberUtil;

public class ProjectSvrImpl implements ProjectSvr {

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private ProjectRecycleDao projectRecycleDao;

	@Autowired
	private ProjectidCreatorDao projectidCreatorDao;

	@Autowired
	private PptSvr pptSvr;

	@Override
	public void createProject(Project project) {
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
		this.pptSvr.deletePptByProjectid(projectid);
		ProjectRecycle projectRecycle = new ProjectRecycle();
		projectRecycle.setProjectid(projectid);
		this.projectRecycleDao.save(projectRecycle);
		this.projectDao.deleteById(null, projectid);
	}

	@Override
	public Project getProject(long projectid) {
		return this.projectDao.getById(null, projectid);
	}

	@Override
	public void updateProject(Project project) {
		this.projectDao.update(project);
	}

	@Override
	public ProjectRecycle getProjectRecycle(long projectid) {
		return this.projectRecycleDao.getById(null, projectid);
	}

	@Override
	public List<Project> getProjectListByCdn(ProjectSearchCdn projectSearchCdn,
			int begin, int size) {
		return this.projectDao.getListByCdn(projectSearchCdn, begin, size);
	}

	public List<ProjectRecycle> getProjectRecycleList(int begin, int size) {
		return this.projectRecycleDao.getList(begin, size);
	}
}