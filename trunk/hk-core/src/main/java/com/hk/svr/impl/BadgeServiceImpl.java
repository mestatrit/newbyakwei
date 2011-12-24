package com.hk.svr.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Badge;
import com.hk.bean.UserBadge;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.BadgeService;
import com.hk.svr.pub.ImageConfig;

public class BadgeServiceImpl implements BadgeService {
	@Autowired
	private QueryManager manager;

	public void createBadge(Badge badge, File file) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException {
		Query query = this.manager.createQuery();
		badge.setBadgeId(query.insertObject(badge).longValue());
		if (file != null) {
			this.updateBadge(badge, file);
		}
	}

	public void updateBadge(Badge badge, File file) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException {
		Query query = this.manager.createQuery();
		if (file != null) {
			JMagickUtil util = new JMagickUtil(file, 1);
			util.setFullQuality(true);
			String dbPath = ImageConfig.getbadgeDbPath(badge.getBadgeId());
			String headPath = ImageConfig.getBadgeUploadPath(dbPath);
			util.makeImage(headPath, ImageConfig.IMG_H57,
					JMagickUtil.IMG_SQUARE, 57);
			util.makeImage(headPath, ImageConfig.IMG_H300,
					JMagickUtil.IMG_SQUARE, 300);
			badge.setPath(dbPath);
		}
		query.updateObject(badge);
	}

	public void deleteBadge(long badgeId) {
		Query query = this.manager.createQuery();
		query.deleteById(Badge.class, badgeId);
	}

	public List<Badge> getBadgeListByChildKindId(long childKindId) {
		Query query = this.manager.createQuery();
		return query.listEx(Badge.class, "childkindid=? and stopflg=?",
				new Object[] { childKindId, Badge.STOPFLG_N });
	}

	public List<Badge> getBadgeListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(Badge.class, "companyid=? and stopflg=?",
				new Object[] { companyId, Badge.STOPFLG_N });
	}

	public List<Badge> getBadgeListByCompanyInGroup(long companyId) {
		Query query = this.manager.createQuery();
		String sql = "select b.* from badge b,cmpadmingroupref r where r.companyid=? and b.groupid=r.groupid";
		return query.listBySqlEx("ds1", sql, Badge.class, companyId);
	}

	public List<Badge> getBadgeListByKindId(long kindId) {
		Query query = this.manager.createQuery();
		return query.listEx(Badge.class, "kindid=? and stopflg=?",
				new Object[] { kindId, Badge.STOPFLG_N });
	}

	public List<Badge> getBadgeListForNoLimitNotInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		if (idList.size() == 0) {
			return query.listEx(Badge.class, "limitflg=? and stopflg=?",
					new Object[] { Badge.LIMITFLG_N, Badge.STOPFLG_N });
		}
		StringBuilder sql = new StringBuilder(
				"select * from badge where limitflg=? and stopflg=? and badgeid not in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		return query.listBySqlEx("ds1", sql.toString(), Badge.class,
				Badge.LIMITFLG_N, Badge.STOPFLG_N);
	}

	public List<Badge> getBadgeListForSysLimitNotInId(List<Long> idList) {
		Query query = this.manager.createQuery();
		if (idList.size() == 0) {
			return query.listEx(Badge.class, "limitflg=? and stopflg=?",
					new Object[] { Badge.LIMITFLG_SYS, Badge.STOPFLG_N });
		}
		StringBuilder sql = new StringBuilder(
				"select * from badge where limitflg=? and stopflg=? and badgeid not in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		return query.listBySqlEx("ds1", sql.toString(), Badge.class,
				Badge.LIMITFLG_SYS, Badge.STOPFLG_N);
	}

	public void updateStopflg(long badgeId, byte stopflg) {
		Query query = this.manager.createQuery();
		Badge badge = this.getBadge(badgeId);
		badge.setStopflg(stopflg);
		query.updateObject(badge);
	}

	public Badge getBadge(long badgeId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(Badge.class, badgeId);
	}

	public List<Badge> getBadgeList(String name, int begin, int size) {
		Query query = this.manager.createQuery();
		if (DataUtil.isEmpty(name)) {
			return query.listEx(Badge.class, "badgeid desc", begin, size);
		}
		return query.listEx(Badge.class, "ruledata like ?", new Object[] { "%"
				+ name + "%" }, "badgeid desc", begin, size);
	}

	public void createUserBadge(UserBadge userBadge) {
		Query query = this.manager.createQuery();
		if (query.count(UserBadge.class, "userid=? and badgeid=?",
				new Object[] { userBadge.getUserId(), userBadge.getBadgeId() }) == 0) {
			if (userBadge.getCreateTime() == null) {
				userBadge.setCreateTime(new Date());
			}
			long id = query.insertObject(userBadge).longValue();
			userBadge.setOid(id);
		}
	}

	public void deleteUserBadge(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(UserBadge.class, oid);
	}

	public List<UserBadge> getUserBadgeListByUserId(long userId, int begin,
			int size) {
		if (size > 0) {
			Query query = this.manager.createQuery();
			return query.listEx(UserBadge.class, "userid=?",
					new Object[] { userId }, "oid desc", begin, size);
		}
		Query query = this.manager.createQuery();
		return query.listEx(UserBadge.class, "userid=?",
				new Object[] { userId }, "oid desc");
	}

	public List<UserBadge> getUserBadgeListByUserIdAndNoLimit(long userId) {
		Query query = this.manager.createQuery();
		return query.listEx(UserBadge.class, "userid=? and limitflg=?",
				new Object[] { userId, Badge.LIMITFLG_N });
	}

	public List<UserBadge> getUserBadgeListByUserIdAndSysLimit(long userId) {
		Query query = this.manager.createQuery();
		return query.listEx(UserBadge.class, "userid=? and limitflg=?",
				new Object[] { userId, Badge.LIMITFLG_SYS });
	}

	public UserBadge getUserBadge(long userId, long badgeId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(UserBadge.class, "userid=? and badgeid=?",
				new Object[] { userId, badgeId });
	}

	public List<Badge> getBadgeListInId(List<Long> idList) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UserBadge> getUserBadgeListByUserIdForLimit(long userId) {
		Query query = this.manager.createQuery();
		return query.listEx(UserBadge.class, "userid=? and limitflg=?",
				new Object[] { userId, Badge.LIMITFLG_Y });
	}

	public UserBadge getUserBadge(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(UserBadge.class, oid);
	}

	public int countUserBadgeByUerId(long userId) {
		Query query = this.manager.createQuery();
		return query
				.count(UserBadge.class, "userid=?", new Object[] { userId });
	}

	public List<Badge> getBadgeListByLimitflg(byte limitflg) {
		Query query = this.manager.createQuery();
		return query.listEx(Badge.class, "limitflg=?",
				new Object[] { limitflg });
	}
}