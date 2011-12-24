package com.hk.sms2.smsport;

import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.Act;
import com.hk.bean.ActSysNum;
import com.hk.bean.ChgCardAct;
import com.hk.frame.util.DataUtil;
import com.hk.sms.cmd.BaseCmd;
import com.hk.sms.cmd.ChgCardActCmd;
import com.hk.sms.cmd.CreateLabaCmd;
import com.hk.sms.cmd.MgrCompanyCmd;
import com.hk.svr.ActService;
import com.hk.svr.ActSysNumService;
import com.hk.svr.ChgCardActService;

public class CopyOfRootSmsPort extends DefSmsPortProcessAble {
	@Autowired
	private CreateLabaCmd createLabaCmd;

	@Autowired
	private ChgCardActCmd chgCardActCmd;

	@Autowired
	private ChgCardActService chgCardActService;

	@Autowired
	ActSysNumService actSysNumService;

	@Autowired
	private ActService actService;

	@Autowired
	private MgrCompanyCmd mgrCompanyCmd;

	@Override
	public BaseCmd getCmd(String content) {
		BaseCmd cmd = super.getCmd(content);
		if (cmd == null) {// 如果不含有短信关键字
			int len = content.length();
			if (DataUtil.isSingleNumber(content)) {// 管理足迹信息
				return this.mgrCompanyCmd;
			}
			// if (len <= 4) {// 4个字符:宝箱系统指令
			// OpenBox box = this.boxService.getOpenBoxByBoxKey(content);
			// if (box == null) {// 如果未到开箱时间或者开箱指令错误，就作为喇叭发送
			// return this.createLabaCmd;
			// }
			// return this.openBoxCmd;
			// }
			// if (len == 5) { // 5位邀请注册码
			// }
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
			return this.createLabaCmd;// 如果不在5个字符内,又不含有短信关键字,就是发喇叭
		}
		return cmd;
	}
}