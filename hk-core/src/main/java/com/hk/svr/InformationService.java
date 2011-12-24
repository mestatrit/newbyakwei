package com.hk.svr;

import java.util.Date;
import java.util.List;
import com.hk.bean.Information;
import com.hk.svr.user.exception.NoSmsPortException;

public interface InformationService {
	void createInformation(Information information, int month)
			throws NoSmsPortException;

	Information getInformationByPortId(long portId);

	List<Information> getInformationList(long userId, int begin, int size);

	Information getInformation(long infoId);

	void changeUseStatus(long infoId, byte useStatus);

	/**
	 * @param minTime 结束时间的最小时间
	 * @param maxTime 结束时间的最大时间
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Information> getInformationListForEndTime(Date minTime, Date maxTime,
			int begin, int size);

	void updateInformation(Information information);
}