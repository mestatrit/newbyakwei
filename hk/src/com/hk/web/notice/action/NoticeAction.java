package com.hk.web.notice.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.Feed;
import com.hk.bean.Notice;
import com.hk.bean.User;
import com.hk.bean.UserNoticeInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.NoticeService;
import com.hk.svr.UserService;
import com.hk.svr.impl.FeedServiceWrapper;
import com.hk.web.feed.action.FeedVo;
import com.hk.web.pub.action.BaseAction;

@Component("/notice/notice")
public class NoticeAction extends BaseAction {
	@Autowired
	private NoticeService noticeService;

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) {
		User loginUser = this.getLoginUser(req);
		long userId = loginUser.getUserId();
		if (this.noticeService.countNoReadNotice(userId,
				Notice.NOTICETYPE_LABAREPLY) == 1) {// 如果通知是一条
			Notice notice = this.noticeService.getLastNoReadNotice(userId,
					Notice.NOTICETYPE_LABAREPLY);// 如果这一条通知是喇叭的通知，则直接跳到喇叭单页面
			if (notice != null) {
				this.noticeService.setNoticeRead(userId, notice.getNoticeId());
				Map<String, String> map = DataUtil.getMapFromJson(notice.getData());
				return "r:/laba/laba.do?labaId=" + map.get("labaid");
			}
		}
		List<Byte> typelist = Notice.getNoticeTypeList();
		List<NoticeSortVo> list = new ArrayList<NoticeSortVo>();
		UserNoticeInfo userNoticeInfo = this.userService
				.getUserNoticeInfo(userId);
		for (Byte b : typelist) {
			int noreadcount = this.noticeService.countNoReadNotice(userId, b);
			if (b.byteValue() == Notice.NOTICETYPE_INVITE && noreadcount == 0) {// 如果是邀请的通知，则看完就不显示
				continue;
			}
			NoticeSortVo vo = new NoticeSortVo();
			vo.setNoReadCount(noreadcount);
			vo.setNoticeType(b);
			vo.setNoticeTypeIntro(req.getText("notice.noticetype."
					+ b.byteValue()));
			vo.setCloseSysNotice(this.setCloseSysNotice(b, userNoticeInfo));
			if (b.byteValue() == Notice.NOTICETYPE_INVITE) {// 邀请通知，设置不显示开关
				vo.setShowSwitch(false);
			}
			list.add(vo);
			// 如果用户设置不提示, 只保留标题显示，不显示内容
			if (userNoticeInfo != null && !userNoticeInfo.isNoticeUserInLaba()
					&& b == Notice.NOTICETYPE_USER_IN_LABA) {// 如果是喇叭中提到了某个用户的通知，用户设置为不提示，则保留标题，方便以后打开
				continue;
			}
			List<Notice> nlist = null;
			if (vo.getNoReadCount() > 0) {// 如果有未读通知，则取未读数据
				nlist = this.noticeService.getNoReadNoticeList(userId, b, 0, 5);
			}
			else {// 否则取以前的通知数据
				nlist = this.noticeService.getNoticeList(userId, b, 0, 5);
			}
			for (Notice o : nlist) {
				if (o.getReadflg() == Notice.READFLG_N) {// 设置为已读
					this.noticeService.setNoticeRead(userId, o.getNoticeId());
				}
			}
			List<NoticeVo> volist = NoticeVo.createList(nlist, req);
			vo.setList(volist);
		}
		Collections.sort(list, c);
		// 好友动态
		FeedServiceWrapper wrapper = new FeedServiceWrapper();
		List<Feed> feedlist = wrapper.getFriendFeedList(userId, 0, 5);
		List<FeedVo> feedvolist = FeedVo.createList(req, feedlist, true, true);
		req.setAttribute("list", list);
		req.setAttribute("feedvolist", feedvolist);
		return "/WEB-INF/page/notice/notice.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String more(HkRequest req, HkResponse resp) {
		User user = this.getLoginUser(req);
		byte noticeType = req.getByte("noticeType");
		if (noticeType == Notice.NOTICETYPE_FOLLOW) {
			return "r:/follow/follow_re.do";
		}
		else if (noticeType == Notice.NOTICETYPE_LABAREPLY) {
			return "r:/laba/reuserlist.do?userId=" + user.getUserId();
		}
		return null;
	}

	private boolean setCloseSysNotice(byte noticeType,
			UserNoticeInfo userNoticeInfo) {
		if (userNoticeInfo != null) {
			if (noticeType == Notice.NOTICETYPE_LABAREPLY) {
				if (userNoticeInfo.getLabaReplySysNotice() == UserNoticeInfo.NOTICE_Y) {
					return true;
				}
				return false;
			}
			else if (noticeType == Notice.NOTICETYPE_FOLLOW) {
				if (userNoticeInfo.getFollowSysNotice() == UserNoticeInfo.NOTICE_Y) {
					return true;
				}
				return false;
			}
			else if (noticeType == Notice.NOTICETYPE_USER_IN_LABA) {
				if (userNoticeInfo.isNoticeUserInLaba()) {
					return true;
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * 有未读通知的模块显示在上方，，如果同样有未读，则喇叭回复模块显示在上
	 */
	private Comparator<NoticeSortVo> c = new Comparator<NoticeSortVo>() {
		public int compare(NoticeSortVo o1, NoticeSortVo o2) {
			if (o1.getNoReadCount() > o2.getNoReadCount()) {
				return -1;
			}
			if (o1.getNoReadCount() == o2.getNoReadCount()) {
				if (o1.getNoticeType() == Notice.NOTICETYPE_LABAREPLY) {
					return -1;
				}
				else if (o2.getNoticeType() == Notice.NOTICETYPE_LABAREPLY) {
					return 1;
				}
				return 0;
			}
			return 1;
		}
	};
}