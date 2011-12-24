package com.hk.listener.laba;

import java.util.EventListener;

import com.hk.bean.Laba;
import com.hk.bean.LabaCmt;
import com.hk.svr.laba.parser.LabaInfo;

public interface LabaEventListener extends EventListener {
	void labaCreated(Laba laba, LabaInfo labaInfo);

	void labaCmtCreated(LabaCmt labaCmt, LabaInfo labaInfo);
}