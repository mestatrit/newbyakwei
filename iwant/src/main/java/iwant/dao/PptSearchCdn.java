package iwant.dao;

import com.hk.frame.util.DataUtil;

public class PptSearchCdn {

	private int projectid;

	private String title;

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public int getProjectid() {
		return projectid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String encTitle;

	public String getEncTitle() {
		if (encTitle == null) {
			this.encTitle = DataUtil.urlEncoder(this.title);
			if (this.encTitle == null) {
				this.encTitle = "";
			}
		}
		return encTitle;
	}
}