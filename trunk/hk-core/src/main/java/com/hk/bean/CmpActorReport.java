package com.hk.bean;

/**
 * 工作人员统计数据
 * 
 * @author akwei
 */
public class CmpActorReport {

	private long actorId;

	/**
	 * 服务成功数量
	 */
	private int workCount;

	private CmpActor cmpActor;

	public void setCmpActor(CmpActor cmpActor) {
		this.cmpActor = cmpActor;
	}

	public CmpActor getCmpActor() {
		return cmpActor;
	}

	public long getActorId() {
		return actorId;
	}

	public void setActorId(long actorId) {
		this.actorId = actorId;
	}

	public int getWorkCount() {
		return workCount;
	}

	public void setWorkCount(int workCount) {
		this.workCount = workCount;
	}
}