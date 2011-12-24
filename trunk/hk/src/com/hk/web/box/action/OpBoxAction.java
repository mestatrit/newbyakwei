package com.hk.web.box.action;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Box;
import com.hk.bean.BoxOpenType;
import com.hk.bean.BoxPretype;
import com.hk.bean.BoxPrize;
import com.hk.bean.BoxType;
import com.hk.bean.City;
import com.hk.bean.Company;
import com.hk.bean.Equipment;
import com.hk.bean.HkbLog;
import com.hk.bean.ObjPhoto;
import com.hk.bean.TmpData;
import com.hk.bean.User;
import com.hk.bean.UserCmpFunc;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.sms.cmd.CmdFactory;
import com.hk.svr.BoxService;
import com.hk.svr.CompanyService;
import com.hk.svr.ObjPhotoService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UserCmpFuncService;
import com.hk.svr.UserService;
import com.hk.svr.box.exception.BoxKeyDuplicateException;
import com.hk.svr.box.exception.PrizeCountMoreThanBoxCountException;
import com.hk.svr.box.exception.PrizeRemainException;
import com.hk.svr.box.exception.PrizeTotalCountException;
import com.hk.svr.processor.BoxProcessor;
import com.hk.svr.processor.CreateBoxResult;
import com.hk.svr.processor.UpdateBoxResult;
import com.hk.svr.pub.BoxOpenTypeUtil;
import com.hk.svr.pub.BoxPretypeUtil;
import com.hk.svr.pub.BoxTypeUtil;
import com.hk.svr.pub.EquipmentConfig;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.HkbConfig;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/box/op/op")
public class OpBoxAction extends BaseAction {

	private final Log log = LogFactory.getLog(OpBoxAction.class);

	private int size = 20;

	@Autowired
	private BoxService boxService;

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private BoxProcessor boxProcessor;

	@Autowired
	private ObjPhotoService objPhotoService;

	@Autowired
	private TmpDataService tmpDataService;

