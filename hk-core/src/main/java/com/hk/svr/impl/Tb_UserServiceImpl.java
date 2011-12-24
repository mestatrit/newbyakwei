package com.hk.svr.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.taobao.Tb_Sina_User;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_User_Api;
import com.hk.bean.taobao.Tb_Userid;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.MD5Util;
import com.hk.svr.Tb_UserService;
import com.hk.svr.pub.Err;

/**
 * 用户逻辑
 * 
 * @author akwei
 */
public class Tb_UserServiceImpl implements Tb_UserService {

	@Autowired
	private QueryManager manager;

	public int createTb_User(Tb_User tbUser) {
		if (tbUser.getCreate_time() == null) {
			tbUser.setCreate_time(new Date());
		}
		Query query = this.manager.createQuery();
		if (!DataUtil.isEmpty(tbUser.getNick())
				&& this.getTb_UserByNick(tbUser.getNick()) != null) {
			return Err.TB_USER_NICK_DUPLICATE;
		}
		if (!DataUtil.isEmpty(tbUser.getEmail())
				&& this.getTb_UserByEmail(tbUser.getEmail()) != null) {
			return Err.TB_USER_EMAIL_DUPLICATE;
		}
		if (!DataUtil.isEmpty(tbUser.getPwd())) {
			tbUser.setPwd(MD5Util.md5Encode32(tbUser.getPwd()));
		}
		Tb_Userid tbUserid = new Tb_Userid();
		tbUserid.setCreate_time(new Date());
		long userid = query.insertObject(tbUserid).longValue();
		tbUserid.setUserid(userid);
		tbUser.setUserid(userid);
		query.insertObject(tbUser);
		return Err.SUCCESS;
	}

	public int createTb_User(Tb_User tbUser, Tb_User_Api tbUserApi) {
		int code = this.createTb_User(tbUser);
		if (code == Err.SUCCESS) {
			tbUserApi.setUserid(tbUser.getUserid());
			this.saveTb_User_Api(tbUserApi);
		}
		return code;
	}

