package com.hk.svr.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpAd;
import com.hk.bean.CmpAdBlock;
import com.hk.bean.CmpArticle;
import com.hk.bean.CmpArticleBlock;
import com.hk.bean.CmpPageBlock;
import com.hk.bean.CmpPageMod;
import com.hk.svr.CmpAdService;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpModService;

public class CmpModProcessor {

	@Autowired
	private CmpModService cmpModService;

	@Autowired
	private CmpArticleService cmpArticleService;

	@Autowired
	private CmpAdService cmpAdService;

	public List<CmpAdBlock> getCmpAdBlockListByCompanyIdAndPageflg(
			long companyId, byte pageflg, boolean buildCmpAd) {
		List<CmpAdBlock> list = this.cmpModService
				.getCmpAdBlockListByCompanyIdAndPageflg(companyId, pageflg);
		if (buildCmpAd) {
			this.buildCmpAd(companyId, list);
		}
		return list;
	}

	private void buildCmpAd(long companyId, List<CmpAdBlock> list) {
		List<Long> idList = new ArrayList<Long>();
		for (CmpAdBlock o : list) {
			idList.add(o.getAdid());
		}
		Map<Long, CmpAd> map = this.cmpAdService.getCmpAdMaByCompanyIdAndInId(
				companyId, idList);
		for (CmpAdBlock o : list) {
			o.setCmpAd(map.get(o.getAdid()));
		}
	}

	public List<CmpAdBlock> getCmpAdBlockListByCompanyIdAndBlockId(
			long companyId, long blockId, boolean buildCmpAd, int begin,
			int size) {
		List<CmpAdBlock> list = this.cmpModService
				.getCmpAdBlockListByCompanyIdAndBlockId(companyId, blockId,
						begin, size);
		if (buildCmpAd) {
			this.buildCmpAd(companyId, list);
		}
		return list;
	}

	public List<CmpArticleBlock> getCmpArticleBlockListByCompanyIdAndPageflg(
			long companyId, byte pageflg, boolean buildCmpArticle) {
		List<CmpArticleBlock> list = this.cmpModService
				.getCmpArticleBlockListByCompanyIdAndPageflg(companyId, pageflg);
		if (buildCmpArticle) {
			this.buildCmpArticle(companyId, list);
		}
		return list;
	}

	public List<CmpArticleBlock> getCmpArticleBlockListByCompanyIdAndBlockId(
			long companyId, long blockId, boolean buildCmpArticle,
			boolean sortDesc, int begin, int size) {
		List<CmpArticleBlock> list = this.cmpModService
				.getCmpArticleBlockListByCompanyIdAndBlockId(companyId,
						blockId, sortDesc, begin, size);
		if (buildCmpArticle) {
			this.buildCmpArticle(companyId, list);
		}
		return list;
	}

	private void buildCmpArticle(long companyId, List<CmpArticleBlock> list) {
		List<Long> idList = new ArrayList<Long>();
		for (CmpArticleBlock o : list) {
			idList.add(o.getArticleId());
		}
		Map<Long, CmpArticle> map = this.cmpArticleService
				.getCmpArticleMapByCompanyIdAndInId(companyId, idList);
		for (CmpArticleBlock o : list) {
			o.setCmpArticle(map.get(o.getArticleId()));
		}
	}

	public void deleteCmpAdBlock(long oid) {
		CmpAdBlock cmpAdBlock = this.cmpModService.getCmpAdBlock(oid);
		if (cmpAdBlock != null) {
			CmpAd cmpAd = this.cmpAdService.getCmpAd(cmpAdBlock.getAdid());
			if (cmpAd != null) {
				if (cmpAdBlock.getPageflg() == 1) {
					cmpAd.setPage1BlockId(0);
					this.cmpAdService.updateCmpAd(cmpAd);
				}
			}
		}
		this.cmpModService.deleteCmpAdBlock(oid);
	}

	public void deleteCmpArticleBlock(long oid) {
		CmpArticleBlock cmpArticleBlock = this.cmpModService
				.getCmpArticleBlock(oid);
		if (cmpArticleBlock != null) {
			CmpArticle cmpArticle = this.cmpArticleService
					.getCmpArticle(cmpArticleBlock.getArticleId());
			if (cmpArticle != null) {
				if (cmpArticleBlock.getPageflg() == 1) {
					cmpArticle.setPage1BlockId(0);
					this.cmpArticleService.updateCmpArticle(cmpArticle, null);
				}
			}
		}
		this.cmpModService.deleteCmpArticleBlock(oid);
	}

	public boolean createCmpAdBlock(CmpAdBlock cmpAdBlock) {
		if (this.cmpModService.createCmpAdBlock(cmpAdBlock)) {
			CmpAd cmpAd = this.cmpAdService.getCmpAd(cmpAdBlock.getAdid());
			if (cmpAdBlock.getPageflg() == 1) {
				cmpAd.setPage1BlockId(cmpAdBlock.getBlockId());
				this.cmpAdService.updateCmpAd(cmpAd);
			}
			return true;
		}
		return false;
	}

	public boolean createCmpArticlePageBlock(CmpArticleBlock cmpArticleBlock,
			CmpPageMod cmpPageMod) {
		if (this.cmpModService.createCmpArticlePageBlock(cmpArticleBlock,
				cmpPageMod)) {
			CmpArticle cmpArticle = this.cmpArticleService
					.getCmpArticle(cmpArticleBlock.getArticleId());
			if (cmpArticleBlock.getPageflg() == 1) {
				cmpArticle.setPage1BlockId(cmpArticleBlock.getBlockId());
				this.cmpArticleService.updateCmpArticle(cmpArticle, null);
			}
			return true;
		}
		return false;
	}

	/**
	 * @param companyId
	 * @param buildRemainSize 是否需要获取是剩余位置的数据,true:是,false:否
	 * @param pageflg
	 * @return
	 *         2010-6-28
	 */
	public List<CmpPageBlock> getCmpPageBlockListByCompanyIdAndPageflg(
			long companyId, boolean buildRemainSize, byte pageflg) {
		List<CmpPageBlock> list = this.cmpModService
				.getCmpPageBlockListByCompanyIdAndPageflg(companyId, pageflg);
		for (CmpPageBlock o : list) {
			int count = 0;
			if (o.getCmpPageMod().isModAd()) {
				count = this.cmpModService
						.countCmpAdBlockByCompanyIdAndBlockId(companyId, o
								.getBlockId());
			}
			if (o.getCmpPageMod().isModArticle()) {
				count = this.cmpModService
						.countCmpArticleBlockByCompanyIdAndBlockId(companyId, o
								.getBlockId());
			}
			int remain = o.getCmpPageMod().getDataSize() - count;
			remain = remain > 0 ? remain : 0;
			o.setRemainSize(remain);
		}
		return list;
	}
}