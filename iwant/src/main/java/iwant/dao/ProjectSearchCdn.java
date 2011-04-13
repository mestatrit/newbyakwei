package iwant.dao;

import iwant.bean.enumtype.ActiveType;

public class ProjectSearchCdn {

	private String name;

	private int catid;

	private ActiveType activeType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public ActiveType getActiveType() {
		return activeType;
	}

	public void setActiveType(ActiveType activeType) {
		this.activeType = activeType;
	}
}