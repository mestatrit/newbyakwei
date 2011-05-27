package iwant.bean;

import com.dev3g.cactus.dao.annotation.Id;
import com.dev3g.cactus.dao.annotation.Table;

@Table(name = "projectrecycle")
public class ProjectRecycle {

	@Id
	private long projectid;

	public void setProjectid(long projectid) {
		this.projectid = projectid;
	}

	public long getProjectid() {
		return projectid;
	}
}
