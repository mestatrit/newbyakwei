package iwant.svr;

import iwant.bean.Slide;
import iwant.svr.exception.ImageProcessException;
import iwant.svr.exception.PptNotFoundException;
import iwant.svr.statusenum.UpdateSldePic0Result;
import iwant.util.PicPoint;

import java.io.File;
import java.util.List;

public interface PptSvr {

	/**
	 * 创建幻灯页数据
	 * 
	 * @param slide
	 * @param imgFile
	 */
	void createSlide(Slide slide, File imgFile) throws PptNotFoundException,
			ImageProcessException;

	/**
	 * 更新幻灯页数据
	 * 
	 * @param slide
	 * @param imgFile
	 */
	void updateSlide(Slide slide, File imgFile) throws PptNotFoundException,
			ImageProcessException;

	int countSlideByProjectid(long projectid);

	List<Slide> getSlideListByProjectid(long projectid, int begin, int size);

	void deleteSlide(Slide slide);

	Slide getSlide(long slideid);

	UpdateSldePic0Result updateSldePic1(long slideid, PicPoint picRect);
}
