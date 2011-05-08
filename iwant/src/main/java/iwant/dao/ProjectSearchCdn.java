package iwant.dao;

import iwant.bean.enumtype.ActiveType;
import cactus.util.DataUtil;

public class ProjectSearchCdn {

	private String name;

	private int catid;

	private int cityid;

	private ActiveType activeType;

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getCityid() {
		return cityid;
	}

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

	private String encName;

	public String getEncName() {
		if (encName == null) {
			this.encName = DataUtil.urlEncoder(this.name);
			if (this.encName == null) {
				this.encName = "";
			}
		}
		return encName;
	}
}