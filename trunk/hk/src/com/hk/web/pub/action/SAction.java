package com.hk.web.pub.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.CmpTagRef;
import com.hk.bean.Company;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpTagService;
import com.hk.svr.CompanyService;
import com.hk.svr.UserService;
import com.hk.svr.pub.ZoneUtil;

@Component("/s")
public class SAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpTagService cmpTagService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return "/WEB-INF/page/pub/search.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String back(HkRequest req, HkResponse resp) throws Exception {
		String sfrom = req.getString("sfrom", "");
		if (sfrom.equals("ias")) {
			return "r:/s.do";
		}
		if (sfrom.equals("userlist1")) {
			return "r:/user/list1.do";
		}
		if (sfrom.equals("userlist2")) {
			return "r:/user/list2.do";
		}
		return "r:/more.do";
	}

	/**
	 * 搜索用户,足迹,足迹标签
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String all(HkRequest req, HkResponse resp) throws Exception {
		int ch = req.getInt("ch");
		int pcityId = this.getPcityId(req);
		String key = req.getString("key");
		req.setEncodeAttribute("key", key);
		if (DataUtil.isEmpty(key)) {
			ch = 0;
		}
		req.setAttribute("ch", ch);
		if (ch == 0) {
			return this.getWeb4Jsp("search/result.jsp");
		}
		int size = 11;
		int real_size = 10;
		// 搜索用户结果
		List<User> userlist = this.userService.getUserListForSearch(key, 0, 33);
		if (userlist.size() == 33) {
			req.setAttribute("user_more", true);
			userlist.remove(32);
		}
		req.setAttribute("userlist", userlist);
		// 搜索足迹名称结果
		List<Company> cmplist = this.companyService
				.getCompanyListByPcityIdAndNameLike(pcityId, key, 0, size);
		if (cmplist.size() == size) {
			req.setAttribute("cmp_more", true);
			cmplist.remove(real_size);
		}
		req.setAttribute("cmplist", cmplist);
		// 在其他地区搜索足迹名称的结果
		List<Long> othercmpidlist = this.companyService
				.getCompanyIdListWithSearchNotPcityId(pcityId, key, 0, size);
		if (othercmpidlist.size() == size) {
			req.setAttribute("othercmp_more", true);
			othercmpidlist.remove(real_size);
		}
		List<Company> othercmplist = this.companyService.getCompanyListInId(
				othercmpidlist, null);
		req.setAttribute("othercmplist", othercmplist);
		// 搜索足迹标签结果
		List<CmpTagRef> refcmplist = this.cmpTagService
				.getCmpTagRefListByPcityIdAndNameLike(pcityId, key, 0, size);
		if (refcmplist.size() == size) {
			req.setAttribute("refcmp_more", true);
			refcmplist.remove(real_size);
		}
		List<Long> idList = new ArrayList<Long>();
		for (CmpTagRef o : refcmplist) {
			idList.add(o.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(idList);
		for (CmpTagRef o : refcmplist) {
			o.setCompany(map.get(o.getCompanyId()));
		}
		req.setAttribute("refcmplist", refcmplist);
		// 搜索其他城市的足迹标签结果
		List<Long> otherrefcmpidlist = this.companyService
				.getCompanyIdListFromCmpTagRefWithSearchNotPcityId(pcityId,
						key, 0, size);
		if (otherrefcmpidlist.size() == size) {
			req.setAttribute("otherrefcmp_more", true);
			othercmpidlist.remove(real_size);
		}
		List<Company> otherrefcmplist = this.companyService.getCompanyListInId(
				otherrefcmpidlist, null);
		req.setAttribute("otherrefcmplist", otherrefcmplist);
		City city = ZoneUtil.getCity(pcityId);
		req.setAttribute("city", city);
		return this.getWeb4Jsp("search/result.jsp");
	}

	/**
	 * 搜索足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String othercmpname(HkRequest req, HkResponse resp) throws Exception {
		int pcityId = this.getPcityId(req);
		String key = req.getString("key");
		req.setEncodeAttribute("key", key);
		SimplePage page = req.getSimplePage(20);
		List<Long> othercmpidlist = this.companyService
				.getCompanyIdListWithSearchNotPcityId(pcityId, key, page
						.getBegin(), page.getSize() + 1);
		List<Company> cmplist = this.companyService.getCompanyListInId(
				othercmpidlist, null);
		this.processListForPage(page, cmplist);
		req.setAttribute("cmplist", cmplist);
		return this.getWeb4Jsp("search/othercmplist_result.jsp");
	}

	/**
	 * 搜索足迹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String cmpname(HkRequest req, HkResponse resp) throws Exception {
		int pcityId = this.getPcityId(req);
		String key = req.getString("key");
		req.setEncodeAttribute("key", key);
		SimplePage page = req.getSimplePage(20);
		List<Company> cmplist = this.companyService
				.getCompanyListByPcityIdAndNameLike(pcityId, key, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, cmplist);
		req.setAttribute("cmplist", cmplist);
		City city = ZoneUtil.getCity(pcityId);
		req.setAttribute("city", city);
		return this.getWeb4Jsp("search/cmplist_result.jsp");
	}

	/**
	 * 搜索足迹标签
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String otherreftagname(HkRequest req, HkResponse resp)
			throws Exception {
		int pcityId = this.getPcityId(req);
		String key = req.getString("key");
		req.setEncodeAttribute("key", key);
		SimplePage page = req.getSimplePage(20);
		List<Long> otherrefcmpidlist = this.companyService
				.getCompanyIdListFromCmpTagRefWithSearchNotPcityId(pcityId,
						key, page.getBegin(), page.getSize() + 1);
		List<Company> cmplist = this.companyService.getCompanyListInId(
				otherrefcmpidlist, null);
		req.setAttribute("cmplist", cmplist);
		return this.getWeb4Jsp("search/otherrefcmplist_result.jsp");
	}

	/**
	 * 搜索足迹标签
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String reftagname(HkRequest req, HkResponse resp) throws Exception {
		int pcityId = this.getPcityId(req);
		String key = req.getString("key");
		req.setEncodeAttribute("key", key);
		SimplePage page = req.getSimplePage(20);
		List<CmpTagRef> refcmplist = this.cmpTagService
				.getCmpTagRefListByPcityIdAndNameLike(pcityId, key, page
						.getBegin(), page.getSize() + 1);
		this.processListForPage(page, refcmplist);
		List<Long> idList = new ArrayList<Long>();
		for (CmpTagRef o : refcmplist) {
			idList.add(o.getCompanyId());
		}
		Map<Long, Company> map = this.companyService.getCompanyMapInId(idList);
		for (CmpTagRef o : refcmplist) {
			o.setCompany(map.get(o.getCompanyId()));
		}
		req.setAttribute("refcmplist", refcmplist);
		City city = ZoneUtil.getCity(pcityId);
		req.setAttribute("city", city);
		return this.getWeb4Jsp("search/refcmplist_result.jsp");
	}

	/**
	 * 搜索用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String nickname(HkRequest req, HkResponse resp) throws Exception {
		String key = req.getString("key");
		req.setEncodeAttribute("key", key);
		SimplePage page = req.getSimplePage(32);
		List<User> userlist = this.userService.getUserListForSearch(key, page
				.getBegin(), page.getSize() + 1);
		this.processListForPage(page, userlist);
		req.setAttribute("userlist", userlist);
		return this.getWeb4Jsp("search/userlist_result.jsp");
	}
}