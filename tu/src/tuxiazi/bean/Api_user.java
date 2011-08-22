package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;
import halo.util.NumberUtil;
import tuxiazi.dao.Api_userDao;
import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;

/**
 * 用户所授权的第三方网站api信息
 * 
 * @author akwei
 */
@Table(name = "api_user", partitionClass = TuxiaziDbPartitionHelper.class)
public class Api_user {

	public Api_user() {
	}

	public Api_user(long userid, int api_type) {
		this.userid = userid;
		this.api_type = api_type;
	}

	public void save() {
		Api_userDao dao = (Api_userDao) HaloUtil.getBean("api_userDao");
		this.oid = NumberUtil.getLong(dao.save(this));
	}

	/**
	 * 新浪授权
	 */
	public static final int API_TYPE_SINA = 1;

	/**
	 * 系统id
	 */
	@Id
	private long oid;

	/**
	 * 用户id
	 */
	@Column
	private long userid;

	/**
	 * 授权类型,参见 {@link Api_user#API_TYPE_SINA}
	 */
	@Column
	private int api_type;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public int getApi_type() {
		return api_type;
	}

	public void setApi_type(int apiType) {
		api_type = apiType;
	}
}