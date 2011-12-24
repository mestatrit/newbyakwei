package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpOtherWebInfo;
import com.hk.bean.CmpVideo;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpOtherWebInfoService;
import com.hk.svr.CmpVideoService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpVideoProcessor {

	@Autowired
	private CmpVideoService cmpVideoService;

	@Autowired
	private CmpOtherWebInfoService cmpOtherWebInfoService;

	/**
	 *上传视频文件，视频文件支持flv
	 * 
	 * @param cmpVideo
	 * @param file
	 * @return {@link Err#SUCCESS} 数据创建成功, {@link Err#UPLOAD_FILE_EMPTY} 文件为空,
	 *         {@link Err#UPLOAD_FILE_SIZE_LIMIT} 文件超过指定大小,
	 *         {@link Err#CMPOTHERWEBINFO_NO_FILESIZE} 文件空间不足
	 * @throws IOException
	 *             2010-6-23
	 */
	public int createCmpVideo(CmpVideo cmpVideo, File file) throws IOException {
		if (file == null && DataUtil.isEmpty(cmpVideo.getHtml())) {
			return Err.CMPVIDEO_FILE_OR_HTML_EMPTY;
		}
		if (DataUtil.isEmpty(cmpVideo.getHtml())) {
			if (file != null && DataUtil.isBigger(file, 50 * 1024)) {
				return Err.UPLOAD_FILE_SIZE_LIMIT;
			}
			CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
					.getCmpOtherWebInfo(cmpVideo.getCompanyId());
			if (cmpOtherWebInfo == null) {
				return Err.CMPOTHERWEBINFO_NO_FILESIZE;
			}
			long fileSize = DataUtil.getFileSize(file,
					DataUtil.FILE_SIZE_TYPE_K);
			if (fileSize > cmpOtherWebInfo.getRemainFileSize()) {
				return Err.CMPOTHERWEBINFO_NO_FILESIZE;
			}
			String fileName = System.currentTimeMillis()
					+ DataUtil.getRandom(4);
			String dbPath = ImageConfig.getCmpVideoSaveToDBPath(cmpVideo
					.getCompanyId(), fileName);
			String filePath = ImageConfig.getCmpVideoFilePath(dbPath);
			DataUtil.copyFile(file, filePath, ImageConfig.FLASHFILE);
			cmpVideo.setPath(dbPath);
			cmpVideo.setFileSize(fileSize);
			cmpOtherWebInfo.addFileSize(fileSize);
			this.cmpOtherWebInfoService.updateCmpOtherWebInfo(cmpOtherWebInfo);
		}
		this.cmpVideoService.createCmpVideo(cmpVideo);
		return Err.SUCCESS;
	}

	/**
	 * @param cmpVideo
	 * @param file
	 * @return {@link Err#SUCCESS} 数据创建成功, {@link Err#UPLOAD_FILE_EMPTY} 文件为空,
	 *         {@link Err#UPLOAD_FILE_SIZE_LIMIT} 文件超过指定大小,
	 *         {@link Err#CMPOTHERWEBINFO_NO_FILESIZE} 文件空间不足
	 * @throws IOException
	 *             2010-6-23
	 */
	public int updateCmpVideo(CmpVideo cmpVideo, File file) throws IOException {
		if (file != null) {
			if (DataUtil.isEmpty(cmpVideo.getHtml())) {
				CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
						.getCmpOtherWebInfo(cmpVideo.getCompanyId());
				if (cmpOtherWebInfo == null) {
					return Err.CMPOTHERWEBINFO_NO_FILESIZE;
				}
				if (DataUtil.isBigger(file, 50 * 1024)) {
					return Err.UPLOAD_FILE_SIZE_LIMIT;
				}
				cmpOtherWebInfo.addFileSize(-cmpVideo.getFileSize());
				long fileSize = DataUtil.getFileSize(file,
						DataUtil.FILE_SIZE_TYPE_K);
				if (fileSize > cmpOtherWebInfo.getRemainFileSize()) {
					return Err.CMPOTHERWEBINFO_NO_FILESIZE;
				}
				// 先删除原有文件
				String path = ImageConfig.getCmpVideoFilePath(cmpVideo
						.getPath());
				DataUtil.deleteFile(new File(path + ImageConfig.FLASHFILE));
				String fileName = System.currentTimeMillis()
						+ DataUtil.getRandom(4);
				String dbPath = ImageConfig.getCmpVideoSaveToDBPath(cmpVideo
						.getCompanyId(), fileName);
				String filePath = ImageConfig.getCmpVideoFilePath(dbPath);
				DataUtil.copyFile(file, filePath, ImageConfig.FLASHFILE);
				cmpVideo.setPath(dbPath);
				cmpVideo.setFileSize(fileSize);
				cmpOtherWebInfo.addFileSize(fileSize);
				this.cmpOtherWebInfoService
						.updateCmpOtherWebInfo(cmpOtherWebInfo);
			}
		}
		this.cmpVideoService.updateCmpVideo(cmpVideo);
		return Err.SUCCESS;
	}

	public void deleteCmpVideo(long oid) {
		CmpVideo cmpVideo = this.cmpVideoService.getCmpVideo(oid);
		if (cmpVideo == null) {
			return;
		}
		String path = ImageConfig.getCmpVideoFilePath(cmpVideo.getPath());
		DataUtil.deleteFile(new File(path + ImageConfig.FLASHFILE));
		this.cmpVideoService.deleteCmpVideo(oid);
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(cmpVideo.getCompanyId());
		if (cmpOtherWebInfo != null) {
			cmpOtherWebInfo.addFileSize(-cmpVideo.getFileSize());
			this.cmpOtherWebInfoService.updateCmpOtherWebInfo(cmpOtherWebInfo);
		}
	}
}