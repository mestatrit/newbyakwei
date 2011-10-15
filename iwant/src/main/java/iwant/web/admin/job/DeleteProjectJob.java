package iwant.web.admin.job;

import iwant.bean.ProjectRecycle;
import iwant.bean.Slide;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 删除项目操作，把耗时的删除操作与普通删除分开，异步化处理
 * 
 * @author akwei
 */
public class DeleteProjectJob {

	@Autowired
	private ProjectSvr projectSvr;

	@Autowired
	private PptSvr pptSvr;

	private boolean processing;

	private final Log log = LogFactory.getLog(DeleteProjectJob.class);

	public void invoke() {
		if (processing) {
			return;
		}
		this.processing = true;
		try {
			List<ProjectRecycle> list = this.projectSvr.getProjectRecycleList(
					0, 100);
			List<Slide> slideList = null;
			for (ProjectRecycle o : list) {
				slideList = this.pptSvr.getSlideListByProjectid(
						o.getProjectid(), 0, 100);
				while (!slideList.isEmpty()) {
					for (Slide slide : slideList) {
						this.pptSvr.deleteSlide(slide);
					}
				}
				this.projectSvr.deleteProjectRecycle(o);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		this.processing = false;
	}
}
