package iwant.web.api;

import iwant.bean.Category;
import iwant.bean.MainPpt;
import iwant.bean.Ppt;
import iwant.bean.PptTimeline;
import iwant.bean.Project;
import iwant.bean.Slide;
import iwant.bean.User;
import iwant.svr.CategorySvr;
import iwant.svr.PptSvr;
import iwant.svr.PptTimelineSvr;
import iwant.svr.ProjectSvr;
import iwant.svr.UserSvr;
import iwant.web.admin.util.Err;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/api/ppt")
public class PptAction extends BaseApiAction {

	@Autowired
	private CategorySvr categorySvr;

	@Autowired
	private PptSvr pptSvr;

	@Autowired
	private ProjectSvr projectSvr;

	@Autowired
	private UserSvr userSvr;

	@Autowired
	private PptTimelineSvr pptTimelineSvr;

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String mainlist(HkRequest req, HkResponse resp) throws Exception {
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 20);
		if (size > 20) {
			size = 20;
		}
		int catid = req.getInt("catid");
		if (catid <= 0) {
			List<Category> list = this.categorySvr.getCategoryListForAll();
			if (!list.isEmpty()) {
				Category category = list.get(0);
				catid = category.getCatid();
				SimplePage simplePage = new SimplePage(size, page);
				List<MainPpt> mainpptlist = this.pptSvr
						.getMainPptListOrderedByCatid(catid, true, simplePage
								.getBegin(), simplePage.getSize());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", mainpptlist);
				APIUtil.writeData(resp, map, "vm/mainpptlist.vm");
			}
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String slidelist(HkRequest req, HkResponse resp) throws Exception {
		long pptid = req.getLong("pptid");
		List<Slide> list = this.pptSvr.getSlideListByPptidOrdered(pptid);
		Ppt ppt = this.pptSvr.getPpt(pptid);
		long projectid = 0;
		if (ppt == null) {
			MainPpt mainPpt = this.pptSvr.getMainPpt(pptid);
			if (mainPpt == null) {
				APIUtil.writeErr(req, resp, Err.RESOURCE_NOT_EXIST);
				return null;
			}
			projectid = mainPpt.getProjectid();
		}
		else {
			projectid = ppt.getProjectid();
		}
		Project project = this.projectSvr.getProject(projectid);
		if (project == null) {
			APIUtil.writeErr(req, resp, Err.RESOURCE_NOT_EXIST);
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project", project);
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/slidelist.vm");
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String timeline(HkRequest req, HkResponse resp) throws Exception {
		String device_token = req.getStringRow("device_token");
		if (DataUtil.isEmpty(device_token)) {
			APIUtil.writeErr(req, resp, Err.USER_NOT_EXIST);
			return null;
		}
		User user = this.userSvr.getUserByDevice_token(device_token);
		if (user == null) {
			APIUtil.writeErr(req, resp, Err.USER_NOT_EXIST);
			return null;
		}
		SimplePage simplePage = new SimplePage(req.getInt("size", 10), req
				.getInt("page", 1));
		List<PptTimeline> list = this.pptTimelineSvr
				.getPptTimelineListByUserid(user.getUserid(), simplePage
						.getBegin(), simplePage.getSize(), true, true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/ppttimelinelist.vm");
		return null;
	}

	public static void main(String[] args) {
		P.println(Integer.MAX_VALUE);
		// P.println(Long.MAX_VALUE);
	}
}