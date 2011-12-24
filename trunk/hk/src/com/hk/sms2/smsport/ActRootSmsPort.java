package com.hk.sms2.smsport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Act;
import com.hk.bean.ActSysNum;
import com.hk.bean.ChgCardAct;
import com.hk.frame.util.DataUtil;
import com.hk.sms.cmd.BaseCmd;
import com.hk.sms.cmd.ChgCardActCmd;
import com.hk.svr.ActService;
import com.hk.svr.ActSysNumService;
import com.hk.svr.ChgCardActService;

public class ActRootSmsPort extends DefSmsPortProcessAble {
	@Autowired
	private ChgCardActCmd chgCardActCmd;

	@Autowired
	private ChgCardActService chgCardActService;

	@Autowired
	ActSysNumService actSysNumService;

	@Autowired
	private ActService actService;

	private final Log log = LogFactory.getLog(ActRootSmsPort.class);

	@Override
	public BaseCmd getCmd(String content) {
		int len = content.length();
		if (len == 6) {
			return this.chgCardActCmd;
		}
		if (len > 6) {// 如果前6位是暗号，
			String s = content.substring(0, 6);
			if (DataUtil.isNumber(s)) {// 如果是6位数字，可认定为系统号码
				ActSysNum o = this.actSysNumService.getActSysNumBySysNum(s);
				if (o != null) {
					// 如果是名片交换活动，就执行
					ChgCardAct act = this.chgCardActService.getChgCardAct(o
							.getActId());
					if (act != null) {
						return this.chgCardActCmd;
					}
					Act _act = this.actService.getAct(o.getActId());
					if (_act != null) {
						return this.chgCardActCmd;
					}
				}
			}
		}
		log.info("no cmd in actrootsmsport");
		return null;
	}
}