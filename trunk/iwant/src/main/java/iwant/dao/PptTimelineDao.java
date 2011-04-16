package iwant.dao;

import iwant.bean.PptTimeline;

import java.util.List;

import com.hk.frame.dao.query2.IDao;

public interface PptTimelineDao extends IDao<PptTimeline> {

	List<PptTimeline> getListByUserid(long userid, int begin, int size);

	PptTimeline getByUseridAndpptid(long userid, long pptid);

	boolean isExistByUseridAndPptid(long userid, long pptid);
}