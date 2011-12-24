package com.etbhk.web.vo.news;

import com.hk.bean.taobao.Tb_News;

public interface CombinedNewsMaker {

	/**
	 * 动态是否可以合并
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 *         2010-9-16
	 */
	boolean isCombined(Tb_News o1, Tb_News o2);
}