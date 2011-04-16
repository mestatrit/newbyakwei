package iwant.bean;

import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

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
