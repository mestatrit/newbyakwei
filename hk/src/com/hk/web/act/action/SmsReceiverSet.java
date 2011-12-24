package com.hk.web.act.action;

import java.util.HashSet;
import java.util.Set;

public class SmsReceiverSet {
	private long actId;

	private Set<Long> useridSet = new HashSet<Long>();

	public void addUserId(long userId) {
		this.useridSet.add(userId);
	}

	public long getActId() {
		return actId;
	}

	public void setActId(long actId) {
		this.actId = actId;
	}

	public Set<Long> getUseridSet() {
		return useridSet;
	}

	public void setUseridSet(Set<Long> useridSet) {
		this.useridSet = useridSet;
	}
}