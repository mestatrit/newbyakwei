package tuxiazi.bean;

/**
 * 新浪用户
 * 
 * @author Administrator
 */
public class SinaUser {

	/**
	 * 系统用户id,如果用户不存在，userid=0
	 */
	private long userid;

	/**
	 * 新浪用户id
	 */
	private long sinaUserid;

	/**
	 * 新浪昵称
	 */
	private String nick;

	/**
	 * 已有图片数量
	 */
	private int pic_num;

	/**
	 * 头像地址
	 */
	private String head;

	private boolean invited;

	public boolean isInvited() {
		return invited;
	}

	public void setInvited(boolean invited) {
		this.invited = invited;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getSinaUserid() {
		return sinaUserid;
	}

	public void setSinaUserid(long sinaUserid) {
		this.sinaUserid = sinaUserid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getPic_num() {
		return pic_num;
	}

	public void setPic_num(int picNum) {
		pic_num = picNum;
	}
}
