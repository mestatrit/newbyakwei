package com.hk.web.laba.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.IndexLaba;
import com.hk.bean.Laba;
import com.hk.frame.util.DataUtil;
import com.hk.svr.LabaService;
import com.hk.web.pub.action.LabaParserCfg;
import com.hk.web.pub.action.LabaVo;

public class IndexSearchLabaJob {

	// private final Log log = LogFactory.getLog(IndexSearchLabaJob.class);
	@Autowired
	private LabaService labaService;

	public void invoke() {
		// log.info("begin index laba data .....");
		// long begin = System.currentTimeMillis();
		int count = this.labaService.count();
		List<Laba> list = this.labaService.getLabaList(0, count);
		LabaParserCfg cfg = new LabaParserCfg();
		cfg.setCheckFav(false);
		cfg.setParseLongContent(true);
		cfg.setParseContent(true);
		List<LabaVo> labavolist = LabaVo.createVoList(list, cfg);
		List<IndexLaba> idxlist = new ArrayList<IndexLaba>();
		for (LabaVo vo : labavolist) {
			IndexLaba o = new IndexLaba();
			o.setLabaId(vo.getLaba().getLabaId());
			if (!DataUtil.isEmpty(vo.getLongContent())) {
				o.setContent(vo.getLongContent());
			}
			else {
				o.setContent(vo.getContent());
			}
			idxlist.add(o);
		}
		labaService.indexLaba(idxlist);
		// long end = System.currentTimeMillis();
		// log.info("end index laba in " + (end - begin));
	}
}