package tuxiazi.webapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Notice;
import tuxiazi.bean.User;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.svr.iface.NoticeService;
import tuxiazi.web.util.APIUtil;

import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/api/notice")
public class NoticeAction extends BaseApiAction {

	@Autowired
	private NoticeService noticeService;

	/**
	 * 获取最新通知.如果有未读通知，就显示最新的未读通知，如果没有未读，就显示最新的通知
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvlasted(HkRequest req, HkResponse resp) throws Exception {
		User user = this.getUser(req);
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 20);
		SimplePage simplePage = new SimplePage(size, page);
		int unreadcount = this.noticeService.countNoticeByUseridForUnread(user
				.getUserid());
		List<Notice> list = null;
		if (unreadcount > 0) {
			list = this.noticeService.getNoticeListByUseridAndReadflg(user
					.getUserid(), NoticeReadEnum.UNREAD, simplePage.getBegin(),
					simplePage.getSize());
			for (Notice o : list) {
				this.noticeService.setNoticeReaded(o.getNoticeid());
			}
		}
		else {
			list = this.noticeService.getNoticeListByUserid(user.getUserid(),
					simplePage.getBegin(), simplePage.getSize());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/notice.vm");
		return null;
	}

	/**
	 * 分类查看未读通知数量
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvinfo(HkRequest req, HkResponse resp) throws Exception {
		User user = this.getUser(req);
		// 评论通知
		int cmt_unread_count = this.noticeService
				.countNoticeByUseridAndNotice_flgForUnread(user.getUserid(),
						NoticeEnum.ADD_PHOTOCMT);
		// 喜欢通知
		int like_unread_count = this.noticeService
				.countNoticeByUseridAndNotice_flgForUnread(user.getUserid(),
						NoticeEnum.ADD_PHOTOLIKE);
		// follow通知
		int follow_unread_count = this.noticeService
				.countNoticeByUseridAndNotice_flgForUnread(user.getUserid(),
						NoticeEnum.ADD_FOLLOW);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cmt_unread_count", cmt_unread_count);
		map.put("like_unread_count", like_unread_count);
		map.put("follow_unread_count", follow_unread_count);
		APIUtil.writeData(resp, map, "vm/noticeinfo.vm");
		return null;
	}

	/**
	 * 分类查看未读通知数量
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvunreadcount(HkRequest req, HkResponse resp)
			throws Exception {
		User user = this.getUser(req);
		int count = this.noticeService.countNoticeByUseridForUnread(user
				.getUserid());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		APIUtil.writeData(resp, map, "vm/unreadcount.vm");
		return null;
	}
}