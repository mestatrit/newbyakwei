package com.hk.svr.processor;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOrgFile;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.CmpOrgFileService;
import com.hk.svr.pub.ImageConfig;

/**
 * 文章配图以及flash
 * 
 * @author akwei
 */
public class CmpOrgFileProcessor {

	@Autowired
	private CmpOrgFileService cmpOrgFileService;

	public void createCmpOrgFile(CmpOrgFile cmpOrgFile, File file)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException {
		String fileName = System.currentTimeMillis() + ""
				+ DataUtil.getRandom(2) + DataUtil.getRandom(2);
		String dbPath = ImageConfig.getCmpOrgFileDbPath(cmpOrgFile
				.getCompanyId(), cmpOrgFile.getOrgId(), fileName);
		String filePath = ImageConfig.getCmpOrgFileFilePath(dbPath);
		JMagickUtil util = new JMagickUtil(file, 2);
		util.makeImage(filePath, ImageConfig.IMG_H60, JMagickUtil.IMG_SQUARE,
				60);
		util.makeImage(filePath, ImageConfig.IMG_H120, JMagickUtil.IMG_SQUARE,
				120);
		util.setRui(1.0);
		util.makeImage(filePath, ImageConfig.IMG_H240, JMagickUtil.IMG_OBLONG,
				240);
		util.makeImage(filePath, ImageConfig.IMG_H320, JMagickUtil.IMG_OBLONG,
				320);
		util.makeImage(filePath, ImageConfig.IMG_H600, JMagickUtil.IMG_OBLONG,
				600);
		util.makeImage(filePath, ImageConfig.IMG_H800, JMagickUtil.IMG_OBLONG,
				800);
		cmpOrgFile.setPath(dbPath);
		this.cmpOrgFileService.createCmpOrgFile(cmpOrgFile);
	}

	public void deleteCmpOrgFile(long companyId, long oid) {
		CmpOrgFile cmpOrgFile = this.cmpOrgFileService.getCmpOrgFile(companyId,
				oid);
		if (cmpOrgFile != null) {
			// 删除文件
			File file = null;
			String path = ImageConfig.getCmpOrgFileFilePath(cmpOrgFile
					.getPath());
			file = new File(path + ImageConfig.IMG_H60);
			DataUtil.deleteFile(file);
			file = new File(path + ImageConfig.IMG_H120);
			DataUtil.deleteFile(file);
			file = new File(path + ImageConfig.IMG_H240);
			DataUtil.deleteFile(file);
			file = new File(path + ImageConfig.IMG_H320);
			DataUtil.deleteFile(file);
			file = new File(path + ImageConfig.IMG_H600);
			DataUtil.deleteFile(file);
			file = new File(path + ImageConfig.IMG_H800);
			DataUtil.deleteFile(file);
			// 删除数据
			this.cmpOrgFileService.deleteCmpOrgFile(companyId, oid);
		}
	}
}