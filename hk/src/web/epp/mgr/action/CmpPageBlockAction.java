package web.epp.mgr.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpAdBlock;
import com.hk.bean.CmpArticleBlock;
import com.hk.bean.CmpArticleGroup;
import com.hk.bean.CmpArticleTag;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpPageBlock;
import com.hk.bean.CmpPageMod;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpArticleTagService;
import com.hk.svr.CmpModService;
import com.hk.svr.CmpNavService;
import com.hk.svr.processor.CmpModProcessor;
import com.hk.svr.pub.CmpPageModUtil;
import com.hk.svr.pub.Err;

@Component("/epp/web/op/webadmin/cmppageblock")
public class CmpPageBlockAction extends EppBaseAction {

	@Autowired
	private CmpModService cmpModService;

	@Autowired
	private CmpModProcessor cmpModProcessor;

	@Autowired
	private CmpNavService cmpNavService;

	@Autowired
	private CmpArticleService cmpArticleService;

	@Autowired
	private CmpArticleTagService cmpArticleTagService;

	public String execute(HkRequest req, HkResponse resp) {
		req.setAttribute("active_29", 1);
		byte pageflg = req.getByteAndSetAttr("pageflg");
		long companyId = req.getLong("companyId");
		List<CmpPageBlock> list = this.cmpModService
				.getCmpPageBlockListByCompanyIdAndPageflg(companyId, pageflg);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmppageblock/list.jsp");
	}

	/**
	 * 区块中的内容，可以是广告或者是文章，分别到相应的页面展示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-7-4
	 */
	public String content(HkRequest req, HkResponse resp) {
		req.setAttribute("active_29", 1);
		long companyId = req.getLong("companyId");
		long blockId = req.getLongAndSetAttr("blockId");
		int change = req.getIntAndSetAttr("change");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		req.setAttribute("pageflg", cmpPageBlock.getPageflg());
		req.setAttribute("cmpPageBlock", cmpPageBlock);
		SimplePage page = req.getSimplePage(20);
		if (cmpPageBlock.getCmpPageMod().isModAd()) {// 到广告列表
			List<CmpAdBlock> cmpadblocklist = this.cmpModProcessor
					.getCmpAdBlockListByCompanyIdAndBlockId(companyId, blockId,
							true, page.getBegin(), page.getSize());
			this.processListForPage(page, cmpadblocklist);
			req.setAttribute("cmpadblocklist", cmpadblocklist);
			return this.getWebPath("admin/cmppageblock/adlist.jsp");
		}
		if (DataUtil.isEmpty(cmpPageBlock.getExpression())
				|| cmpPageBlock.isAuto() || change == 1) {
			long tagId = cmpPageBlock.getTagId();
			long groupId = cmpPageBlock.getGroupId();
			long navId = cmpPageBlock.getNavId();
			if (tagId > 0) {
				CmpArticleTag cmpArticleTag = this.cmpArticleTagService
						.getCmpArticleTag(companyId, tagId);
				req.setAttribute("cmpArticleTag", cmpArticleTag);
			}
			if (groupId > 0) {
				CmpArticleGroup cmpArticleGroup = this.cmpArticleService
						.getCmpArticleGroup(groupId);
				req.setAttribute("cmpArticleGroup", cmpArticleGroup);
				if (cmpArticleGroup != null) {
					CmpNav group_cmpNav = this.cmpNavService
							.getCmpNav(cmpArticleGroup.getCmpNavOid());
					req.setAttribute("group_cmpNav", group_cmpNav);
				}
			}
			if (navId > 0) {
				CmpNav cmpNav = this.cmpNavService.getCmpNav(navId);
				req.setAttribute("cmpNav", cmpNav);
			}
			return this.getWebPath("admin/cmppageblock/setexpression.jsp");
		}
		List<CmpArticleBlock> cmparticleblocklist = this.cmpModProcessor
				.getCmpArticleBlockListByCompanyIdAndBlockId(companyId,
						blockId, true, true, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, cmparticleblocklist);
		req.setAttribute("cmparticleblocklist", cmparticleblocklist);
		return this.getWebPath("admin/cmppageblock/articlelist.jsp");
	}

