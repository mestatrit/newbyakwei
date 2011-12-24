package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpFile;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.CmpFileService;
import com.hk.svr.pub.ImageConfig;

/**
 * 文章配图以及flash
 * 
 * @author akwei
 */
public class CmpFileProcessor {

	@Autowired
	private CmpFileService cmpFileService;

	public void createCmpFile(CmpFile cmpFile, File file)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException, IOException {
		String fileName = System.currentTimeMillis() + ""
				+ DataUtil.getRandom(2) + DataUtil.getRandom(2);
		String dbPath = ImageConfig.getCmpFileDbPath(cmpFile.getCompanyId()
				+ "", fileName);
		String filePath = ImageConfig.getCmpFileFilePath(dbPath);
		if (cmpFile.getFileflg() == CmpFile.FILEFLG_IMG) {
			JMagickUtil util = new JMagickUtil(file, 2);
			util.makeImage(filePath, ImageConfig.IMG_H60,
					JMagickUtil.IMG_SQUARE, 60);
			util.makeImage(filePath, ImageConfig.IMG_H120,
					JMagickUtil.IMG_SQUARE, 120);
			util.setRui(1.0);
			util.makeImage(filePath, ImageConfig.IMG_H240,
					JMagickUtil.IMG_OBLONG, 240);
			util.makeImage(filePath, ImageConfig.IMG_H320,
					JMagickUtil.IMG_OBLONG, 320);
			util.makeImage(filePath, ImageConfig.IMG_H600,
					JMagickUtil.IMG_OBLONG, 600);
			util.makeImage(filePath, ImageConfig.IMG_H800,
					JMagickUtil.IMG_OBLONG, 800);
		}
		else {
			DataUtil.copyFile(file, filePath, "h.swf");
		}
		cmpFile.setPath(dbPath);
		this.cmpFileService.createCmpFile(cmpFile);
	}

	public void deleteCmpFile(long oid) {
		CmpFile cmpFile = this.cmpFileService.getCmpFile(oid);
		if (cmpFile != null) {
			// 删除文件
			File file = null;
			String path = ImageConfig.getCmpFileFilePath(cmpFile.getPath());
			if (cmpFile.getFileflg() == CmpFile.FILEFLG_IMG) {
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
			}
			else {
				file = new File(path + "h.swf");
				DataUtil.deleteFile(file);
			}
			// 删除数据
			this.cmpFileService.deleteCmpFile(oid);
		}
	}
}