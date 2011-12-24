package com.hk.svr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpAdBlock;
import com.hk.bean.CmpArticleBlock;
import com.hk.bean.CmpPageBlock;
import com.hk.bean.CmpPageMod;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.svr.CmpModService;

public class CmpModServiceImpl implements CmpModService {

	@Autowired
	private QueryManager manager;

	public boolean createCmpAdBlock(CmpAdBlock cmpAdBlock) {
		Query query = this.manager.createQuery();
		if (query.count(CmpAdBlock.class,
				"companyid=? and adid=? and pageflg=?", new Object[] {
						cmpAdBlock.getCompanyId(), cmpAdBlock.getAdid(),
						cmpAdBlock.getPageflg() }) > 0) {
			return false;
		}
		long oid = query.insertObject(cmpAdBlock).longValue();
		cmpAdBlock.setOid(oid);
		return true;
	}

	public boolean createCmpArticlePageBlock(CmpArticleBlock cmpArticleBlock,
			CmpPageMod cmpPageMod) {
		Query query = this.manager.createQuery();
		if (query.count(CmpArticleBlock.class,
				"companyid=? and articleid=? and pageflg=?", new Object[] {
						cmpArticleBlock.getCompanyId(),
						cmpArticleBlock.getArticleId(),
						cmpArticleBlock.getPageflg() }) > 0) {
			return false;
		}
		long oid = query.insertObject(cmpArticleBlock).longValue();
		cmpArticleBlock.setOid(oid);
		if (cmpPageMod.getCmpNavArticleCount() > 0) {
			List<CmpArticleBlock> list = query.listEx(CmpArticleBlock.class,
					"companyid=? and cmpnavoid=? and blockid=?", new Object[] {
							cmpArticleBlock.getCompanyId(),
							cmpArticleBlock.getCmpNavOid(),
							cmpArticleBlock.getBlockId() }, "oid asc");
			if (list.size() > cmpPageMod.getCmpNavArticleCount()) {
				this.deleteCmpArticleBlock(list.get(0).getOid());
			}
		}
		return true;
	}

	public boolean updateCmpArticlePageBlock(CmpArticleBlock cmpArticleBlock) {
		Query query = this.manager.createQuery();
		CmpArticleBlock o = query.getObjectEx(CmpArticleBlock.class,
				"companyid=? and articleid=? and pageflg=?", new Object[] {
						cmpArticleBlock.getCompanyId(),
						cmpArticleBlock.getArticleId(),
						cmpArticleBlock.getPageflg() });
		if (o.getOid() != cmpArticleBlock.getOid()) {
			return false;
		}
		query.updateObject(cmpArticleBlock);
		return true;
	}

	public void createCmpPageBlock(CmpPageBlock cmpPageBlock) {
		Query query = this.manager.createQuery();
		query.insertObject(cmpPageBlock);
	}

