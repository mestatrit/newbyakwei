package iwant.svr.impl;

import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.PptQueue;
import iwant.bean.PptidCreator;
import iwant.bean.Project;
import iwant.bean.Slide;
import iwant.bean.enumtype.ActiveType;
import iwant.dao.MainPptDao;
import iwant.dao.MainPptSearchCdn;
import iwant.dao.PptDao;
import iwant.dao.PptQueueDao;
import iwant.dao.PptSearchCdn;
import iwant.dao.PptidCreatorDao;
import iwant.dao.SlideDao;
import iwant.svr.OptStatus;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;
import iwant.util.ErrorCode;
import iwant.util.FileCnf;
import iwant.util.PicUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.NumberUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image2.ImageProcessor;
import com.hk.frame.util.image2.ImgFileInfo;

public class PptSvrImpl implements PptSvr {

	@Autowired
	private PptDao pptDao;

	@Autowired
	private MainPptDao mainPptDao;

	@Autowired
	private SlideDao slideDao;

	@Autowired
	private PptidCreatorDao pptidCreatorDao;

	@Autowired
	private PptQueueDao pptQueueDao;

	@Autowired
	private ProjectSvr projectSvr;

	private FileCnf fileCnf;

	private Log log = LogFactory.getLog(PptSvrImpl.class);

	public void setFileCnf(FileCnf fileCnf) {
		this.fileCnf = fileCnf;
	}

	@Override
	public void createMainPpt(MainPpt mainPpt) {
		Ppt ppt = new Ppt();
		ppt.setCreatetime(mainPpt.getCreatetime());
		ppt.setName(mainPpt.getName());
		ppt.setPic_path(mainPpt.getPic_path());
		ppt.setProjectid(mainPpt.getProjectid());
		this.createPpt(ppt);
		mainPpt.setPptid(ppt.getPptid());
		mainPpt.setOrder_flag(mainPpt.getPptid());
		this.mainPptDao.save(mainPpt);
	}

	@Override
	public void createPpt(Ppt ppt) {
		long pptid = NumberUtil.getLong(this.pptidCreatorDao
				.save(new PptidCreator()));
		ppt.setPptid(pptid);
		this.pptDao.save(ppt);
		PptQueue pptQueue = new PptQueue();
		pptQueue.setPptid(ppt.getPptid());
		pptQueue.setProjectid(ppt.getProjectid());
		this.pptQueueDao.save(pptQueue);
	}

	@Override
	public void deleteSlide(Slide slide) {
		this.deleteSlideOldPic(slide.getPic_path());
		this.slideDao.delete(slide);
		Ppt ppt = this.getPpt(slide.getPptid());
		if (ppt != null && slide.getPic_path().equals(ppt.getPic_path())) {
			List<Slide> list = this.slideDao.getListByPptidOrdered(slide
					.getPptid());
			if (list.isEmpty()) {
				return;
			}
			ppt.setPic_path(list.get(0).getPic_path());
			this.updatePpt(ppt);
			MainPpt mainPpt = this.getMainPpt(slide.getPptid());
			if (mainPpt != null) {
				mainPpt.setPic_path(slide.getPic_path());
				this.updateMainPpt(mainPpt);
			}
		}
	}

	@Override
	public void deletePpt(long pptid) {
		this.mainPptDao.deleteById(null, pptid);
		List<Slide> list = this.slideDao.getListByPptidOrdered(pptid);
		for (Slide slide : list) {
			this.deleteSlide(slide);
		}
		this.pptQueueDao.deleteById(null, pptid);
		this.pptDao.deleteById(null, pptid);
	}

	@Override
	public List<Slide> getSlideListByProjectid(long projectid, int begin,
			int size) {
		return this.slideDao.getListByProjectid(projectid, begin, size);
	}

	@Override
	public void deletePptByProjectid(long projectid) {
		this.pptDao.deleteByProjectid(projectid);
		this.mainPptDao.deleteByProjectid(projectid);
	}