	public void deleteTb_User(long userid) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_User.class, userid);
	}

	public Tb_User getTb_User(long userid) {
		return this.manager.createQuery().getObjectById(Tb_User.class, userid);
	}

	public Tb_User getTb_UserByNick(String nick) {
		if (DataUtil.isEmpty(nick)) {
			return null;
		}
		Query query = this.manager.createQuery();
		return query
				.getObjectEx(Tb_User.class, "nick=?", new Object[] { nick });
	}

	public Tb_User getTb_UserByEmail(String email) {
		if (DataUtil.isEmpty(email)) {
			return null;
		}
		Query query = this.manager.createQuery();
		return query.getObjectEx(Tb_User.class, "email=?",
				new Object[] { email });
	}

	public int updateTb_User(Tb_User tbUser) {
		Query query = this.manager.createQuery();
		Tb_User db_data = this.getTb_User(tbUser.getUserid());
		if (DataUtil.isNotEmpty(tbUser.getNick())) {
			if (!db_data.getNick().equalsIgnoreCase(tbUser.getNick())) {
				Tb_User o = this.getTb_UserByNick(tbUser.getNick());
				if (o != null) {
					return Err.TB_USER_NICK_DUPLICATE;
				}
			}
		}
		if (DataUtil.isNotEmpty(tbUser.getEmail())) {
			if (!db_data.getEmail().equalsIgnoreCase(tbUser.getEmail())) {
				Tb_User o = this.getTb_UserByEmail(tbUser.getEmail());
				if (o != null) {
					return Err.TB_USER_EMAIL_DUPLICATE;
				}
			}
		}
		query.updateObject(tbUser);
		return Err.SUCCESS;
	}

	public void updatePwd(long userid, String pwd) {
		Query query = this.manager.createQuery();
		query.addField("pwd", MD5Util.md5Encode32(pwd));
		query.updateById(Tb_User.class, userid);
	}

	public void createTb_User_Api(Tb_User_Api tbUserApi) {
		Query query = this.manager.createQuery();
		query.insertObject(tbUserApi);
		if (tbUserApi.getReg_source() == Tb_User_Api.REG_SOURCE_SINA) {
			try {
				Tb_Sina_User tbSinaUser = new Tb_Sina_User();
				tbSinaUser.setUserid(tbUserApi.getUserid());
				tbSinaUser.setUid(Long.valueOf(tbUserApi.getUid()));
				if (query
						.getObjectById(Tb_Sina_User.class, tbSinaUser.getUid()) == null) {
					query.insertObject(tbSinaUser);
				}
			}
			catch (NumberFormatException e) {// 非数字id转换错误，忽略
			}
		}
	}

	public void deleteTb_User_Api(Tb_User_Api tbUserApi) {
		Query query = this.manager.createQuery();
		query.deleteById(Tb_User_Api.class, tbUserApi.getOid());
		if (tbUserApi.getReg_source() == Tb_User_Api.REG_SOURCE_SINA) {
			try {
				query.deleteById(Tb_Sina_User.class, Long.valueOf(tbUserApi
						.getUid()));
			}
			catch (NumberFormatException e) {// 非数字id转换错误，忽略
			}
		}
	}

	public void updateTb_User_Api(Tb_User_Api tbUserApi) {
		Query query = this.manager.createQuery();
		query.updateObject(tbUserApi);
	}

	public void saveTb_User_Api(Tb_User_Api tbUserApi) {
		this.deleteTb_User_Api(tbUserApi);
		if (tbUserApi.getOid() > 0) {
			this.updateTb_User_Api(tbUserApi);
		}
		else {
			this.createTb_User_Api(tbUserApi);
		}
	}

	public Tb_User_Api getTb_User_Api(long userid, byte regSource) {
		Query query = this.manager.createQuery();
		return query
				.getObjectEx(Tb_User_Api.class, "userid=? and reg_source=?",
						new Object[] { userid, regSource });
	}

	public List<Tb_User_Api> getTb_User_ApiByUserid(long userid) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_User_Api.class, "userid=?",
				new Object[] { userid }, "oid desc");
	}

	public List<Tb_Sina_User> getTb_Sina_UserListInId(List<Long> idList,
			boolean buildUser) {
		Query query = this.manager.createQuery();
		return query.listInField(Tb_Sina_User.class, null, null, "uid", idList,
				null);
	}

	public Map<Long, Tb_User> getTb_UserMapInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		List<Tb_User> list = query.listInField(Tb_User.class, null, null,
				"userid", idList, null);
		Map<Long, Tb_User> map = new HashMap<Long, Tb_User>();
		for (Tb_User o : list) {
			map.put(o.getUserid(), o);
		}
		return map;
	}

	public Tb_User_Api getTb_User_ApiByUidAndReg_source(String uid,
			byte regSource) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(Tb_User_Api.class, "uid=? and reg_source=?",
				new Object[] { uid, regSource });
	}

	public Tb_Sina_User getTb_Sina_User(long uid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Tb_Sina_User.class, uid);
	}

	public List<Tb_User> getTb_UserListForNew(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_User.class, "userid desc", begin, size);
	}

	@Override
	public void addItem_count(long userid, int add) {
		Query query = this.manager.createQuery();
		query.addField("item_count", "add", add);
		query.updateById(Tb_User.class, userid);
	}

	@Override
	public List<Tb_User> getTb_UserListForSuperMan(int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(Tb_User.class, "item_count desc", begin, size);
	}

	@Override
	public void updateLoginInfoUserid(long userid, String apiPicPath,
			String apiinfo, byte login_flg) {
		this.manager.createQuery().addField("api_pic_path", apiPicPath)
				.addField("apiinfo", apiinfo).addField("login_flg", login_flg)
				.updateById(Tb_User.class, userid);
	}

	@Override
	public void updateItem_cmt_countByUserid(long userid, int count) {
		this.manager.createQuery().addField("item_cmt_count", count)
				.updateById(Tb_User.class, userid);
	}

	@Override
	public void updateItem_hold_countByUserid(long userid, int count) {
		this.manager.createQuery().addField("item_hold_count", count)
				.updateById(Tb_User.class, userid);
	}

	@Override
	public void updateItem_want_countByUserid(long userid, int count) {
		this.manager.createQuery().addField("item_want_count", count)
				.updateById(Tb_User.class, userid);
	}
}