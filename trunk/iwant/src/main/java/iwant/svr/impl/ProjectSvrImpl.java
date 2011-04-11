package iwant.svr.impl;

import iwant.bean.Project;
import iwant.bean.ProjectRecycle;
import iwant.bean.ProjectidCreator;
import iwant.dao.ProjectDao;
import iwant.dao.ProjectRecycleDao;
import iwant.dao.ProjectidCreatorDao;
import iwant.svr.OptStatus;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;
import iwant.util.ErrorCode;
import iwant.util.FileCnf;
import iwant.util.PicUtil;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.NumberUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image2.ImageProcessor;
import com.hk.frame.util.image2.ImgFileInfo;

public class ProjectSvrImpl implements ProjectSvr {

	@Autowired
	private ProjectDao projectDao;

	private FileCnf fileCnf;

	@Autowired
	private ProjectRecycleDao projectRecycleDao;

	@Autowired
	private ProjectidCreatorDao projectidCreatorDao;

	@Autowired
	private PptSvr pptSvr;

	private Log log = LogFactory.getLog(ProjectSvrImpl.class);

	public void setFileCnf(FileCnf fileCnf) {
		this.fileCnf = fileCnf;
	}

	@Override
	public OptStatus createProject(Project project, File imgFile) {
		OptStatus optStatus = new OptStatus();
		this.processImage(imgFile, optStatus);
		if (!optStatus.isSuccess()) {
			return optStatus;
		}
		long projectid = NumberUtil.getLong(this.projectidCreatorDao
				.save(new ProjectidCreator()));
		project.setProjectid(projectid);
		this.projectDao.save(project);
		optStatus.setSuccess(true);
		optStatus.setError_code(ErrorCode.success);
		return optStatus;
	}

	@Override
	public boolean deleteProject(long projectid) {
		Project project = this.getProject(projectid);
		if (project == null) {
			return true;
		}
		this.pptSvr.deletePptByProjectid(projectid);
		ProjectRecycle projectRecycle = new ProjectRecycle();
		projectRecycle.setProjectid(projectid);
		this.projectRecycleDao.save(projectRecycle);
		this.deleteOldPic(project.getPic_path());
		this.projectDao.deleteById(null, projectid);
		return true;
	}

	@Override
	public Project getProject(long projectid) {
		return this.projectDao.getById(null, projectid);
	}

	@Override
	public OptStatus updateProject(Project project, File imgFile) {
		String oldPath = project.getPic_path();
		OptStatus optStatus = new OptStatus();
		this.processImage(imgFile, optStatus);
		if (!optStatus.isSuccess()) {
			return optStatus;
		}
		this.projectDao.update(project);
		optStatus.setSuccess(true);
		optStatus.setError_code(ErrorCode.success);
		this.deleteOldPic(oldPath);
		return optStatus;
	}

	/**
	 * 删除老图片
	 * 
	 * @param oldPath
	 */
	private void deleteOldPic(String oldPath) {
		if (DataUtil.isNotEmpty(oldPath)) {
			DataUtil.deleteAllFile(fileCnf.getFilePath(oldPath));
		}
	}

	@Override
	public ProjectRecycle getProjectRecycle(long projectid) {
		return this.projectRecycleDao.getById(null, projectid);
	}

	private void processImage(File imgFile, OptStatus optStatus) {
		ImgFileInfo imgFileInfo = ImgFileInfo.getImageFileInfo(imgFile);
		if (imgFileInfo != null) {
			ImageProcessor imageProcessor = new ImageProcessor(imgFileInfo);
			imageProcessor.setMaxWidth(960);
			String name = FileCnf.createFileName();
			String dbPath = fileCnf.getFileSaveToDbPath(name);
			String filePath = fileCnf.getFilePath(dbPath);
			try {
				imageProcessor.makeImage(filePath, PicUtil.PROJECT_PIC1_NAME,
						false, 70);
				imageProcessor.makeImage(filePath, PicUtil.PROJECT_PIC2_NAME,
						false, 960);
				optStatus.setSuccess(true);
			}
			catch (ImageException e) {
				log.error("createProject image error : " + e);
				optStatus.setSuccess(false);
				optStatus.setError_code(ErrorCode.err_image);
			}
		}
	}
}