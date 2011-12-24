package com.hk.svr;

import com.hk.bean.CmdData;

public interface CmdDataService {
	/**
	 * 创建关键词(最好采取同步方式，防止出现同样关键词)
	 * 
	 * @param cmdData
	 * @return false 在有效期内存在同名关键词，创建失败。true创建成功
	 */
	boolean createCmdData(CmdData cmdData);

	/**
	 * 根据关键词获得指令对象
	 * 
	 * @param name 关键词
	 * @return
	 */
	CmdData getCmdDataByName(String name);

	CmdData getCmdDataByOidAndOtype(long oid, int otype);

	/**
	 * 删除指令对象
	 * 
	 * @param cmdId
	 */
	void deleteCmdData(long cmdId);

	void deleteCmdDataByOidAndOtype(long oid, int otype);

	/**
	 * 更新指令对象
	 * 
	 * @param cmdData
	 * @return true 更新成功 ,false:更新失败，存在同名并且有效期冲突
	 */
	boolean updateCmdData(CmdData cmdData);
}