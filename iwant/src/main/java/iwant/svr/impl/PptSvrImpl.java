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
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;
import iwant.svr.exception.ImageProcessException;
import iwant.svr.exception.PptNotFoundException;
import iwant.svr.exception.ProjectNotFoundException;
import iwant.svr.statusenum.UpdateSldePic0Result;
import iwant.util.FileCnf;
import iwant.util.PicUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dev3g.cactus.util.DataUtil;
import com.dev3g.cactus.util.FileUtil;
import com.dev3g.cactus.util.NumberUtil;
import com.dev3g.cactus.util.jmagick.ImageException;
import com.dev3g.cactus.util.jmagick.ImageProcessor;
import com.dev3g.cactus.util.jmagick.ImgFileInfo;
import com.dev3g.cactus.util.jmagick.PicRect;

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
	public void createMainPpt(MainPpt mainPpt) throws ProjectNotFoundException {
		Project project = this.projectSvr.getProject(mainPpt.getProjectid());
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		mainPpt.setCityid(project.getCityid());
		mainPpt.setDid(project.getDid());
		long pptid = NumberUtil.getLong(this.pptidCreatorDao
				.save(new PptidCreator()));
		mainPpt.setPptid(pptid);
		mainPpt.setOrder_flag(mainPpt.getPptid());
		this.mainPptDao.save(mainPpt);
	}

	@Override
	public void createPpt(Ppt ppt) throws ProjectNotFoundException {
		Project project = this.projectSvr.getProject(ppt.getProjectid());
		if (project == null) {
			throw new ProjectNotFoundException();
		}
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
			try {
				this.updatePpt(ppt);
			}
			catch (ProjectNotFoundException e1) {
			}
			MainPpt mainPpt = this.getMainPpt(slide.getPptid());
			if (mainPpt != null) {
				mainPpt.setPic_path(slide.getPic_path());
				try {
					this.updateMainPpt(mainPpt);
				}
				catch (ProjectNotFoundException e) {
				}
			}
		}
	}

	@Override
	public void deletePpt(long pptid) {
		List<Slide> list = this.slideDao.getListByPptidOrdered(pptid);
		for (Slide slide : list) {
			this.deleteSlide(slide);
		}
		this.pptQueueDao.deleteById(null, pptid);
		this.pptDao.deleteById(null, pptid);
	}

	@Override
	public void deleteMainPpt(long pptid) {
		List<Slide> list = this.slideDao.getListByPptidOrdered(pptid);
		for (Slide slide : list) {
			this.deleteSlide(slide);
		}
		this.mainPptDao.deleteById(null, pptid);
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
	public List<MainPpt> getMainPptListOrderedByDid(int catid, int did,
			boolean buildProject, int begin, int size) {
		List<MainPpt> list = this.mainPptDao.getListOrderedByCatidAndDid(catid,
				did, begin, size);
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
	public void updatePpt(Ppt ppt) throws ProjectNotFoundException {
		Project project = this.projectSvr.getProject(ppt.getProjectid());
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		this.pptDao.update(ppt);
	}

	@Override
	public void createSlide(Slide slide, File imgFile, PicRect picRect)
			throws PptNotFoundException, ImageProcessException {
		Ppt ppt = this.getPpt(slide.getPptid());
		MainPpt mainPpt = this.getMainPpt(slide.getPptid());
		if (ppt != null) {
			slide.setProjectid(ppt.getProjectid());
		}
		if (mainPpt != null) {
			slide.setProjectid(mainPpt.getProjectid());
		}
		if (mainPpt == null && ppt == null) {
			throw new PptNotFoundException();
		}
		this.processSlideImage(slide, imgFile, picRect);
		int count = this.slideDao.countByPptid(slide.getPptid());
		slide.setOrder_flag(count + 1);
		long slideid = NumberUtil.getLong(this.slideDao.save(slide));
		slide.setSlideid(slideid);
		if (ppt != null && DataUtil.isEmpty(ppt.getPic_path())) {
			ppt.setPic_path(slide.getPic_path());
			try {
				this.updatePpt(ppt);
			}
			catch (ProjectNotFoundException e) {
			}
		}
		if (mainPpt != null && DataUtil.isEmpty(mainPpt.getPic_path())) {
			mainPpt.setPic_path(slide.getPic_path());
			try {
				this.updateMainPpt(mainPpt);
			}
			catch (ProjectNotFoundException e) {
			}
		}
	}

	@Override
	public void updateSlide(Slide slide, File imgFile, PicRect picRect)
			throws PptNotFoundException, ImageProcessException {
		Ppt ppt = this.getPpt(slide.getPptid());
		MainPpt mainPpt = this.getMainPpt(slide.getPptid());
		if (mainPpt == null && ppt == null) {
			throw new PptNotFoundException();
		}
		String oldPic = slide.getPic_path();
		boolean chgpptpic = false;
		if (ppt != null) {
			if (ppt.getPic_path().equals(slide.getPic_path())
					|| DataUtil.isEmpty(ppt.getPic_path())) {
				chgpptpic = true;
			}
		}
		if (mainPpt != null) {
			if (mainPpt.getPic_path().equals(slide.getPic_path())
					|| DataUtil.isEmpty(mainPpt.getPic_path())) {
				chgpptpic = true;
			}
		}
		if (imgFile != null) {
			this.processSlideImage(slide, imgFile, picRect);
			this.deleteSlideOldPic(oldPic);
		}
		this.slideDao.update(slide);
		if (chgpptpic) {
			if (ppt != null) {
				ppt.setPic_path(slide.getPic_path());
				try {
					this.updatePpt(ppt);
				}
				catch (ProjectNotFoundException e) {
				}
			}
			if (mainPpt != null) {
				mainPpt.setPic_path(slide.getPic_path());
				try {
					this.updateMainPpt(mainPpt);
				}
				catch (ProjectNotFoundException e) {
				}
			}
		}
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
			FileUtil.deleteAllFile(fileCnf.getFilePath(oldPath));
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
	public void updateMainPpt(MainPpt mainPpt) throws ProjectNotFoundException {
		Project project = this.projectSvr.getProject(mainPpt.getProjectid());
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		mainPpt.setCityid(project.getCityid());
		mainPpt.setDid(project.getDid());
		this.mainPptDao.update(mainPpt);
	}

	private void processSlideImage(Slide slide, File imgFile, PicRect picRect)
			throws ImageProcessException {
		ImgFileInfo imgFileInfo = ImgFileInfo.getImageFileInfo(imgFile);
		if (imgFileInfo != null) {
			ImageProcessor imageProcessor = new ImageProcessor(imgFileInfo);
			imageProcessor.setMaxWidth(960);
			String name = FileCnf.createFileName();
			String dbPath = fileCnf.getFileSaveToDbPath(name);
			String filePath = fileCnf.getFilePath(dbPath);
			slide.setPic_path(dbPath);
			try {
				if (picRect == null) {
					imageProcessor.makeImage(filePath, PicUtil.SLIDE_PIC1_NAME,
							false, 70);
				}
				else {
					imageProcessor.cutImage(filePath, PicUtil.SLIDE_PIC1_NAME,
							picRect);
				}
				imageProcessor.makeImage(filePath, PicUtil.SLIDE_PIC2_NAME,
						false, 960);
			}
			catch (ImageException e) {
				log.error("processSlideImage image error : " + e);
				throw new ImageProcessException();
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

	@Override
	public int countSlideByPptid(long pptid) {
		return this.slideDao.countByPptid(pptid);
	}

	@Override
	public boolean isCanAddSlide(long pptid) {
		int count = this.countSlideByPptid(pptid);
		if (count >= 50) {
			return false;
		}
		return true;
	}

	@Override
	public UpdateSldePic0Result updateSldePic1(long slideid, PicRect picRect) {
		Slide slide = this.getSlide(slideid);
		if (slide == null) {
			return UpdateSldePic0Result.FILE_NOT_FOUND;
		}
		String realPath = this.fileCnf.getFilePath(slide.getPic_path());
		File file = new File(realPath + PicUtil.SLIDE_PIC2_NAME);
		ImgFileInfo imgFileInfo = ImgFileInfo.getImageFileInfo(file);
		if (imgFileInfo == null) {
			return UpdateSldePic0Result.FILE_NOT_FOUND;
		}
		ImageProcessor imageProcessor = new ImageProcessor(imgFileInfo);
		try {
			imageProcessor.cutImage(realPath, PicUtil.SLIDE_PIC1_NAME, picRect);
			imageProcessor.makeImage(realPath, PicUtil.SLIDE_PIC1_NAME, true,
					70);
			return UpdateSldePic0Result.SUCCESS;
		}
		catch (ImageException e) {
			return UpdateSldePic0Result.IMAGE_PROCESS_ERR;
		}
	}

	@Override
	public void updateMainPptCityidAndDidByProjectid(long projectid,
			int cityid, int did) {
		this.mainPptDao.updateBySQL("cityid=?,did=?", "projectid=?",
				new Object[] { cityid, did, projectid });
	}
}