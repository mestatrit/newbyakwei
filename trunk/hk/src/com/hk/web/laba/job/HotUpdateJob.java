package com.hk.web.laba.job;

import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.RespLaba;
import com.hk.bean.Tag;
import com.hk.svr.LabaService;
import com.hk.svr.TagService;

public class HotUpdateJob {
	@Autowired
	private TagService tagService;

	@Autowired
	private LabaService labaService;

	public void invoke() {
		this.invokeHotTag();
		this.invokeHotLaba();
	}

	public void invokeHotTag() {
		Calendar b = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		b.add(Calendar.DATE, -7);
		List<Tag> list = this.tagService.getTagListByUpdateTimeRange(b
				.getTime(), e.getTime());
		for (Tag o : list) {
			this.tagService.updateTagHot(o);
		}
		b.set(Calendar.YEAR, 2000);// 设置开始时间为最小
		e.add(Calendar.DATE, -7);
		list = this.tagService.getTagListByUpdateTimeRange(b.getTime(), e
				.getTime());
		for (Tag o : list) {// 7天之外数据hot更新为喇叭数量×100
			this.tagService.updateHot(o.getTagId(), o.getLabaCount() * 100);
		}
	}

	public void invokeHotLaba() {
		Calendar b = Calendar.getInstance();
		Calendar e = Calendar.getInstance();
		b.add(Calendar.DATE, -7);
		List<RespLaba> list = this.labaService.getRespLabaList(b.getTime(), e
				.getTime());
		for (RespLaba o : list) {
			this.labaService.updateHotLaba(o);
		}
		b.set(Calendar.YEAR, 2000);// 设置开始时间为最小
		e.add(Calendar.DATE, -7);
		list = this.labaService.getRespLabaList(b.getTime(), e.getTime());
		for (RespLaba o : list) {// 7天之外数据hot更新为喇叭数量×100
			o.setHot(o.getRespcount() * 100);
			this.labaService.updateHotLaba(o);
		}
	}
}