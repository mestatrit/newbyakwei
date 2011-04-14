package iwant.dao;

import com.hk.frame.util.DataUtil;

public class PptSearchCdn {

	private int projectid;

	private String name;

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public int getProjectid() {
		return projectid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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