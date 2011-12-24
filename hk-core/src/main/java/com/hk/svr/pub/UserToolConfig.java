package com.hk.svr.pub;

public class UserToolConfig {
	private int initGroundCount;// 初始化地皮数量

	private int createCompanyAddGroundCount;// 创建地皮时消耗数量

	private int companyStatusNormalAddGroundCount;// 一般状态时地皮奖赏数量

	private int companyStatusGoodAddGroundCount;// 好状态时地皮奖赏数量

	private int companyStatusVeryGoodAddGroundCount;// 非常好状态时地皮奖赏数量

	private int companyStatusFreezeGroundCount;// 冻结企业，将扣除地皮数量

	public void setCompanyStatusFreezeGroundCount(
			int companyStatusFreezeGroundCount) {
		this.companyStatusFreezeGroundCount = companyStatusFreezeGroundCount;
	}

	public int getCompanyStatusFreezeGroundCount() {
		return companyStatusFreezeGroundCount;
	}

	public int getInitGroundCount() {
		return initGroundCount;
	}

	public void setInitGroundCount(int initGroundCount) {
		this.initGroundCount = initGroundCount;
	}

	public int getCreateCompanyAddGroundCount() {
		return createCompanyAddGroundCount;
	}

	public void setCreateCompanyAddGroundCount(int createCompanyAddGroundCount) {
		this.createCompanyAddGroundCount = createCompanyAddGroundCount;
	}

	public int getCompanyStatusNormalAddGroundCount() {
		return companyStatusNormalAddGroundCount;
	}

	public void setCompanyStatusNormalAddGroundCount(
			int companyStatusNormalAddGroundCount) {
		this.companyStatusNormalAddGroundCount = companyStatusNormalAddGroundCount;
	}

	public int getCompanyStatusGoodAddGroundCount() {
		return companyStatusGoodAddGroundCount;
	}

	public void setCompanyStatusGoodAddGroundCount(
			int companyStatusGoodAddGroundCount) {
		this.companyStatusGoodAddGroundCount = companyStatusGoodAddGroundCount;
	}

	public int getCompanyStatusVeryGoodAddGroundCount() {
		return companyStatusVeryGoodAddGroundCount;
	}

	public void setCompanyStatusVeryGoodAddGroundCount(
			int companyStatusVeryGoodAddGroundCount) {
		this.companyStatusVeryGoodAddGroundCount = companyStatusVeryGoodAddGroundCount;
	}
}