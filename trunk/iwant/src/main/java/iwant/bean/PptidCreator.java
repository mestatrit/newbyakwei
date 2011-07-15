package iwant.bean;

import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

@Table(name = "pptidcreator")
public class PptidCreator {

	@Id
	private long pptid;

	public long getPptid() {
		return pptid;
	}

	public void setPptid(long pptid) {
		this.pptid = pptid;
	}
}
