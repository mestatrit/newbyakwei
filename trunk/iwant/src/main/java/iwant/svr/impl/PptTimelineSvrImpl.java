package iwant.svr.impl;

import iwant.bean.Ppt;
import iwant.bean.PptTimeline;
import iwant.bean.Project;
import iwant.bean.enumtype.ReadFlagType;
import iwant.dao.PptTimelineDao;
import iwant.svr.PptSvr;
import iwant.svr.PptTimelineSvr;
import iwant.svr.ProjectSvr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.NumberUtil;

public class PptTimelineSvrImpl implements PptTimelineSvr {

	@Autowired
	private PptTimelineDao pptTimelineDao;

	@Autowired
	private PptSvr pptSvr;

	@Autowired
	private ProjectSvr projectSvr;

	@Override
	public boolean createPptTimeline(PptTimeline pptTimeline) {
		if (this.pptTimelineDao.isExistByUseridAndPptid(
				pptTimeline.getUserid(), pptTimeline.getPptid())) {
			return false;
		}
		pptTimeline.setSysid(NumberUtil.getLong(this.pptTimelineDao
				.save(pptTimeline)));
		return true;
	}

	private void updatePptTimeline(PptTimeline pptTimeline) {
		this.pptTimelineDao.update(pptTimeline);
	}

	@Override
	public void updateForReaded(long userid, long pptid) {
		PptTimeline pptTimeline = this.pptTimelineDao.getByUseridAndpptid(
				userid, pptid);
		if (pptTimeline != null) {
			pptTimeline.setRead_flag(ReadFlagType.READED.getValue());
			pptTimeline.setReadtime(new Date());
			this.updatePptTimeline(pptTimeline);
		}
	}

	@Override
	public List<PptTimeline> getPptTimelineListByUserid(long userid, int begin,
			int size, boolean buildPpt, boolean buildProject) {
		List<PptTimeline> list = this.pptTimelineDao.getListByUserid(userid,
				begin, size);
		if (buildPpt) {
			List<Long> idList = new ArrayList<Long>();
			for (PptTimeline o : list) {
				idList.add(o.getPptid());
			}
			Map<Long, Ppt> map = this.pptSvr.getPptMapInId(idList);
			for (PptTimeline o : list) {
				o.setPpt(map.get(o.getPptid()));
			}
		}
		if (buildProject) {
			List<Long> idList = new ArrayList<Long>();
			for (PptTimeline o : list) {
				idList.add(o.getProjectid());
			}
			Map<Long, Project> map = this.projectSvr.getProjectMap(idList);
			for (PptTimeline o : list) {
				if (o.getPpt() != null) {
					o.getPpt().setProject(map.get(o.getProjectid()));
				}
			}
		}
		return list;
	}

	@Override
	public PptTimeline getPptTimelineByUseridAndPptid(long userid, long pptid) {
		return this.pptTimelineDao.getByUseridAndpptid(userid, pptid);
	}
}