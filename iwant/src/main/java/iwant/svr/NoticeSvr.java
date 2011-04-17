package iwant.svr;

import iwant.bean.Notice;
import iwant.bean.UserNotice;

import java.util.List;

public interface NoticeSvr {

	/**
	 * 创建通知信息
	 * 
	 * @param notice
	 */
	void createNotice(Notice notice);

	/**
	 * 删除通知，并不能取消通知对用户的发送
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

	/**
	 * 发送推送信息到APNS
	 * 
	 * @param noticeid
	 * @param userid
	 * @return
	 */
	boolean sendApnsNotice(String content, long userid);

	boolean sendApnsNotice(String device_token, String content);

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
}