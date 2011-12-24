package com.hk.web.card.job;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.ActSysNum;
import com.hk.svr.ActSysNumService;
import com.hk.svr.ChgCardActService;

/**
 * 启动间隔时间来更新活动暗号的状态和过期时间
 * 
 * @author yuanwei
 */
public class CardJob {
	@Autowired
	private ChgCardActService chgCardActService;

	@Autowired
	ActSysNumService actSysNumService;

	public void invoke() {
		List<ActSysNum> list = this.chgCardActService.getInUseActSysNumList(0,
				1000);
		Date date = new Date();
		for (ActSysNum o : list) {
			this.actSysNumService.updateActSysNumInfo(o.getSysId(), 0,
					ActSysNum.SYSSTATUS_FREE, ActSysNum.USETYPE_CARD, date);
		}
	}
}