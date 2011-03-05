package tuxiazi.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 用户表
 * 
 * @author Administrator
 */
@Table(name = "user")
public class User {

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
}
