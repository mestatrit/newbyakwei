package iwant.svr;

import iwant.bean.PptTimeline;
import iwant.svr.exception.PptNotFoundException;
import iwant.svr.exception.UserNotFoundException;

import java.util.List;

public interface PptTimelineSvr {

	/**
	 * 创建pptTimeline,不能存在与userid,pptid相同的数据
	 * 
	 * @param pptTimeline
	 * @return true:创建成功,false:已经存在，创建失败
	 */
	boolean createPptTimeline(PptTimeline pptTimeline)
			throws PptNotFoundException, UserNotFoundException;

	PptTimeline getPptTimelineByUseridAndPptid(long userid, long pptid);

	void updateForReaded(long userid, long pptid);

	List<PptTimeline> getPptTimelineListByUserid(long userid, int begin,
			int size, boolean buildPpt, boolean buildProject);
}