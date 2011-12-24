package com.hk.svr.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpActor;
import com.hk.bean.CmpActorPink;
import com.hk.bean.CmpActorRole;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.CmpActorService;
import com.hk.svr.CmpActorSpTimeService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpActorProcessor {

	@Autowired
	private CmpActorService cmpActorService;

	@Autowired
	private CmpActorSpTimeService cmpActorSpTimeService;

	public int createCmpActor(CmpActor cmpActor, File file)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException {
		if (file != null) {
			if (DataUtil.isBigger(file, 500)) {
				return Err.UPLOAD_FILE_SIZE_LIMIT;
			}
			String picName = System.currentTimeMillis() + "";
			String dbPath = ImageConfig.getCmpActorPicDbPath(cmpActor
					.getCompanyId(), picName);
			cmpActor.setPicPath(dbPath);
			String filePath = ImageConfig.getCmpActorPicFilePath(dbPath);
			JMagickUtil jMagickUtil = new JMagickUtil(file, 1);
			jMagickUtil.makeImage(filePath, ImageConfig.IMG_H60,
					JMagickUtil.IMG_SQUARE, 60);
			jMagickUtil.makeImage(filePath, ImageConfig.IMG_H150,
					JMagickUtil.IMG_OBLONG, 150);
			jMagickUtil.makeImage(filePath, ImageConfig.IMG_H500,
					JMagickUtil.IMG_OBLONG, 500);
		}
		cmpActor.setWorkDay("2,3,4,5,6,7,1");
		this.cmpActorService.createCmpActor(cmpActor);
		return Err.SUCCESS;
	}

	public int updateCmpActor(CmpActor cmpActor, File file)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException {
		if (file != null) {
			if (DataUtil.isBigger(file, 500)) {
				return Err.UPLOAD_FILE_SIZE_LIMIT;
			}
			// 先删除旧图片
			String oldpath = cmpActor.getPicPath();
			String old_filePath = ImageConfig.getCmpActorPicFilePath(oldpath);
			DataUtil.deleteFile(new File(old_filePath + ImageConfig.IMG_H60));
			DataUtil.deleteFile(new File(old_filePath + ImageConfig.IMG_H120));
			DataUtil.deleteFile(new File(old_filePath + ImageConfig.IMG_H500));
			String picName = System.currentTimeMillis() + "";
			String dbPath = ImageConfig.getCmpActorPicDbPath(cmpActor
					.getCompanyId(), picName);
			cmpActor.setPicPath(dbPath);
			String filePath = ImageConfig.getCmpActorPicFilePath(dbPath);
			JMagickUtil jMagickUtil = new JMagickUtil(file, 1);
			jMagickUtil.makeImage(filePath, ImageConfig.IMG_H60,
					JMagickUtil.IMG_SQUARE, 60);
			jMagickUtil.makeImage(filePath, ImageConfig.IMG_H150,
					JMagickUtil.IMG_OBLONG, 150);
			jMagickUtil.makeImage(filePath, ImageConfig.IMG_H500,
					JMagickUtil.IMG_OBLONG, 500);
		}
		this.cmpActorService.updateCmpActor(cmpActor);
		return Err.SUCCESS;
	}

	public void deleteCmpActor(CmpActor cmpActor) {
		this.cmpActorSpTimeService.deleteCmpActorSpTimeByCompanyIdAndActorId(
				cmpActor.getCompanyId(), cmpActor.getActorId());
		// 先删除图片
		String path = cmpActor.getPicPath();
		String filePath = ImageConfig.getCmpActorPicFilePath(path);
		DataUtil.deleteFile(new File(filePath + ImageConfig.IMG_H60));
		DataUtil.deleteFile(new File(filePath + ImageConfig.IMG_H150));
		DataUtil.deleteFile(new File(filePath + ImageConfig.IMG_H500));
		this.cmpActorService.deleteCmpActor(cmpActor.getActorId());
	}

	public List<CmpActor> getCmpActorListByCompanyIdForCanReserve(
			long companyId, boolean buildRole, int begin, int size) {
		List<CmpActor> list = this.cmpActorService
				.getCmpActorListByCompanyIdForCanReserve(companyId, begin, size);
		if (buildRole) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpActor o : list) {
				idList.add(o.getRoleId());
			}
			Map<Long, CmpActorRole> map = this.cmpActorService
					.getCmpActorRoleMapByCompanyIdAndInId(idList);
			for (CmpActor o : list) {
				o.setCmpActorRole(map.get(o.getRoleId()));
			}
		}
		return list;
	}

	public List<CmpActorPink> getCmpActorPinkList(boolean buildActor,
			boolean buildRole, int begin, int size) {
		List<CmpActorPink> list = this.cmpActorService.getCmpActorPinkList(
				begin, size);
		if (buildActor) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpActorPink o : list) {
				idList.add(o.getActorId());
			}
			Map<Long, CmpActor> map = this.cmpActorService
					.getCmpActorMapInId(idList);
			for (CmpActorPink o : list) {
				o.setCmpActor(map.get(o.getActorId()));
			}
		}
		if (buildRole) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpActorPink o : list) {
				if (o.getCmpActor() != null) {
					idList.add(o.getCmpActor().getRoleId());
				}
			}
			Map<Long, CmpActorRole> map = this.cmpActorService
					.getCmpActorRoleMapByCompanyIdAndInId(idList);
			for (CmpActorPink o : list) {
				if (o.getCmpActor() != null) {
					o.getCmpActor().setCmpActorRole(
							map.get(o.getCmpActor().getRoleId()));
				}
			}
		}
		return list;
	}

	public List<CmpActor> getCmpActorListForWorkCount(boolean buildRole,
			int begin, int size) {
		List<CmpActor> list = this.cmpActorService.getCmpActorListForWorkCount(
				begin, size);
		if (buildRole) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpActor o : list) {
				idList.add(o.getRoleId());
			}
			Map<Long, CmpActorRole> map = this.cmpActorService
					.getCmpActorRoleMapByCompanyIdAndInId(idList);
			for (CmpActor o : list) {
				o.setCmpActorRole(map.get(o.getRoleId()));
			}
		}
		return list;
	}
}