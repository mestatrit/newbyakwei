package com.hk.api.action.laba;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.api.action.BaseApiAction;
import com.hk.api.util.APIUtil;
import com.hk.bean.Laba;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.LabaService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.LabaParserCfg;
import com.hk.web.pub.action.LabaVo;

// @Component("/pubapi/laba")
public class LabaAction extends BaseApiAction {

	@Autowired
	private LabaService labaService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long labaId = req.getLong("labaId");
		Laba laba = this.labaService.getLaba(labaId);
		if (laba == null) {
			APIUtil.sendRespStatus(resp, "fail", Err.NOOBJECT_ERROR);
			return null;
		}
		LabaParserCfg cfg = this.getApiLabaParserCfg();
		cfg.setParseLongContent(true);
		LabaVo labaVo = LabaVo.create(laba, cfg);
		VelocityContext context = new VelocityContext();
		context.put("vo", labaVo);
		this.write(resp, "vm/laba/laba.vm", context);
		return null;
	}

	public String search(HkRequest req, HkResponse resp) throws Exception {
		int size = this.getSize(req);
		String key = req.getString("key");
		VelocityContext context = new VelocityContext();
		if (!DataUtil.isEmpty(key)) {
			key = key.replaceAll("\\+", "").replaceAll("&", "");
			SimplePage page = req.getSimplePage(size);
			List<Laba> list = labaService.getLabaListForSearch(key, page
					.getBegin(), size);
			LabaParserCfg cfg = this.getApiLabaParserCfg();
			List<LabaVo> volist = LabaVo.createVoList(list, cfg);
			context.put("labalist", volist);
		}
		this.write(resp, "vm/laba/labalist.vm", context);
		return null;
	}

	public String list(HkRequest req, HkResponse resp) throws Exception {
		int size = this.getSize(req);
		SimplePage page = req.getSimplePage(size);
		List<Laba> list = this.labaService.getLabaList(page.getBegin(), size);
		LabaParserCfg cfg = this.getApiLabaParserCfg();
		List<LabaVo> volist = LabaVo.createVoList(list, cfg);
		VelocityContext context = new VelocityContext();
		context.put("labalist", volist);
		this.write(resp, "vm/laba/labalist.vm", context);
		return null;
	}

	public String user(HkRequest req, HkResponse resp) throws Exception {
		int size = this.getSize(req);
		long userId = req.getLong("userId");
		SimplePage page = req.getSimplePage(size);
		List<Laba> list = this.labaService.getLabaListByUserId(userId, page
				.getBegin(), size);
		LabaParserCfg cfg = this.getApiLabaParserCfg();
		List<LabaVo> volist = LabaVo.createVoList(list, cfg);
		VelocityContext context = new VelocityContext();
		context.put("labalist", volist);
		this.write(resp, "vm/laba/labalist.vm", context);
		return null;
	}
}