	@Override
	public MainPpt getMainPpt(long pptid) {
		return this.mainPptDao.getById(null, pptid);
	}

	@Override
	public MainPpt getMainPptByProjectid(long projectid) {
		return this.mainPptDao.getByProjectid(projectid);
	}

	@Override
	public List<MainPpt> getMainPptListOrderedByCatid(int catid,
			boolean buildProject, int begin, int size) {
		List<MainPpt> list = this.mainPptDao.getListOrderedByCatid(catid,
				begin, size);
		if (buildProject) {
			this.buildProject(list);
		}
		return list;
	}

	@Override
	public List<MainPpt> getMainPptListOrderedByCdn(
			MainPptSearchCdn mainPptSearchCdn, boolean buildProject, int begin,
			int size) {
		List<MainPpt> list = this.mainPptDao.getListOrderedByCdn(
				mainPptSearchCdn, begin, size);
		if (buildProject) {
			this.buildProject(list);
		}
		return list;
	}

	private void buildProject(List<MainPpt> list) {
		List<Long> idList = new ArrayList<Long>();
		for (MainPpt o : list) {
			idList.add(o.getProjectid());
		}
		Map<Long, Project> map = this.projectSvr.getProjectMap(idList);
		for (MainPpt o : list) {
			o.setProject(map.get(o.getProjectid()));
		}
	}

	@Override
	public int countMainPptByCdn(MainPptSearchCdn mainPptSearchCdn) {
		return this.mainPptDao.countByCdn(mainPptSearchCdn);
	}

	@Override
	public Ppt getPpt(long pptid) {
		return this.pptDao.getById(null, pptid);
	}

	@Override
	public List<Slide> getSlideListByPptidOrdered(long pptid) {
		return this.slideDao.getListByPptidOrdered(pptid);
	}

	@Override
	public void updatePpt(Ppt ppt) {
		this.pptDao.update(ppt);
		MainPpt mainPpt = this.getMainPpt(ppt.getPptid());
		if (mainPpt != null) {
			mainPpt.setCreatetime(ppt.getCreatetime());
			mainPpt.setName(ppt.getName());
			mainPpt.setPic_path(ppt.getPic_path());
			mainPpt.setProjectid(ppt.getProjectid());
			this.mainPptDao.update(mainPpt);
		}
	}

	@Override
	public OptStatus createSlide(Slide slide, File imgFile) {
		OptStatus optStatus = new OptStatus();
		this.processSlideImage(slide, imgFile, optStatus);
		if (!optStatus.isSuccess()) {
			return optStatus;
		}
		int count = this.slideDao.countByPptid(slide.getPptid());
		slide.setOrder_flag(count + 1);
		Ppt ppt = this.getPpt(slide.getPptid());
		slide.setProjectid(ppt.getProjectid());
		long slideid = NumberUtil.getLong(this.slideDao.save(slide));
		slide.setSlideid(slideid);
		optStatus.setSuccess(true);
		optStatus.setError_code(ErrorCode.success);
		if (DataUtil.isEmpty(ppt.getPic_path())) {
			ppt.setPic_path(slide.getPic_path());
			this.updatePpt(ppt);
			MainPpt mainPpt = this.getMainPpt(slide.getPptid());
			if (mainPpt != null) {
				mainPpt.setPic_path(slide.getPic_path());
				this.updateMainPpt(mainPpt);
			}
		}
		return optStatus;
	}

