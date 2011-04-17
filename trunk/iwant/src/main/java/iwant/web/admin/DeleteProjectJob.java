package iwant.web.admin;

import iwant.bean.ProjectRecycle;
import iwant.bean.Slide;
import iwant.svr.FollowProjectSvr;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class DeleteProjectJob {

	@Autowired
	private ProjectSvr projectSvr;

	@Autowired
	private PptSvr pptSvr;

	@Autowired
	private FollowProjectSvr followProjectSvr;

	public void invoke() {
		List<ProjectRecycle> list = this.projectSvr.getProjectRecycleList(0,
				100);
		List<Slide> slideList = null;
		for (ProjectRecycle o : list) {
			this.followProjectSvr.deleteFollowProjectByProjectid(o
					.getProjectid());
			slideList = this.pptSvr.getSlideListByProjectid(o.getProjectid(),
					0, 100);
			for (Slide slide : slideList) {
				this.pptSvr.deleteSlide(slide);
			}
		}
	}
}
