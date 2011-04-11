package iwant.dao.impl;

import iwant.bean.PptTimeline;
import iwant.dao.PptTimelineDao;

import java.util.List;

import com.hk.frame.dao.query2.BaseDao;

public class PptTimelineDaoImpl extends BaseDao<PptTimeline> implements
		PptTimelineDao {

	@Override
	public Class<PptTimeline> getClazz() {
		return PptTimeline.class;
	}

	@Override
	public PptTimeline getByUseridAndpptid(long userid, long pptid) {
		return this.getObject(null, "userid=? and pptid=?", new Object[] {
				userid, pptid });
	}

	@Override
	public List<PptTimeline> getListByUserid(long userid, int begin, int size) {
		return this.getList(null, "userid=?", new Object[] { userid },
				"sysid desc", begin, size);
	}

	@Override
	public boolean isExistByUseridAndPptid(long userid, long pptid) {
		if (this.count(null, "userid=? and pptid=?", new Object[] { userid,
				pptid }) > 0) {
			return true;
		}
		return false;
	}
}