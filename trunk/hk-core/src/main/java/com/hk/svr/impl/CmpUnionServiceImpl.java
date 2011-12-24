package com.hk.svr.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.City;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionBoard;
import com.hk.bean.CmpUnionCmdKind;
import com.hk.bean.CmpUnionKind;
import com.hk.bean.CmpUnionLink;
import com.hk.bean.CmpUnionOpUser;
import com.hk.bean.Pcity;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.svr.CmpUnionService;
import com.hk.svr.ZoneService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;
import com.hk.svr.pub.ZoneUtil;

public class CmpUnionServiceImpl implements CmpUnionService {

	@Autowired
	private QueryManager manager;

	public int createCmpUnion(CmpUnion cmpUnion) {
		CmpUnion o = this.getCmpUnionByDomain(cmpUnion.getDomain());
		if (o != null) {
			return Err.CMPUNION_DOMAIN_DUPLICATE;
		}
		if (cmpUnion.getWebName() != null) {
			o = this.getCmpUnionByWebName(cmpUnion.getWebName());
			if (o != null) {
				return Err.CMPUNION_WEBNAME_DUPLICATE;
			}
		}
		cmpUnion.setCreateTime(new Date());
		Query query = manager.createQuery();
		long uid = query.insertObject(cmpUnion).longValue();
		cmpUnion.setUid(uid);
		return 0;
	}

	public void deleteCmpUnion(long uid) {
		Query query = manager.createQuery();
		query.deleteById(CmpUnion.class, uid);
	}

