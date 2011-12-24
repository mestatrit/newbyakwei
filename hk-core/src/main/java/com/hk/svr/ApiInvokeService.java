package com.hk.svr;

import java.util.Date;

import com.hk.bean.taobao.ApiInvoke;
import com.hk.bean.taobao.ApiInvokeRate;

public interface ApiInvokeService {

	ApiInvoke addInvoke_count(int add, byte testflg, Date current);

	ApiInvoke getApiInvokeByTestflg(byte testflg);

	ApiInvokeRate initApiInvokeRateForLast();

	void updateApiInvokeRate(ApiInvokeRate apiInvokeRate);
}