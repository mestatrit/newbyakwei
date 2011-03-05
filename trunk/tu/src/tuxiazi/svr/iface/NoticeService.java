package tuxiazi.svr.iface;

import java.util.List;

import tuxiazi.bean.Notice;
import tuxiazi.bean.benum.NoticeEnum;
import tuxiazi.bean.benum.NoticeReadEnum;

/**
 * 通知功能的业务处理
 * 
 * @author akwei
 */
public interface NoticeService {

	/**
	 * 创建通知
	 * 
	 * @param notice
	 */
	void createNotice(Notice notice);

	void deleteNotice(Notice notice);

	Notice getNotice(long noticeid);

	/**
	 * 获取用户的通知信息
	 * 
	 * @param userid 用户id
	 * @param begin 记录开始数
	 * @param size 获取记录数量
	 * @return 通知数据集合
	 */
	List<Notice> getNoticeListByUserid(long userid, int begin, int size);

	/**
	 * 根据读取状态获取通知数据集合
	 * 
	 * @param userid
	 * @param noticeReadEnum
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Notice> getNoticeListByUseridAndReadflg(long userid,
			NoticeReadEnum noticeReadEnum, int begin, int size);

	/**
	 * 根据不同类型获得未读通知
	 * 
	 * @param userid
	 * @param noticeEnum
	 * @return
	 */
	int countNoticeByUseridAndNotice_flgForUnread(long userid,
			NoticeEnum noticeEnum);

	/**
	 * 统计所有未读通知
	 * 
	 * @param userid
	 * @return
	 */
	int countNoticeByUseridForUnread(long userid);

	void setNoticeReaded(long noticeid);
}