package com.hk.web.notice.util;

import javax.servlet.http.HttpServletRequest;

import com.hk.bean.Notice;

public interface NoticeContentCreater {
	String execute(HttpServletRequest request, Notice notice);
}