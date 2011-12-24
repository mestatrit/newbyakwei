package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpInfo;
import com.hk.bean.CmpLanguageRef;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpInfoService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpInfoProcessor {

	@Autowired
	private CmpInfoService cmpInfoService;

	public List<CmpLanguageRef> getCmpLanguageRefListByCompanyId(
			long companyId, boolean buildRefCmpInfo) {
		List<CmpLanguageRef> list = this.cmpInfoService
				.getCmpLanguageRefListByCompanyId(companyId);
		if (buildRefCmpInfo) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpLanguageRef o : list) {
				idList.add(o.getRefCompanyId());
			}
			Map<Long, CmpInfo> map = this.cmpInfoService
					.getCmpInfoMapInId(idList);
			for (CmpLanguageRef o : list) {
				o.setRefCmpInfo(map.get(o.getRefCompanyId()));
			}
		}
		return list;
	}

	public int updateBgImage(long companyId, File file) {
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		if (file == null) {
			return Err.SUCCESS;
		}
		if (DataUtil.isBigger(file, 200)) {
			return Err.CMPINFO_BGIMG_ERROR;
		}
		String dbPath = ImageConfig.getCompanyHeadDbPath(companyId);
		String filePath = ImageConfig.getCompanyHeadUploadPath(dbPath);
		String fileName = "bg.jpg";
		try {
			DataUtil.copyFile(file, filePath, fileName);
		}
		catch (IOException e) {
			return Err.CMPINFO_BGIMG_UPLOAD_ERROR;
		}
		cmpInfo.setBgPicPath(dbPath);
		this.cmpInfoService.updateCmpInfo(cmpInfo);
		return Err.SUCCESS;
	}

	public void deleteBgImage(long companyId) {
		CmpInfo cmpInfo = this.cmpInfoService.getCmpInfo(companyId);
		if (!DataUtil.isEmpty(cmpInfo.getBgPicPath())) {
			String filePath = ImageConfig.getCmpNavFilePath(cmpInfo
					.getBgPicPath());
			File file = new File(filePath + "bg.jpg");
			DataUtil.deleteFile(file);
		}
		cmpInfo.setBgPicPath(null);
		this.cmpInfoService.updateCmpInfo(cmpInfo);
	}
}