	public void deleteCmpAdBlock(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpAdBlock.class, oid);
	}

	public void deleteCmpArticleBlock(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpArticleBlock.class, oid);
	}

	public void deleteCmpPageBlock(long blockId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpPageBlock.class, blockId);
		query.delete(CmpAdBlock.class, "blockid=?", new Object[] { blockId });
		query.delete(CmpArticleBlock.class, "blockid=?",
				new Object[] { blockId });
	}

	public CmpPageBlock getCmpPageBlock(long blockId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpPageBlock.class, blockId);
	}

	public List<CmpPageBlock> getCmpPageBlockListByCompanyIdAndPageflg(
			long companyId, byte pageflg) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpPageBlock.class, "companyid=? and pageflg=?",
				new Object[] { companyId, pageflg },
				"orderflg desc,blockid asc");
	}

	public List<CmpPageBlock> getCmpPageBlockListByCompanyIdAndPageModId(
			long companyId, int pageModId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpPageBlock.class, "companyid=? and pagemodid=?",
				new Object[] { companyId, pageModId },
				"orderflg desc,blockid asc");
	}

	public void updateCmpPageBlock(CmpPageBlock cmpPageBlock) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpPageBlock);
	}

	public void updateCmpPageBlockOrderflg(long blockId, int orderflg) {
		Query query = this.manager.createQuery();
		query.addField("orderflg", orderflg);
		query.updateById(CmpPageBlock.class, blockId);
	}

	public List<CmpAdBlock> getCmpAdBlockListByCompanyIdAndPageflg(
			long companyId, byte pageflg) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpAdBlock.class, "companyid=? and pageflg=?",
				new Object[] { companyId, pageflg }, "oid desc");
	}

	public List<CmpArticleBlock> getCmpArticleBlockListByCompanyIdAndPageflg(
			long companyId, byte pageflg) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticleBlock.class, "companyid=? and pageflg=?",
				new Object[] { companyId, pageflg }, "oid desc");
	}

	public List<CmpAdBlock> getCmpAdBlockListByCompanyIdAndBlockId(
			long companyId, long blockId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpAdBlock.class, "companyid=? and blockid=?",
				new Object[] { companyId, blockId }, "oid desc", begin, size);
	}

	public List<CmpArticleBlock> getCmpArticleBlockListByCompanyIdAndBlockId(
			long companyId, long blockId, boolean sortDesc, int begin, int size) {
		Query query = this.manager.createQuery();
		String order = null;
		if (sortDesc) {
			order = "oid desc";
		}
		else {
			order = "oid asc";
		}
		return query.listEx(CmpArticleBlock.class, "companyid=? and blockid=?",
				new Object[] { companyId, blockId }, order, begin, size);
	}

	public CmpAdBlock getCmpAdBlock(long companyId, long adid, byte pageflg) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpAdBlock.class,
				"companyid=? and adid=? and pageflg=? ", new Object[] {
						companyId, adid, pageflg });
	}

	public CmpAdBlock getCmpAdBlock(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpAdBlock.class, oid);
	}

	public CmpArticleBlock getCmpArticleBlock(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpArticleBlock.class, oid);
	}

	public Map<Long, CmpPageBlock> getCmpPageBlockInMap(List<Long> idList) {
		Query query = this.manager.createQuery();
		Map<Long, CmpPageBlock> map = new HashMap<Long, CmpPageBlock>();
		List<CmpPageBlock> list = query.listInField(CmpPageBlock.class, null,
				null, "blockid", idList, null);
		for (CmpPageBlock o : list) {
			map.put(o.getBlockId(), o);
		}
		return map;
	}

	public CmpAdBlock getCmpAdBlock(long companyId, long adid, long blockId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpAdBlock.class,
				"companyid=? and adid=? and blockid=?", new Object[] {
						companyId, adid, blockId });
	}

	public CmpArticleBlock getCmpArticleBlock(long companyId, long articleId,
			long blockId) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpArticleBlock.class,
				"companyid=? and articleid=? and blockid=?", new Object[] {
						companyId, articleId, blockId });
	}

	public CmpArticleBlock getCmpArticleBlock(long companyId, long articleId,
			byte pageflg) {
		Query query = this.manager.createQuery();
		return query.getObjectEx(CmpArticleBlock.class,
				"companyid=? and articleid=? and pageflg=?", new Object[] {
						companyId, articleId, pageflg });
	}

	public int countCmpAdBlockByCompanyIdAndBlockId(long companyId, long blockId) {
		Query query = this.manager.createQuery();
		return query.count(CmpAdBlock.class, "companyid=? and blockid=?",
				new Object[] { companyId, blockId });
	}

	public int countCmpArticleBlockByCompanyIdAndBlockId(long companyId,
			long blockId) {
		Query query = this.manager.createQuery();
		return query.count(CmpArticleBlock.class, "companyid=? and blockid=?",
				new Object[] { companyId, blockId });
	}

	public void deleteCmpArticleBlockByCompanyIdAndArticleId(long companyId,
			long articleId) {
		Query query = this.manager.createQuery();
		query.delete(CmpArticleBlock.class, "companyid=? and articleid=?",
				new Object[] { companyId, articleId });
	}

	public void delteCmpAdBlockByCompanyIdAndAdid(long companyId, long adid) {
		Query query = this.manager.createQuery();
		query.delete(CmpAdBlock.class, "companyid=? and adid=?", new Object[] {
				companyId, adid });
	}

	public void deleteCmpAdBlockByCompanyIdAndAdid(long companyId, long adid) {
		Query query = this.manager.createQuery();
		query.delete(CmpAdBlock.class, "companyid=? and adid=?", new Object[] {
				companyId, adid });
	}

	public List<CmpArticleBlock> getCmpArticleBlockList() {
		Query query = this.manager.createQuery();
		return query.listEx(CmpArticleBlock.class);
	}
}