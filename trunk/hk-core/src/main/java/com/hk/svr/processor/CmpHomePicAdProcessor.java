package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpHomePicAd;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.CmpHomePicAdService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpHomePicAdProcessor {

	@Autowired
	private CmpHomePicAdService cmpHomePicAdService;

	/**
	 * 创建首页焦点图数据
	 * 
	 * @param cmpHomePicAd 图片广告数据
	 * @param file 上传的图片
	 * @return
	 *         返回{@link Err#MUST_PIC_UPLOAD} 返回{@link Err#SUCCESS}为成功
	 * @throws IOException 图片上出错
	 * @throws ImageException 图片上出错
	 * @throws NotPermitImageFormatException 不是有效的图片类型
	 * @throws OutOfSizeException 超出文件限制大小
	 *             2010-6-13
	 */
	public int createCmpHomePicAd(CmpHomePicAd cmpHomePicAd, File file)
			throws IOException {
		if (file == null) {
			return Err.MUST_PIC_UPLOAD;
		}
		if (!DataUtil.isImage(file)) {
			return Err.IMG_FMT_ERROR;
		}
		if (DataUtil.isBigger(file, 100)) {
			return Err.IMG_OUTOFSIZE_ERROR;
		}
		String picname = String.valueOf(System.nanoTime());
		String dbPath = ImageConfig.getCmpHomePicAdSaveToDBPath(cmpHomePicAd
				.getCompanyId(), picname);
		DataUtil.copyFile(file, ImageConfig.getCmpHomePicAdFilePath(dbPath),
				"h.jpg");
		cmpHomePicAd.setPath(dbPath);
		this.cmpHomePicAdService.createCmpHomePicAd(cmpHomePicAd);
		return Err.SUCCESS;
	}

	public int updateCmpHomePicAd(CmpHomePicAd cmpHomePicAd, File file)
			throws IOException {
		if (file != null) {
			if (!DataUtil.isImage(file)) {
				return Err.IMG_FMT_ERROR;
			}
			if (!DataUtil.isBigger(file, 100)) {
				return Err.IMG_OUTOFSIZE_ERROR;
			}
			String dbPath = cmpHomePicAd.getPath();
			DataUtil.copyFile(file,
					ImageConfig.getCmpHomePicAdFilePath(dbPath), "h.jpg");
			cmpHomePicAd.setPath(dbPath);
		}
		this.cmpHomePicAdService.updateCmpHomePicAd(cmpHomePicAd);
		return Err.SUCCESS;
	}

	public void deleteCmpHomePicAd(long adid) {
		CmpHomePicAd cmpHomePicAd = this.cmpHomePicAdService
				.getCmpHomePicAd(adid);
		if (cmpHomePicAd == null) {
			return;
		}
		this.cmpHomePicAdService.deleteCmpHomePicAd(adid);
		File file = new File(ImageConfig.getCmpHomePicAdFilePath(cmpHomePicAd
				.getPath())
				+ "h.jpg");
		DataUtil.deleteFile(file);
	}
}