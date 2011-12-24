package com.hk.bean.taobao;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hk.bean.City;
import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.frame.util.JsonObj;
import com.hk.frame.util.JsonUtil;
import com.hk.frame.util.MD5Util;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.TbImageConfig;
import com.hk.svr.pub.ZoneUtil;

/**
 * 用户
 * 
 * @author akwei
 */
@Table(name = "tb_user")
public class Tb_User {

	public static final byte GENDER_MALE = 1;

	public static final byte GENDER_FEMALE = 2;

	@Id
	private long userid;

	@Column
	private String nick;

	@Column
	private int cityid;

	@Column
	private String email;

	@Column
	private String pwd;

	@Column
	private int reg_source;

	@Column
	private byte gender;

	@Column
	private String intro;

	@Column
	private String pic_path;

	/**
	 * api网站头像
	 */
	@Column
	private String api_pic_path;

	@Column
	private Date create_time;

	@Column
	private String taobao_nick;

	@Column
	private int friend_count;

	@Column
	private int fans_count;

	/**
	 * 商品发布数量
	 */
	@Column
	private int item_count;

	/**
	 * 想买的商品数量
	 */
	@Column
	private int item_want_count;

	/**
	 * 拥有的商品数量
	 */
	@Column
	private int item_hold_count;

	/**
	 * 评价的商品数量
	 */
	@Column
	private int item_cmt_count;

	/**
	 * 登录的方式
	 */
	@Column
	private byte login_flg;

	/**
	 * api网站的其他信息(json格式存储)
	 */
	@Column
	private String apiinfo;

	@Column
	private String sina_nick;

	private JsonObj jsonObj;

