package tuxiazi.bean;

import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

/**
 * 图片评论id生成器,id自动增加，不删除此表数据
 * 
 * @author akwei
 */
@Table(name = "photocmtid", partitionClass = TuxiaziDbPartitionHelper.class)
public class PhotoCmtid {

	@Id
	private long cmtId;

	public long getCmtId() {
		return cmtId;
	}

	public void setCmtId(long cmtId) {
		this.cmtId = cmtId;
	}
}