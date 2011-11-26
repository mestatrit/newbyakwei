package iwant.svr.impl;

import halo.util.DataUtil;
import halo.util.FileUtil;
import halo.util.NumberUtil;
import halo.util.image.ImageParam;
import halo.util.image.ImageRect;
import halo.util.image.ImageShaper;
import halo.util.image.ImageShaperFactory;
import halo.util.image.ImageSize;
import halo.util.image.ImageSizeMaker;
import iwant.bean.Project;
import iwant.bean.Slide;
import iwant.dao.ProjectDao;
import iwant.dao.SlideDao;
import iwant.svr.PptSvr;
import iwant.svr.exception.ImageProcessException;
import iwant.svr.statusenum.UpdateSldePic0Result;
import iwant.util.FileCnf;
import iwant.util.PicPoint;
import iwant.util.PicUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("pptSvr")
public class PptSvrImpl implements PptSvr {

	@Autowired
	private SlideDao slideDao;

	@Autowired
	private FileCnf fileCnf;

	@Autowired
	private ProjectDao projectDao;

	private Log log = LogFactory.getLog(PptSvrImpl.class);

	public void setFileCnf(FileCnf fileCnf) {
		this.fileCnf = fileCnf;
	}

	@Override
	public void deleteSlide(Slide slide) {
		this.deleteSlideOldPic(slide.getPic_path());
		this.slideDao.deleteById(slide.getSlideid());
	}

	@Override
	public List<Slide> getSlideListByProjectid(long projectid, int begin,
			int size) {
		return this.slideDao.getListByProjectid(projectid, begin, size);
	}

	@Override
	public void createSlideTx(Slide slide, File imgFile)
			throws ImageProcessException {
		this.processSlideImage(slide, imgFile);
		int count = this.slideDao.countByProjectid(slide.getProjectid());
		slide.setOrder_flag(count + 1);
		long slideid = NumberUtil.getLong(this.slideDao.save(slide));
		slide.setSlideid(slideid);
		Project project = this.projectDao.getById(slide.getProjectid());
		if (DataUtil.isEmpty(project.getPath())) {
			project.setPath(slide.getPic_path());
			this.projectDao.update(project);
		}
	}

	@Override
	public void updateSlideTx(Slide slide, File imgFile)
			throws ImageProcessException {
		String oldPic = slide.getPic_path();
		if (imgFile != null) {
			this.processSlideImage(slide, imgFile);
			this.deleteSlideOldPic(oldPic);
		}
		this.slideDao.update(slide);
		Project project = this.projectDao.getById(slide.getProjectid());
		if (DataUtil.isEmpty(project.getPath())) {
			project.setPath(slide.getPic_path());
			this.projectDao.update(project);
		}
		else {
			if (oldPic.equals(project.getPath())) {
				project.setPath(slide.getPic_path());
				this.projectDao.update(project);
			}
		}
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

	private void processSlideImage(Slide slide, File imgFile)
			throws ImageProcessException {
		String name = FileCnf.createFileName();
		String dbPath = fileCnf.getFileSaveToDbPath(name);
		String filePath = fileCnf.getFilePath(dbPath);
		slide.setPic_path(dbPath);
		ImageShaper shaper = ImageShaperFactory
				.getImageShaper(ImageShaperFactory.SHAPER_JMAGICK);
		try {
			ImageParam imageParam = new ImageParam(imgFile, 0, 0, 0, true);
			ImageSize scaleImageSize = new ImageSize(70, 70);
			shaper.scale(imageParam, scaleImageSize, filePath,
					PicUtil.SLIDE_PIC1_NAME);
			imageParam = new ImageParam(imgFile, 0, 0, 0, true);
			scaleImageSize = ImageSizeMaker.makeSize(imageParam.getOriginInfo()
					.getWidth(), imageParam.getOriginInfo().getHeight(), 960);
			shaper.scale(imageParam, scaleImageSize, filePath,
					PicUtil.SLIDE_PIC2_NAME);
		}
		catch (IOException e) {
			log.error("processSlideImage image error : " + e);
			throw new ImageProcessException();
		}
		catch (halo.util.image.ImageException e) {
			log.error("processSlideImage image error : " + e);
			throw new ImageProcessException();
		}
	}

	@Override
	public UpdateSldePic0Result updateSldePic1(long slideid, PicPoint picRect) {
		Slide slide = this.getSlide(slideid);
		if (slide == null) {
			return UpdateSldePic0Result.FILE_NOT_FOUND;
		}
		String realPath = this.fileCnf.getFilePath(slide.getPic_path());
		File file = new File(realPath + PicUtil.SLIDE_PIC2_NAME);
		ImageShaper shaper = ImageShaperFactory
				.getImageShaper(ImageShaperFactory.SHAPER_JMAGICK);
		try {
			int x = picRect.getX0();
			int y = picRect.getY0();
			int width = picRect.getX1() - picRect.getX0();
			int height = picRect.getY1() - picRect.getY0();
			ImageRect cutImageRect = new ImageRect(x, y, width, height);
			ImageParam imageParam = new ImageParam(file, 0, 0, 0, true);
			ImageSize scaledmageSize = new ImageSize(70, 70);
			shaper.cutAndScale(imageParam, cutImageRect, scaledmageSize,
					realPath, PicUtil.SLIDE_PIC1_NAME);
			// imageProcessor.cutImage(realPath, PicUtil.SLIDE_PIC1_NAME,
			// picRect);
			// imageProcessor.makeImage(realPath, PicUtil.SLIDE_PIC1_NAME, true,
			// 70);
			return UpdateSldePic0Result.SUCCESS;
		}
		catch (IOException e) {
			return UpdateSldePic0Result.IMAGE_PROCESS_ERR;
		}
		catch (halo.util.image.ImageException e) {
			return UpdateSldePic0Result.IMAGE_PROCESS_ERR;
		}
	}

	@Override
	public int countSlideByProjectid(long projectid) {
		return this.slideDao.countByProjectid(projectid);
	}
}