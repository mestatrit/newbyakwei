package iwant.svr;

import iwant.bean.Notice;
import iwant.bean.UserNotice;

import java.util.List;

import javapns.data.PayLoad;

public interface NoticeSvr {

	/**
	 * 创建通知信息
	 * 
	 * @param notice
	 */
	void createNotice(Notice notice);

	/**
	 * 删除通知
	 * 
	 * @param noticeid
	 */
	void deleteNotice(long noticeid);

	/**
	 * 创建用户通知
	 * 
	 * @param noticeid
	 * @param userid
	 * @return true:创建成功;false:如果已经存在，不再次创建
	 */
	UserNotice createUserNotice(long noticeid, long userid);

	void sendApnsNotice(String device_token, PayLoad payLoad) throws Exception;

	/**
	 * 获取所有通知信息，按照时间倒序
	 * 
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Notice> getNoticeList(int begin, int size);

	/**
	 * 获得通知信息
	 * 
	 * @param noticeid
	 * @return
	 */
	Notice getNotice(long noticeid);

	List<UserNotice> getUserNoticeList(boolean buildNotice, int begin, int size);

	void deleteUserNotice(UserNotice userNotice);

	UserNotice getUserNoticeByUseridAndNoticeid(long userid, long noticeid);
}