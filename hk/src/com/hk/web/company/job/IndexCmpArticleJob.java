package com.hk.web.company.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpInfo;
import com.hk.svr.CmpInfoService;
import com.hk.svr.processor.CmpArticleProcessor;

public class IndexCmpArticleJob {

	@Autowired
	private CmpInfoService cmpInfoService;

	@Autowired
	private CmpArticleProcessor cmpArticleProcessor;

	public void invoke() {
		List<CmpInfo> list = this.cmpInfoService.getCmpInfoList(0, -1);
		for (CmpInfo o : list) {
			this.cmpArticleProcessor.indexCmpArticleListByComapnyIdAndKey(o
					.getCompanyId());
		}
	}
}