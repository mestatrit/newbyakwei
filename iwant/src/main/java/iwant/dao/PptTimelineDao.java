package iwant.dao;

import halo.dao.query.IDao;
import iwant.bean.PptTimeline;

import java.util.List;

public interface PptTimelineDao extends IDao<PptTimeline> {

	List<PptTimeline> getListByUserid(long userid, int begin, int size);

	PptTimeline getByUseridAndpptid(long userid, long pptid);

	boolean isExistByUseridAndPptid(long userid, long pptid);
}