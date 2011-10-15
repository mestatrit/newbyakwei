package iwant.web.api;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import halo.web.util.SimplePage;
import iwant.bean.Category;
import iwant.bean.Project;
import iwant.bean.Slide;
import iwant.svr.CategorySvr;
import iwant.svr.PptSvr;
import iwant.svr.ProjectSvr;
import iwant.web.admin.util.Err;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("/api/ppt")
public class PptAction extends BaseApiAction {

	@Autowired
	private CategorySvr categorySvr;

	@Autowired
	private PptSvr pptSvr;

	@Autowired
	private ProjectSvr projectSvr;

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String mainlist(HkRequest req, HkResponse resp) throws Exception {
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 20);
		int did = req.getInt("did");
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
				List<Project> plist = this.projectSvr
						.getProjectListByCatidAndDid(catid, did,
								simplePage.getBegin(), simplePage.getSize());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", plist);
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
		List<Slide> list = this.pptSvr.getSlideListByProjectid(pptid, 0, 50);
		long projectid = pptid;
		Project project = this.projectSvr.getProject(projectid);
		if (project == null) {
			APIUtil.writeErr(resp, Err.RESOURCE_NOT_EXIST);
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project", project);
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/slidelist.vm");
		return null;
	}

	/**
	 * 暂时不支持，为了让未更新的客户端不报错，则定向到首页的数据
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String timeline(HkRequest req, HkResponse resp) throws Exception {
		return this.mainlist(req, resp);
	}
}