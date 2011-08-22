package tuxiazi.bean;

import halo.dao.annotation.Column;
import halo.dao.annotation.Id;
import halo.dao.annotation.Table;
import halo.util.HaloUtil;

import java.util.Date;

import tuxiazi.dao.UserDao;
import tuxiazi.dao.dbpartitionhelper.TuxiaziDbPartitionHelper;

/**
 * 用户表
 * 
 * @author Administrator
 */
@Table(name = "user", partitionClass = TuxiaziDbPartitionHelper.class)
public class User {

	public User() {
	}

	private SinaUserFromAPI sinaUserFromAPI;

	public User(SinaUserFromAPI sinaUserFromAPI) {
		this.sinaUserFromAPI = sinaUserFromAPI;
		this.nick = sinaUserFromAPI.getNick();
		this.head_path = sinaUserFromAPI.getHead();
	}

	/**
	 * 用户id
	 */
	@Id
	private long userid;

	/**
	 * 昵称，根据登陆使用的api网站不同而变化
	 */
	@Column
	private String nick;

	/**
	 * 头像地址
	 */
	@Column
	private String head_path;

	/**
	 * 用户图片数量
	 */
	@Column
	private int pic_num;

	/**
	 * 账号创建时间
	 */
	@Column
	private Date create_time;

	@Column
	private int fans_num;

	@Column
	private int friend_num;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHead_path() {
		return head_path;
	}

	public void setHead_path(String headPath) {
		head_path = headPath;
	}

	public int getPic_num() {
		return pic_num;
	}

	public void setPic_num(int picNum) {
		pic_num = picNum;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public int getFans_num() {
		return fans_num;
	}

	public void setFans_num(int fansNum) {
		fans_num = fansNum;
	}

	public int getFriend_num() {
		return friend_num;
	}

	public void setFriend_num(int friendNum) {
		friend_num = friendNum;
	}

	private Api_user_sina api_user_sina;

	private Api_user api_user;

	public Api_user getApi_user() {
		return api_user;
	}

	public Api_user_sina getApi_user_sina() {
		return api_user_sina;
	}

	/**
	 * 保存用户信息，以及创建其他相关信息
	 */
	public void save() {
		this.create_time = new Date();
		UserDao dao = (UserDao) HaloUtil.getBean("userDao");
		dao.save(this);
		this.api_user_sina = new Api_user_sina(userid, sinaUserFromAPI);
		this.api_user_sina.save();
		api_user = new Api_user(this.userid, Api_user.API_TYPE_SINA);
		api_user.save();
	}

	public void update() {
		UserDao dao = (UserDao) HaloUtil.getBean("userDao");
		dao.update(this);
	}

	public void addPic_num(int add) {
		this.pic_num = this.pic_num + add;
		if (this.pic_num < 0) {
			this.pic_num = 0;
		}
	}

	public void updatePic_num(int count) {
		this.pic_num = count;
		this.update();
	}
}