	/**
	 * 创建区块
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-25
	 */
	public String create(HkRequest req, HkResponse resp) {
		req.setAttribute("active_29", 1);
		byte pageflg = req.getByteAndSetAttr("pageflg");
		if (pageflg < 1) {
			pageflg = 1;
		}
		if (pageflg > 3) {
			pageflg = 1;
		}
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/cmppageblock/create.jsp");
		}
		int pageModId = req.getInt("pageModId");
		CmpPageBlock cmpPageBlock = new CmpPageBlock();
		cmpPageBlock.setCompanyId(req.getLong("companyId"));
		cmpPageBlock.setName(req.getHtmlRow("name"));
		cmpPageBlock.setPageModId(pageModId);
		cmpPageBlock.setPageflg(pageflg);
		int code = cmpPageBlock.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		CmpPageMod cmpPageMod = CmpPageModUtil.getCmpPageMod(pageModId);
		if (cmpPageMod == null) {
			return null;
		}
		this.cmpModService.createCmpPageBlock(cmpPageBlock);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "createok", null);
	}

	/**
	 * 修改区块
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-25
	 */
	public String update(HkRequest req, HkResponse resp) {
		req.setAttribute("active_29", 1);
		long blockId = req.getLongAndSetAttr("blockId");
		req.reSetAttribute("pageflg");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		req.setAttribute("cmpPageBlock", cmpPageBlock);
		if (this.isForwardPage(req)) {
			return this.getWebPath("admin/cmppageblock/update.jsp");
		}
		int pageModId = req.getInt("pageModId");
		cmpPageBlock.setName(req.getHtmlRow("name"));
		cmpPageBlock.setPageModId(pageModId);
		int code = cmpPageBlock.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		CmpPageMod cmpPageMod = CmpPageModUtil.getCmpPageMod(pageModId);
		if (cmpPageMod == null) {
			return null;
		}
		this.cmpModService.updateCmpPageBlock(cmpPageBlock);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 删除区块
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-25
	 */
	public String del(HkRequest req, HkResponse resp) {
		long blockId = req.getLongAndSetAttr("blockId");
		this.cmpModService.deleteCmpPageBlock(blockId);
		this.setDelSuccessMsg(req);
		return null;
	}

	/**
	 * 根据模块来显示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-25
	 */
	public String lisetbypagemodid(HkRequest req, HkResponse resp) {
		req.setAttribute("active_29", 1);
		byte pageModId = req.getByteAndSetAttr("pageModId");
		long companyId = req.getLong("companyId");
		List<CmpPageBlock> list = this.cmpModService
				.getCmpPageBlockListByCompanyIdAndPageflg(companyId, pageModId);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmppageblock/list.jsp");
	}

	/**
	 * 更新区块顺序
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-6-25
	 */
	public String updateorderflg(HkRequest req, HkResponse resp) {
		long blockId = req.getLong("blockId");
		int orderflg = req.getInt("orderflg");
		this.cmpModService.updateCmpPageBlockOrderflg(blockId, orderflg);
		return null;
	}

	/**
	 * 设置模块的顺序
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String setorderflg(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			long companyId = req.getLong("companyId");
			int pageModId = req.getIntAndSetAttr("pageModId");
			req.reSetAttribute("pageflg");
			List<CmpPageBlock> list = this.cmpModService
					.getCmpPageBlockListByCompanyIdAndPageModId(companyId,
							pageModId);
			req.setAttribute("list", list);
			return this.getWebPath("admin/cmppageblock/listfororder.jsp");
		}
		long blockId = req.getLong("blockId");
		int orderflg = req.getInt("orderflg");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock != null) {
			cmpPageBlock.setOrderflg(orderflg);
			this.cmpModService.updateCmpPageBlock(cmpPageBlock);
		}
		return this.onSuccess2(req, "setorderflgok", null);
	}

	/**
	 * 取消推荐广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String delcmpadblock(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		CmpAdBlock cmpAdBlock = this.cmpModService.getCmpAdBlock(oid);
		if (cmpAdBlock != null && cmpAdBlock.getCompanyId() == companyId) {
			this.cmpModService.deleteCmpAdBlock(oid);
			req.setSessionText("epp.cmpagetblockref.del.success");
		}
		return null;
	}

	/**
	 * 取消推荐文章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String delcmparticleblock(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long oid = req.getLong("oid");
		CmpArticleBlock cmpArticleBlock = this.cmpModService
				.getCmpArticleBlock(oid);
		if (cmpArticleBlock != null
				&& cmpArticleBlock.getCompanyId() == companyId) {
			this.cmpModService.deleteCmpArticleBlock(oid);
			req.setSessionText("epp.cmpagetblockref.del.success");
		}
		return null;
	}

	/**
	 * 手动推荐文章
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String setnotauto(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long blockId = req.getLong("blockId");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("auto", "0");
			String expression = DataUtil.toJson(map);
			cmpPageBlock.setExpression(expression);
			this.cmpModService.updateCmpPageBlock(cmpPageBlock);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/epp/web/op/webadmin/cmppageblock.do?companyId=" + companyId
				+ "&pageflg=" + req.getByte("pageflg");
	}

	/**
	 * 栏目列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String navlist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long blockId = req.getLongAndSetAttr("blockId");
		long navId = req.getLongAndSetAttr("navId");
		req.reSetAttribute("change");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		req.setAttribute("cmpPageBlock", cmpPageBlock);
		if (navId == 0) {// 所有一级栏目
			List<CmpNav> navlist = this.cmpNavService
					.getCmpNavListByCompanyIdAndNlevel(companyId,
							CmpNav.NLEVEL_1);
			req.setAttribute("navlist", navlist);
			return this.getWebPath("admin/cmppageblock/navlist.jsp");
		}
		if (navId > 0) {// 如果选中栏目，
			CmpNav cmpNav = this.cmpNavService.getCmpNav(navId);
			if (cmpNav == null) {
				return null;
			}
			if (cmpNav.isDirectory()) {// 查看该栏目是否是目录，是目录的情况下继续显示列表
				List<CmpNav> navlist = this.cmpNavService
						.getCmpNavListByCompanyIdAndParentId(companyId, navId);
				req.setAttribute("navlist", navlist);
				return this.getWebPath("admin/cmppageblock/navlist.jsp");
			}
		}
		return null;
	}

	/**
	 * 查看栏目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String viewnav(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long blockId = req.getLongAndSetAttr("blockId");
		long navId = req.getLongAndSetAttr("navId");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navId);
		if (cmpNav == null) {
			return null;
		}
		List<CmpArticleGroup> grouplist = this.cmpArticleService
				.getCmpArticleGroupListByCompanyIdAndCmpNavOid(companyId, navId);
		if (grouplist.size() > 0) {// 有组数据，就到组列表
			return "r:/epp/web/op/webadmin/cmppageblock_grouplist.do?companyId="
					+ companyId + "&blockId=" + blockId + "&navId=" + navId;
		}
		// 没有组数据，就直接选择该栏目
		return "r:/epp/web/op/webadmin/cmppageblock_selnav.do?companyId="
				+ companyId + "&blockId=" + blockId + "&navId=" + navId;
	}

	/**
	 * 栏目选择
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String selnav(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long blockId = req.getLongAndSetAttr("blockId");
		long navId = req.getLongAndSetAttr("navId");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navId);
		if (cmpNav == null) {
			return null;
		}
		if (cmpNav.isArticleList()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("auto", "1");
			map.put("navid", navId + "");
			String expression = DataUtil.toJson(map);
			cmpPageBlock.setExpression(expression);
			this.cmpModService.updateCmpPageBlock(cmpPageBlock);
			this.setOpFuncSuccessMsg(req);
		}
		return "r:/epp/web/op/webadmin/cmppageblock.do?companyId=" + companyId
				+ "&pageflg=" + +cmpPageBlock.getPageflg();
	}

	/**
	 * 组选择
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String grouplist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long blockId = req.getLongAndSetAttr("blockId");
		long navId = req.getLongAndSetAttr("navId");
		req.reSetAttribute("change");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		CmpNav cmpNav = this.cmpNavService.getCmpNav(navId);
		if (cmpNav == null) {
			return null;
		}
		List<CmpArticleGroup> grouplist = this.cmpArticleService
				.getCmpArticleGroupListByCompanyIdAndCmpNavOid(companyId, navId);
		req.setAttribute("cmpPageBlock", cmpPageBlock);
		req.setAttribute("cmpNav", cmpNav);
		req.setAttribute("grouplist", grouplist);
		return this.getWebPath("admin/cmppageblock/grouplist.jsp");
	}

	/**
	 * 组选择
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String selgroup(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long blockId = req.getLongAndSetAttr("blockId");
		long groupId = req.getLongAndSetAttr("groupId");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		CmpArticleGroup cmpArticleGroup = this.cmpArticleService
				.getCmpArticleGroup(groupId);
		if (cmpArticleGroup == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("auto", "1");
		map.put("groupid", groupId + "");
		String expression = DataUtil.toJson(map);
		cmpPageBlock.setExpression(expression);
		this.cmpModService.updateCmpPageBlock(cmpPageBlock);
		this.setOpFuncSuccessMsg(req);
		return "r:/epp/web/op/webadmin/cmppageblock.do?companyId=" + companyId
				+ "&pageflg=" + +cmpPageBlock.getPageflg();
	}

	/**
	 * 标签列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String taglist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long blockId = req.getLongAndSetAttr("blockId");
		req.reSetAttribute("change");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		req.setAttribute("cmpPageBlock", cmpPageBlock);
		SimplePage page = req.getSimplePage(20);
		String name = req.getHtmlRow("name");
		req.setEncodeAttribute("name", name);
		List<CmpArticleTag> taglist = this.cmpArticleTagService
				.getCmpArticleTagListByCompanyId(companyId, name, page
						.getBegin(), page.getSize() + 1);
		req.setAttribute("taglist", taglist);
		return this.getWebPath("admin/cmppageblock/taglist.jsp");
	}

	/**
	 * 组选择
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-27
	 */
	public String seltag(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long blockId = req.getLongAndSetAttr("blockId");
		long tagId = req.getLongAndSetAttr("tagId");
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		CmpArticleTag cmpArticleTag = this.cmpArticleTagService
				.getCmpArticleTag(companyId, tagId);
		if (cmpArticleTag == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("auto", "1");
		map.put("tagid", tagId + "");
		String expression = DataUtil.toJson(map);
		cmpPageBlock.setExpression(expression);
		this.cmpModService.updateCmpPageBlock(cmpPageBlock);
		this.setOpFuncSuccessMsg(req);
		return "r:/epp/web/op/webadmin/cmppageblock.do?companyId=" + companyId
				+ "&pageflg=" + cmpPageBlock.getPageflg();
	}

	/**
	 * 重推页面的广告(先删除，后添加)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String reseladblock(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		CmpAdBlock cmpAdBlock = this.cmpModService.getCmpAdBlock(oid);
		if (cmpAdBlock == null) {
			return null;
		}
		CmpPageBlock cmpPageBlock = this.cmpModService
				.getCmpPageBlock(cmpAdBlock.getBlockId());
		if (cmpPageBlock == null) {
			return null;
		}
		this.cmpModService.deleteCmpAdBlock(oid);
		cmpAdBlock.setOid(0);
		if (this.cmpModProcessor.createCmpAdBlock(cmpAdBlock)) {
			this.setOpFuncSuccessMsg(req);
			return null;
		}
		return null;
	}

	/**
	 * 重推页面的文章(先删除，后添加)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String reselarticleblock(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		CmpArticleBlock cmpArticleBlock = this.cmpModService
				.getCmpArticleBlock(oid);
		if (cmpArticleBlock == null) {
			return null;
		}
		CmpPageBlock cmpPageBlock = this.cmpModService
				.getCmpPageBlock(cmpArticleBlock.getBlockId());
		if (cmpPageBlock == null) {
			return null;
		}
		CmpPageMod cmpPageMod = CmpPageModUtil.getCmpPageMod(cmpPageBlock
				.getPageModId());
		if (cmpPageMod == null) {
			return null;
		}
		this.cmpModService.deleteCmpArticleBlock(oid);
		cmpArticleBlock.setOid(0);
		if (this.cmpModProcessor.createCmpArticlePageBlock(cmpArticleBlock,
				cmpPageMod)) {
			this.setOpFuncSuccessMsg(req);
			return null;
		}
		return null;
	}
}