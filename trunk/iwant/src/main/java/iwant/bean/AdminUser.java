package iwant.bean;

import halo.util.DesUtil;
import halo.util.P;
import iwant.bean.exception.NoAdminUserException;

/**
 * 管理员，后台内容管理
 * 
 * @author akwei
 */
public class AdminUser {

	private int adminid;

	private String name;

	private String pwd;

	/**
	 * 能登录的城市
	 */
	private int cityid;

	/**
	 * 级别:0普通管理员:1超级管理员
	 */
	private int level;

	private static final String key = "adminuserkey";

	private static final DesUtil desUtil = new DesUtil(key);

	public AdminUser() {
	}

	/**
	 *通过加密字符来创建对象,不会对密码字段赋值
	 * 
	 * @param secretKey
	 * @throws NoAdminUserException
	 */
	public AdminUser(String secretKey) throws NoAdminUserException {
		String decode_key = desUtil.decrypt(secretKey);
		if (decode_key == null || decode_key.length() == 0) {
			throw new NoAdminUserException("no admin user");
		}
		// adminid:name:level:cityid
		String[] v = decode_key.split(":");
		if (v.length != 4) {
			throw new NoAdminUserException("no admin user");
		}
		try {
			this.adminid = Integer.valueOf(v[0]);
			this.name = v[1];
			this.level = Integer.valueOf(v[2]);
			this.cityid = Integer.valueOf(v[3]);
		}
		catch (Exception e) {
			throw new NoAdminUserException("no admin user");
		}
	}

	public int getAdminid() {
		return adminid;
	}

	public void setAdminid(int adminid) {
		this.adminid = adminid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public String getSecretKey() {
		return desUtil.encrypt(this.adminid + ":" + this.name + ":"
				+ this.level + ":" + this.cityid);
	}

	/**
	 * 验证用户登录
	 * 
	 * @param inputPwd
	 * @return true:登录成功
	 */
	public boolean isLogin(String inputPwd) {
		String enc_key = desUtil.encrypt(inputPwd);
		if (this.pwd == null || this.pwd.length() == 0) {
			return false;
		}
		if (this.pwd.equals(enc_key)) {
			return true;
		}
		return false;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isSuperAdmin() {
		if (this.level == 1) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		P.println("guilinadmin");
		P.println(desUtil.encrypt("adminekgjegke"));
		P.println("liuzhouadmin");
		P.println(desUtil.encrypt("adminelgcdifj"));
		P.println("wuzhouadmin");
		P.println(desUtil.encrypt("adminefdkvefg"));
		P.println("beihaiadmin");
		P.println(desUtil.encrypt("adminejgcxeid"));
		P.println("fangchenggangadmin");
		P.println(desUtil.encrypt("admingpeitoew"));
		P.println("qinzhouadmin");
		P.println(desUtil.encrypt("admindjetiwow"));
		P.println("guigangadmin");
		P.println(desUtil.encrypt("admineurnchew"));
		P.println("yulinadmin");
		P.println(desUtil.encrypt("adminowpppppe"));
		P.println("baiseadmin");
		P.println(desUtil.encrypt("adminewqpeotm"));
		P.println("hezhouadmin");
		P.println(desUtil.encrypt("adminwiejdmeo"));
		P.println("hechiadmin");
		P.println(desUtil.encrypt("admineueyrtww"));
		P.println("laibinadmin");
		P.println(desUtil.encrypt("admindmdbcgew"));
		P.println("chongzuoadmin");
		P.println(desUtil.encrypt("admineueyfhwt"));
	}
}