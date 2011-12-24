package com.hk.web.cmpunion.valuecreater.feed;

import javax.servlet.http.HttpServletRequest;

import com.hk.web.cmpunion.action.CmpUnionFeedVo;

/**
 * 处理接口。用于组装数据
 * 
 * @author akwei
 */
public interface FeedValueCreater {
	/**
	 * 根据obj 返回相应数据
	 * 
	 * @param request
	 * @param obj
	 * @return
	 */
	String getValue(HttpServletRequest request, CmpUnionFeedVo cmpUnionFeedVo);
}
