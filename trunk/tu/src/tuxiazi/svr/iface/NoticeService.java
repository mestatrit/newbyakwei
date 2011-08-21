package tuxiazi.svr.iface;

import tuxiazi.bean.Notice;

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

	void setNoticeReaded(long noticeid);
}