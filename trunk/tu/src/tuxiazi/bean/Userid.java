package tuxiazi.bean;

import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

/**
 * id
 * 
 * @author akwei
 */
@Table(name = "userid", partitionClass = TuxiaziDbPartitionHelper.class)
public class Userid {

	@Id
	private long userid;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
}