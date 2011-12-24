package com.hk.web.hk4.venue.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Laba;
import com.hk.bean.LabaCmt;
import com.hk.bean.Notice;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.svr.NoticeService;
import com.hk.svr.UserService;
import com.hk.svr.laba.parser.LabaOutPutParser;
import com.hk.web.notice.action.NoticeVo;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/op/notice")
public class NoticeAction extends BaseAction {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private LabaService labaService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		req.setAttribute("isWeb", true);
		long userId = loginUser.getUserId();
		if (this.noticeService.countNoReadNotice(userId,
				Notice.NOTICETYPE_LABAREPLY) == 1) {// 如果通知是一条
			Notice notice = this.noticeService.getLastNoReadNotice(userId,
					Notice.NOTICETYPE_LABAREPLY);// 如果这一条通知是喇叭的通知，则直接跳到喇叭单页面
			if (notice != null) {
				this.noticeService.setNoticeRead(userId, notice.getNoticeId());
				Map<String, String> map = DataUtil.getMapFromJson(notice
						.getData());
				return "r:/laba/" + map.get("labaid");
			}
		}
		Notice notice = this.noticeService.getLastNoReadNotice(userId);
		if (notice != null) {
			if (notice.getNoticeType() == Notice.NOTICETYPE_LABAREPLY) {
				return this.type2(req, resp);
			}
			if (notice.getNoticeType() == Notice.NOTICETYPE_USER_IN_LABA) {
				return this.type4(req, resp);
			}
			if (notice.getNoticeType() == Notice.NOTICETYPE_INVITE) {
				return this.type5(req, resp);
			}
			if (notice.getNoticeType() == Notice.NOTICETYPE_FOLLOW) {
				return this.type3(req, resp);
			}
			if (notice.getNoticeType() == Notice.NOTICETYPE_CHANGEMAYOR) {
				return this.type6(req, resp);
			}
		}
		else {
			return this.type2(req, resp);
		}
		return null;
	}

	/**
	 * 喇叭回复
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String type2(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		long userId = this.getLoginUser(req).getUserId();
		List<Notice> list = this.noticeService.getNoReadNoticeList(userId,
				Notice.NOTICETYPE_LABAREPLY, page.getBegin(),
				page.getSize() + 1);
		if (list.size() == 0) {
			list = this.noticeService.getNoticeList(userId,
					Notice.NOTICETYPE_LABAREPLY, page.getBegin(), page
							.getSize() + 1);
		}
		this.processListForPage(page, list);
		List<Long> labaIdList = new ArrayList<Long>();
		List<Long> cmtIdList = new ArrayList<Long>();
		List<Long> senderIdList = new ArrayList<Long>();
		for (Notice o : list) {
			if (o.isNoRead()) {
				this.noticeService.setNoticeRead(userId, o.getNoticeId());
			}
			Map<String, String> map = DataUtil.getMapFromJson(o.getData());
			labaIdList.add(Long.valueOf(map.get("labaid")));
			cmtIdList.add(Long.valueOf(map.get("cmtid")));
			senderIdList.add(Long.valueOf(map.get("userid")));
		}
		Map<Long, User> userMap = this.userService.getUserMapInId(senderIdList);
		Map<Long, Laba> labaMap = this.labaService.getLabaMapInId(labaIdList);
		Map<Long, LabaCmt> labaCmtMap = this.labaService
				.getLabaCmtMapInId(cmtIdList);
		LabaOutPutParser parser = null;
		Laba laba = null;
		LabaCmt cmt = null;
		UrlInfo urlInfo = this.getUrlInfoWeb4(req);
		for (Notice o : list) {
			parser = new LabaOutPutParser();
			Map<String, String> map = DataUtil.getMapFromJson(o.getData());
			o.setSender(userMap.get(Long.valueOf(map.get("userid"))));
			laba = labaMap.get(Long.valueOf(map.get("labaid")));
			if (laba != null) {
				o.setLabaContent(parser.getHtml(urlInfo, laba.getContent(), 0));
			}
			cmt = labaCmtMap.get(map.get("cmtid"));
			if (cmt == null) {
				o.setLabaCmtContent(parser.getHtml(urlInfo, map.get("content"),
						0));
			}
			else {
				o.setLabaCmtContent(parser
						.getHtml(urlInfo, cmt.getContent(), 0));
			}
		}
		req.setAttribute("list", list);
		return this.getWeb4Jsp("notice/notice_labareply.jsp");
	}

	/**
	 *关注通知
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String type3(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		long userId = this.getLoginUser(req).getUserId();
		List<Notice> list = this.noticeService.getNoReadNoticeList(userId,
				Notice.NOTICETYPE_FOLLOW, page.getBegin(), page.getSize() + 1);
		if (list.size() == 0) {
			list = this.noticeService.getNoticeList(userId,
					Notice.NOTICETYPE_FOLLOW, page.getBegin(),
					page.getSize() + 1);
		}
		this.processListForPage(page, list);
		for (Notice o : list) {
			if (o.isNoRead()) {
				this.noticeService.setNoticeRead(userId, o.getNoticeId());
			}
		}
		req.setAttribute("isWeb", true);
		List<NoticeVo> noticevolist = NoticeVo.createList(list, req);
		req.setAttribute("noticevolist", noticevolist);
		return this.getWeb4Jsp("notice/notice_follow.jsp");
	}

	/**
	 *地主
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String type6(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		long userId = this.getLoginUser(req).getUserId();
		List<Notice> list = this.noticeService.getNoReadNoticeList(userId,
				Notice.NOTICETYPE_CHANGEMAYOR, page.getBegin(),
				page.getSize() + 1);
		if (list.size() == 0) {
			list = this.noticeService.getNoticeList(userId,
					Notice.NOTICETYPE_CHANGEMAYOR, page.getBegin(), page
							.getSize() + 1);
		}
		this.processListForPage(page, list);
		for (Notice o : list) {
			if (o.isNoRead()) {
				this.noticeService.setNoticeRead(userId, o.getNoticeId());
			}
		}
		req.setAttribute("isWeb", true);
		List<NoticeVo> noticevolist = NoticeVo.createList(list, req);
		req.setAttribute("noticevolist", noticevolist);
		return this.getWeb4Jsp("notice/notice_changemayor.jsp");
	}

	/**
	 * 喇叭中提到了某个用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String type4(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		long userId = this.getLoginUser(req).getUserId();
		List<Notice> list = this.noticeService.getNoReadNoticeList(userId,
				Notice.NOTICETYPE_USER_IN_LABA, page.getBegin(),
				page.getSize() + 1);
		if (list.size() == 0) {
			list = this.noticeService.getNoticeList(userId,
					Notice.NOTICETYPE_USER_IN_LABA, page.getBegin(), page
							.getSize() + 1);
		}
		this.processListForPage(page, list);
		for (Notice o : list) {
			if (o.isNoRead()) {
				this.noticeService.setNoticeRead(userId, o.getNoticeId());
			}
		}
		List<Long> labaIdList = new ArrayList<Long>();
		List<Long> senderIdList = new ArrayList<Long>();
		for (Notice o : list) {
			Map<String, String> map = DataUtil.getMapFromJson(o.getData());
			labaIdList.add(Long.valueOf(map.get("labaid")));
			senderIdList.add(Long.valueOf(map.get("userid")));
		}
		Map<Long, Laba> labaMap = this.labaService.getLabaMapInId(labaIdList);
		Map<Long, User> userMap = this.userService.getUserMapInId(senderIdList);
		LabaOutPutParser parser = null;
		Laba laba = null;
		UrlInfo urlInfo = this.getUrlInfoWeb4(req);
		for (Notice o : list) {
			parser = new LabaOutPutParser();
			Map<String, String> map = DataUtil.getMapFromJson(o.getData());
			o.setSender(userMap.get(Long.valueOf(map.get("userid"))));
			laba = labaMap.get(Long.valueOf(map.get("labaid")));
			if (laba != null) {
				o.setLabaContent(parser.getHtml(urlInfo, laba.getContent(), 0));
			}
		}
		req.setAttribute("list", list);
		return this.getWeb4Jsp("notice/notice_userinlaba.jsp");
	}

	/**
	 * 邀请通知
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String type5(HkRequest req, HkResponse resp) throws Exception {
		SimplePage page = req.getSimplePage(20);
		long userId = this.getLoginUser(req).getUserId();
		List<Notice> list = this.noticeService.getNoReadNoticeList(userId,
				Notice.NOTICETYPE_INVITE, page.getBegin(), page.getSize() + 1);
		if (list.size() == 0) {
			list = this.noticeService.getNoticeList(userId,
					Notice.NOTICETYPE_INVITE, page.getBegin(),
					page.getSize() + 1);
		}
		this.processListForPage(page, list);
		for (Notice o : list) {
			if (o.isNoRead()) {
				this.noticeService.setNoticeRead(userId, o.getNoticeId());
			}
		}
		req.setAttribute("isWeb", true);
		List<NoticeVo> noticevolist = NoticeVo.createList(list, req);
		req.setAttribute("noticevolist", noticevolist);
		return this.getWeb4Jsp("notice/notice_invite.jsp");
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String list(HkRequest req, HkResponse resp) {
		User user = this.getLoginUser(req);
		byte noticeType = req.getByteAndSetAttr("noticeType");
		SimplePage page = req.getSimplePage(20);
		List<Notice> list = this.noticeService.getNoticeList(user.getUserId(),
				noticeType, page.getBegin(), page.getSize() + 1);
		this.processListForPage(page, list);
		for (Notice o : list) {
			if (o.isNoRead()) {
				this.noticeService.setNoticeRead(user.getUserId(), o
						.getNoticeId());
			}
		}
		req.setAttribute("isWeb", true);
		List<NoticeVo> noticevolist = NoticeVo.createList(list, req);
		req.setAttribute("noticevolist", noticevolist);
		return this.getWeb4Jsp("notice/list.jsp");
	}
}