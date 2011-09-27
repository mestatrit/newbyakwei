package iwant.web.admin.job;

import iwant.bean.Notice;
import iwant.bean.NoticeQueue;
import iwant.bean.Ppt;
import iwant.bean.PptQueue;
import iwant.bean.PptTimeline;
import iwant.bean.Project;
import iwant.bean.ProjectFans;
import iwant.bean.enumtype.ReadFlagType;
import iwant.svr.FollowProjectSvr;
import iwant.svr.NoticeSvr;
import iwant.svr.PptSvr;
import iwant.svr.PptTimelineSvr;
import iwant.svr.ProjectSvr;
import iwant.svr.exception.PptNotFoundException;
import iwant.svr.exception.UserNotFoundException;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 创建用户订阅的ppt数据
 * 
 * @author akwei
 */
public class CreateUserNoticeJob {

	@Autowired
	private FollowProjectSvr followProjectSvr;

	@Autowired
	private PptTimelineSvr pptTimelineSvr;

	@Autowired
	private PptSvr pptSvr;

	@Autowired
	private ProjectSvr projectSvr;

	@Autowired
	private NoticeSvr noticeSvr;

	private boolean processing;

	public void invoke() {
		if (this.processing) {
			return;
		}
		this.processing = true;
		this.processTimeline();
		this.processCreateUserNotice();
		this.processing = false;
	}

	private void processCreateUserNotice() {
		List<NoticeQueue> list = this.noticeSvr.getNoticeQueueList(0, 50);
		Notice notice = null;
		for (NoticeQueue o : list) {
			notice = this.noticeSvr.getNotice(o.getNoticeid());
			if (notice != null) {
				List<ProjectFans> fanslist = this.followProjectSvr
						.getProjectFansListByProjectid(notice.getProjectid(),
								0, -1);
				for (ProjectFans fans : fanslist) {
					this.noticeSvr.createUserNotice(o.getNoticeid(),
							fans.getUserid());
				}
			}
			this.noticeSvr.deleteNoticeQueue(o);
		}
	}

	private void processTimeline() {
		PptTimeline pptTimeline = null;
		Ppt ppt = null;
		Project project = null;
		List<PptQueue> list = this.pptSvr.getPptQueueList(0, 100);
		for (PptQueue o : list) {
			ppt = this.pptSvr.getPpt(o.getPptid());
			if (ppt != null) {
				project = this.projectSvr.getProject(ppt.getProjectid());
				if (project != null) {
					List<ProjectFans> fanslist = this.followProjectSvr
							.getProjectFansListByProjectid(o.getProjectid(), 0,
									-1);
					for (ProjectFans fans : fanslist) {
						pptTimeline = new PptTimeline();
						pptTimeline.setUserid(fans.getUserid());
						pptTimeline.setCreatetime(new Date());
						pptTimeline.setPptid(o.getPptid());
						pptTimeline.setRead_flag(ReadFlagType.NOTREAD
								.getValue());
						pptTimeline.setReadtime(new Date());
						try {
							this.pptTimelineSvr.createPptTimeline(pptTimeline);
						}
						catch (PptNotFoundException e) {
							e.printStackTrace();
						}
						catch (UserNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
			}
			this.pptSvr.deletePptQueue(o);
		}
	}
}