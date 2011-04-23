package iwant.svr;

import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.PptQueue;
import iwant.bean.Slide;
import iwant.dao.MainPptSearchCdn;
import iwant.dao.PptSearchCdn;
import iwant.svr.statusenum.UpdateSldePic0Result;
import iwant.util.ErrorCode;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hk.frame.util.image2.PicRect;

public interface PptSvr {

	/**
	 * 创建mainppt，order_flag会根据id自动赋值
	 * 
	 * @param mainPpt
	 * @return
	 */
	void createMainPpt(MainPpt mainPpt);

	/**
	 * 转为主要项目简介
	 * 
	 * @param pptid
	 * @return true:成功,false:已经存在项目简介
	 */
	boolean changePptToMainPpt(long pptid);

	/**
	 * 创建幻灯页数据
	 * 
	 * @param slide
	 * @param imgFile
	 * @param picRect
	 *            小方图图片区域,可以为空，为空时，自动切小方图
	 * @return OptStatus中error_code的值为 {@link ErrorCode#success},
	 *         {@link ErrorCode#err_image}
	 */
	OptStatus createSlide(Slide slide, File imgFile, PicRect picRect);

	/**
	 * 更新幻灯页数据
	 * 
	 * @param slide
	 * @param imgFile
	 * @param picRect
	 *            小方图图片区域 ,可以为空，为空时，自动切小方图
	 * @return OptStatus中error_code的值为 {@link ErrorCode#success},
	 *         {@link ErrorCode#err_image}
	 */
	OptStatus updateSlide(Slide slide, File imgFile, PicRect picRect);

	/**
	 * 获得mainppt
	 * 
	 * @param pptid
	 * @return
	 */
	MainPpt getMainPpt(long pptid);

	MainPpt getMainPptByProjectid(long projectid);

	/**
	 * 获取已激活的mainppt集合，按照定义的顺序排列
	 * 
	 * @param catid
	 *            分类id
	 * @param begin
	 * @param size
	 * @return
	 */
	List<MainPpt> getMainPptListOrderedByCatid(int catid, boolean buildProject,
			int begin, int size);

	List<MainPpt> getMainPptListOrderedByCdn(MainPptSearchCdn mainPptSearchCdn,
			boolean buildProject, int begin, int size);

	int countMainPptByCdn(MainPptSearchCdn mainPptSearchCdn);

	/**
	 * 创建ppt
	 * 
	 * @param ppt
	 * @return
	 */
	void createPpt(Ppt ppt);

	/**
	 * 更新ppt
	 * 
	 * @param ppt
	 * @return
	 */
	void updatePpt(Ppt ppt);

	void updateMainPpt(MainPpt mainPpt);

	/**
	 * 删除ppt
	 * 
	 * @param pptid
	 * @return
	 */
	void deletePpt(long pptid);

	void deleteMainPpt(long pptid);

	/**
	 * 获得ppt
	 * 
	 * @param pptid
	 * @return
	 */
	Ppt getPpt(long pptid);

	List<Ppt> getPptListByProjectid(long projectid, int begin, int size);

	List<Ppt> getPptListByCdn(long projectid, PptSearchCdn pptSearchCdn,
			int begin, int size);

	List<Slide> getSlideListByPptidOrdered(long pptid);

	int countSlideByPptid(long pptid);

	List<Slide> getSlideListByProjectid(long projectid, int begin, int size);

	void deleteSlide(Slide slide);

	/**
	 * 只删除ppt数据,slide数据异步删除
	 * 
	 * @param projectid
	 */
	void deletePptByProjectid(long projectid);

	Map<Long, Ppt> getPptMapInId(List<Long> idList);

	Slide getSlide(long slideid);

	List<PptQueue> getPptQueueList(int begin, int size);

	void deletePptQueue(PptQueue pptQueue);

	boolean isCanAddSlide(long pptid);

	UpdateSldePic0Result updateSldePic0(long slideid, PicRect picRect);
}
