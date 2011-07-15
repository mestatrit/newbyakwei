package iwant.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;

import java.util.Date;

/**
 * 用户信息
 * 
 * @author akwei
 */
@Table(name = "user")
public class User {

	/**
	 * id
	 */
	@Id
	private long userid;

	/**
	 * apple设备的唯一设备码
	 */
	@Column
	private String device_token;

	/**
	 * 用户email
	 */
	@Column
	private String email;

	/**
	 * 性别
	 */
	@Column
	private int gender;

	/**
	 * 姓名
	 */
	@Column
	private String name;

	/**
	 * 手机号码
	 */
	@Column
	private String mobile;

	/**
	 * 创建时间
	 */
	@Column
	private Date createtime;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getDevice_token() {
		return device_token;
	}

	public void setDevice_token(String deviceToken) {
		device_token = deviceToken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}