package tuxiazi.webapi;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import halo.web.util.SimplePage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Notice;
import tuxiazi.bean.User;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;
import tuxiazi.dao.NoticeDao;
import tuxiazi.svr.iface.NoticeService;
import tuxiazi.web.util.APIUtil;

@Component("/api/notice")
public class NoticeAction extends BaseApiAction {

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private NoticeDao noticeDao;

	private final Log log = LogFactory.getLog(NoticeAction.class);

	/**
	 * 获取最新通知.如果有未读通知，就显示最新的未读通知，如果没有未读，就显示最新的通知
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String prvlasted(HkRequest req, HkResponse resp) throws Exception {
		try {
			User user = this.getUser(req);
			int page = req.getInt("page", 1);
			int size = req.getInt("size", 20);
			SimplePage simplePage = new SimplePage(size, page);
			int unreadcount = this.noticeDao.countByUseridForUnread(user
					.getUserid());
			// 如果有未读信息，就获取未读通知
			// 如果没有，就获取所有已读的通知
			List<Notice> list = null;
			if (unreadcount > 0) {
				list = this.noticeDao.getListByUseridAndReadflg(
						user.getUserid(), NoticeReadEnum.UNREAD, true,
						simplePage.getBegin(), simplePage.getSize());
				for (Notice o : list) {
					this.noticeService.setNoticeReaded(o.getNoticeid());
				}
			}
			else {
				list = this.noticeDao.getListByUserid(user.getUserid(), true,
						simplePage.getBegin(), simplePage.getSize());
			}
			List<NoticeInfo> infolist = new ArrayList<NoticeInfo>(list.size());
			for (Notice o : list) {
				infolist.add(new NoticeInfo(o));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("infolist", infolist);
			APIUtil.writeData(resp, map, "vm/notice.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
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
		try {
			User user = this.getUser(req);
			// 评论通知
			int cmt_unread_count = this.noticeDao
					.countByUseridAndNotice_flgForUnread(user.getUserid(),
							NoticeEnum.ADD_PHOTOCMT);
			// 喜欢通知
			int like_unread_count = this.noticeDao
					.countByUseridAndNotice_flgForUnread(user.getUserid(),
							NoticeEnum.ADD_PHOTOLIKE);
			// follow通知
			int follow_unread_count = this.noticeDao
					.countByUseridAndNotice_flgForUnread(user.getUserid(),
							NoticeEnum.ADD_FOLLOW);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cmt_unread_count", cmt_unread_count);
			map.put("like_unread_count", like_unread_count);
			map.put("follow_unread_count", follow_unread_count);
			APIUtil.writeData(resp, map, "vm/noticeinfo.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
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
	public String prvunreadcount(HkRequest req, HkResponse resp) {
		try {
			User user = this.getUser(req);
			int count = this.noticeDao.countByUseridForUnread(user.getUserid());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("count", count);
			APIUtil.writeData(resp, map, "vm/unreadcount.vm");
		}
		catch (Exception e) {
			log.error(e.getMessage());
			this.writeSysErr(resp);
		}
		return null;
	}
}