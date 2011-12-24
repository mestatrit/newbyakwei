package com.hk.web.company.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.AuthCompany;
import com.hk.bean.BizCircle;
import com.hk.bean.City;
import com.hk.bean.CmpChildKind;
import com.hk.bean.CmpChildKindRef;
import com.hk.bean.CmpSmsPort;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.CmpUnionReq;
import com.hk.bean.Company;
import com.hk.bean.CompanyKind;
import com.hk.bean.CompanyKindUtil;
import com.hk.bean.CompanyTag;
import com.hk.bean.Pcity;
import com.hk.bean.Province;
import com.hk.bean.TmpData;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserTool;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.PageSupport;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BizCircleService;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpSmsPortService;
import com.hk.svr.CmpUnionMessageService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyKindService;
import com.hk.svr.CompanyService;
import com.hk.svr.CompanyTagService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UpdateCompanyResult;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.company.exception.NoAvailableCmpSmsPortException;
import com.hk.svr.processor.CompanyProcessor;
import com.hk.svr.processor.CreateCompanyResult;
import com.hk.svr.pub.CmpUnionMessageUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkSvrUtil;
import com.hk.svr.pub.UserToolConfig;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.company.action.CompanyVo;
import com.hk.web.pub.action.BaseAction;

@Component("/e/op/op")
public class OpCompanyAction extends BaseAction {

	private final Log log = LogFactory.getLog(OpCompanyAction.class);

	@Autowired
	private CompanyService companyService;

	@Autowired
	private BizCircleService bizCircleService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserToolConfig userToolConfig;

	@Autowired
	private CmpSmsPortService cmpSmsPortService;

	@Autowired
	private CompanyKindService companyKindService;

	@Autowired
	private CompanyTagService companyTagService;

	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private CmpUnionMessageService cmpUnionMessageService;

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CompanyProcessor companyProcessor;