	@Autowired
	private UserCmpFuncService userCmpFuncService;

	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 到发布宝箱页面
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		User loginUser = this.getLoginUser(req);
		List<Company> companylist = this.companyService
				.getCompanyListByUserId(loginUser.getUserId());
		if (companylist.size() == 0) {// 没有认领企业，则默认个人发布宝箱
			return "r:/box/op/op_toadd.do";
		}
		req.setAttribute("companylist", companylist);
		long companyId = req.getLong("companyId");
		req.reSetAttribute("companyId");
		if (companyId > 0) {
			return "r:/box/op/op_toadd.do?companyId=" + companyId;
		}
		return "/WEB-INF/page/box/op/selcreatebox.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String web(HkRequest req, HkResponse resp) throws Exception {
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		User loginUser = this.getLoginUser(req);
		List<Company> companylist = this.companyService
				.getCompanyListByUserId(loginUser.getUserId());
		if (companylist.size() == 0) {// 没有认领企业，则默认个人发布宝箱
			return "r:/box/op/op_toaddweb.do";
		}
		req.setAttribute("companylist", companylist);
		long companyId = req.getLong("companyId");
		req.reSetAttribute("companyId");
		if (companyId > 0) {
			return "r:/box/op/op_toaddweb.do?companyId=" + companyId;
		}
		// return "/WEB-INF/page/box/op/selcreatebox.jsp";
		return this.getWeb3Jsp("box/op/selcmp.jsp");
	}

	/**
	 * 到创建宝箱的页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toadd(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		UserCmpFunc userCmpFunc = this.userCmpFuncService
				.getUserCmpFunc(loginUser.getUserId());
		if (userCmpFunc == null || !userCmpFunc.isBoxOpen()) {
			req.setMessage("你没有权限发布宝箱");
			return "r:/more.do";
		}
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("companyId", companyId);
		req.setAttribute("company", company);
		Box o = (Box) req.getAttribute("o");
		req.setAttribute("admin", this.isAdminUser(req));
		String zoneName = req.getString("zoneName");
		if (zoneName == null) {
			zoneName = (String) req.getSessionValue("zoneName");
			req.removeSessionvalue("zoneName");
			if (zoneName == null) {
				zoneName = this.getZoneNameFromIdP(req.getRemoteAddr());
			}
			if (zoneName == null) {
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
						&& tmpData.getDatatype() == TmpData.DATATYPE_BOX) {
					Map<String, String> map = DataUtil.getMapFromJson(tmpData
							.getData());
					o = new Box();
					o.setName(map.get("name"));
					o.setTotalCount(Integer.valueOf(map.get("totalcount")));
					o.setBoxKey(map.get("boxkey"));
					o
							.setBeginTime(new Date(Long.valueOf(map
									.get("begintime"))));
					o.setEndTime(new Date(Long.valueOf(map.get("endtime"))));
					o.setUserId(this.getLoginUser(req).getUserId());
					o.setPrecount(Integer.valueOf(map.get("precount")));
					o.setPretype(Byte.valueOf(map.get("pretype")));
					o.setIntro(DataUtil.toHtml(map.get("intro")));
					o.setOpentype(Byte.valueOf(map.get("opentype")));
					o.setCompanyId(Long.valueOf(map.get("companyid")));
					o.setVirtualflg(Byte.valueOf(map.get("virtualflg")));
					req.setAttribute("o", o);
				}
			}
		}
		return "/WEB-INF/page/box/op/create.jsp";
	}

	/**
	 * 到创建宝箱的页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toaddweb(HkRequest req, HkResponse resp) throws Exception {
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		long companyId = req.getLong("companyId");
		Company company = this.companyService.getCompany(companyId);
		req.setAttribute("companyId", companyId);
		req.setAttribute("company", company);
		// return "/WEB-INF/page/box/op/create.jsp";
		return this.getWeb3Jsp("box/op/add.jsp");
	}

	/**
	 * 编辑宝箱信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditbox(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getInt("boxId");
		req.reSetAttribute("t");
		req.reSetAttribute("boxId");
		req.reSetAttribute("repage");
		req.reSetAttribute("fromadmin");
		Box o = (Box) req.getAttribute("o");
		req.setAttribute("admin", this.isAdminUser(req));
		if (o == null) {
			o = this.boxService.getBox(boxId);
		}
		if (o.getBoxStatus() == Box.BOX_STATUS_NORMAL) {
			return "/WEB-INF/page/box/op/confirmpauseforedit.jsp";
		}
		req.setAttribute("o", o);
		String zoneName = req.getString("zoneName");
		if (zoneName == null) {
			zoneName = (String) req.getSessionValue("zoneName");
			req.removeSessionvalue("zoneName");
			// if (zoneName == null) {
			// zoneName = this.getZoneNameFromIdP(req.getRemoteAddr());
			// }
		}
		if (zoneName == null) {
			City city = ZoneUtil.getCity(o.getCityId());
			if (city != null) {
				zoneName = city.getCity();
			}
		}
		req.setAttribute("zoneName", zoneName);
		List<BoxType> typeList = BoxTypeUtil.getTypeList();
		List<BoxOpenType> opentypelist = BoxOpenTypeUtil.getTypeList();
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		req.setAttribute("typeList", typeList);
		req.setAttribute("opentypelist", opentypelist);
		return "/WEB-INF/page/box/op/editbox.jsp";
	}

	/**
	 * 编辑宝箱信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toeditboxweb(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getInt("boxId");
		req.reSetAttribute("boxId");
		Box o = this.boxService.getBox(boxId);
		if (o.getBoxStatus() == Box.BOX_STATUS_NORMAL) {
			this.boxService.pauseBox(boxId);
		}
		req.setAttribute("o", o);
		return this.getWeb3Jsp("box/op/edit.jsp");
	}

	/**
	 * 停止
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String confirmpauseforedit(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String cancel = req.getString("cancel");
		if (cancel == null) {
			if (!this.hasOpBoxPower(req)) {
				return "r:/more.do";
			}
			this.boxService.pauseBox(boxId);
			return "/box/op/op_toeditbox.do";
		}
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&repage=" + req.getInt("repage")
				+ "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String editbox(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return null;
		}
		long boxId = req.getInt("boxId");
		String name = req.getString("name");
		String intro = req.getString("intro");
		int totalCount = req.getInt("totalCount");
		String boxKey = req.getString("boxKey");
		String begint = req.getString("begint");
		String endt = req.getString("endt");
		int precount = req.getInt("precount");
		int boxType = req.getInt("boxType");
		if (precount < 0) {
			precount = 0;
		}
		byte pretype = req.getByte("pretype");
		byte opentype = req.getByte("opentype");
		Date beginTime = DataUtil.parseTime(begint, "yyyyMMddHHmm");
		Date endTime = DataUtil.parseTime(endt, "yyyyMMddHHmm");
		Box o = this.boxService.getBox(boxId);
		int oldTotalCount = o.getTotalCount();
		if (this.isAdminUser(req)) {
			byte otherPrizeflg = req.getByte("otherPrizeflg");
			o.setOtherPrizeflg(otherPrizeflg);
		}
		o.setBoxType(boxType);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTotalCount(totalCount);
		o.setBoxKey(DataUtil.toHtmlRow(boxKey));
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setPrecount(precount);
		o.setPretype(pretype);
		o.setIntro(DataUtil.toHtml(intro));
		o.setOpentype(opentype);
		req.setAttribute("o", o);
		int code = o.validate(CmdFactory.getKeyList());
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/box/op/op_toeditbox.do";
		}
		try {
			UpdateBoxResult updateBoxResult = this.boxProcessor.updateBox(o,
					req.getStringAndSetAttr("zoneName"), oldTotalCount,
					totalCount, true);
			if (updateBoxResult.getErrorCode() != Err.SUCCESS) {
				if (updateBoxResult.getErrorCode() == Err.ZONE_NAME_ERROR) {
					if (updateBoxResult.getProvinceId() > 0) {// 到省下的城市中
						return "r:/index_selcityfromprovince.do?provinceId="
								+ updateBoxResult.getProvinceId()
								+ "&forsel=1"
								+ "&return_url="
								+ DataUtil
										.urlEncoder("/box/op/op_toeditbox.do?boxId="
												+ boxId
												+ "&t="
												+ req.getInt("t")
												+ "&repage="
												+ req.getInt("repage")
												+ "&fromadmin="
												+ req.getInt("fromadmin"));
					}
				}
				req.setText(String.valueOf(updateBoxResult.getErrorCode()));
				return "/box/op/op_toeditbox.do";
			}
		}
		catch (BoxKeyDuplicateException e) {
			req.setMessage(req.getText("func.boxkey_alread_exist"));
			return "/box/op/op_toeditbox.do";
		}
		catch (PrizeCountMoreThanBoxCountException e) {
			req
					.setMessage(req
							.getText("func.boxkey_totalcount_smaller_than_prizetotalcount"));
			return "/box/op/op_toeditbox.do";
		}
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&repage=" + req.getInt("repage")
				+ "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String editboxweb(HkRequest req, HkResponse resp) {
		long boxId = req.getInt("boxId");
		String name = req.getString("name");
		String intro = req.getString("intro");
		int totalCount = req.getInt("totalCount");
		String boxKey = req.getString("boxKey");
		String begint = req.getString("begint");
		String endt = req.getString("endt");
		int begint_hour = req.getInt("begint_hour");
		int endt_hour = req.getInt("endt_hour");
		int begint_min = req.getInt("begint_min");
		int endt_min = req.getInt("endt_min");
		int precount = req.getInt("precount");
		if (precount < 0) {
			precount = 0;
		}
		byte pretype = req.getByte("pretype");
		byte opentype = req.getByte("opentype");
		int boxType = req.getInt("boxType");
		Date beginTime = null;
		Date endTime = null;
		try {
			if (begint != null) {
				beginTime = sdf1.parse(begint + " " + begint_hour + ":"
						+ begint_min);
			}
		}
		catch (ParseException e) {
			log.error("error beginTime date format [ " + begint + " ]");
		}
		try {
			if (endt != null) {
				endTime = sdf1.parse(endt + " " + endt_hour + ":" + endt_min);
			}
		}
		catch (ParseException e) {
			log.error("error endTime date format [ " + endt + " ]");
		}
		Box o = this.boxService.getBox(boxId);
		// 如果箱子已经废除，就不能更新数据
		if (o.isStop()) {
			return null;
		}
		int remainBoxCount = o.getTotalCount() - totalCount;
		// 检查箱子数量是否变化，如果有变化，就更新相应的酷币值
		if (remainBoxCount != 0) {
			int addHkb = remainBoxCount;
			HkbLog hkbLog = HkbLog.create(this.getLoginUser(req).getUserId(),
					HkbConfig.CREATEBOX, o.getBoxId(), addHkb);
			this.userService.addHkb(hkbLog);
		}
		o.setBoxType(boxType);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTotalCount(totalCount);
		o.setBoxKey(DataUtil.toHtmlRow(boxKey));
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setPrecount(precount);
		o.setPretype(pretype);
		o.setIntro(DataUtil.toHtml(intro));
		o.setOpentype(opentype);
		req.setAttribute("o", o);
		int code = o.validate(CmdFactory.getKeyList());
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "editbox");
		}
		try {
			this.boxService.updateBox(o);
			this.setOpFuncSuccessMsg(req);
			return this.initSuccess(req, "editbox");
		}
		catch (BoxKeyDuplicateException e) {
			return this.initError(req, Err.BOX_BOXKEY_ALREADY_EXIST, "editbox");
		}
		catch (PrizeCountMoreThanBoxCountException e) {
			return this.initError(req, Err.BOXPRIZE_OUT_OF_TOTALCOUNT,
					"editbox");
		}
	}

	/**
	 * 创建宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String create(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		UserCmpFunc userCmpFunc = this.userCmpFuncService
				.getUserCmpFunc(loginUser.getUserId());
		if (userCmpFunc == null || !userCmpFunc.isBoxOpen()) {
			req.setMessage("你没有权限发布宝箱");
			return "r:/more.do";
		}
		String cancel = req.getString("cancel");
		if (cancel != null) {
			return "r:/more.do";
		}
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		String intro = req.getString("intro");
		int totalCount = req.getInt("totalCount");
		String boxKey = req.getString("boxKey");
		String begint = req.getString("begint");
		String endt = req.getString("endt");
		int precount = req.getInt("precount");
		if (precount < 0) {
			precount = 0;
		}
		byte pretype = req.getByte("pretype");
		byte opentype = req.getByte("opentype");
		Date beginTime = DataUtil.parseTime(begint, "yyyyMMddHHmm");
		Date endTime = DataUtil.parseTime(endt, "yyyyMMddHHmm");
		long uid = this.getUidFromCompany(companyId);
		Box o = new Box();
		if (this.isAdminUser(req)) {
			byte virtualflg = req.getByte("virtualflg");
			o.setVirtualflg(virtualflg);
		}
		o.setUid(uid);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTotalCount(totalCount);
		o.setBoxKey(DataUtil.toHtmlRow(boxKey));
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setUserId(this.getLoginUser(req).getUserId());
		o.setPrecount(precount);
		o.setPretype(pretype);
		o.setIntro(DataUtil.toHtml(intro));
		o.setOpentype(opentype);
		o.setCompanyId(companyId);
		req.setAttribute("o", o);
		int code = o.validate(CmdFactory.getKeyList());
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/box/op/op_toadd.do";
		}
		try {
			CreateBoxResult createBoxResult = this.boxProcessor.createTmpBox(o,
					true, req.getStringAndSetAttr("zoneName"), true);
			if (createBoxResult.getErrorCode() != Err.SUCCESS) {
				if (createBoxResult.getErrorCode() == Err.ZONE_NAME_ERROR) {
					if (createBoxResult.getProvinceId() > 0) {// 到省下的城市中
						req.setSessionText("view2.cannotfindcityandselect");
						return "r:/index_selcityfromprovince.do?provinceId="
								+ createBoxResult.getProvinceId()
								+ "&forsel=1"
								+ "&return_url="
								+ DataUtil
										.urlEncoder("/box/op/op_toadd.do?companyId="
												+ companyId
												+ "&tmpoid="
												+ createBoxResult.getOid());
					}
				}
				req.setText(String.valueOf(createBoxResult.getErrorCode()));
				return "/box/op/op_toadd.do";
			}
		}
		catch (BoxKeyDuplicateException e) {
			req.setText("func.boxkey_alread_exist");
			return "/box/op/op_toadd.do";
		}
		return "r:/box/op/op_toAddPrize.do?boxId=" + o.getBoxId();
	}

	/**
	 * 创建宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String addweb(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		String name = req.getString("name");
		String intro = req.getString("intro");
		int totalCount = req.getInt("totalCount");
		String boxKey = req.getString("boxKey");
		String begint = req.getString("begint");
		String endt = req.getString("endt");
		int begint_hour = req.getInt("begint_hour");
		int endt_hour = req.getInt("endt_hour");
		int begint_min = req.getInt("begint_min");
		int endt_min = req.getInt("endt_min");
		int precount = req.getInt("precount");
		if (precount < 0) {
			precount = 0;
		}
		byte pretype = req.getByte("pretype");
		byte opentype = req.getByte("opentype");
		Date beginTime = null;
		Date endTime = null;
		try {
			if (begint != null) {
				beginTime = sdf1.parse(begint + " " + begint_hour + ":"
						+ begint_min);
			}
		}
		catch (ParseException e) {
			log.error("error beginTime date format [ " + begint + " ]");
		}
		try {
			if (endt != null) {
				endTime = sdf1.parse(endt + " " + endt_hour + ":" + endt_min);
			}
		}
		catch (ParseException e) {
			log.error("error endTime date format [ " + endt + " ]");
		}
		UserOtherInfo userOtherInfo = this.userService.getUserOtherInfo(this
				.getLoginUser(req).getUserId());
		if (userOtherInfo == null) {
			return null;
		}
		long uid = this.getUidFromCompany(companyId);
		Box o = new Box();
		o.setUid(uid);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTotalCount(totalCount);
		o.setBoxKey(DataUtil.toHtmlRow(boxKey));
		o.setBeginTime(beginTime);
		o.setEndTime(endTime);
		o.setUserId(this.getLoginUser(req).getUserId());
		o.setPrecount(precount);
		o.setPretype(pretype);
		o.setIntro(DataUtil.toHtml(intro));
		o.setOpentype(opentype);
		o.setCompanyId(companyId);
		if (userOtherInfo.getHkb() < totalCount) {
			return this.initError(req, Err.NOENOUGH_HKB, "addbox");
		}
		int code = o.validate(CmdFactory.getKeyList());
		if (code != Err.SUCCESS) {
			return this.initError(req, code, "addbox");
		}
		try {
			// 保存奖品
			String[] prizeName = req.getStrings("pname");
			int[] pcount = req.getInts("pcount");
			String[] tip = req.getStrings("ptip");
			List<BoxPrize> plist = new ArrayList<BoxPrize>();
			for (int i = 0; i < prizeName.length; i++) {
				BoxPrize p = new BoxPrize();
				p.setName(DataUtil.toHtmlRow(prizeName[i]));
				p.setTip(DataUtil.toHtmlRow(tip[i]));
				p.setPcount(pcount[i]);
				p.setSignal((byte) 0);
				code = p.validate();
				if (code != Err.SUCCESS) {
					return this.initError(req, code, "addbox");
				}
				plist.add(p);
			}
			// 发布宝箱
			this.boxService.createBoxAndPrize(o, plist);
			int addHkb = -totalCount;
			HkbLog hkbLog = HkbLog.create(this.getLoginUser(req).getUserId(),
					HkbConfig.CREATEBOX, o.getBoxId(), addHkb);
			this.userService.addHkb(hkbLog);
			req.setSessionText("func.box.createok");
			return this.initSuccess(req, "addbox");
		}
		catch (BoxKeyDuplicateException e) {
			return this.initError(req, Err.BOX_BOXKEY_ALREADY_EXIST, "addbox");
		}
		catch (PrizeTotalCountException e) {
			return this
					.initError(req, Err.BOXPRIZE_OUT_OF_TOTALCOUNT, "addbox");
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toAddPrize(HkRequest req, HkResponse resp) throws Exception {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLongAndSetAttr("boxId");
		req.setReturnUrl("/box/op/op_toAddPrize.do?boxId=" + boxId);
		Box box = this.boxService.getBox(boxId);
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		req.setAttribute("list", list);
		req.setAttribute("box", box);
		long eid = req.getLongAndSetAttr("eid");
		if (eid > 0) {
			Equipment equipment = EquipmentConfig.getEquipment(eid);
			if (equipment != null) {
				BoxPrize o = (BoxPrize) req.getAttribute("o");
				if (o == null) {
					o = new BoxPrize();
					o.setBoxId(boxId);
					o.setName(equipment.getName());
					o.setEid(eid);
					o.setTip(equipment.getIntro());
					req.setAttribute("o", o);
				}
			}
		}
		return "/WEB-INF/page/box/op/createprize.jsp";
	}

	/**
	 * 添加物品2在修改宝箱信息的时候
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createprize2(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLongAndSetAttr("boxId");
		int t = req.getIntAndSetAttr("t");
		int fromadmin = req.getIntAndSetAttr("fromadmin");
		req.setReturnUrl("/box/op/op_createprize2.do?boxId=" + boxId + "&t="
				+ t + "&fromadmin=" + fromadmin);
		long eid = req.getLongAndSetAttr("eid");
		if (eid > 0) {
			Equipment equipment = EquipmentConfig.getEquipment(eid);
			if (equipment != null) {
				BoxPrize o = (BoxPrize) req.getAttribute("o");
				if (o == null) {
					o = new BoxPrize();
					o.setBoxId(boxId);
					o.setName(equipment.getName());
					o.setEid(eid);
					o.setTip(equipment.getIntro());
					req.setAttribute("o", o);
				}
			}
		}
		Box box = this.boxService.getBox(boxId);
		if (box == null) {
			return null;
		}
		req.setAttribute("box", box);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWapJsp("box/op/createprize2.jsp");
		}
		String name = req.getString("name");
		String tip = req.getString("tip");
		int pcount = req.getInt("pcount");
		if (pcount < 0) {
			pcount = 0;
		}
		byte signal = req.getByte("signal");
		BoxPrize o = new BoxPrize();
		if (this.isAdminUser(req)) {
			o.setEid(req.getLong("eid"));
		}
		o.setBoxId(boxId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTip(DataUtil.toHtmlRow(tip));
		o.setPcount(pcount);
		o.setSignal(signal);
		req.setAttribute("o", o);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/box/op/op_createprize2.do?boxId=" + boxId + "&ch=0";
		}
		try {
			this.boxProcessor.createBoxPrize(o);
			req.setSessionMessage(req.getText("op.exeok"));
		}
		catch (PrizeTotalCountException e) {
			req.setText("func.boxprize_count_more_than_boxremaincount",
					new Object[] { String.valueOf(box.getTotalCount()
							- box.getOpenCount()) });
			return "/box/op/op_createprize2.do?boxId=" + boxId + "&ch=0";
		}
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String updateprize(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long prizeId = req.getLongAndSetAttr("prizeId");
		req.reSetAttribute("boxId");
		req.reSetAttribute("t");
		req.reSetAttribute("fromadmin");
		BoxPrize prize = (BoxPrize) req.getAttribute("prize");
		if (prize == null) {
			prize = this.boxService.getBoxPrize(prizeId);
		}
		req.setAttribute("prize", prize);
		if (req.getInt("ch") == 0) {
			return this.getWapJsp("box/op/updateprize.jsp");
		}
		String name = req.getString("name");
		String tip = req.getString("tip");
		int pcount = req.getInt("pcount");
		byte signal = req.getByte("signal");
		if (pcount < 0) {
			pcount = 0;
		}
		prize.setSignal(signal);
		prize.setPrizeId(prizeId);
		prize.setPcount(pcount);
		prize.setName(DataUtil.toHtmlRow(name));
		prize.setTip(DataUtil.toHtmlRow(tip));
		int code = prize.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/box/op/op_updateprize.do?ch=0";
		}
		try {
			this.boxService.updateBoxPrize(prize);
			this.setOpFuncSuccessMsg(req);
		}
		catch (PrizeTotalCountException e) {
			Box box = this.boxService.getBox(prize.getBoxId());
			req.setText("func.boxprize_count_more_than_boxremaincount",
					new Object[] { String.valueOf(box.getTotalCount()
							- box.getOpenCount()) });
			return "/box/op/op_updateprize.do?ch=0";
		}
		catch (PrizeRemainException e) {
			req.setText(String.valueOf(Err.BOXPRIZE_OUT_OF_REMAIN_LIMIT));
			return "/box/op/op_updateprize.do?ch=0";
		}
		return "r:/box/op/op_getbox.do?boxId=" + prize.getBoxId() + "&t="
				+ req.getInt("t") + "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * 添加物品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String createPrize(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		if (box == null) {
			return null;
		}
		String name = req.getString("name");
		String tip = req.getString("tip");
		int pcount = req.getInt("pcount");
		if (pcount < 0) {
			pcount = 0;
		}
		if (DataUtil.isEmpty(name) && DataUtil.isEmpty(tip) && pcount == 0
				&& req.getString("complete") != null) {
			return "r:/box/op/op_createPreBox.do?boxId=" + boxId;
		}
		byte signal = req.getByte("signal");
		BoxPrize o = new BoxPrize();
		if (this.isAdminUser(req)) {
			o.setEid(req.getLong("eid"));
		}
		o.setBoxId(boxId);
		o.setName(DataUtil.toHtmlRow(name));
		o.setTip(DataUtil.toHtmlRow(tip));
		o.setPcount(pcount);
		o.setSignal(signal);
		req.setAttribute("o", o);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/box/op/op_toAddPrize.do?boxId=" + boxId;
		}
		try {
			this.boxProcessor.createBoxPrize(o);
			req.setSessionMessage(req.getText("op.exeok"));
		}
		catch (PrizeTotalCountException e) {
			req.setMessage(req
					.getText("func.boxprize_count_more_than_boxcount"));
			return "/box/op/op_toAddPrize.do?boxId=" + boxId;
		}
		String add = req.getString("add");
		if (add != null) {
			return "r:/box/op/op_toAddPrize.do?boxId=" + boxId;
		}
		return "r:/box/op/op_createPreBox.do?boxId=" + boxId;
	}

	/**
	 * 物品列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prizelist(HkRequest req, HkResponse resp) throws Exception {
		long boxId = req.getLong("boxId");
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		req.setAttribute("list", list);
		req.setAttribute("boxId", boxId);
		return "/WEB-INF/page/box/op/prizelist.jsp";
	}

	/**
	 * 删除物品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delprize2(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		Box box = this.boxService.getBox(boxId);
		BoxPrize boxPrize = this.boxService.getBoxPrize(prizeId);
		if (boxPrize != null && boxPrize.getPcount() == boxPrize.getRemain()) {
			this.boxProcessor.deleteBoxPrize(box, boxPrize);
		}
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selprizepic(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		User loginUser = this.getLoginUser(req);
		SimplePage page = req.getSimplePage(20);
		List<ObjPhoto> list = this.objPhotoService.getObjPhotoListByUserId(
				loginUser.getUserId(), page.getBegin(), page.getSize() + 1);
		req.reSetAttribute("prizeId");
		req.reSetAttribute("boxId");
		req.reSetAttribute("t");
		req.reSetAttribute("fromadmin");
		req.setAttribute("list", list);
		return this.getWapJsp("box/op/piclist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String selpic(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		long photoId = req.getLong("photoId");
		Box box = this.boxService.getBox(boxId);
		this.boxProcessor.updateBoxPrizePhoto(box, prizeId, photoId);
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String uploadpic(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		Box box = this.boxService.getBox(boxId);
		BoxPrize boxPrize = this.boxService.getBoxPrize(prizeId);
		File f = req.getFile("f");
		if (f != null) {
			try {
				this.boxProcessor.updateBoxPrizePhoto(box, boxPrize, f);
				req.setSessionText("view2.setpicforboxprizeok");
			}
			catch (ImageException e) {
				req.setSessionText(String.valueOf(Err.IMG_UPLOAD_ERROR));
				return "r:/box/op/op_selprizepic.do?boxId=" + boxId
						+ "&prizeId=" + prizeId + "&t=" + req.getInt("t")
						+ "&fromadmin=" + req.getInt("fromadmin");
			}
			catch (NotPermitImageFormatException e) {
				req.setSessionText(String.valueOf(Err.IMG_FMT_ERROR));
				return "r:/box/op/op_selprizepic.do?boxId=" + boxId
						+ "&prizeId=" + prizeId + "&t=" + req.getInt("t")
						+ "&fromadmin=" + req.getInt("fromadmin");
			}
			catch (OutOfSizeException e) {
				req.setSessionText(String.valueOf(Err.IMG_OUTOFSIZE_ERROR),
						"2M");
				return "r:/box/op/op_selprizepic.do?boxId=" + boxId
						+ "&prizeId=" + prizeId + "&t=" + req.getInt("t")
						+ "&fromadmin=" + req.getInt("fromadmin");
			}
		}
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * 删除物品
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String delPrize(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLong("boxId");
		long prizeId = req.getLong("prizeId");
		Box box = this.boxService.getBox(boxId);
		BoxPrize boxPrize = this.boxService.getBoxPrize(prizeId);
		this.boxProcessor.deleteBoxPrize(box, boxPrize);
		return "/box/op/op_toAddPrize.do?boxId=" + boxId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String createPreBox(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLong("boxId");
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		if (list.size() == 0) {
			req.setSessionMessage("您还没有创建物品");
			return "r:/box/op/op_toAddPrize.do?boxId=" + boxId;
		}
		this.boxService.updateBoxForCheck(boxId);
		req.setSessionMessage("您发布的宝箱信息已经提交,请耐心等待审核");
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t=1";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String admin(HkRequest req, HkResponse resp) {
		return "/WEB-INF/page/box/op/admin.jsp";
	}

	/**
	 * 等待审核的宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String preboxlist(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getBoxListByCdn(this.getLoginUser(req)
				.getUserId(), name, Box.CHECKFLG_UNCHECK, (byte) -1, page
				.getBegin(), size);
		page.setListSize(list.size());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		return "/WEB-INF/page/box/op/preboxlist.jsp";
	}

	/**
	 * 正在使用的宝箱(暂停和为暂停的)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String openboxlist(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getInTimeBoxList(this
				.getLoginUser(req).getUserId(), name, page.getBegin(), size);
		page.setListSize(list.size());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		return "/WEB-INF/page/box/op/openboxlist.jsp";
	}

	/**
	 * 正在使用的宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String begin(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getCanOpenBoxList(this.getLoginUser(
				req).getUserId(), name, page.getBegin(), page.getSize() + 1);
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		if (list.size() == page.getSize() + 1) {
			req.setAttribute("hasmore", true);
			list.remove(page.getSize());
		}
		int ajax = req.getIntAndSetAttr("ajax");
		if (ajax == 1) {
			return this.getWeb3Jsp("box/op/boxlist_inc.jsp");
		}
		return this.getWeb3Jsp("box/op/openboxlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String overboxlist(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getEndBoxList(getLoginUser(req)
				.getUserId(), name, page.getBegin(), size);
		page.setListSize(list.size());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		return "/WEB-INF/page/box/op/overboxlist.jsp";
	}

	/**
	 * 审核通过未开始的宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String unopenboxlist(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getNotBeginBoxList(getLoginUser(req)
				.getUserId(), name, page.getBegin(), size);
		page.setListSize(list.size());
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		return "/WEB-INF/page/box/op/unopenboxlist.jsp";
	}

	/**
	 * 审核通过未开始的宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String notbegin(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		SimplePage page = req.getSimplePage(size);
		List<Box> list = this.boxService.getNotBeginBoxList(getLoginUser(req)
				.getUserId(), name, page.getBegin(), page.getSize() + 1);
		req.setEncodeAttribute("name", name);
		req.setAttribute("list", list);
		if (list.size() == page.getSize() + 1) {
			req.setAttribute("hasmore", true);
			list.remove(page.getSize());
		}
		int ajax = req.getIntAndSetAttr("ajax");
		if (ajax == 1) {
			return this.getWeb3Jsp("box/op/boxlist_inc.jsp");
		}
		return "/WEB-INF/page/box/op/unopenboxlist.jsp";
	}

	/**
	 * 审核通过未开始的宝箱
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String my(HkRequest req, HkResponse resp) {
		String name = req.getString("name");
		byte checkflg = req.getByteAndSetAttr("checkflg", (byte) -1);
		byte boxStatus = req.getByteAndSetAttr("boxStatus", (byte) -1);
		SimplePage page = req.getSimplePage(size);
		List<Box> boxlist = this.boxService.getBoxListByCdn(getLoginUser(req)
				.getUserId(), name, checkflg, boxStatus, page.getBegin(), page
				.getSize() + 1);
		req.setEncodeAttribute("name", name);
		this.processListForPage(page, boxlist);
		req.setAttribute("boxlist", boxlist);
		req.setAttribute("op_func", 11);
		return this.getWeb3Jsp("box/op/boxlist.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String getbox(HkRequest req, HkResponse resp) {
		int t = req.getInt("t");
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		boolean stop = false;
		boolean overdue = false;
		boolean pause = false;
		boolean normal = false;
		if (box.getBoxStatus() == Box.BOX_STATUS_STOP) {
			stop = true;
		}
		if (box.getBoxStatus() == Box.BOX_STATUS_PAUSE) {
			pause = true;
		}
		if (box.getBoxStatus() == Box.BOX_STATUS_NORMAL) {
			normal = true;
		}
		if (box.getEndTime().getTime() < System.currentTimeMillis()) {
			overdue = true;
		}
		BoxType boxType = BoxTypeUtil.getBoxType(box.getBoxType());
		BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box.getPretype());
		if (box.getCityId() > 0) {
			City city = ZoneUtil.getCity(box.getCityId());
			if (city != null) {
				req.setAttribute("zoneName", city.getCity());
			}
		}
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		req.setAttribute("boxPretype", boxPretype);
		req.setAttribute("boxType", boxType);
		req.setAttribute("list", list);
		req.setAttribute("t", t);
		req.setAttribute("normal", normal);
		req.setAttribute("pause", pause);
		req.setAttribute("stop", stop);
		req.setAttribute("overdue", overdue);
		req.setAttribute("boxId", boxId);
		req.setAttribute("box", box);
		req.reSetAttribute("fromadmin");
		req.reSetAttribute("repage");
		return "/WEB-INF/page/box/op/box.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String boxweb(HkRequest req, HkResponse resp) {
		int t = req.getInt("t");
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		boolean forCheck = false;
		List<BoxPrize> list = this.boxService.getBoxPrizeListByBoxId(boxId);
		boolean stop = false;
		boolean overdue = false;
		boolean pause = false;
		boolean normal = false;
		if (box.getBoxStatus() == Box.BOX_STATUS_STOP) {
			stop = true;
		}
		if (box.getBoxStatus() == Box.BOX_STATUS_PAUSE) {
			pause = true;
		}
		if (box.getBoxStatus() == Box.BOX_STATUS_NORMAL) {
			normal = true;
		}
		if (box.getEndTime().getTime() < System.currentTimeMillis()) {
			overdue = true;
		}
		BoxType boxType = BoxTypeUtil.getBoxType(box.getBoxType());
		BoxPretype boxPretype = BoxPretypeUtil.getBoxPretype(box.getPretype());
		List<BoxPretype> prelist = BoxPretypeUtil.getList();
		req.setAttribute("prelist", prelist);
		req.setAttribute("boxPretype", boxPretype);
		req.setAttribute("boxType", boxType);
		req.setAttribute("list", list);
		req.setAttribute("t", t);
		req.setAttribute("normal", normal);
		req.setAttribute("pause", pause);
		req.setAttribute("stop", stop);
		req.setAttribute("overdue", overdue);
		req.setAttribute("boxId", boxId);
		req.setAttribute("forCheck", forCheck);
		req.setAttribute("box", box);
		req.reSetAttribute("repage");
		return this.getWeb3Jsp("box/op/box.jsp");
	}

	public String optbox(HkRequest req, HkResponse resp) {
		if (req.getString("stop") != null) {
			return this.stop(req, resp);
		}
		if (req.getString("pause") != null) {
			return this.pause(req, resp);
		}
		if (req.getString("cont") != null) {
			return this.cont(req, resp);
		}
		return this.toeditkey(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String cont(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		this.boxService.continueBox(boxId);
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&repage="
				+ req.getInt("repage") + "&t=" + req.getInt("t")
				+ "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String contweb(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return null;
		}
		long boxId = req.getLong("boxId");
		this.boxService.continueBox(boxId);
		req.setSessionText("func.box.runok");
		return "r:/box/op/op_boxweb.do?boxId=" + boxId;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String toeditkey(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		long boxId = req.getLong("boxId");
		Box box = this.boxService.getBox(boxId);
		req.setAttribute("box", box);
		req.reSetAttribute("boxId");
		req.reSetAttribute("repage");
		req.reSetAttribute("t");
		req.reSetAttribute("fromadmin");
		return "/WEB-INF/page/box/op/editkey.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updateBoxKey(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String boxKey = req.getString("boxKey");
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		Box o = this.boxService.getBox(boxId);
		if (o == null) {
			return null;
		}
		o.setBoxKey(boxKey);
		int errorCode = o.validateBoxKey(CmdFactory.getKeyList());
		if (errorCode != Err.SUCCESS) {
			req.setMessage(req.getText(errorCode + ""));
			return "/box/op/op_toeditkey.do";
		}
		if (this.boxService.updateBoxKey(boxId, boxKey)) {
			return "r:/box/op/op_getbox.do?boxId=" + boxId + "&repage="
					+ req.getInt("repage") + "&t=" + req.getInt("t")
					+ "&fromadmin=" + req.getInt("fromadmin");
		}
		req.setMessage(req.getText("func.boxkey_alread_exist"));
		return "/box/op/op_toeditkey.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String updateboxkeyweb(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String boxKey = req.getString("boxKey");
		if (!this.hasOpBoxPower(req)) {
			return null;
		}
		Box o = this.boxService.getBox(boxId);
		if (o == null) {
			return null;
		}
		o.setBoxKey(boxKey);
		int errorCode = o.validateBoxKey(CmdFactory.getKeyList());
		if (errorCode != Err.SUCCESS) {
			return this.initError(req, errorCode, "editkey");
		}
		if (this.boxService.updateBoxKey(boxId, boxKey)) {
			return this.initSuccess(req, "editkey");
		}
		return this.initError(req, Err.BOX_BOXKEY_ALREADY_EXIST, "editkey");
	}

	/**
	 * 作废
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String stop(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return "r:/more.do";
		}
		req.reSetAttribute("boxId");
		req.reSetAttribute("t");
		req.reSetAttribute("repage");
		req.reSetAttribute("fromadmin");
		return "/WEB-INF/page/box/op/confirmstop.jsp";
	}

	/**
	 * 确认作废
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String confirmstop(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String cancel = req.getString("cancel");
		if (cancel == null) {
			if (!this.hasOpBoxPower(req)) {
				return "r:/more.do";
			}
			this.boxService.stopBox(boxId);
			req.setSessionMessage("操作成功");
		}
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&repage=" + req.getInt("repage")
				+ "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 * 确认作废
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String stopweb(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		if (!this.hasOpBoxPower(req)) {
			return null;
		}
		this.boxService.stopBox(boxId);
		req.setSessionText("func.box.stopok");
		return "r:/box/op/op_boxweb.do?boxId=" + boxId;
	}

	/**
	 * 停止
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String pause(HkRequest req, HkResponse resp) {
		req.reSetAttribute("boxId");
		req.reSetAttribute("t");
		req.reSetAttribute("repage");
		req.reSetAttribute("fromadmin");
		return "/WEB-INF/page/box/op/confirmpause.jsp";
	}

	/**
	 * 停止
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String pauseweb(HkRequest req, HkResponse resp) {
		if (!this.hasOpBoxPower(req)) {
			return null;
		}
		long boxId = req.getLong("boxId");
		this.boxService.pauseBox(boxId);
		req.setSessionText("func.box.pauseok");
		return "r:/box/op/op_boxweb.do?boxId=" + boxId;
	}

	/**
	 * 确认停止
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String confirmpause(HkRequest req, HkResponse resp) {
		long boxId = req.getLong("boxId");
		String cancel = req.getString("cancel");
		if (cancel == null) {
			if (!this.hasOpBoxPower(req)) {
				return "r:/more.do";
			}
			this.boxService.pauseBox(boxId);
			req.setSessionMessage("操作成功");
		}
		return "r:/box/op/op_getbox.do?boxId=" + boxId + "&t="
				+ req.getInt("t") + "&repage=" + req.getInt("repage")
				+ "&fromadmin=" + req.getInt("fromadmin");
	}

	/**
	 *选择道具
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String selequ(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			List<Equipment> list = EquipmentConfig.getEquipments();
			req.setAttribute("list", list);
			return this.getWapJsp("box/op/selequ.jsp");
		}
		long eid = req.getLong("eid");
		String return_url = req.getReturnUrl();
		return "r:" + return_url + "&eid=" + eid;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String back(HkRequest req, HkResponse resp) {
		int t = req.getInt("t");
		int repage = req.getInt("repage");
		int fromadmin = req.getInt("fromadmin");
		String url_a = null;
		if (fromadmin == 1) {
			url_a = "/box/admin/adminbox";
		}
		else {
			url_a = "/box/op/op";
		}
		if (t == 1) {
			return "r:" + url_a + "_preboxlist.do?page=" + repage;
		}
		if (t == 2) {
			return "r:" + url_a + "_openboxlist.do?page=" + repage;
		}
		if (t == 3) {
			return "r:" + url_a + "_overboxlist.do?page=" + repage;
		}
		if (t == 4) {
			return "r:" + url_a + "_unopenboxlist.do?page=" + repage;
		}
		if (t == 5) {
			return "r:" + url_a + "_pauseboxlist.do?page=" + repage;
		}
		return null;
	}
}