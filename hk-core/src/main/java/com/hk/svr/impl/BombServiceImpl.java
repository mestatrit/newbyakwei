package com.hk.svr.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.hk.bean.BombLaba;
import com.hk.bean.Bomber;
import com.hk.bean.LabaDel;
import com.hk.bean.LabaDelInfo;
import com.hk.bean.PinkLaba;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.BombService;
import com.hk.svr.bomb.exception.NotEnoughBombException;
import com.hk.svr.bomb.exception.NotEnoughPinkException;

public class BombServiceImpl implements BombService {
	@Autowired
	private QueryManager manager;

	public BombLaba createBombLaba(long userId, long labaId,
			LabaDelInfo labaDelInfo) {
		Query query = manager.createQuery();
		query.setTable(BombLaba.class);
		query.where("labaid=?").setParam(labaId);
		if (query.count() > 0) {
			return null;
		}
		BombLaba o = new BombLaba();
		o.setLabaId(labaId);
		o.setUserId(userId);
		o.setCreateTime(new Date());
		query.addField("userid", o.getUserId());
		query.addField("labaid", o.getLabaId());
		query.addField("createtime", o.getCreateTime());
		query.addField("optime", labaDelInfo.getOptime());
		long id = query.insert(BombLaba.class).longValue();
		o.setSysId(id);
		return o;
	}

	private void processAdminBomb(long adminUserId, int count)
			throws NotEnoughBombException {
		Bomber bomber = this.getBomber(adminUserId);
		if (bomber.getUserLevel() == Bomber.USERLEVEL_ADMIN) {
			if (bomber.getRemainCount() < count) {
				throw new NotEnoughBombException("bomb not enough");
			}
			this.addRemainBombCount(adminUserId, -count);
		}
	}

	private void processAdminPink(long adminUserId, int count)
			throws NotEnoughPinkException {
		Bomber bomber = this.getBomber(adminUserId);
		if (bomber.getUserLevel() == Bomber.USERLEVEL_ADMIN) {
			if (bomber.getRemainPinkCount() < count) {
				throw new NotEnoughPinkException("pinkcount not enough");
			}
			this.addRemainBombCount(adminUserId, -count);
		}
	}

	public void createBomber(long adminUserId, Bomber bomber)
			throws NotEnoughBombException, NotEnoughPinkException {
		this.processAdminBomb(adminUserId, bomber.getBombCount());
		this.processAdminPink(adminUserId, bomber.getPinkCount());
		Query query = manager.createQuery();
		query.addField("userid", bomber.getUserId());
		query.addField("bombcount", bomber.getBombCount());
		query.addField("remaincount", bomber.getBombCount());
		query.addField("createtime", new Date());
		query.addField("userlevel", bomber.getUserLevel());
		query.addField("pinkcount", bomber.getPinkCount());
		query.addField("remainpinkcount", bomber.getRemainPinkCount());
		query.insert(Bomber.class);
	}

	public void createBomber(long adminUserId, long userId, int bombCount,
			byte userLevel, int pinkCount) throws NotEnoughBombException {
		this.processAdminBomb(adminUserId, bombCount);
		Query query = manager.createQuery();
		query.addField("userid", userId);
		query.addField("bombcount", bombCount);
		query.addField("remaincount", bombCount);
		query.addField("createtime", new Date());
		query.addField("userlevel", userLevel);
		query.addField("pinkcount", pinkCount);
		query.addField("remainpinkcount", pinkCount);
		query.insert(Bomber.class);
	}

