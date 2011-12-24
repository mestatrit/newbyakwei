package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpAd;
import com.hk.bean.CmpAdBlock;
import com.hk.bean.CmpAdRef;
import com.hk.bean.CmpPageBlock;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpAdService;
import com.hk.svr.CmpModService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpAdProcessor {

	@Autowired
	private CmpAdService cmpAdService;

	@Autowired
	private CmpModService cmpModService;

	public int createCmpAd(CmpAd cmpAd, File file, long blockId, int refflg)
			throws IOException {
		if (file != null) {
			if (!DataUtil.isImage(file)) {
				return Err.IMG_FMT_ERROR;
			}
			if (DataUtil.isBigger(file, 100)) {
				return Err.IMG_OUTOFSIZE_ERROR;
			}
			String picname = String.valueOf(System.nanoTime());
			String dbPath = ImageConfig.getCmpAdPicSaveToDBPath(cmpAd
					.getCompanyId(), picname);
			DataUtil.copyFile(file, ImageConfig.getCmpAdPicFilePath(dbPath),
					"h.jpg");
			cmpAd.setPath(dbPath);
			cmpAd.setAdflg(CmpAd.ADFLG_IMG);
		}
		else {
			if (DataUtil.isEmpty(cmpAd.getUrl())) {
				cmpAd.setAdflg(CmpAd.ADFLG_HTML);
			}
			else {
				cmpAd.setAdflg(CmpAd.ADFLG_TEXT);
			}
		}
		this.cmpAdService.createCmpAd(cmpAd);
		if (blockId > 0) {
			CmpAdBlock cmpAdBlock = new CmpAdBlock();
			cmpAdBlock.setAdid(cmpAd.getAdid());
			cmpAdBlock.setCompanyId(cmpAd.getCompanyId());
			cmpAdBlock.setBlockId(blockId);
			cmpAdBlock.setPageflg((byte) 1);
			this.cmpModService.createCmpAdBlock(cmpAdBlock);
		}
		if (refflg == 1) {
			CmpAdRef cmpAdRef = new CmpAdRef();
			cmpAdRef.setCompanyId(cmpAd.getCompanyId());
			cmpAdRef.setAdid(cmpAd.getAdid());
			this.cmpAdService.createCmpAdRef(cmpAdRef);
		}
		return Err.SUCCESS;
	}

	public int updateCmpAd(CmpAd cmpAd, File file, int refflg)
			throws IOException {
		if (file != null) {
			if (!DataUtil.isImage(file)) {
				return Err.IMG_FMT_ERROR;
			}
			if (DataUtil.isBigger(file, 100)) {
				return Err.IMG_OUTOFSIZE_ERROR;
			}
			String dbPath = cmpAd.getPath();
			DataUtil.copyFile(file, ImageConfig.getCmpAdPicFilePath(dbPath),
					"h.jpg");
		}
		this.cmpAdService.updateCmpAd(cmpAd);
		if (refflg == 1) {
			CmpAdRef cmpAdRef = new CmpAdRef();
			cmpAdRef.setCompanyId(cmpAd.getCompanyId());
			cmpAdRef.setAdid(cmpAd.getAdid());
			this.cmpAdService.createCmpAdRef(cmpAdRef);
		}
		else {
			CmpAdRef cmpAdRef = this.cmpAdService
					.getCmpAdRefByCompanyIdAndAdid(cmpAd.getCompanyId(), cmpAd
							.getAdid());
			if (cmpAdRef != null) {
				this.cmpAdService.deleteCmpAd(cmpAd.getCompanyId(), cmpAdRef
						.getOid());
			}
		}
		return Err.SUCCESS;
	}

	public void deleteCmpAd(CmpAd cmpAd) {
		this.cmpModService.deleteCmpAdBlockByCompanyIdAndAdid(cmpAd
				.getCompanyId(), cmpAd.getAdid());
		this.cmpAdService.deleteCmpAd(cmpAd.getCompanyId(), cmpAd.getAdid());
		File file = new File(ImageConfig.getCmpAdPicFilePath(cmpAd.getPath())
				+ "h.jpg");
		DataUtil.deleteFile(file);
	}

	public List<CmpAd> getCmpAdListByCompanyId(long companyId, long groupId,
			boolean buildBlock, int begin, int size) {
		List<CmpAd> list = this.cmpAdService.getCmpAdListByCompanyId(companyId,
				groupId, begin, size);
		if (buildBlock) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpAd o : list) {
				idList.add(o.getPage1BlockId());
			}
			Map<Long, CmpPageBlock> map = this.cmpModService
					.getCmpPageBlockInMap(idList);
			for (CmpAd o : list) {
				o.setPage1Block(map.get(o.getPage1BlockId()));
			}
		}
		return list;
	}

	public List<CmpAdRef> getCmpAdRefListByCompanyId(long companyId,
			boolean buildCmpAd, int begin, int size) {
		List<CmpAdRef> list = this.cmpAdService.getCmpAdRefListByCompanyId(
				companyId, begin, size);
		List<Long> idList = new ArrayList<Long>();
		if (buildCmpAd) {
			for (CmpAdRef o : list) {
				idList.add(o.getAdid());
			}
			Map<Long, CmpAd> map = this.cmpAdService
					.getCmpAdMaByCompanyIdAndInId(companyId, idList);
			for (CmpAdRef o : list) {
				o.setCmpAd(map.get(o.getAdid()));
			}
		}
		return list;
	}
}