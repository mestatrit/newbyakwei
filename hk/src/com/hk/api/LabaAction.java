package com.hk.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.IpCityLaba;
import com.hk.bean.IpCityRange;
import com.hk.bean.IpCityRangeLaba;
import com.hk.bean.Laba;
import com.hk.bean.UrlInfo;
import com.hk.bean.UserRecentLaba;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.IpCityService;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.TimeUtil;
import com.hk.web.pub.action.BaseAction;
import com.hk.web.pub.action.LabaParserCfg;
import com.hk.web.pub.action.LabaVo;
import com.hk.web.util.HkWebConfig;

@Component("/api/bosee/laba")
public class LabaAction extends BaseAction {

	@Autowired
	private LabaService labaService;

	@Autowired
	private IpCityService ipCityService;

	private UrlInfo getApiUrlInfo(HkRequest req) {
		UrlInfo urlInfo = new UrlInfo();
		String urlClass = req.getString("urlClass", "");
		urlInfo.setUserUrlClass(urlClass);
		urlInfo.setRewriteUserUrl(true);
		urlInfo.setUserUrl("http://" + HkWebConfig.getWebDomain()
				+ req.getContextPath() + "/home_viewbynickname.do?v={0}");
		urlInfo.setTagUrl("http://" + HkWebConfig.getWebDomain()
				+ req.getContextPath()
				+ "/laba/taglabalist_viewbyname.do?v={0}&reuserId={1}");
		urlInfo.setCompanyUrl("http://" + HkWebConfig.getWebDomain()
				+ "/e/cmp.do?companyId={0}");
		urlInfo.setCmpListUrl("http://" + HkWebConfig.getWebDomain()
				+ "/e/cmp_s.do?name={0}");
		urlInfo.setTagUrlClass(urlClass);
		urlInfo.setTagNewWin(true);
		urlInfo.setUserNewWin(true);
		urlInfo.setUrlNewWin(true);
		return urlInfo;
	}

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		resp.sendHtml("hello");
		return null;
	}

	public String createLaba(HkRequest req, HkResponse resp) throws Exception {
		String content = req.getString("content");
		long userId = req.getLong("userId");
		String ip = req.getString("ip");
		int code = Laba.validateContent(content);
		if (code != Err.SUCCESS) {
			resp.sendHtml("-2");// 内容不能不能超过500个字符
			return null;
		}
		content = DataUtil.toHtmlRow(content);
		LabaInPutParser parser = new LabaInPutParser(HkWebConfig
				.getShortUrlDomain());
		LabaInfo labaInfo = parser.parse(content);
		labaInfo.setIp(ip);
		labaInfo.setUserId(userId);
		labaInfo.setSendFrom(Laba.SENDFROM_BOSEE);
		if (labaInfo.isEmptyContent()) {
			resp.sendHtml(1);
			return null;
		}
		this.labaService.createLaba(labaInfo);
		resp.sendHtml(1);
		return null;
	}

	/**
	 * 某个人的最新喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String getLastUserLaba(HkRequest req, HkResponse resp)
			throws Exception {
		long userId = req.getLong("userId");
		Laba o = labaService.getLastUserLaba(userId);
		if (o != null) {
			LabaVo vo = LabaVo.create(o, null);
			resp.sendXML(this.getLabaXmlData(vo));
		}
		return null;
	}

	/**
	 * 某个人的最新喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String getLastUserLabaInId(HkRequest req, HkResponse resp)
			throws Exception {
		String[] userId = req.getStrings("userId");
		int size = req.getInt("size");
		if (size > 20) {
			size = 20;
		}
		List<Long> idList = new ArrayList<Long>();
		if (userId != null) {
			for (String s : userId) {
				idList.add(Long.parseLong(s));
			}
		}
		Map<Long, UserRecentLaba> map = this.labaService
				.getUserRecentLabaMapInUser(idList);
		List<Long> labaIdList = new ArrayList<Long>();
		for (UserRecentLaba o : map.values()) {
			long id = o.getLastLabaId();
			if (id != 0) {
				labaIdList.add(id);
			}
		}
		List<Laba> labaList = this.labaService.getLabaListInId(labaIdList, 0,
				size);
		List<LabaVo> labavolist = LabaVo.createVoList(labaList,
				getApiLabaParserCfg(req));
		resp.sendXML(this.getLabaListXmlData(labavolist));
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String getUserLabaList(HkRequest req, HkResponse resp)
			throws Exception {
		long userId = req.getLong("userId");
		int size = req.getInt("size", 20);
		SimplePage simplePage = req.getSimplePage(size);
		List<Laba> list = labaService.getLabaListByUserId(userId, simplePage
				.getBegin(), size);
		List<LabaVo> labavolist = LabaVo.createVoList(list,
				getApiLabaParserCfg(req));
		resp.sendXML(this.getLabaListXmlData(labavolist));
		return null;
	}

	private LabaParserCfg getApiLabaParserCfg(HkRequest req) {
		LabaParserCfg cfg = new LabaParserCfg();
		cfg.setUrlInfo(this.getApiUrlInfo(req));
		cfg.setCheckFav(false);
		return cfg;
	}

	/**
	 * 好友的喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String getFriendLabaList(HkRequest req, HkResponse resp)
			throws Exception {
		long userId = req.getLong("userId");
		int size = req.getInt("size", 20);
		SimplePage simplePage = req.getSimplePage(size);
		List<Laba> list = labaService.getLabaListForFollowByUserId(userId,
				simplePage.getBegin(), size);
		List<LabaVo> labavolist = LabaVo.createVoList(list, this
				.getApiLabaParserCfg(req));
		resp.sendXML(this.getLabaListXmlData(labavolist));
		return null;
	}

	/**
	 * 所有人的喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String getLabaList(HkRequest req, HkResponse resp) throws Exception {
		int size = req.getInt("size", 20);
		SimplePage simplePage = req.getSimplePage(size);
		List<Laba> list = null;
		list = labaService.getLabaList(simplePage.getBegin(), size);
		List<LabaVo> labavoList = LabaVo.createVoList(list, this
				.getApiLabaParserCfg(req));
		resp.sendXML(this.getLabaListXmlData(labavoList));
		return null;
	}

	/**
	 * 根据ip范围取喇叭
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String getRangeLabaList(HkRequest req, HkResponse resp)
			throws Exception {
		String w = req.getString("w", "city");
		String ip = req.getString("ip");
		if (ip == null) {
			resp.sendHtml(-3);// ip为空
			return null;
		}
		int size = req.getInt("size", 20);
		SimplePage page = req.getSimplePage(size);
		List<Laba> olist = new ArrayList<Laba>();
		if (w.equals("range")) {
			IpCityRange range = this.ipCityService.getIpCityRange(ip);
			if (range != null) {
				List<IpCityRangeLaba> list = labaService
						.getIpCityRangeLabaList(range.getRangeId(), page
								.getBegin(), size);
				for (IpCityRangeLaba o : list) {
					olist.add(o.getLaba());
				}
			}
		}
		else if (w.equals("city")) {
			IpCityRange range = this.ipCityService.getIpCityRange(ip);
			if (range != null) {
				List<IpCityLaba> list = labaService.getIpCityLabaList(range
						.getCityId(), page.getBegin(), size);
				for (IpCityLaba o : list) {
					olist.add(o.getLaba());
				}
			}
		}
		else if (w.equals("ip")) {
			olist = labaService.getIpLabaList(ip, page.getBegin(), size);
		}
		List<LabaVo> labavoList = LabaVo.createVoList(olist, this
				.getApiLabaParserCfg(req));
		resp.sendXML(this.getLabaListXmlData(labavoList));
		return null;
	}

	private String getLabaListXmlData(List<LabaVo> list) {
		StringBuilder sb = new StringBuilder("<resp>");
		for (LabaVo o : list) {
			sb.append("<laba labaId=\"" + o.getLaba().getLabaId()
					+ "\" userId=\"" + o.getLaba().getUserId() + "\" time=\""
					+ o.getLaba().getCreateTime().getTime() + "\" head32=\""
					+ o.getLaba().getUser().getHead32Pic() + "\" hktime=\""
					+ TimeUtil.getHkTime(o.getLaba().getCreateTime()) + "\">");
			sb.append("<nick><![CDATA[");
			sb.append(o.getLaba().getUser().getNickName());
			sb.append("]]></nick>");
			sb.append("<content><![CDATA[");
			sb.append(o.getContent());
			sb.append("]]></content>");
			sb.append("</laba>");
		}
		sb.append("</resp>");
		String filter = DataUtil.filerUnicodeString(sb.toString());
		return filter;
	}

	private String getLabaXmlData(LabaVo vo) {
		StringBuilder sb = new StringBuilder("<resp>");
		sb.append("<laba labaId=\"" + vo.getLaba().getLabaId() + "\" userId=\""
				+ vo.getLaba().getUserId() + "\" time=\""
				+ vo.getLaba().getCreateTime().getTime() + "\" head32=\""
				+ vo.getLaba().getUser().getHead32Pic() + "\" hktime=\""
				+ TimeUtil.getHkTime(vo.getLaba().getCreateTime()) + "\">");
		sb.append("<nick><![CDATA[");
		sb.append(vo.getLaba().getUser().getNickName());
		sb.append("]]></nick>");
		sb.append("<content><![CDATA[");
		sb.append(vo.getContent());
		sb.append("]]></content>");
		sb.append("</laba>");
		sb.append("</resp>");
		String filter = DataUtil.filerUnicodeString(sb.toString());
		return filter;
	}
}