	@Override
	public OptStatus updateSlide(Slide slide, File imgFile) {
		OptStatus optStatus = new OptStatus();
		Ppt ppt = this.getPpt(slide.getPptid());
		boolean chgpptpic = false;
		if (ppt.getPic_path().equals(slide.getPic_path())
				|| DataUtil.isEmpty(ppt.getPic_path())) {
			chgpptpic = true;
		}
		if (imgFile != null) {
			this.processSlideImage(slide, imgFile, optStatus);
			if (!optStatus.isSuccess()) {
				return optStatus;
			}
			this.deleteSlideOldPic(slide.getPic_path());
		}
		this.slideDao.update(slide);
		optStatus.setSuccess(true);
		optStatus.setError_code(ErrorCode.success);
		if (chgpptpic) {
			ppt.setPic_path(slide.getPic_path());
			this.updatePpt(ppt);
			MainPpt mainPpt = this.getMainPpt(slide.getPptid());
			if (mainPpt != null) {
				mainPpt.setPic_path(slide.getPic_path());
				this.updateMainPpt(mainPpt);
			}
		}
		return optStatus;
	}

	@Override
	public Map<Long, Ppt> getPptMapInId(List<Long> idList) {
		return this.pptDao.getPptMapInId(idList);
	}

	@Override
	public Slide getSlide(long slideid) {
		return this.slideDao.getBySlideid(slideid);
	}

	/**
	 * 删除老图片
	 * 
	 * @param oldPath
	 */
	private void deleteSlideOldPic(String oldPath) {
		if (DataUtil.isNotEmpty(oldPath)) {
			DataUtil.deleteAllFile(fileCnf.getFilePath(oldPath));
		}
	}

	@Override
	public List<Ppt> getPptListByProjectid(long projectid, int begin, int size) {
		return this.pptDao.getListByProjectid(projectid, begin, size);
	}

	@Override
	public List<Ppt> getPptListByCdn(long projectid, PptSearchCdn pptSearchCdn,
			int begin, int size) {
		return this.pptDao.getListByCdn(projectid, pptSearchCdn, begin, size);
	}

	@Override
	public void updateMainPpt(MainPpt mainPpt) {
		this.mainPptDao.update(mainPpt);
	}

	private void processSlideImage(Slide slide, File imgFile,
			OptStatus optStatus) {
		ImgFileInfo imgFileInfo = ImgFileInfo.getImageFileInfo(imgFile);
		if (imgFileInfo != null) {
			ImageProcessor imageProcessor = new ImageProcessor(imgFileInfo);
			imageProcessor.setMaxWidth(960);
			String name = FileCnf.createFileName();
			String dbPath = fileCnf.getFileSaveToDbPath(name);
			String filePath = fileCnf.getFilePath(dbPath);
			slide.setPic_path(dbPath);
			try {
				imageProcessor.makeImage(filePath, PicUtil.SLIDE_PIC1_NAME,
						false, 70);
				imageProcessor.makeImage(filePath, PicUtil.SLIDE_PIC2_NAME,
						false, 960);
				optStatus.setSuccess(true);
			}
			catch (ImageException e) {
				log.error("processSlideImage image error : " + e);
				optStatus.setSuccess(false);
				optStatus.setError_code(ErrorCode.err_image);
			}
		}
	}

	@Override
	public List<PptQueue> getPptQueueList(int begin, int size) {
		return this.pptQueueDao.getList(begin, size);
	}

	@Override
	public void deletePptQueue(PptQueue pptQueue) {
		this.pptQueueDao.delete(pptQueue);
	}

	@Override
	public boolean changePptToMainPpt(long pptid) {
		Ppt ppt = this.getPpt(pptid);
		MainPpt mainPpt = this.getMainPptByProjectid(ppt.getProjectid());
		if (mainPpt != null) {
			return false;
		}
		Project project = this.projectSvr.getProject(ppt.getProjectid());
		mainPpt = new MainPpt();
		mainPpt.setPptid(ppt.getPptid());
		mainPpt.setActive_flag(ActiveType.ACTIVE.getValue());
		mainPpt.setCatid(project.getCatid());
		mainPpt.setCreatetime(ppt.getCreatetime());
		mainPpt.setName(ppt.getName());
		mainPpt.setOrder_flag(ppt.getPptid());
		mainPpt.setPic_path(ppt.getPic_path());
		mainPpt.setProjectid(ppt.getProjectid());
		this.mainPptDao.save(mainPpt);
		return true;
	}
}