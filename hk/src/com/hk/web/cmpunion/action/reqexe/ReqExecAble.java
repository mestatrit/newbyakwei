package com.hk.web.cmpunion.action.reqexe;

import java.util.HashMap;
import java.util.Map;

import com.hk.bean.CmpUnionReq;
import com.hk.svr.pub.CmpUnionMessageUtil;

/**
 * 请求分派处理
 * 
 * @author akwei
 */
public abstract class ReqExecAble {
	public static final Map<Integer, ReqExecAble> map = new HashMap<Integer, ReqExecAble>();
	/**
	 * 初始化过程，加载预定义的请求处理类
	 */
	static {
		map.put(CmpUnionMessageUtil.REQ_JOIN_IN_CMPUNION,
				new JoinInCmpUnionReqExecAble());
	}

	public static void exe(CmpUnionReq cmpUnionReq) {
		map.get(cmpUnionReq.getReqflg()).execute(cmpUnionReq);
	}

	/**
	 * 根据类型处理请求
	 * 
	 * @param cmpUnionReq 请求对象
	 */
	abstract void execute(CmpUnionReq cmpUnionReq);
}