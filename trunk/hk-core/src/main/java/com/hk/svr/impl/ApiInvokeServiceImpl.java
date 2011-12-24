package com.hk.svr.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.ApiInvoke;
import com.hk.bean.taobao.ApiInvokeRate;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.ApiInvokeService;

public class ApiInvokeServiceImpl implements ApiInvokeService {

	@Autowired
	private QueryManager manager;

	@Override
	public ApiInvoke addInvoke_count(int add, byte testflg, Date current) {
		Query query = manager.createQuery();
		if (testflg == ApiInvoke.TESTFLG_PUBLICTEST) {// 线上测试环境每天只能调用api 5000次
			Date date = DataUtil.getDate(current);// 获得日期
			ApiInvoke apiInvoke = query.getObjectEx(ApiInvoke.class,
					"testflg=?", new Object[] { testflg });
			if (apiInvoke == null) {
				apiInvoke = new ApiInvoke();
				apiInvoke.setTime(date);
				apiInvoke.setInvoke_count(1);
				apiInvoke.setTestflg(testflg);
				apiInvoke.setOid(query.insertObject(apiInvoke).intValue());
			}
			else {
				Calendar last = Calendar.getInstance();
				last.setTime(apiInvoke.getTime());
				Calendar now = Calendar.getInstance();
				now.setTime(current);
				// 如果调用时间为同一天，则计数器累加
				if (last.get(Calendar.DATE) == now.get(Calendar.DATE)) {
					apiInvoke
							.setInvoke_count(apiInvoke.getInvoke_count() + add);
				}
				else {// 如果不是同一天，更新最新日期与次数=1
					apiInvoke.setInvoke_count(1);
					apiInvoke.setTime(date);
				}
				query.updateObject(apiInvoke);
			}
			return apiInvoke;
		}
		else if (testflg == ApiInvoke.TESTFLG_PUBLIC) {// 正式环境每分钟调用频率
			ApiInvoke apiInvoke = query.getObjectEx(ApiInvoke.class,
					"testflg=?", new Object[] { testflg });
			if (apiInvoke == null) {
				apiInvoke = new ApiInvoke();
				apiInvoke.setTime(current);
				apiInvoke.setInvoke_count(1);
				apiInvoke.setTestflg(testflg);
				apiInvoke.setOid(query.insertObject(apiInvoke).intValue());
			}
			else {
				// 与最新调用频率进行比较
				if (apiInvoke.getTime().getTime() > current.getTime() - 60000) {// 调用时间在1分钟内
					apiInvoke
							.setInvoke_count(apiInvoke.getInvoke_count() + add);
				}
				else {
					apiInvoke.setInvoke_count(1);
					apiInvoke.setTime(current);
				}
				query.updateObject(apiInvoke);
			}
			return apiInvoke;
		}
		return null;
	}

	@Override
	public ApiInvoke getApiInvokeByTestflg(byte testflg) {
		Query query = manager.createQuery();
		return query.getObjectEx(ApiInvoke.class, "testflg=?",
				new Object[] { testflg });
	}

	@Override
	public ApiInvokeRate initApiInvokeRateForLast() {
		Query query = manager.createQuery();
		ApiInvokeRate apiInvokeRate = query.getObject(ApiInvokeRate.class,
				null, null, "oid desc");
		if (apiInvokeRate == null) {
			apiInvokeRate = new ApiInvokeRate();
			apiInvokeRate.setRate(50);
			apiInvokeRate.setOid(query.insertObject(apiInvokeRate).intValue());
		}
		return apiInvokeRate;
	}

	@Override
	public void updateApiInvokeRate(ApiInvokeRate apiInvokeRate) {
		Query query = manager.createQuery();
		query.updateObject(apiInvokeRate);
	}
}