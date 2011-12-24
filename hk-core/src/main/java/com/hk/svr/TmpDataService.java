package com.hk.svr;

import com.hk.bean.TmpData;

public interface TmpDataService {

	void createTmpData(TmpData tmpData);

	TmpData getTmpData(long oid);

	/**
	 * 获得用户最后一次创建的临时数据
	 * 
	 * @param userId
	 * @param dataType
	 * @return
	 *         2010-4-24
	 */
	TmpData getLastTmpDataByUserIdAndDataType(long userId, byte dataType);

	void deleteTmpData(long oid);

	void deleteTmpDataByUserIdAndDataType(long userId, byte dataType);

	void updateTmpData(TmpData tmpData);
}