	public CmpUnion getCmpUnion(long uid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpUnion.class, uid);
	}

	public CmpUnion getCmpUnionByDomain(String domain) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpUnion.class, "domain=?",
				new Object[] { domain });
	}

	public CmpUnion getCmpUnionByWebName(String webName) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpUnion.class, "webname=?",
				new Object[] { webName });
	}

	public int updateCmpUnion(CmpUnion cmpUnion) {
		CmpUnion o = this.getCmpUnionByDomain(cmpUnion.getDomain());
		if (o != null && o.getUid() != cmpUnion.getUid()) {
			return Err.CMPUNION_DOMAIN_DUPLICATE;
		}
		if (cmpUnion.getWebName() != null) {
			o = this.getCmpUnionByWebName(cmpUnion.getWebName());
			if (o != null && o.getUid() != cmpUnion.getUid()) {
				return Err.CMPUNION_WEBNAME_DUPLICATE;
			}
		}
		Query query = manager.createQuery();
		query.updateObject(cmpUnion);
		return 0;
	}

	public boolean createCmpUnionKind(CmpUnionKind cmpUnionKind) {
		boolean result = this.createCmpUnionKindData(cmpUnionKind);
		if (result) {
			CmpUnionKind o = new CmpUnionKind();
			o.setHasChildflg(CmpUnionKind.HASCHILDFLG_N);
			o.setUid(cmpUnionKind.getUid());
			o.setKindLevel(cmpUnionKind.getKindLevel());
			o.setParentId(cmpUnionKind.getParentId());
			o.setName("其他");
			o.setOrderflg(99);
			this.createCmpUnionKindData(o);
			if (cmpUnionKind.getParentId() > 0) {
				o = this.getCmpUnionKind(cmpUnionKind.getParentId());
				if (o != null) {
					o.setHasChildflg(CmpUnionKind.HASCHILDFLG_Y);
					this.updateCmpUnionKind(o);
				}
			}
		}
		return result;
	}

	private boolean createCmpUnionKindData(CmpUnionKind cmpUnionKind) {
		CmpUnionKind o = this.getCmpUnionKindByUidAndName(
				cmpUnionKind.getUid(), cmpUnionKind.getParentId(), cmpUnionKind
						.getName());
		if (o != null) {
			return false;
		}
		this.validateCmpUnionKind(cmpUnionKind);
		Query query = manager.createQuery();
		long kindId = query.insertObject(cmpUnionKind).longValue();
		cmpUnionKind.setKindId(kindId);
		return true;
	}

	public boolean updateCmpUnionKind(CmpUnionKind cmpUnionKind) {
		CmpUnionKind o = this.getCmpUnionKindByUidAndName(
				cmpUnionKind.getUid(), cmpUnionKind.getParentId(), cmpUnionKind
						.getName());
		if (o != null && o.getKindId() != cmpUnionKind.getKindId()) {
			return false;
		}
		Query query = manager.createQuery();
		query.updateObject(cmpUnionKind);
		return true;
	}

	private void validateCmpUnionKind(CmpUnionKind cmpUnionKind) {
		if (cmpUnionKind.getUid() <= 0) {
			throw new RuntimeException("invalid uid [ " + cmpUnionKind.getUid()
					+ " ]");
		}
	}

	private CmpUnionKind getCmpUnionKindByUidAndName(long uid, long parentId,
			String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpUnionKind.class,
				"uid=? and parentid=? and name=?", new Object[] { uid,
						parentId, name });
	}

	public CmpUnionKind getCmpUnionKind(long kindId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpUnionKind.class, kindId);
	}

	public List<CmpUnionKind> getCmpUnionKindListByUid(long uid, int begin,
			int size) {
		Query query = manager.createQuery();
		if (size > 0) {
			return query.listEx(CmpUnionKind.class, "uid=?",
					new Object[] { uid }, "kindid desc", begin, size);
		}
		return query.listEx(CmpUnionKind.class, "uid=?", new Object[] { uid },
				"kindid desc");
	}

	public boolean deleteCmpUnionKind(long kindId) {
		CmpUnionKind cmpUnionKind = this.getCmpUnionKind(kindId);
		if (cmpUnionKind.isHasChild()) {
			return false;
		}
		Query query = manager.createQuery();
		query.deleteById(CmpUnionKind.class, kindId);
		if (cmpUnionKind.getParentId() > 0) {
			CmpUnionKind parent = this.getCmpUnionKind(cmpUnionKind
					.getParentId());
			int count = query.count(CmpUnionKind.class, "uid=? and parentid=?",
					new Object[] { cmpUnionKind.getUid(),
							cmpUnionKind.getParentId() });
			if (count == 0) {
				parent.setHasChildflg(CmpUnionKind.HASCHILDFLG_N);
				this.updateCmpUnionKind(parent);
			}
		}
		// TODO 更新相关的product 把分类id置为0?
		return true;
	}

	public void createCmpUnionOpUser(CmpUnionOpUser cmpUnionOpUser) {
		Query query = manager.createQuery();
		if (query.count(CmpUnionOpUser.class, "uid=? and userid=?",
				new Object[] { cmpUnionOpUser.getUid(),
						cmpUnionOpUser.getUserId() }) == 0) {
			query.addField("uid", cmpUnionOpUser.getUid());
			query.addField("userid", cmpUnionOpUser.getUserId());
			query.insert(CmpUnionOpUser.class);
		}
	}

	public void deleteCmpUnionOpUser(long uid, long userId) {
		Query query = manager.createQuery();
		query.delete(CmpUnionOpUser.class, "uid=? and userid=?", new Object[] {
				uid, userId });
	}

	public List<CmpUnionOpUser> getCmpUnionOpUserListByUid(long uid) {
		Query query = manager.createQuery();
		return query
				.listEx(CmpUnionOpUser.class, "uid=?", new Object[] { uid });
	}

	public List<CmpUnion> getCmpUnionList(String name, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from cmpunion where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (name != null) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sql.append(" order by uid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpUnion.class, olist);
	}

	public boolean isCmpUnionOpUser(long uid, long userId) {
		Query query = manager.createQuery();
		if (query.count(CmpUnionOpUser.class, "uid=? and userid=?",
				new Object[] { uid, userId }) > 0) {
			return true;
		}
		return false;
	}

	public int countCmpUnionKindByUid(long uid, long parentId) {
		Query query = manager.createQuery();
		return query.count(CmpUnionKind.class, "uid=? and parentid=?",
				new Object[] { uid, parentId });
	}

	public List<CmpUnionKind> getCmpUnionKindListByUid(long uid, long parentId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpUnionKind.class, "uid=? and parentid=?",
				new Object[] { uid, parentId }, "orderflg asc,kindid desc",
				begin, size);
	}

	public void updateCmpUnionLogo(long uid, File file) throws Exception {
		if (file != null) {
			String dbPath = ImageConfig.getUnionLogoSaveToDBPath(uid + "");
			String filePath = ImageConfig.getUnionLogoFilePath(dbPath);
			JMagickUtil util = new JMagickUtil(file, 2);
			util.makeImage(filePath, ImageConfig.IMG_H48,
					JMagickUtil.IMG_OBLONG, 48);
			util.makeImage(filePath, ImageConfig.IMG_H80,
					JMagickUtil.IMG_OBLONG, 80);
			CmpUnion cmpUnion = this.getCmpUnion(uid);
			cmpUnion.setLogo(dbPath);
			this.updateCmpUnion(cmpUnion);
		}
	}

	public void updateCmpUnionData(long uid, String data) {
		Query query = manager.createQuery();
		query.addField("data", data);
		query.updateById(CmpUnion.class, uid);
	}

	public List<CmpUnion> getCmpUnionListLikeName(int pcityId, String name,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpUnion.class, "pcityid=? and name like ?",
				new Object[] { pcityId, "%" + name + "%" }, "uid desc", begin,
				size);
	}

	public int countCmpUnionLikeName(int pcityId, String name) {
		Query query = manager.createQuery();
		return query.count(CmpUnion.class, "pcityid=? and  name like ?",
				new Object[] { pcityId, "%" + name + "%" });
	}

	public Map<Long, CmpUnionKind> getCmpUnionKindMapInId(List<Long> idList) {
		List<CmpUnionKind> list = this.getCmpUnionKindListInId(idList);
		Map<Long, CmpUnionKind> map = new HashMap<Long, CmpUnionKind>();
		for (CmpUnionKind o : list) {
			map.put(o.getKindId(), o);
		}
		return map;
	}

	private List<CmpUnionKind> getCmpUnionKindListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpUnionKind>();
		}
		StringBuffer sb = new StringBuffer(
				"select * from cmpunionkind where kindid in (");
		for (Long l : idList) {
			sb.append(l).append(",");
		}
		sb.deleteCharAt(sb.length() - 1).append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sb.toString(), CmpUnionKind.class);
	}

	public int countCmpUnionBoardByUid(long uid) {
		Query query = manager.createQuery();
		return query.count(CmpUnionBoard.class, "uid=?", new Object[] { uid });
	}

	public void createCmpUnionBoard(CmpUnionBoard cmpUnionBoard) {
		cmpUnionBoard.setCreateTime(new Date());
		Query query = manager.createQuery();
		long boardId = query.insertObject(cmpUnionBoard).longValue();
		cmpUnionBoard.setBoardId(boardId);
	}

	public void deleteCmpUnionBoard(long boardId) {
		Query query = manager.createQuery();
		query.deleteById(CmpUnionBoard.class, boardId);
	}

	public CmpUnionBoard getCmpUnionBoard(long boardId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpUnionBoard.class, boardId);
	}

	public List<CmpUnionBoard> getCmpUnionBoardListByUid(long uid, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpUnionBoard.class, "uid=?", new Object[] { uid },
				"boardid desc", begin, size);
	}

	public void updateCmpUnionBoard(CmpUnionBoard cmpUnionBoard) {
		Query query = manager.createQuery();
		query.updateObject(cmpUnionBoard);
	}

	public int countCmpUnionLinkByUid(long uid) {
		Query query = manager.createQuery();
		return query.count(CmpUnionLink.class, "uid=?", new Object[] { uid });
	}

	public void createCmpUnionLink(CmpUnionLink cmpUnionLink) {
		Query query = manager.createQuery();
		long linkId = query.insertObject(cmpUnionLink).longValue();
		cmpUnionLink.setLinkId(linkId);
	}

	public void deleteCmpUnionLink(long linkId) {
		Query query = manager.createQuery();
		query.deleteById(CmpUnionLink.class, linkId);
	}

	public List<CmpUnionLink> getCmpUnionLinkListByUid(long uid, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpUnionLink.class, "uid=?", new Object[] { uid },
				"linkid desc", begin, size);
	}

	public void updateCmpUnionLink(CmpUnionLink cmpUnionLink) {
		Query query = manager.createQuery();
		query.updateObject(cmpUnionLink);
	}

	public CmpUnionLink getCmpUnionLink(long linkId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpUnionLink.class, linkId);
	}

	public boolean createCmpUnionCmdKind(CmpUnionCmdKind cmpUnionCmdKind) {
		CmpUnionKind cmpUnionKind = this.getCmpUnionKind(cmpUnionCmdKind
				.getKindId());
		if (cmpUnionKind == null || cmpUnionKind.getKindLevel() == 0) {
			return false;
		}
		Query query = manager.createQuery();
		if (query.count(CmpUnionCmdKind.class, "kindid=?",
				new Object[] { cmpUnionCmdKind.getKindId() }) > 0) {
			return false;
		}
		long oid = query.insertObject(cmpUnionCmdKind).longValue();
		cmpUnionCmdKind.setOid(oid);
		return true;
	}

	public void deleteCmpUnionCmdKind(long uid, long kindId) {
		Query query = manager.createQuery();
		query.delete(CmpUnionCmdKind.class, "uid=? and kindid=?", new Object[] {
				uid, kindId });
	}

	public List<CmpUnionCmdKind> getCmpUnionCmdKindListByUid(long uid,
			int begin, int size) {
		Query query = manager.createQuery();
		if (size > 0) {
			return query.listEx(CmpUnionCmdKind.class, "uid=?",
					new Object[] { uid }, "oid desc", begin, size);
		}
		return query.listEx(CmpUnionCmdKind.class, "uid=?",
				new Object[] { uid }, "oid desc");
	}

	public List<CmpUnionKind> getCmpUnionKindListInId(long uid,
			List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpUnionKind>();
		}
		StringBuffer sql = new StringBuffer(
				"select * from cmpunionkind where uid=? and kindid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = manager.createQuery();
		return query
				.listBySqlEx("ds1", sql.toString(), CmpUnionKind.class, uid);
	}

	public void updateCmpUnionStatus(long uid, byte status) {
		Query query = manager.createQuery();
		query.addField("unionstatus", status);
		query.updateById(CmpUnion.class, uid);
	}

	public void updateCmpUnionPcityIdData() {
		ZoneService zoneService = (ZoneService) HkUtil.getBean("zoneService");
		Query query = manager.createQuery();
		List<CmpUnion> list = query.listEx(CmpUnion.class);
		for (CmpUnion o : list) {
			Pcity pcity = ZoneUtil.getPcityOld(o.getPcityId());
			if (pcity != null) {
				String name = DataUtil.filterZoneName(pcity.getNameOld());
				City city = zoneService.getCityLike(name);
				if (city != null) {
					o.setPcityId(city.getCityId());
					query.updateObject(o);
				}
			}
		}
	}

	public List<CmpUnionKind> getLastLevelCmpUnionKindListByUid(long uid) {
		Query query = manager.createQuery();
		return query.listEx(CmpUnionKind.class,
				"uid=? and haschildflg=? and name!=?", new Object[] { uid,
						CmpUnionKind.HASCHILDFLG_N, "其他" }, "kindid asc");
	}
}