	@Autowired
	private TmpDataService tmpDataService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		return "r:/e/op/op_toeditweb.do?companyId=" + companyId;
	}

	/**
	 * 设置周边足迹(必须认领后才能使用，通过拦截器拦截认证)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String tosetnearby(HkRequest req, HkResponse resp) {
		// long companyId = req.getLong("companyId");
		return "/WEB-INF/page/e/op/setnearby.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String tosetmapweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		Company company = o;
		req.setAttribute("company", company);
		req.setAttribute("o", o);
		req.reSetAttribute("companyId");
		req.setAttribute("op_func", 3);
		return this.getWeb3Jsp("e/op/setmap.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String tosetmap(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		req.setAttribute("o", o);
		req.reSetAttribute("companyId");
		return "/WEB-INF/page/e/op/setmap.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setmapweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		double markerX = req.getDouble("marker_x");
		double markerY = req.getDouble("marker_y");
		o.setMarkerX(markerX);
		o.setMarkerY(markerY);
		this.companyService.updateCompany(o);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "set_map");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setmapweb2(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		double markerX = req.getDouble("marker_x");
		double markerY = req.getDouble("marker_y");
		o.setMarkerX(markerX);
		o.setMarkerY(markerY);
		this.companyService.updateCompany(o);
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setmap(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		double markerX = req.getDouble("marker_x");
		double markerY = req.getDouble("marker_y");
		o.setMarkerX(markerX);
		o.setMarkerY(markerY);
		this.companyService.updateCompany(o);
		req.setText("func.setmap_ok");
		String return_url = req.getReturnUrl();
		if (DataUtil.isEmpty(return_url)) {
			return "r:/e/cmp_map.do?companyId=" + companyId;
		}
		return "r:" + return_url;
	}

	/**
	 * 管理我的企业，如果认领的企业只有1个，就直接进入.有多个，就列表显示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String myauthwap(HkRequest req, HkResponse resp) {
		int f = req.getInt("f");
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(size);
		List<Company> list = this.companyService.getCompanyListByUserId(
				loginUser.getUserId(), page.getBegin(), size);
		if (page.getBegin() == 0 && list.size() == 1) {
			return "r:/e/op/op_opauth.do?f=" + f + "&companyId="
					+ list.iterator().next().getCompanyId() + "&pcityId="
					+ req.getInt("pcityId") + "&kind=" + req.getInt("kind")
					+ "&cityId=" + req.getInt("cityId") + "&provinceId="
					+ req.getInt("provinceId") + "&move_oid="
					+ req.getLong("move_oid");
		}
		List<CompanyVo> companyvolist = CompanyVo.createVoList(list);
		req.setAttribute("companyvolist", companyvolist);
		req.reSetAttribute("f");
		req.reSetAttribute("pcityId");
		req.reSetAttribute("kind");
		req.reSetAttribute("cityId");
		req.reSetAttribute("provinceId");
		req.reSetAttribute("move_oid");
		return "/WEB-INF/page/e/op/myauth.jsp";
	}

	/**
	 * 管理我的企业，如果认领的企业只有1个，就直接进入.有多个，就列表显示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String myauth(HkRequest req, HkResponse resp) {
		// if (this.isPcBrowse(req)) {
		// return this.myauthweb(req, resp);
		// }
		return this.myauthwap(req, resp);
	}

	/**
	 * pc 显示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String myauthweb(HkRequest req, HkResponse resp) {
		String query_str = req.getQueryString();
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(size);
		List<Company> list = this.companyService.getCompanyListByUserId(
				loginUser.getUserId(), page.getBegin(), size);
		if (page.getBegin() == 0 && list.size() == 1) {
			return "r:/e/op/op_opauth.do?" + query_str;
		}
		List<CompanyVo> companyvolist = CompanyVo.createVoList(list);
		req.setAttribute("companyvolist", companyvolist);
		req.setAttribute("query_str", query_str);
		return this.getWeb2Jsp("e/op/myauth.jsp");
	}

	/**
	 * 控制操作去向
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String opauth(HkRequest req, HkResponse resp) {
		int f = req.getInt("f");
		long companyId = req.getLong("companyId");
		if (f == 1) {// 发行企业宝箱
			return "r:/box/op/op.do?companyId=" + companyId;
		}
		if (f == 2) {// 修改企业信息
			return "r:/e/op/op_toedit.do?companyId=" + companyId;
		}
		if (f == 3) {// 补充图片
			return "r:/e/op/photo/photo_toadd.do?companyId=" + companyId;
		}
		if (f == 4) {// 管理图片
			return "r:/e/op/photo/photo_smallphoto.do?companyId=" + companyId;
		}
		if (f == 5) {// 管理企业内容分类
			return "r:/e/op/product/op_sortlist.do?companyId=" + companyId;
		}
		if (f == 6) {// 产品管理
			return "r:/e/op/product/op_productlist.do?companyId=" + companyId;
		}
		if (f == 7) {// 地图设置
			return "r:/e/op/op_tosetmap.do?companyId=" + companyId;
		}
		if (f == 8) {// 设置首页竞价排名
			return "r:/e/op/cmporder_toeditorder.do?companyId=" + companyId
					+ "&cityId=" + req.getInt("cityId");
		}
		if (f == 9) {// 设置关键词竞价排名
			return "r:/e/op/cmporder_findkeytag.do?companyId=" + companyId;
		}
		if (f == 10) {// 设置首页竞价排名
			return "r:/e/op/cmporder_toeditorder.do?companyId=" + companyId
					+ "&cityId=" + req.getInt("cityId") + "&move_oid="
					+ req.getLong("move_oid");
		}
		if (f == 11) {// 设置分类竞价排名
			return "r:/e/op/cmporder_toeditkindorder.do?companyId=" + companyId
					+ "&cityId=" + req.getInt("cityId") + "&move_oid="
					+ req.getLong("move_oid");
		}
		return null;
	}

	/**
	 * 创建企业
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toaddweb(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		UserTool userTool = this.userService.checkUserTool(loginUser
				.getUserId());
		if (userTool.getGroundCount() <= 0) {// 地皮数量不足，不能创建
			resp.alertJSAndGoBack(req.getText("func.noenoughground"));
			return null;
		}
		Company lastCmp = this.companyService.getLastCreateCompany(loginUser
				.getUserId());
		req.setAttribute("lastCmp", lastCmp);
		int cityId = req.getInt("cityId");
		int provinceId = req.getInt("provinceId");
		req.setAttribute("cityId", cityId);
		req.setAttribute("provinceId", provinceId);
		return this.getWeb3Jsp("e/op/add.jsp");
	}

	/**
	 * 创建企业
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toadd(HkRequest req, HkResponse resp) {
		// UserTool userTool = this.userService.checkUserTool(loginUser
		// .getUserId());
		// if (userTool.getGroundCount() <= 0) {
		// req.setSessionMessage(req.getText("func.noenoughground"));
		// return "r:/more.do";
		// }
		Company o = (Company) req.getAttribute("o");
		String zoneName = req.getString("zoneName");
		if (zoneName == null) {
			zoneName = (String) req.getSessionValue("zoneName");
			req.removeSessionvalue("zoneName");
			if (zoneName == null) {
				zoneName = this.getZoneNameFromIdP(req.getRemoteAddr());
			}
			if (zoneName == null) {
				User loginUser = this.getLoginUser(req);
				City city = ZoneUtil.getCity(loginUser.getPcityId());
				if (city != null) {
					zoneName = city.getCity();
				}
			}
		}
		req.setAttribute("zoneName", zoneName);
		if (o == null) {
			long tmpoid = req.getLong("tmpoid");
			if (tmpoid > 0) {// 从临时数据中恢复
				TmpData tmpData = this.tmpDataService.getTmpData(tmpoid);
				if (tmpData != null
						&& tmpData.getDatatype() == TmpData.DATATYPE_CMP) {
					Map<String, String> map = DataUtil.getMapFromJson(tmpData
							.getData());
					o = new Company();
					o.setName(map.get("name"));
					o.setTel(map.get("tel"));
					o.setAddr(map.get("addr"));
				}
			}
		}
		req.setAttribute("o", o);
		String name = null;
		if (o != null) {
			name = o.getName();
		}
		else {
			name = req.getString("name");
		}
		req.setAttribute("name", name);
		return this.getWapJsp("e/op/addcompany.jsp");
	}

	/**
	 * 创建企业
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addweb(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		UserTool userTool = this.userService.checkUserTool(loginUser
				.getUserId());
		if (userTool.getGroundCount() <= 0) {
			return this.initError(req, Err.NOENOUGH_GROUND, "add_company");
		}
		int pcityId = req.getInt("pcityId");
		String name = req.getString("name");
		String addr = req.getString("addr");
		String tel = req.getString("tel");
		String intro = req.getString("intro");
		String traffic = req.getString("traffic");
		// int kindId = req.getInt("kindId");
		// int childKindId = req.getInt("childKindId");
		Company o = new Company();
		o.setPcityId(pcityId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTel(DataUtil.toHtmlRow(tel));
		o.setAddr(DataUtil.toHtml(addr));
		o.setIntro(DataUtil.toHtml(intro));
		// o.setKindId(kindId);
		o.setCreaterId(loginUser.getUserId());
		o.setTraffic(DataUtil.toHtml(traffic));
		req.setAttribute("o", o);
		int code = o.validate(true);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "add_company");
		}
		this.companyProcessor.createCompany(o, req.getRemoteAddr());
		userTool.addGroundCount(-this.userToolConfig
				.getCreateCompanyAddGroundCount());
		// 扣除相应的地皮数量
		this.userService.updateuserTool(userTool);
		this.setOpFuncSuccessMsg(req);
		return this.onSuccess(req, o.getCompanyId() + "", "add_company");
	}

	/**
	 * 创建企业
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String add(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		// UserTool userTool = this.userService.checkUserTool(loginUser
		// .getUserId());
		// if (userTool.getGroundCount() <= 0) {
		// req.setSessionMessage(req.getText("func.noenoughground"));
		// return "r:/more.do";
		// }
		String name = req.getString("name");
		String addr = req.getString("addr");
		String tel = req.getString("tel");
		String zoneName = req.getStringAndSetAttr("zoneName");
		Company o = new Company();
		o.setName(DataUtil.toHtmlRow(name));
		o.setTel(DataUtil.toHtmlRow(tel));
		o.setAddr(DataUtil.toHtml(addr));
		o.setCreaterId(loginUser.getUserId());
		req.setAttribute("o", o);
		int code = o.validate(false);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/e/op/op_toadd.do";
		}
		CreateCompanyResult createCompanyResult = this.companyProcessor
				.createCompanyForWap(o, zoneName, req.getRemoteAddr());
		if (createCompanyResult.getErrorCode() != Err.SUCCESS) {
			if (createCompanyResult.getErrorCode() == Err.ZONE_NAME_ERROR) {
				if (createCompanyResult.getProvinceId() > 0) {// 到省下的城市中
					req.setSessionText("view2.cannotfindcityandselect");
					return "r:/index_selcityfromprovince.do?provinceId="
							+ createCompanyResult.getProvinceId()
							+ "&forsel=1"
							+ "&return_url="
							+ DataUtil.urlEncoder("/e/op/op_toadd.do?"
									+ "tmpoid=" + createCompanyResult.getOid());
				}
			}
			req.setText(String.valueOf(createCompanyResult.getErrorCode()));
			return "/e/op/op_toadd.do";
		}
		// userTool.addGroundCount(-this.userToolConfig
		// .getCreateCompanyAddGroundCount());
		// // 扣除相应的地皮数量
		// this.userService.updateuserTool(userTool);
		req.setText("func.company.create.success");
		return "r:/e/cmp.do?companyId=" + o.getCompanyId();
	}

	/**
	 * 修改企业信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toedit(HkRequest req, HkResponse resp) {
		return this.toeditwap(req, resp);
	}

	/**
	 * 修改企业信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toeditwap(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = (Company) req.getAttribute("o");
		if (o == null) {
			o = this.companyService.getCompany(companyId);
		}
		if (o == null) {
			return null;
		}
		List<BizCircle> circlelist = this.companyService
				.getBizCircleByCompanyId(companyId);
		req.setAttribute("o", o);
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(o.getKindId());
		req.setAttribute("companyKind", companyKind);
		req.setAttribute("circlelist", circlelist);
		req.reSetAttribute("companyId");
		req.setAttribute("pcity", ZoneUtil.getPcity(o.getPcityId()));
		CmpSmsPort cmpSmsPort = this.cmpSmsPortService
				.getCmpSmsPortByCompanyId(companyId);
		if (cmpSmsPort != null) {
			req.setAttribute("companyport", this.getCompanyPort(cmpSmsPort
					.getPort()));
		}
		req.setAttribute("cmpSmsPort", cmpSmsPort);
		String zoneName = req.getString("zoneName");
		if (zoneName == null) {
			zoneName = (String) req.getSessionValue("zoneName");
			req.removeSessionvalue("zoneName");
		}
		if (zoneName == null) {
			City city = ZoneUtil.getCity(o.getPcityId());
			if (city != null) {
				zoneName = city.getCity();
			}
		}
		req.setAttribute("zoneName", zoneName);
		return "/WEB-INF/page/e/op/editcompany.jsp";
	}

	/**
	 * 标签管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String addtag(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		int tagId = req.getInt("tagId");
		this.companyTagService.createCompanyTagRef(companyId, tagId, company
				.getPcityId());
		this.setOpFuncSuccessMsg(req);
		return "r:/e/op/op_taglist.do?companyId=" + companyId;
	}

	/**
	 * 标签管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String deltag(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		int tagId = req.getInt("tagId");
		this.companyTagService.deleteCompanyTagRef(companyId, tagId, company
				.getPcityId());
		this.setOpFuncSuccessMsg(req);
		return "r:/e/op/op_taglist.do?companyId=" + companyId;
	}

	/**
	 * 标签管理
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String taglist(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		List<CompanyTag> list = this.companyTagService
				.getCompanyTagListByCompanyId(companyId);
		List<Integer> idList = new ArrayList<Integer>();
		for (CompanyTag t : list) {
			idList.add(t.getTagId());
		}
		Company company = this.companyService.getCompany(companyId);
		List<CompanyTag> alllist = this.companyTagService
				.getCompanyTagListExcept(company.getKindId(), idList);
		req.setAttribute("list", list);
		req.setAttribute("alllist", alllist);
		req.setAttribute("companyId", companyId);
		req.setAttribute("op_func", 9);
		return this.getWeb3Jsp("e/op/taglist.jsp");
	}

	/**
	 * 修改企业信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toeditweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		if (o == null) {
			return null;
		}
		List<BizCircle> circlelist = this.companyService
				.getBizCircleByCompanyId(companyId);
		req.setAttribute("o", o);
		req.setAttribute("pcityId", o.getPcityId());
		CompanyKind companyKind = CompanyKindUtil.getCompanyKind(o.getKindId());
		req.setAttribute("companyKind", companyKind);
		req.setAttribute("circlelist", circlelist);
		req.reSetAttribute("companyId");
		req.setAttribute("op_func", 0);
		List<CmpChildKind> cmpchildkindlist = this.companyKindService
				.getCmpChildKindList(0);
		req.setAttribute("cmpchildkindlist", cmpchildkindlist);
		CmpChildKindRef cmpChildKindRef = this.companyKindService
				.getCmpChildKindRefByCompanyId(companyId);
		int childKindId = 0;
		if (cmpChildKindRef != null) {
			childKindId = cmpChildKindRef.getOid();
		}
		req.setAttribute("childKindId", childKindId);
		// 如果已经加入联盟，可以修改联盟分类
		if (o.getUid() > 0) {
			List<CmpUnionKind> cmpunionkindlist = this.cmpUnionService
					.getLastLevelCmpUnionKindListByUid(o.getUid());
			req.setAttribute("cmpunionkindlist", cmpunionkindlist);
		}
		return this.getWeb3Jsp("e/op/editcompany.jsp");
	}

	/**
	 * 生成足迹通道号码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createCompanySmsPort(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		try {
			CmpSmsPort cmpSmsPort = this.cmpSmsPortService
					.createAvailableCmpSmsPort(companyId);
			req.setAttribute("cmpSmsPort", cmpSmsPort);
			req.setSessionText("func.cmpsmsport.create.success");
		}
		catch (NoAvailableCmpSmsPortException e) {
			log.warn(e.getMessage());
			req.setSessionText("func.cmpsmsport.create.fail_nosmsport");
		}
		return "r:/e/op/op_toedit.do?companyId=" + companyId;
	}

	/**
	 * 修改企业信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String editweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		String addr = req.getString("addr");
		String tel = req.getString("tel");
		String intro = req.getString("intro");
		String traffic = req.getString("traffic");
		double price = req.getDouble("price");
		int kindId = req.getInt("kindId");
		int unionKindId = req.getInt("unionKindId");
		int childKindId = req.getInt("childKindId");
		double rebate = req.getDouble("rebate");
		int pcityId = req.getInt("pcityId");
		Company o = this.companyService.getCompany(companyId);
		int old_pcityId = o.getPcityId();
		o.setUnionKindId(unionKindId);
		o.setPrice(price);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTel(DataUtil.toHtmlRow(tel));
		o.setAddr(DataUtil.toHtml(addr));
		o.setIntro(DataUtil.toHtml(intro));
		o.setKindId(kindId);
		o.setTraffic(DataUtil.toHtml(traffic));
		o.setRebate(rebate);
		o.setPcityId(pcityId);
		int code = o.validate(true);
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "edit_company");
		}
		this.companyService.updateCompany(o);
		if (childKindId > 0) {
			this.companyKindService.createCmpChildKindRef(childKindId,
					companyId, o.getPcityId());
		}
		if (old_pcityId != o.getPcityId()) {
			this.cmpProductService.updateCmpProductPcityIdByCompanyId(
					companyId, o.getPcityId());
		}
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "edit_company");
	}

	/**
	 * 创建企业
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String edit(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String zoneName = req.getStringAndSetAttr("zoneName");
		String name = req.getString("name");
		String addr = req.getString("addr");
		String tel = req.getString("tel");
		String intro = req.getString("intro");
		String traffic = req.getString("traffic");
		double price = req.getDouble("price");
		int kindId = req.getInt("kindId");
		double rebate = req.getDouble("rebate");
		Company o = this.companyService.getCompany(companyId);
		int old_pcityId = o.getPcityId();
		o.setPrice(price);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTel(DataUtil.toHtmlRow(tel));
		o.setAddr(DataUtil.toHtml(addr));
		o.setIntro(DataUtil.toHtml(intro));
		o.setKindId(kindId);
		o.setTraffic(DataUtil.toHtml(traffic));
		o.setRebate(rebate);
		req.setAttribute("o", o);
		int code = o.validate(false);
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/e/op/op_toedit.do";
		}
		UpdateCompanyResult updateCompanyResult = this.companyProcessor
				.updateCompanyForWap(o, zoneName, old_pcityId);
		if (updateCompanyResult.getErrorCode() != Err.SUCCESS) {
			if (updateCompanyResult.getErrorCode() == Err.ZONE_NAME_ERROR) {
				if (updateCompanyResult.getProvinceId() > 0) {// 到省下的城市中
					req.setSessionText("view2.cannotfindcityandselect");
					return "r:/index_selcityfromprovince.do?provinceId="
							+ updateCompanyResult.getProvinceId()
							+ "&forsel=1"
							+ "&return_url="
							+ DataUtil
									.urlEncoder("/e/op/op_toedit.do?companyId="
											+ companyId);
				}
			}
			req.setText(String.valueOf(updateCompanyResult.getErrorCode()));
			return "/e/op/op_toedit.do";
		}
		this.setOpFuncSuccessMsg(req);
		return "r:/e/cmp.do?companyId=" + o.getCompanyId();
	}

	/**
	 * 给企业添加商圈
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String searchcity(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		int provinceId = req.getInt("provinceId");
		if (provinceId > 0) {
			return "r:/e/op/op_searchcityfromp.do?provinceId=" + provinceId
					+ "&companyId=" + companyId;
		}
		String name = req.getString("name");
		if (!DataUtil.isEmpty(name)) {
			name = DataUtil.filterZoneName(name);
			List<City> clist = this.zoneService.getCityList(name);// 查看是否在地区中有
			if (clist.size() > 0) {
				return "r:/e/op/op_bizcirclelist.do?cityId="
						+ clist.iterator().next().getCityId() + "&companyId="
						+ companyId;
			}
			List<Province> plist = this.zoneService.getProvinceList(name);// 查看是否在省市中有
			if (plist.size() > 0) {
				return "r:/e/op/op_bizcirclelist.do?provinceId="
						+ plist.iterator().next().getProvinceId()
						+ "&companyId=" + companyId;
				// return "r:/e/op/op_searchcityfromp.do?provinceId="
				// + plist.iterator().next().getProvinceId()
				// + "&companyId=" + companyId;
			}
			req.setEncodeAttribute("noresult", true);
		}
		req.reSetAttribute("companyId");
		req.setEncodeAttribute("name", name);
		return "/WEB-INF/page/e/op/searchcity.jsp";
	}

	/**
	 * 列出商圈供用户选择
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String bizcirclelist(HkRequest req, HkResponse resp) {
		int provinceId = req.getInt("provinceId");
		int cityId = req.getInt("cityId");
		SimplePage page = req.getSimplePage(50);
		List<BizCircle> list = this.bizCircleService.getBizCircleList(null,
				cityId, provinceId);
		page.setListSize(list.size());
		req.setAttribute("list", list);
		req.reSetAttribute("companyId");
		req.reSetAttribute("provinceId");
		req.setAttribute("cityId", cityId);
		return "/WEB-INF/page/e/op/bizcirclelist.jsp";
	}

	/**
	 * 根据省份id列出城市
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String searchcityfromp(HkRequest req, HkResponse resp) {
		int provinceId = req.getInt("provinceId");
		List<City> list = this.zoneService.getCityList(provinceId);
		req.setAttribute("list", list);
		req.reSetAttribute("companyId");
		req.reSetAttribute("provinceId");
		return "/WEB-INF/page/e/op/searchcityfromp.jsp";
	}

	/**
	 * 给企业添加商圈
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toselbizcircleweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		Pcity pcity = ZoneUtil.getPcity(company.getPcityId());
		if (pcity == null) {
			req.setSessionText("func.nocityzone");
			return "r:/e/op/op_toeditweb.do?companyId=" + companyId;
		}
		List<BizCircle> bclist = this.bizCircleService.getBizCircleList(null,
				pcity.getOcityId(), pcity.getProvinceId());
		List<BizCircle> bclist2 = this.companyService
				.getBizCircleByCompanyId(companyId);
		// 删除已经添加过的商圈，只显示未添加的商圈
		Iterator<BizCircle> it = bclist.iterator();
		while (it.hasNext()) {
			BizCircle o = it.next();
			for (BizCircle oo : bclist2) {
				if (o.getCircleId() == oo.getCircleId()) {
					it.remove();
					break;
				}
			}
		}
		req.setAttribute("bclist", bclist);
		req.setAttribute("bclist2", bclist2);
		req.setAttribute("companyId", companyId);
		req.setAttribute("bclist", bclist);
		req.setAttribute("op_func", 20);
		return this.getWeb3Jsp("e/op/selbizcircle.jsp");
	}

	/**
	 * 给企业添加商圈
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String selbizcircle(HkRequest req, HkResponse resp) {
		int circleId = req.getInt("circleId");
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		this.bizCircleService.createCompanyBizCircle(companyId, circleId);
		req.setSessionText("op.addcircleok");
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * 给企业添加商圈
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String selbizcircleweb(HkRequest req, HkResponse resp) {
		int circleId = req.getInt("circleId");
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		this.bizCircleService.createCompanyBizCircle(companyId, circleId);
		this.setOpFuncSuccessMsg(req);
		return "r:/e/op/op_toselbizcircleweb.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delbizcircle(HkRequest req, HkResponse resp) {
		int circleId = req.getInt("circleId");
		long companyId = req.getLong("companyId");
		this.bizCircleService.deleteCompanyBizCircle(companyId, circleId);
		return "r:/e/op/op_toedit.do?companyId=" + companyId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String delbizcircleweb(HkRequest req, HkResponse resp) {
		int circleId = req.getInt("circleId");
		long companyId = req.getLong("companyId");
		this.bizCircleService.deleteCompanyBizCircle(companyId, circleId);
		this.setOpFuncSuccessMsg(req);
		return "r:/e/op/op_toselbizcircleweb.do?companyId=" + companyId;
	}

	/**
	 * 到申请认领足迹页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toApplyAuth(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		Company o = this.companyService.getCompany(companyId);
		req.setAttribute("o", o);
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/op/applyauth.jsp";
	}

	/**
	 * 到申请认领足迹页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toApplyAuth2(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		req.setAttribute("companyId", companyId);
		return "/WEB-INF/page/e/op/applyauth2.jsp";
	}

	/**
	 * 申请认领足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String applyAuth(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		if (HkSvrUtil.isNotCompany(companyId)) {
			return null;
		}
		UserOtherInfo info = this.userService.getUserOtherInfo(this
				.getLoginUser(req).getUserId());
		if (!info.isMobileAlreadyBind()) {
			req.setSessionMessage(req.getText("func.mobilenotbind"));
			return "r:/e/cmp.do?companyId=" + companyId;
		}
		String content = req.getString("content");
		AuthCompany o = new AuthCompany();
		o.setCompanyId(companyId);
		o.setUserId(this.getLoginUser(req).getUserId());
		o.setContent(DataUtil.toHtmlRow(content));
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/e/op/op_toApplyAuth2.do";
		}
		this.companyService.createAuthCompany(o);
		req.setSessionMessage(req.getText("func.applyauthinfo_submit_ok"));
		return "r:/e/cmp.do?companyId=" + companyId;
	}

	/**
	 * 设置企业搜索产品的默认方式
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String tosetsearchtype(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company o = this.companyService.getCompany(companyId);
		req.setAttribute("o", o);
		req.setAttribute("op_func", 14);
		return this.getWeb3Jsp("/e/op/setsearchtype.jsp");
	}

	/**
	 * 设置企业搜索产品的默认方式
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setsearchtype(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		byte psearchType = req.getByte("psearchType");
		this.companyService.updatePsearchType(companyId, psearchType);
		this.setOpFuncSuccessMsg(req);
		return this.initSuccess(req, "search");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cmpunionlist(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		Company company = this.companyService.getCompany(companyId);
		if (company.getUid() > 0) {
			CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(company
					.getUid());
			req.setAttribute("cmpUnion", cmpUnion);
		}
		String name = req.getString("name");
		int pcityId = req.getIntAndSetAttr("pcityId");
		PageSupport page = req.getPageSupport(20);
		page.setTotalCount(this.cmpUnionService.countCmpUnionLikeName(pcityId,
				name));
		List<CmpUnion> list = this.cmpUnionService.getCmpUnionListLikeName(
				pcityId, name, page.getBegin(), page.getSize());
		req.setAttribute("list", list);
		req.setEncodeAttribute("name", name);
		req.reSetAttribute("s");
		// List<Pcity> clist = zoneService.getPcityListByCountryId(1);// 选取中国
		// List<Province> provincelist = zoneService.getProvinceList(1);
		// req.setAttribute("provincelist", provincelist);
		// req.setAttribute("clist", clist);
		return this.getWeb3Jsp("e/op/cmpunion.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String requestjoinincmpunion(HkRequest req, HkResponse resp) {
		long companyId = req.getLongAndSetAttr("companyId");
		long uid = req.getLongAndSetAttr("uid");
		Company company = this.companyService.getCompany(companyId);
		CmpUnionReq cmpUnionReq = new CmpUnionReq();
		cmpUnionReq.setUid(uid);
		cmpUnionReq.setObjId(companyId);
		cmpUnionReq.setReqflg(CmpUnionMessageUtil.REQ_JOIN_IN_CMPUNION);
		cmpUnionReq.setDealflg(CmpUnionReq.DEALFLG_N);
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", company.getName());
		cmpUnionReq.setData(DataUtil.toJson(map));
		this.cmpUnionMessageService.createCmpUnionReq(cmpUnionReq);
		req.setSessionText("func.company.joincmpunionreq");
		return null;
	}
}