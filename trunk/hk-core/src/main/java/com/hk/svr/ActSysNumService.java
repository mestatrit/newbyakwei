package com.hk.svr;

import java.util.Date;

import com.hk.bean.ActSysNum;
import com.hk.svr.user.exception.NoAvailableActSysNumException;

public interface ActSysNumService {
	void tmpcreateSysnum(ActSysNum actSysNum);

	ActSysNum createAvailableActSysNum() throws NoAvailableActSysNumException;

	/**
	 * 修改暗号状态和过期时间
	 * 
	 * @param sysId
	 * @param actId
	 * @param sysStatus
	 * @param overTime
	 */
	void updateActSysNumInfo(int sysId, long actId, byte sysStatus,
			byte usetype, Date overTime);

	ActSysNum getActSysNumBySysNum(String sysNum);

	ActSysNum getActSysNum(long sysId);
}