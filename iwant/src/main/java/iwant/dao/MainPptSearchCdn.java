package iwant.dao;

import halo.util.DataUtil;
import iwant.bean.enumtype.ActiveType;

public class MainPptSearchCdn {

	private int catid;

	private String name;

	private int did;

	private ActiveType activeType;

	private String order;

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
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

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
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