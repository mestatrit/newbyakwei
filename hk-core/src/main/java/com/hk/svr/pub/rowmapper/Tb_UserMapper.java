package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_User;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_UserMapper extends HkRowMapper<Tb_User> {

	@Override
	public Class<Tb_User> getMapperClass() {
		return Tb_User.class;
	}

	public Tb_User mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_User o = new Tb_User();
		o.setUserid(rs.getLong("userid"));
		o.setNick(rs.getString("nick"));
		o.setCityid(rs.getInt("cityid"));
		o.setEmail(rs.getString("email"));
		o.setPwd(rs.getString("pwd"));
		o.setReg_source(rs.getInt("reg_source"));
		o.setGender(rs.getByte("gender"));
		o.setIntro(rs.getString("intro"));
		o.setPic_path(rs.getString("pic_path"));
		o.setApi_pic_path(rs.getString("api_pic_path"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		o.setTaobao_nick(rs.getString("taobao_nick"));
		o.setFriend_count(rs.getInt("friend_count"));
		o.setFans_count(rs.getInt("fans_count"));
		o.setItem_count(rs.getInt("item_count"));
		o.setApiinfo(rs.getString("apiinfo"));
		o.setLogin_flg(rs.getByte("login_flg"));
		o.setItem_cmt_count(rs.getInt("item_cmt_count"));
		o.setItem_hold_count(rs.getInt("item_hold_count"));
		o.setItem_want_count(rs.getInt("item_want_count"));
		o.setSina_nick(rs.getString("sina_nick"));
		return o;
	}
}