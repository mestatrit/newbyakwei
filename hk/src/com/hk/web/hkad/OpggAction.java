package com.hk.web.hkad;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.HkAd;
import com.hk.bean.TmpData;
import com.hk.bean.User;
import com.hk.bean.UserCmpFunc;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.HkAdService;
import com.hk.svr.TmpDataService;
import com.hk.svr.UserCmpFuncService;
import com.hk.svr.processor.CreateHkAdResult;
import com.hk.svr.processor.HkAdProcessor;
import com.hk.svr.processor.UpdateHkAdResult;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ZoneUtil;
import com.hk.web.pub.action.BaseAction;

@Component("/op/gg")
public class OpggAction extends BaseAction {

	@Autowired
	private HkAdService hkAdService;

	@Autowired
	private HkAdProcessor hkAdProcessor;

	@Autowired
	private UserCmpFuncService userCmpFuncService;

	@Autowired
	private TmpDataService tmpDataService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		HkAd hkAd = this.hkAdService.getHkAd(oid);
		req.setAttribute("hkAd", hkAd);
		if (hkAd.getCityId() > 0) {
			req.setAttribute("city", ZoneUtil.getCity(hkAd.getCityId()));
		}
		return this.getWapJsp("hkad/view.jsp");
	}

	/**
	 * 广告预览
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		SimplePage page = req.getSimplePage(20);
		List<HkAd> list = this.hkAdService.getHkAdByUserId(userId, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWapJsp("hkad/list.jsp");
	}

	/**
	 * 发布广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String create(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		User loginUser = this.getLoginUser(req);
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
		if (ch == 0) {
			HkAd o = (HkAd) req.getAttribute("o");
			if (o == null) {
				long tmpoid = req.getLong("tmpoid");
				if (tmpoid > 0) {// 从临时数据中恢复
					TmpData tmpData = this.tmpDataService.getTmpData(tmpoid);
					if (tmpData != null
							&& tmpData.getDatatype() == TmpData.DATATYPE_HKAD) {
						Map<String, String> map = DataUtil
								.getMapFromJson(tmpData.getData());
						o = new HkAd();
						o.setName(map.get("name"));
						o.setShowflg(Byte.valueOf(map.get("showflg")));
						o.setExpendflg(Byte.valueOf(map.get("expendflg")));
						o.setHref(map.get("href"));
						o.setTotalViewCount(Integer.valueOf(map
								.get("totalviewcount")));
						o.setData(map.get("data"));
						req.setAttribute("o", o);
					}
				}
			}
			return this.getWapJsp("hkad/create.jsp");
		}
		long userId = loginUser.getUserId();
		UserCmpFunc userCmpFunc = this.userCmpFuncService
				.getUserCmpFunc(userId);
		if (!userCmpFunc.isHkAdOpen()) {
			return null;
		}
		String name = req.getHtmlRow("name");
		String href = req.getHtmlRow("href");
		byte showflg = req.getByte("showflg");
		String adData = req.getHtml("adData");
		int totalViewCount = req.getInt("totalViewCount");
		HkAd o = new HkAd();
		o.setUserId(userId);
		o.setName(name);
		o.setShowflg(showflg);
		o.setTotalViewCount(totalViewCount);
		o.setHref(href);
		o.setUseflg(HkAd.USEFLG_Y);
		o.setAdData(adData);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/op/gg_create.do?ch=0";
		}
		File f = null;
		if (o.isImageShow()) {
			f = req.getFile("f");
		}
		CreateHkAdResult r;
		try {
			r = this.hkAdProcessor.createHkAd(o, f, adData, zoneName);
		}
		catch (IOException e) {
			req.setText(String.valueOf(Err.UPLOAD_ERROR));
			return "/op/gg_create.do?ch=0";
		}
		if (r.getErrorCode() != Err.SUCCESS) {
			if (r.getErrorCode() == Err.ZONE_NAME_ERROR) {
				if (r.getProvinceId() > 0) {// 到省下的城市中
					req.setSessionText("view2.cannotfindcityandselect");
					return "r:/index_selcityfromprovince.do?provinceId="
							+ r.getProvinceId()
							+ "&forsel=1"
							+ "&return_url="
							+ DataUtil.urlEncoder("/op/gg_create.do?tmpoid="
									+ r.getOid());
				}
			}
			if (r.getErrorCode() == Err.HKAD_NOENOUGH_HKB_VIEW) {
				req.setText(String.valueOf(code), String.valueOf(r
						.getRemainHkb()));
			}
			else {
				req.setText(String.valueOf(r.getErrorCode()));
			}
			return "/op/gg_create.do?ch=0";
		}
		return "r:/op/gg.do?oid=" + o.getOid();
	}

	/**
	 * 发布广告
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String update(HkRequest req, HkResponse resp) {
		long oid = req.getLongAndSetAttr("oid");
		HkAd o = (HkAd) req.getAttribute("o");
		if (o == null) {
			o = this.hkAdService.getHkAd(oid);
			req.setAttribute("o", o);
		}
		int ch = req.getInt("ch");
		String zoneName = req.getString("zoneName");
		if (ch == 0) {
			if (zoneName == null) {
				zoneName = (String) req.getSessionValue("zoneName");
				req.removeSessionvalue("zoneName");
			}
			if (zoneName == null) {
				City city = ZoneUtil.getCity(o.getCityId());
				if (city != null) {
					zoneName = city.getCity();
				}
			}
			req.setAttribute("zoneName", zoneName);
			return this.getWapJsp("hkad/update.jsp");
		}
		String name = req.getHtmlRow("name");
		String href = req.getHtmlRow("href");
		byte showflg = req.getByte("showflg");
		int totalViewCount = req.getInt("totalViewCount");
		String adData = req.getHtml("adData");
		o.setName(name);
		o.setShowflg(showflg);
		o.setTotalViewCount(totalViewCount);
		o.setHref(href);
		o.setAdData(adData);
		int code = o.validate();
		if (code != Err.SUCCESS) {
			req.setText(String.valueOf(code));
			return "/op/gg_update.do?ch=0";
		}
		File f = null;
		if (o.isImageShow()) {
			f = req.getFile("f");
		}
		UpdateHkAdResult r = null;
		try {
			r = this.hkAdProcessor.updateHkAd(o, f, adData, zoneName);
		}
		catch (IOException e) {
			req.setText(String.valueOf(Err.UPLOAD_ERROR));
			return "/op/gg_update.do?ch=0";
		}
		if (r.getErrorCode() != Err.SUCCESS) {
			if (r.getErrorCode() == Err.ZONE_NAME_ERROR) {
				if (r.getProvinceId() > 0) {// 到省下的城市中
					req.setSessionText("view2.cannotfindcityandselect");
					return "r:/index_selcityfromprovince.do?provinceId="
							+ r.getProvinceId()
							+ "&forsel=1"
							+ "&return_url="
							+ DataUtil.urlEncoder("/op/gg_update.do?oid=" + oid
									+ "&tmpoid=" + r.getOid());
				}
			}
			if (r.getErrorCode() == Err.HKAD_NOENOUGH_HKB_VIEW) {
				req.setText(String.valueOf(code), String.valueOf(r
						.getRemainHkb()));
			}
			else {
				req.setText(String.valueOf(r.getErrorCode()));
			}
			return "/op/gg_update.do?ch=0";
		}
		return "r:/op/gg.do?oid=" + o.getOid();
	}

	/**
	 * 运行
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String dorun(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.hkAdService.updateUseflg(oid, HkAd.USEFLG_Y);
		req.setSessionText("view2.hkad.run.ok");
		String from = req.getString("from");
		if (from != null && from.equals("list")) {
			return "r:/op/gg_list.do";
		}
		return "r:/op/gg.do?oid=" + oid;
	}

	/**
	 * 暂停展示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String pause(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.hkAdService.updateUseflg(oid, HkAd.USEFLG_N);
		req.setSessionText("view2.hkad.pause.ok");
		String from = req.getString("from");
		if (from != null && from.equals("list")) {
			return "r:/op/gg_list.do";
		}
		return "r:/op/gg.do?oid=" + oid;
	}

	/**
	 * 暂停展示
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-4
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLongAndSetAttr("oid");
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWapJsp("hkad/cfmdel.jsp");
		}
		if (req.getString("ok") != null) {
			this.hkAdProcessor.deleteHkAd(oid);
			req.setSessionText("view2.hkad.del.ok");
			return "r:/op/gg_list.do";
		}
		return "r:/op/gg.do?oid=" + oid;
	}
}