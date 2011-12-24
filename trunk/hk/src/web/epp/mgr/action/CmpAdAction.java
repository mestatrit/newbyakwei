package web.epp.mgr.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpAd;
import com.hk.bean.CmpAdBlock;
import com.hk.bean.CmpAdGroup;
import com.hk.bean.CmpAdRef;
import com.hk.bean.CmpPageBlock;
import com.hk.bean.CmpPageMod;
import com.hk.bean.Company;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpAdService;
import com.hk.svr.CmpModService;
import com.hk.svr.processor.CmpAdProcessor;
import com.hk.svr.processor.CmpModProcessor;
import com.hk.svr.pub.CmpPageModUtil;
import com.hk.svr.pub.Err;

/**
 * 企业广告（暂时只支持图片广告），最多只能有5个广告
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/cmpad")
public class CmpAdAction extends EppBaseAction {

	@Autowired
	private CmpAdService cmpAdService;

	@Autowired
	private CmpAdProcessor cmpAdProcessor;

	@Autowired
	private CmpModService cmpModService;

	@Autowired
	private CmpModProcessor cmpModProcessor;

	/**
	 *广告列表
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_23", 1);
		long companyId = req.getLong("companyId");
		long groupId = req.getLongAndSetAttr("groupId");
		SimplePage page = req.getSimplePage(20);
		List<CmpAd> list = this.cmpAdProcessor.getCmpAdListByCompanyId(
				companyId, groupId, true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		if (groupId > 0) {
			CmpAdGroup cmpAdGroup = this.cmpAdService.getCmpAdGroup(companyId,
					groupId);
			req.setAttribute("cmpAdGroup", cmpAdGroup);
		}
		return this.getWebPath("admin/cmpad/list.jsp");
	}

	/**
	 * 发布新广告，最多只能发布5个
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String create(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long groupId = req.getLongAndSetAttr("groupId");
		long blockId = req.getLongAndSetAttr("blockId");
		int htmlflg = req.getIntAndSetAttr("htmlflg");
		if (blockId > 0) {
			CmpPageBlock cmpPageBlock = this.cmpModService
					.getCmpPageBlock(blockId);
			req.setAttribute("cmpPageBlock", cmpPageBlock);
		}
		if (groupId > 0) {
			CmpAdGroup cmpAdGroup = this.cmpAdService.getCmpAdGroup(companyId,
					groupId);
			if (cmpAdGroup == null) {
				return null;
			}
			req.setAttribute("cmpAdGroup", cmpAdGroup);
		}
		List<CmpAd> list = this.cmpAdService.getCmpAdListByCompanyId(companyId,
				0, 0, 5);
		Company o = (Company) req.getAttribute("o");
		if (list.size() == 5 && o.isCmpFlgEnterprise()) {
			req.setSessionText("epp.cmpad.limit", new Object[] { 5 });
			return "r:/epp/web/op/webadmin/cmpad.do?companyId=" + companyId;
		}
		if (req.getInt("ch") == 0) {
			if (htmlflg == 1) {
				return this.getWebPath("admin/cmpad/createhtml.jsp");
			}
			return this.getWebPath("admin/cmpad/create.jsp");
		}
		CmpAd cmpAd = new CmpAd();
		cmpAd.setName(req.getHtmlRow("name"));
		cmpAd.setCompanyId(companyId);
		cmpAd.setUrl(req.getHtmlRow("url"));
		cmpAd.setHtml(req.getString("html"));
		if (groupId > 0) {
			cmpAd.setGroupId(groupId);
		}
		int code = cmpAd.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		try {
			if (!DataUtil.isEmpty(cmpAd.getUrl())
					&& cmpAd.getUrl().toLowerCase().startsWith("http://")) {
				cmpAd.setUrl(cmpAd.getUrl().substring(7));
			}
			code = this.cmpAdProcessor.createCmpAd(cmpAd, req.getFile("f"),
					blockId, req.getInt("refflg"));
			if (code != Err.SUCCESS) {
				if (code == Err.IMG_OUTOFSIZE_ERROR) {
					return this.onError(req, code, new Object[] { "100K" },
							"createerror", null);
				}
				return this.onError(req, code, "createerror", null);
			}
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "createok", cmpAd.getAdid());
		}
		catch (IOException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, "createerror", null);
		}
	}

	/**
	 * 广告创建成功
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-28
	 */
	public String createok(HkRequest req, HkResponse resp) {
		req.reSetAttribute("adid");
		return this.getWebPath("admin/cmpad/createok.jsp");
	}

	/**
	 * 更新广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		long adid = req.getLongAndSetAttr("adid");
		CmpAd cmpAd = this.cmpAdService.getCmpAd(adid);
		if (cmpAd == null) {
			return null;
		}
		req.setAttribute("cmpAd", cmpAd);
		req.reSetAttribute("blockId");
		long companyId = req.getLong("companyId");
		CmpAdRef cmpAdRef = this.cmpAdService.getCmpAdRefByCompanyIdAndAdid(
				companyId, adid);
		if (cmpAdRef != null) {
			req.setAttribute("refflg", 1);
		}
		if (req.getInt("ch") == 0) {
			if (DataUtil.isEmpty(cmpAd.getUrl())) {
				return this.getWebPath("admin/cmpad/createhtml.jsp");
			}
			return this.getWebPath("admin/cmpad/update.jsp");
		}
		cmpAd.setName(req.getHtmlRow("name"));
		cmpAd.setUrl(req.getHtmlRow("url"));
		cmpAd.setHtml(req.getString("html"));
		int code = cmpAd.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		try {
			if (!DataUtil.isEmpty(cmpAd.getUrl())
					&& cmpAd.getUrl().toLowerCase().startsWith("http://")) {
				cmpAd.setUrl(cmpAd.getUrl().substring(7));
			}
			code = this.cmpAdProcessor.updateCmpAd(cmpAd, req.getFile("f"), req
					.getInt("refflg"));
			if (code != Err.SUCCESS) {
				if (code == Err.IMG_OUTOFSIZE_ERROR) {
					return this.onError(req, code, new Object[] { "100K" },
							"updateerror", null);
				}
				return this.onError(req, code, "updateerror", null);
			}
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "updateok", null);
		}
		catch (IOException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, "updateerror", null);
		}
	}

	/**
	 * 删除广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long adid = req.getLongAndSetAttr("adid");
		long companyId = req.getLong("companyId");
		CmpAd cmpAd = this.cmpAdService.getCmpAd(adid);
		if (cmpAd == null) {
			return null;
		}
		if (cmpAd.getCompanyId() == companyId) {
			this.cmpAdProcessor.deleteCmpAd(cmpAd);
			this.setDelSuccessMsg(req);
		}
		return null;
	}

	/**
	 * 推荐到页面的广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String selblock(HkRequest req, HkResponse resp) {
		long adid = req.getLongAndSetAttr("adid");
		long companyId = req.getLong("companyId");
		req.reSetAttribute("page");
		CmpAd cmpAd = this.cmpAdService.getCmpAd(adid);
		if (cmpAd == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			List<CmpPageBlock> blocklist = this.cmpModProcessor
					.getCmpPageBlockListByCompanyIdAndPageflg(companyId, true,
							(byte) 1);
			req.setAttribute("blocklist", blocklist);
			byte pageflg = req.getByteAndSetAttr("pageflg", (byte) 1);
			CmpAdBlock cmpAdBlock = this.cmpModService.getCmpAdBlock(companyId,
					adid, pageflg);
			if (cmpAdBlock != null) {
				CmpPageBlock cmpPageBlock = this.cmpModService
						.getCmpPageBlock(cmpAdBlock.getBlockId());
				req.setAttribute("cmpAdBlock", cmpAdBlock);
				req.setAttribute("cmpPageBlock", cmpPageBlock);
			}
			return this.getWebPath("admin/cmpad/selblock.jsp");
		}
		long blockId = req.getLong("blockId");
		CmpAdBlock cmpAdBlock = new CmpAdBlock();
		cmpAdBlock.setCompanyId(companyId);
		cmpAdBlock.setAdid(adid);
		cmpAdBlock.setPageflg((byte) 1);
		cmpAdBlock.setBlockId(blockId);
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		CmpPageMod cmpPageMod = CmpPageModUtil.getCmpPageMod(cmpPageBlock
				.getPageModId());
		if (cmpPageMod == null) {
			return null;
		}
		if (this.cmpModProcessor.createCmpAdBlock(cmpAdBlock)) {
			this.setOpFuncSuccessMsg(req);
			return null;
		}
		resp.sendHtml(1);
		req.setSessionText(String.valueOf(Err.CMPADBLOCK_DUPLICATE_ERROR));
		return null;
	}

	/**
	 * 取消页面广告推荐按
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String deladblock(HkRequest req, HkResponse resp) {
		long oid = req.getLong("oid");
		this.cmpModProcessor.deleteCmpAdBlock(oid);
		return null;
	}

	/**
	 * 取消页面广告推荐按
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String deladblock2(HkRequest req, HkResponse resp) {
		long blockId = req.getLong("blockId");
		long companyId = req.getLong("companyId");
		long adid = req.getLong("adid");
		CmpAdBlock cmpAdBlock = this.cmpModService.getCmpAdBlock(companyId,
				adid, blockId);
		if (cmpAdBlock != null) {
			this.cmpModProcessor.deleteCmpAdBlock(cmpAdBlock.getOid());
		}
		return null;
	}

	/**
	 * 创建广告组
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-28
	 */
	public String creategroup(HkRequest req, HkResponse resp) {
		if (this.isForwardPage(req)) {
			req.reSetAttribute("adid");
			return this.getWebPath("admin/cmpad/creategroup.jsp");
		}
		long companyId = req.getLong("companyId");
		CmpAdGroup cmpAdGroup = new CmpAdGroup();
		cmpAdGroup.setCompanyId(companyId);
		cmpAdGroup.setName(req.getHtmlRow("name"));
		int code = cmpAdGroup.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "createerror", null);
		}
		if (this.cmpAdService.createCmpAdGroup(cmpAdGroup)) {
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "createok", cmpAdGroup.getGroupId());
		}
		return this.onError(req, Err.CMPADGROUP_NAME_DUPLICATE, "createerror",
				null);
	}

	/**
	 * 修改广告组
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-28
	 */
	public String updategroup(HkRequest req, HkResponse resp) {
		long groupId = req.getLongAndSetAttr("groupId");
		long companyId = req.getLong("companyId");
		CmpAdGroup cmpAdGroup = this.cmpAdService.getCmpAdGroup(companyId,
				groupId);
		if (cmpAdGroup == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpAdGroup", cmpAdGroup);
			return this.getWebPath("admin/cmpad/updategroup.jsp");
		}
		cmpAdGroup.setCompanyId(companyId);
		cmpAdGroup.setName(req.getHtmlRow("name"));
		int code = cmpAdGroup.validate();
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		if (this.cmpAdService.updateCmpAdGroup(cmpAdGroup)) {
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "updateok", null);
		}
		return this.onError(req, Err.CMPADGROUP_NAME_DUPLICATE, "updateerror",
				null);
	}

	/**
	 * 删除广告组
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-28
	 */
	public String delgroup(HkRequest req, HkResponse resp) {
		long groupId = req.getLong("groupId");
		long companyId = req.getLong("companyId");
		CmpAdGroup cmpAdGroup = this.cmpAdService.getCmpAdGroup(companyId,
				groupId);
		if (cmpAdGroup != null && cmpAdGroup.getCompanyId() == companyId) {
			this.cmpAdService.deleteCmpAdGroup(companyId, groupId);
			this.setDelSuccessMsg(req);
		}
		return null;
	}

	/**
	 * 广告组列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-28
	 */
	public String grouplist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String name = req.getHtmlRow("name");
		SimplePage page = req.getSimplePage(20);
		List<CmpAdGroup> list = this.cmpAdService.getCmpAdGroupListByCompanyId(
				companyId, name, page.getBegin(), page.getSize() + 1);
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		long adid = req.getLongAndSetAttr("adid");
		if (adid > 0) {
			CmpAd cmpAd = this.cmpAdService.getCmpAd(adid);
			req.setAttribute("cmpAd", cmpAd);
		}
		return this.getWebPath("admin/cmpad/grouplist.jsp");
	}

	/**
	 * 设置广告与广告组的关联
	 * 
	 * @param req
	 * @param resp
	 * @return
	 *         2010-6-28
	 */
	public String updatecmpadgroupid(HkRequest req, HkResponse resp) {
		long groupId = req.getLong("groupId");
		long companyId = req.getLong("companyId");
		long adid = req.getLong("adid");
		CmpAd cmpAd = this.cmpAdService.getCmpAd(adid);
		if (cmpAd == null) {
			return null;
		}
		cmpAd.setGroupId(groupId);
		this.cmpAdService.updateCmpAd(cmpAd);
		req.setSessionText("epp.cmpad.updategroupid.ok");
		if (req.getInt("topage") == 1) {
			return "r:/epp/web/op/webadmin/cmpad.do?companyId=" + companyId
					+ "&groupId=" + groupId;
		}
		return null;
	}

	/**
	 * 推荐到页面的广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-27
	 */
	public String selblockforgroup(HkRequest req, HkResponse resp) {
		long groupId = req.getLongAndSetAttr("groupId");
		long companyId = req.getLong("companyId");
		byte pageflg = req.getByteAndSetAttr("pageflg", (byte) 1);
		CmpAdGroup cmpAdGroup = this.cmpAdService.getCmpAdGroup(companyId,
				groupId);
		if (cmpAdGroup == null) {
			return null;
		}
		if (this.isForwardPage(req)) {
			req.setAttribute("cmpAdGroup", cmpAdGroup);
			List<CmpPageBlock> blocklist = this.cmpModProcessor
					.getCmpPageBlockListByCompanyIdAndPageflg(companyId, true,
							(byte) 1);
			req.setAttribute("blocklist", blocklist);
			return this.getWebPath("admin/cmpad/selblockforgroup.jsp");
		}
		long blockId = req.getLong("blockId");
		List<CmpAd> cmpAdList = this.cmpAdService
				.getCmpAdListByCompanyIdAndGroupId(companyId, groupId);
		CmpPageBlock cmpPageBlock = this.cmpModService.getCmpPageBlock(blockId);
		if (cmpPageBlock == null) {
			return null;
		}
		CmpPageMod cmpPageMod = CmpPageModUtil.getCmpPageMod(cmpPageBlock
				.getPageModId());
		if (cmpPageMod == null) {
			return null;
		}
		for (CmpAd cmpAd : cmpAdList) {
			if (pageflg == 1) {// 首页的区块
				if (cmpAd.getPage1BlockId() == 0) {
					CmpAdBlock cmpAdBlock = new CmpAdBlock();
					cmpAdBlock.setCompanyId(companyId);
					cmpAdBlock.setAdid(cmpAd.getAdid());
					cmpAdBlock.setPageflg((byte) 1);
					cmpAdBlock.setBlockId(blockId);
					if (this.cmpModProcessor.createCmpAdBlock(cmpAdBlock)) {
						req.setSessionText("epp.cmpageblock.create.success");
					}
				}
			}
		}
		return null;
	}

	/**
	 * 二级页面的广告列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-6
	 */
	public String reflist(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_23", 1);
		long companyId = req.getLong("companyId");
		SimplePage page = req.getSimplePage(20);
		List<CmpAdRef> list = this.cmpAdProcessor.getCmpAdRefListByCompanyId(
				companyId, true, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpad/reflist.jsp");
	}

	/**
	 * 重新推荐到二级页面广告（为了内容靠前）
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-6
	 */
	public String recmpadref(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		CmpAdRef cmpAdRef = this.cmpAdService.getCmpAdRefByCompanyIdAndOid(
				companyId, oid);
		if (cmpAdRef == null) {
			return null;
		}
		this.cmpAdService.deleteCmpAdRef(companyId, oid);
		cmpAdRef.setOid(0);
		this.cmpAdService.createCmpAdRef(cmpAdRef);
		return null;
	}

	/**
	 * 删除推荐到二级页面的广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-7-6
	 */
	public String delcmpadref(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		long companyId = req.getLong("companyId");
		this.cmpAdService.deleteCmpAdRef(companyId, oid);
		this.setDelSuccessMsg(req);
		return null;
	}
}