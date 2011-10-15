package iwant.dao;

import halo.util.DataUtil;
import iwant.bean.enumtype.ActiveType;

public class ProjectSearchCdn {

	private String name;

	private int did;

	private ActiveType activeType;

	public void setDid(int did) {
		this.did = did;
	}

	public int getDid() {
		return did;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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