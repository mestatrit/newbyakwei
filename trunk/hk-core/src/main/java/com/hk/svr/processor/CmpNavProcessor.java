package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpFile;
import com.hk.bean.CmpNav;
import com.hk.bean.CmpStudyKind;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.CmpArticleService;
import com.hk.svr.CmpNavService;
import com.hk.svr.CmpStudyKindService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpNavProcessor {

	@Autowired
	private CmpNavService cmpNavService;

	@Autowired
	private CmpArticleService cmpArticleService;

	@Autowired
	private CmpStudyKindService cmpStudyKindService;

	/**
	 * 只有一级导航才可以使用图片
	 * 
	 * @param cmpNav
	 * @param file
	 * @throws IOException
	 *             2010-5-10
	 * @throws OutOfSizeException
	 * @throws NotPermitImageFormatException
	 */
	public int createCmpNav(CmpNav cmpNav, File file, File bgFile) {
		if (cmpNav.getReffunc() != CmpNav.REFFUNC_LINK) {
			cmpNav.setUrl(null);
		}
		else {
			if (!DataUtil.isEmpty(cmpNav.getUrl())) {
				if (cmpNav.getUrl().toLowerCase().startsWith("http://")) {
					cmpNav.setUrl(cmpNav.getUrl().substring(7));
				}
			}
		}
		this.cmpNavService.createCmpNav(cmpNav);
		if (cmpNav.getNlevel() == CmpNav.NLEVEL_1) {
			int code = this.updateFile(cmpNav, file);
			if (code != Err.SUCCESS) {
				this.cmpNavService.deleteCmpNav(cmpNav.getOid());
				return code;
			}
		}
		return this.updateBgImage(cmpNav, bgFile);
	}

	public void deleteCmpNav(long oid) {
		CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
		if (cmpNav == null) {
			return;
		}
		this.cmpArticleService.setCmpArticleUnknown(cmpNav.getCompanyId(), oid);
		List<CmpNav> children = this.cmpNavService
				.getCmpNavListByCompanyIdAndParentId(cmpNav.getCompanyId(), oid);
		for (CmpNav o : children) {
			this.cmpArticleService.setCmpArticleUnknown(o.getCompanyId(), o
					.getOid());
			this.deleteCmpNav(o.getOid());
		}
		if (cmpNav.getFilepath() != null) {
			String filePath = ImageConfig.getCmpNavFilePath(cmpNav
					.getFilepath());
			File file = new File(filePath + "h.jpg");
			DataUtil.deleteFile(file);
			file = new File(filePath + "s.jpg");
			DataUtil.deleteFile(file);
			file = new File(filePath + "h.swf");
			DataUtil.deleteFile(file);
		}
		this.cmpArticleService.deleteCmpArticleNavPinkByCompanyIdAndNavId(
				cmpNav.getCompanyId(), oid);
		this.cmpNavService.deleteCmpNav(oid);
	}

	public void deleteCmpNavFile(long oid) {
		CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
		if (cmpNav == null) {
			return;
		}
		if (cmpNav.getFilepath() != null) {
			String filePath = ImageConfig.getCmpNavFilePath(cmpNav
					.getFilepath());
			File file = new File(filePath + "h.jpg");
			DataUtil.deleteFile(file);
			file = new File(filePath + "s.jpg");
			DataUtil.deleteFile(file);
			file = new File(filePath + "h.swf");
			DataUtil.deleteFile(file);
		}
		cmpNav.setFileflg(CmpFile.FILEFLG_NONE);
		cmpNav.setFilepath(null);
		this.cmpNavService.updateCmpNav(cmpNav);
	}

	public void deleteCmpNavBgFile(long oid) {
		CmpNav cmpNav = this.cmpNavService.getCmpNav(oid);
		if (cmpNav == null) {
			return;
		}
		if (cmpNav.getFilepath() != null) {
			String filePath = ImageConfig.getCmpNavFilePath(cmpNav
					.getBgPicPath());
			File file = new File(filePath + "bg.jpg");
			DataUtil.deleteFile(file);
		}
		cmpNav.setBgPicPath(null);
		this.cmpNavService.updateCmpNav(cmpNav);
	}

	public int updateCmpNav(CmpNav cmpNav, File file, File bgFile) {
		if (cmpNav.getReffunc() != CmpNav.REFFUNC_LINK) {
			cmpNav.setUrl(null);
		}
		else {
			if (!DataUtil.isEmpty(cmpNav.getUrl())) {
				if (cmpNav.getUrl().toLowerCase().startsWith("http://")) {
					cmpNav.setUrl(cmpNav.getUrl().substring(7));
				}
			}
		}
		this.cmpNavService.updateCmpNav(cmpNav);
		if (cmpNav.getNlevel() == CmpNav.NLEVEL_1) {
			int code = this.updateFile(cmpNav, file);
			if (code != Err.SUCCESS) {
				return code;
			}
		}
		return this.updateBgImage(cmpNav, bgFile);
	}

	private int updateBgImage(CmpNav cmpNav, File file) {
		if (file == null) {
			return Err.SUCCESS;
		}
		if (DataUtil.isBigger(file, 200)) {
			return Err.CMPNAV_BGIMG_ERROR;
		}
		String dbPath = ImageConfig.getCmpNavDbPath(cmpNav.getOid());
		String filePath = ImageConfig.getCmpNavFilePath(dbPath);
		String fileName = "bg.jpg";
		try {
			DataUtil.copyFile(file, filePath, fileName);
		}
		catch (IOException e) {
			return Err.CMPNAV_BGIMG_UPLOAD_ERROR;
		}
		cmpNav.setBgPicPath(dbPath);
		this.cmpNavService.updateCmpNav(cmpNav);
		return Err.SUCCESS;
	}

	private int updateFile(CmpNav cmpNav, File file) {
		if (file == null) {
			return Err.SUCCESS;
		}
		boolean isImage = false;
		try {
			isImage = DataUtil.isImage(file);
		}
		catch (IOException e1) {
			return Err.UPLOAD_ERROR;
		}
		String dbPath = ImageConfig.getCmpNavDbPath(cmpNav.getOid());
		String filePath = ImageConfig.getCmpNavFilePath(dbPath);
		String type = null;
		if (isImage) {
			type = ".jpg";
			cmpNav.setFileflg(CmpFile.FILEFLG_IMG);
		}
		else {
			type = ".swf";
			cmpNav.setFileflg(CmpFile.FILEFLG_SWF);
		}
		String fileName = "h" + type;
		try {
			DataUtil.copyFile(file, filePath, fileName);
		}
		catch (IOException e) {
			return Err.UPLOAD_ERROR;
		}
		cmpNav.setFilepath(dbPath);
		if (isImage) {
			JMagickUtil util;
			try {
				util = new JMagickUtil(file, 0.2);
				util.makeImage(filePath, "s.jpg", JMagickUtil.IMG_SQUARE, 60);
				this.cmpNavService.updateCmpNav(cmpNav);
			}
			catch (ImageException e) {
				return Err.UPLOAD_ERROR;
			}
			catch (NotPermitImageFormatException e) {
				return Err.IMG_FMT_ERROR;
			}
			catch (OutOfSizeException e) {
				return Err.IMG_OUTOFSIZE_ERROR_FOR_SIZE;
			}
		}
		return Err.SUCCESS;
	}

	public void moveUpCmpNavOrderPosition(long oid, List<CmpNav> list) {
		CmpNav[] arr = list.toArray(new CmpNav[list.size()]);
		// 移动位置
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getOid() == oid && i > 0) {
				CmpNav tmp = arr[i - 1];
				arr[i - 1] = arr[i];
				arr[i] = tmp;
				break;
			}
		}
		// 保存位置
		for (int i = 0; i < arr.length; i++) {
			arr[i].setOrderflg(i + 1);
			this.cmpNavService.updateCmpNavOrderflg(arr[i].getOid(), arr[i]
					.getOrderflg());
		}
	}

	/**
	 * 调整模块在首页布局的顺序
	 * 
	 * @param oid
	 * @param list
	 *            2010-5-23
	 */
	public void moveUpCmpNavInHomePage(long oid, List<CmpNav> list) {
		CmpNav[] arr = list.toArray(new CmpNav[list.size()]);
		// 移动位置
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].getOid() == oid && i > 0) {
				CmpNav tmp = arr[i - 1];
				arr[i - 1] = arr[i];
				arr[i] = tmp;
				break;
			}
		}
		// 保存位置
		for (int i = 0; i < arr.length; i++) {
			arr[i].setShowInHome(i + 1);
			this.cmpNavService.updateCmpNavShowInHome(arr[i].getOid(), arr[i]
					.getShowInHome());
		}
	}

	/**
	 * 取出企业的所有导航数据，并按照之间的父子关系进行排列与组合
	 * 
	 * @param companyId
	 * @return
	 *         2010-5-14
	 */
	public List<CmpNav> getCmpNavListByCompanyIdForMap(long companyId) {
		List<CmpNav> list = this.cmpNavService
				.getCmpNavListByCompanyId(companyId);
		// 把一级放到map中
		Map<Long, CmpNav> map = new LinkedHashMap<Long, CmpNav>();
		for (CmpNav o : list) {
			if (o.isTopNLevel()) {
				map.put(o.getOid(), o);
			}
		}
		// 循环集合，并把相应的二级添加到map对应的一级子集合中
		for (CmpNav o : list) {
			if (!o.isTopNLevel()) {
				CmpNav parent = map.get(o.getParentId());
				if (parent != null) {
					parent.addChild(o);
				}
			}
		}
		return new ArrayList<CmpNav>(map.values());
	}

	public List<CmpNav> getCmpNavListByCompanyIdAndNlevel(long companyId,
			int nlevel, boolean buildKind) {
		List<CmpNav> list = this.cmpNavService
				.getCmpNavListByCompanyIdAndNlevel(companyId, nlevel);
		if (buildKind) {
			this.buildKind(companyId, list);
		}
		return list;
	}

	public List<CmpNav> getCmpNavListByCompanyIdAndParentId(long companyId,
			long parentId, boolean buildKind) {
		List<CmpNav> list = this.cmpNavService
				.getCmpNavListByCompanyIdAndParentId(companyId, parentId);
		if (buildKind) {
			this.buildKind(companyId, list);
		}
		return list;
	}

	private void buildKind(long companyId, List<CmpNav> list) {
		List<Long> idList = new ArrayList<Long>();
		for (CmpNav o : list) {
			if (o.getKindId() > 0) {
				idList.add(o.getKindId());
			}
		}
		Map<Long, CmpStudyKind> map = this.cmpStudyKindService
				.getCmpStudyKindByCompanyIdInId(companyId, idList);
		for (CmpNav o : list) {
			if (o.getKindId() > 0) {
				o.setCmpStudyKind(map.get(o.getKindId()));
			}
		}
	}
}