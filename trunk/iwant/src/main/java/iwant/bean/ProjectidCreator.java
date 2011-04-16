package iwant.bean;

import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

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
