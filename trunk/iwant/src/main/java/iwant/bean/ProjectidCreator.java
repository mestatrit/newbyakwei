package iwant.bean;

import cactus.dao.annotation.Id;
import cactus.dao.annotation.Table;

@Table(name = "projectidcreator")
public class ProjectidCreator {

	@Id
	private long projectid;

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public long getProjectid() {
		return projectid;
	}
}