	public String getLocation() {
		City city = ZoneUtil.getCity(this.cityid);
		if (city != null) {
			return city.getCity();
		}
		return null;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public byte getGender() {
		return gender;
	}

	public String getIntro() {
		return intro;
	}

	/**
	 * 没有换行的描述
	 * 
	 * @return
	 *         2010-8-31
	 */
	public String getIntroRow() {
		if (DataUtil.isEmpty(this.intro)) {
			return null;
		}
		return this.intro.replaceAll("<br />", "");
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getTaobao_nick() {
		return taobao_nick;
	}

	public void setTaobao_nick(String taobaoNick) {
		taobao_nick = taobaoNick;
	}

	/**
	 * 验证密码是否相等
	 * 
	 * @param input_pwd
	 * @return true:密码相等,false:密码错误
	 *         2010-8-29
	 */
	public boolean checkPwd(String input_pwd) {
		if (DataUtil.isEmpty(input_pwd)) {
			return false;
		}
		if (this.pwd.equals(MD5Util.md5Encode32(input_pwd))) {
			return true;
		}
		return false;
	}

	public int getReg_source() {
		return reg_source;
	}

	public void setReg_source(int regSource) {
		reg_source = regSource;
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String picPath) {
		pic_path = picPath;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public String getApi_pic_path() {
		return api_pic_path;
	}

	public void setApi_pic_path(String apiPicPath) {
		api_pic_path = apiPicPath;
	}

	public String getPic_url_80() {
		if (DataUtil.isEmpty(this.pic_path)
				&& DataUtil.isNotEmpty(this.api_pic_path)) {
			return this.api_pic_path;
		}
		if (this.login_flg != Tb_User_Api.REG_SOURCE_LOCAL) {
			return this.api_pic_path;
		}
		return TbImageConfig.getHead80PicUrl(this.pic_path, this.gender);
	}

	public String getPic_url_48() {
		if (DataUtil.isEmpty(this.pic_path)
				&& DataUtil.isNotEmpty(this.api_pic_path)) {
			return this.api_pic_path;
		}
		if (this.login_flg != Tb_User_Api.REG_SOURCE_LOCAL) {
			return this.api_pic_path;
		}
		return TbImageConfig.getHead48PicUrl(this.pic_path, this.gender);
	}

	public void setFriend_count(int friendCount) {
		friend_count = friendCount;
	}

	public int getFriend_count() {
		return friend_count;
	}

	public int getFans_count() {
		return fans_count;
	}

	public void setFans_count(int fansCount) {
		fans_count = fansCount;
	}

	public Set<Integer> validateList(String repwd, String code, String sys_code) {
		Set<Integer> errlist = new HashSet<Integer>();
		if (!HkValidate.validateEmptyAndLength(this.nick, true, 2, 20)) {
			errlist.add(Err.TB_USER_NICK_ERROR);
		}
		else {
			if (!DataUtil.checkEnAndChineseNick(nick)) {
				errlist.add(Err.TB_USER_NICK_ERROR);
			}
		}
		if (!DataUtil.isLegalEmail(this.email)) {
			errlist.add(Err.TB_USER_EMAIL_ERROR);
		}
		if (!HkValidate.validateEmptyAndLength(this.email, true, 40)) {
			errlist.add(Err.TB_USER_EMAIL_ERROR);
		}
		if (!HkValidate.validateEmptyAndLength(this.pwd, true, 4, 16)) {
			errlist.add(Err.TB_USER_PWD_ERROR);
		}
		if (DataUtil.isEmpty(repwd) || !this.pwd.equals(repwd)) {
			errlist.add(Err.TB_USER_REPWD_ERROR);
		}
		if (this.gender != GENDER_MALE && this.gender != GENDER_FEMALE) {
			errlist.add(Err.TB_USER_GENDER_ERROR);
		}
		if (this.cityid <= 0) {
			errlist.add(Err.TB_USER_CITYID_ERROR);
		}
		if (!HkValidate.validateLength(this.taobao_nick, true, 20)) {
			errlist.add(Err.TB_USER_TAOBAO_NICK_ERROR);
		}
		if (DataUtil.isEmpty(code) || DataUtil.isEmpty(sys_code)
				|| !code.equals(sys_code)) {
			errlist.add(Err.TB_USER_CODE_ERROR);
		}
		return errlist;
	}

	public int validate(String repwd) {
		if (!HkValidate.validateEmptyAndLength(this.nick, true, 2, 20)) {
			return Err.TB_USER_NICK_ERROR;
		}
		if (!DataUtil.checkEnAndChineseNick(nick)) {
			return Err.TB_USER_NICK_ERROR;
		}
		if (!DataUtil.isLegalEmail(this.email)) {
			return Err.TB_USER_EMAIL_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.email, true, 40)) {
			return Err.TB_USER_EMAIL_ERROR;
		}
		if (!HkValidate.validateEmptyAndLength(this.pwd, true, 4, 16)) {
			return Err.TB_USER_PWD_ERROR;
		}
		if (DataUtil.isEmpty(repwd) || !this.pwd.equals(repwd)) {
			return Err.TB_USER_REPWD_ERROR;
		}
		if (this.gender != GENDER_MALE && this.gender != GENDER_FEMALE) {
			return Err.TB_USER_GENDER_ERROR;
		}
		if (this.cityid <= 0) {
			return Err.TB_USER_CITYID_ERROR;
		}
		if (!HkValidate.validateLength(this.taobao_nick, true, 20)) {
			return Err.TB_USER_TAOBAO_NICK_ERROR;
		}
		return Err.SUCCESS;
	}

	public void setItem_count(int itemCount) {
		item_count = itemCount;
	}

	public int getItem_count() {
		return item_count;
	}

	public String getApiinfo() {
		return apiinfo;
	}

	public void setApiinfo(String apiinfo) {
		this.apiinfo = apiinfo;
	}

	/**
	 * 是否是新浪微博认证用户
	 */
	public boolean isSinaVerified() {
		Map<String, String> map = initMap();
		if (this.jsonObj == null) {
			this.jsonObj = new JsonObj(map);
		}
		return this.jsonObj.getBoolean(JsonKey.USER_SINA_VERIFIED);
	}

	public void setSinaVerified(boolean verified) {
		Map<String, String> map = initMap();
		map.put(JsonKey.USER_SINA_VERIFIED, String.valueOf(verified));
		this.apiinfo = JsonUtil.toJson(map);
	}

	private Map<String, String> initMap() {
		Map<String, String> map = null;
		if (DataUtil.isNotEmpty(this.apiinfo)) {
			map = JsonUtil.getMapFromJson(this.apiinfo);
		}
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return map;
	}

	public void setSinaFans_count(int fans_count) {
		Map<String, String> map = initMap();
		map.put(JsonKey.USER_SINA_FANS_COUNT, String.valueOf(fans_count));
		this.apiinfo = JsonUtil.toJson(map);
	}

	public int getSinaFans_count() {
		Map<String, String> map = initMap();
		if (this.jsonObj == null) {
			this.jsonObj = new JsonObj(map);
		}
		return this.jsonObj.getInt(JsonKey.USER_SINA_FANS_COUNT);
	}

	public void setSinaFriend_count(int friend_count) {
		Map<String, String> map = initMap();
		map.put(JsonKey.USER_SINA_FRIEND_COUNT, String.valueOf(friend_count));
		this.apiinfo = JsonUtil.toJson(map);
	}

	public int getSinaFriend_count() {
		Map<String, String> map = initMap();
		if (this.jsonObj == null) {
			this.jsonObj = new JsonObj(map);
		}
		return this.jsonObj.getInt(JsonKey.USER_SINA_FRIEND_COUNT);
	}

	public void setSinaWeibo_count(int weibo_count) {
		Map<String, String> map = initMap();
		map.put(JsonKey.USER_SINA_WEIBO_COUNT, String.valueOf(weibo_count));
		this.apiinfo = JsonUtil.toJson(map);
	}

	public int getSinaWeibo_count() {
		Map<String, String> map = initMap();
		if (this.jsonObj == null) {
			this.jsonObj = new JsonObj(map);
		}
		return this.jsonObj.getInt(JsonKey.USER_SINA_WEIBO_COUNT);
	}

	public void setSinaLocation(String location) {
		Map<String, String> map = initMap();
		map.put(JsonKey.USER_SINA_LOCATION, location);
		this.apiinfo = JsonUtil.toJson(map);
	}

	public String getSinaLocation() {
		Map<String, String> map = initMap();
		if (this.jsonObj == null) {
			this.jsonObj = new JsonObj(map);
		}
		return this.jsonObj.getString(JsonKey.USER_SINA_LOCATION);
	}

	public byte getLogin_flg() {
		return login_flg;
	}

	public void setLogin_flg(byte loginFlg) {
		login_flg = loginFlg;
	}

	public int getItem_want_count() {
		return item_want_count;
	}

	public void setItem_want_count(int itemWantCount) {
		item_want_count = itemWantCount;
	}

	public int getItem_hold_count() {
		return item_hold_count;
	}

	public void setItem_hold_count(int itemHoldCount) {
		item_hold_count = itemHoldCount;
	}

	public int getItem_cmt_count() {
		return item_cmt_count;
	}

	public void setItem_cmt_count(int itemCmtCount) {
		item_cmt_count = itemCmtCount;
	}

	public boolean isLoginFromSina() {
		if (this.login_flg == Tb_User_Api.REG_SOURCE_SINA) {
			return true;
		}
		return false;
	}

	public String getShow_nick() {
		if (DataUtil.isNotEmpty(this.sina_nick)) {
			return this.sina_nick;
		}
		return this.nick;
	}

	public String getSina_nick() {
		return sina_nick;
	}

	public void setSina_nick(String sinaNick) {
		sina_nick = sinaNick;
	}
}