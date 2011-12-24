package com.hk.sms2.smsport;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmdData;
import com.hk.frame.util.DataUtil;
import com.hk.sms.cmd.BaseCmd;
import com.hk.sms.cmd.CreateLabaCmd;
import com.hk.sms.cmd.MgrCompanyCmd;
import com.hk.svr.CmdDataService;

public class RootSmsPort extends DefSmsPortProcessAble {
	private Map<Integer, BaseCmd> cmddatacmdmap;

	@Autowired
	private CreateLabaCmd createLabaCmd;

	@Autowired
	private MgrCompanyCmd mgrCompanyCmd;

	@Autowired
	private CmdDataService cmdDataService;

	public void setCmddatacmdmap(Map<Integer, BaseCmd> cmddatacmdmap) {
		this.cmddatacmdmap = cmddatacmdmap;
	}

	@Override
	public BaseCmd getCmd(String content) {
		BaseCmd cmd = super.getCmd(content);
		if (cmd == null) {// 如果不含有系统关键字
			if (DataUtil.isSingleNumber(content)) {// 管理足迹信息
				return this.mgrCompanyCmd;
			}
			CmdData cmdData = this.cmdDataService.getCmdDataByName(content);
			if (cmdData != null) {
				cmd = this.cmddatacmdmap.get(cmdData.getOtype());
				if (cmd != null) {
					return cmd;
				}
			}
			return this.createLabaCmd;// 如果不在5个字符内,又不含有短信关键字,就是发喇叭
		}
		return cmd;
	}
}