package com.hk.web.hk4.user;

import java.util.ArrayList;
import java.util.List;

import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.svr.pub.Err;

public class RegUser {

	private String mobile;

	private String email;

	private String password;

	private String repassword;

	private byte sex;

	private String code;

	private String nickName;

	private HkRequest req;

	/**
	 * 数据输入验证
	 * 
	 * @param req
	 * @param checkPassword 是否检查密码
	 */
	public RegUser(HkRequest req) {
		this.req = req;
		code = req.getString("code");
		email = req.getString("email");
		mobile = req.getString("mobile");
		password = req.getString("password");
		repassword = req.getString("repassword");
		sex = req.getByte("sex", (byte) -1);
		nickName = req.getString("nickName");
	}

	/**
	 * @param needzyCode true:需要验证码,false:不需要
	 * @return
	 *         2010-4-28
	 */
	public List<Integer> validate(boolean needzyCode) {
		List<Integer> list = new ArrayList<Integer>();
		if (needzyCode) {
			if (DataUtil.isEmpty(code)) {
				list.add(Err.REG_INPUT_VALIDATECODE);
			}
			String session_code = (String) req
					.getSessionValue(HkUtil.CLOUD_IMAGE_AUTH);
			if (session_code == null || !session_code.equals(code)) {
				list.add(Err.REG_INPUT_VALIDATECODE);
			}
		}
		if (password == null) {
			list.add(Err.PASSWORD_DATA_ERROR);
		}
		if (password != null && !password.equals(repassword)) {
			list.add(Err.REG_2_PASSWORD_NOT_SAME);
		}
		if (DataUtil.isEmpty(email)) {
			list.add(Err.EMAIL_ERROR);
		}
		if (!DataUtil.isLegalEmail(email)) {
			list.add(Err.EMAIL_ERROR);
		}
		if (!DataUtil.isEmpty(mobile) && !DataUtil.isLegalMobile(mobile)) {
			list.add(Err.MOBILE_ERROR);
		}
		if (sex != User.SEX_FEMALE && sex != User.SEX_MALE) {
			list.add(Err.SEX_ERROR);
		}
		if (!DataUtil.isLegalPassword(password)) {
			list.add(Err.PASSWORD_DATA_ERROR);
		}
		if (DataUtil.isEmpty(nickName)) {
			list.add(Err.NICKNAME_ERROR);
		}
		int c = User.validateNickName(nickName);
		if (c != Err.SUCCESS) {
			list.add(Err.NICKNAME_ERROR2);
		}
		return list;
	}

	public String getMobile() {
		return mobile;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getRepassword() {
		return repassword;
	}

	public byte getSex() {
		return sex;
	}

	public String getCode() {
		return code;
	}

	public HkRequest getReq() {
		return req;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setReq(HkRequest req) {
		this.req = req;
	}
}