	public void addRemainBombCount(long userId, int add) {
		Query query = manager.createQuery();
		query.setTable(Bomber.class);
		query.addField("remaincount", "add", add);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public void addRemainPinkCount(long userId, int add) {
		Query query = manager.createQuery();
		query.setTable(Bomber.class);
		query.addField("remainpinkcount", "add", add);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public void deleteBomber(long userId) {
		Query query = manager.createQuery();
		query.deleteById(Bomber.class, userId);
	}

	public List<Bomber> getBomberList(int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(Bomber.class);
		query.where("userlevel!=?").setParam(Bomber.USERLEVEL_SUPERADMIN);
		query.orderByDesc("createtime");
		return query.list(begin, size, Bomber.class);
	}

	public void updateBomber(Bomber bomber) {
		Query query = manager.createQuery();
		query.setTable(Bomber.class);
		query.addField("bombcount", bomber.getBombCount());
		query.addField("remaincount", bomber.getRemainCount());
		query.addField("createtime", bomber.getCreateTime());
		query.where("userid=?").setParam(bomber.getUserId());
		query.update();
	}

	public Bomber getBomber(long userId) {
		Query query = manager.createQuery();
		return query.getObjectById(Bomber.class, userId);
	}

	// public boolean login(long userId, String pwd) {
	// Bomber o = this.getBomber(userId);
	// if (o == null) {
	// return false;
	// }
	// if (o.getPwdHash() == MD5Util.md5Encode32(pwd).hashCode()) {
	// return true;
	// }
	// return false;
	// }
	public void addBomb(long adminUserId, long userId, int add)
			throws NotEnoughBombException {
		this.processAdminBomb(adminUserId, add);
		Query query = manager.createQuery();
		query.setTable(Bomber.class);
		query.addField("bombcount", "add", add);
		query.addField("remaincount", "add", add);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	public void addPink(long adminUserId, long userId, int add)
			throws NotEnoughPinkException {
		this.processAdminPink(adminUserId, add);
		Query query = manager.createQuery();
		query.setTable(Bomber.class);
		query.addField("pinkcount", "add", add);
		query.addField("remainpinkcount", "add", add);
		query.where("userid=?").setParam(userId);
		query.update();
	}

	// public LabaDelInfo bombLaba(long userId, long labaId) {
	// LabaDelInfo labaDelInfo = this.labaService.removeLaba(userId, labaId);
	// if (labaDelInfo == null) {
	// return null;
	// }
	// Query query = manager.createQuery();
	// query.setTable(BombLaba.class);
	// query.where("labaid=?").setParam(labaId);
	// if (query.count() > 0) {
	// return null;
	// }
	// BombLaba o = new BombLaba();
	// o.setLabaId(labaId);
	// o.setUserId(userId);
	// o.setCreateTime(new Date());
	// query.setTable(BombLaba.class);
	// query.addField("userid", o.getUserId());
	// query.addField("labaid", o.getLabaId());
	// query.addField("createtime", o.getCreateTime());
	// query.addField("optime", labaDelInfo.getOptime());
	// query.insert();
	// Bomber bomber = this.getBomber(userId);
	// if (bomber.getUserLevel() != Bomber.USERLEVEL_SUPERADMIN) {
	// this.addRemainBombCount(userId, -1);
	// }
	// return labaDelInfo;
	// }
	ParameterizedRowMapper<BombLaba> mapper = new ParameterizedRowMapper<BombLaba>() {
		public BombLaba mapRow(ResultSet rs, int rowNum) throws SQLException {
			BombLaba o = new BombLaba();
			o.setSysId(rs.getLong("b.sysid"));
			o.setUserId(rs.getLong("b.userid"));
			o.setLabaId(rs.getLong("b.labaid"));
			o.setCreateTime(rs.getTimestamp("b.createtime"));
			o.setOptime(rs.getLong("b.optime"));
			LabaDel o2 = new LabaDel();
			o2.setLabaId(rs.getLong("d.labaid"));
			o2.setContent(rs.getString("d.content"));
			o2.setUserId(rs.getLong("d.userid"));
			o2.setCreateTime(rs.getTimestamp("d.createtime"));
			o2.setReplyCount(rs.getInt("d.replycount"));
			o2.setSendFrom(rs.getByte("d.sendfrom"));
			o2.setIp(rs.getString("d.ip"));
			o2.setCityId(rs.getInt("d.cityid"));
			o2.setRangeId(rs.getInt("d.rangeid"));
			o2.setOpuserId(rs.getLong("d.opuserid"));
			o2.setOptime(rs.getLong("d.optime"));
			o.setLaba(o2);
			return o;
		}
	};

	public List<BombLaba> getBombLabaList(long userId, int begin, int size) {
		Query query = manager.createQuery();
		String sql = "select * from bomblaba b,labadel d where b.labaid=d.labaid and b.userid=? order by b.sysid desc";
		return query.listBySqlWithMapper("ds1", sql, begin, size, mapper, userId);
	}

	public List<BombLaba> getBombLabaList(int begin, int size) {
		Query query = manager.createQuery();
		String sql = "select * from bomblaba b,labadel d where b.labaid=d.labaid order by b.sysid desc";
		return query.listBySqlWithMapper("ds1", sql, begin, size, mapper);
	}

	public BombLaba getBombLaba(long sysId) {
		Query query = manager.createQuery();
		return query.getObjectById(BombLaba.class, sysId);
	}

	// public boolean updatePwd(long userId, String oldPwd, String newPwd) {
	// Bomber bomber = this.getBomber(userId);
	// if (bomber.getPwdHash() == MD5Util.md5Encode32(oldPwd).hashCode()) {
	// Query query = manager.createQuery();
	// query.setTable(Bomber.class);
	// query.addField("pwdhash", MD5Util.md5Encode32(newPwd).hashCode());
	// query.where("userid=?").setParam(userId);
	// query.update();
	// return true;
	// }
	// return false;
	// }
	public boolean pinkLaba(long userId, long labaId) {
		Query query = manager.createQuery();
		query.setTable(PinkLaba.class);
		if (query.where("labaid=?").setParam(labaId).count() == 0) {
			query.addField("labaid", labaId);
			query.addField("pinkuserid", userId);
			query.addField("createtime", new Date());
			query.insert(PinkLaba.class);
			return true;
		}
		return false;
	}

	public List<PinkLaba> getPinkLabaList(int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(PinkLaba.class);
		query.orderByDesc("createtime");
		return query.list(begin, size, PinkLaba.class);
	}

	public List<PinkLaba> getPinkLabaList(long userId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(PinkLaba.class);
		query.where("pinkuserid=?").setParam(userId);
		query.orderByDesc("createtime");
		return query.list(begin, size, PinkLaba.class);
	}

	public PinkLaba getPinkLaba(long labaId) {
		Query query = manager.createQuery();
		return query.getObjectById(PinkLaba.class, labaId);
	}

	public void deletePinkLaba(long labaId) {
		Query query = manager.createQuery();
		query.deleteById(PinkLaba.class, labaId);
	}

	public boolean hasBombLaba(long labaId) {
		Query query = manager.createQuery();
		query.setTable(BombLaba.class);
		query.where("labaid=?").setParam(labaId);
		int count = query.count();
		if (count > 0) {
			return true;
		}
		return false;
	}
}