package tuxiazi.bean.helper.noticedata;

import tuxiazi.bean.Notice;

/**
 * 创建notice通知数据
 * 
 * @author akwei
 */
public interface NoticeCreater {

	Notice buildNotice();

	String getIntro(Notice notice);
}