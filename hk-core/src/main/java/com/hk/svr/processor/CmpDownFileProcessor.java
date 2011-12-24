package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpDownFile;
import com.hk.bean.CmpOtherWebInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;
import com.hk.svr.CmpDownFileService;
import com.hk.svr.CmpOtherWebInfoService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpDownFileProcessor {

	@Autowired
	private CmpDownFileService cmpDownFileService;

	@Autowired
	private CmpOtherWebInfoService cmpOtherWebInfoService;

	/**
	 * 文件上传，文件大小不能超过1M，必须有文件
	 * 
	 * @param cmpDownFile
	 * @param file
	 * @return {@link Err#SUCCESS} 数据创建成功 {@link Err#UPLOAD_FILE_EMPTY} 文件为空
	 *         {@link Err#UPLOAD_FILE_SIZE_LIMIT} 文件超过指定大小
	 *         {@link Err#CMPOTHERWEBINFO_NO_FILESIZE} 文件空间不足
	 * @throws IOException 上传失败
	 *             2010-6-22
	 */
	public int createCmpDownFile(CmpDownFile cmpDownFile, File file)
			throws IOException {
		if (file == null) {
			return Err.UPLOAD_FILE_EMPTY;
		}
		if (DataUtil.isBigger(file, 1024)) {
			return Err.UPLOAD_FILE_SIZE_LIMIT;
		}
		long fileSize = DataUtil.getFileSize(file, DataUtil.FILE_SIZE_TYPE_K);
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(cmpDownFile.getCompanyId());
		// 如果没有进行空间设置
		if (cmpOtherWebInfo == null) {
			return Err.CMPOTHERWEBINFO_NO_FILESIZE;
		}
		// 计算剩余空间
		long remain = cmpOtherWebInfo.getRemainFileSize();
		// 剩余空间不够用
		if (fileSize > remain) {
			return Err.CMPOTHERWEBINFO_NO_FILESIZE;
		}
		cmpOtherWebInfo.addFileSize(fileSize);
		String fileName = System.currentTimeMillis() + DataUtil.getRandom(4);
		String dbPath = ImageConfig.getCmpDownFileSaveToDBPath(cmpDownFile
				.getCompanyId(), fileName);
		String filePath = ImageConfig.getCmpDownFileFilePath(dbPath);
		DataUtil.copyFile(file, filePath, ImageConfig.RARFILE);
		cmpDownFile.setPath(dbPath);
		cmpDownFile.setFileSize(fileSize);
		this.cmpDownFileService.createCmpDownFile(cmpDownFile);
		this.cmpOtherWebInfoService.updateCmpOtherWebInfo(cmpOtherWebInfo);
		return Err.SUCCESS;
	}

	/**
	 * @param cmpDownFile
	 * @param file
	 * @throws IOException
	 * @return {@link Err#SUCCESS} 数据更新成功, {@link Err#UPLOAD_FILE_SIZE_LIMIT}
	 *         文件超过指定大小, {@link Err#CMPOTHERWEBINFO_NO_FILESIZE} 文件空间不足
	 *         2010-6-23
	 */
	public int updateCmpDownFile(CmpDownFile cmpDownFile, File file)
			throws IOException {
		if (file != null) {
			CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
					.getCmpOtherWebInfo(cmpDownFile.getCompanyId());
			if (cmpOtherWebInfo == null) {
				return Err.CMPOTHERWEBINFO_NO_FILESIZE;
			}
			if (DataUtil.isBigger(file, 1024)) {
				return Err.UPLOAD_FILE_SIZE_LIMIT;
			}
			String path = ImageConfig.getCmpDownFileFilePath(cmpDownFile
					.getPath());
			// 减去旧文件的大小然后在计算剩余文件大小
			cmpOtherWebInfo.addFileSize(-cmpDownFile.getFileSize());
			long remain = cmpOtherWebInfo.getRemainFileSize();
			long fileSize = DataUtil.getFileSize(file,
					DataUtil.FILE_SIZE_TYPE_K);
			if (fileSize > remain) {
				return Err.CMPOTHERWEBINFO_NO_FILESIZE;
			}
			// 先删除原有文件
			DataUtil.deleteFile(new File(path + ImageConfig.RARFILE));
			String fileName = System.currentTimeMillis()
					+ DataUtil.getRandom(4);
			String dbPath = ImageConfig.getCmpDownFileSaveToDBPath(cmpDownFile
					.getCompanyId(), fileName);
			String filePath = ImageConfig.getCmpDownFileFilePath(dbPath);
			DataUtil.copyFile(file, filePath, ImageConfig.RARFILE);
			cmpDownFile.setPath(dbPath);
			cmpDownFile.setFileSize(fileSize);
			cmpOtherWebInfo.addFileSize(fileSize);
			this.cmpOtherWebInfoService.updateCmpOtherWebInfo(cmpOtherWebInfo);
		}
		this.cmpDownFileService.updateCmpDownFile(cmpDownFile);
		return Err.SUCCESS;
	}

	public void deleteCmpDownFile(long oid) {
		CmpDownFile cmpDownFile = this.cmpDownFileService.getCmpDownFile(oid);
		if (cmpDownFile == null) {
			return;
		}
		CmpOtherWebInfo cmpOtherWebInfo = this.cmpOtherWebInfoService
				.getCmpOtherWebInfo(cmpDownFile.getCompanyId());
		String path = ImageConfig.getCmpDownFileFilePath(cmpDownFile.getPath());
		DataUtil.deleteFile(new File(path + ImageConfig.RARFILE));
		this.cmpDownFileService.deleteCmpDownFile(oid);
		if (cmpOtherWebInfo != null) {
			cmpOtherWebInfo.addFileSize(-cmpDownFile.getFileSize());
			this.cmpOtherWebInfoService.updateCmpOtherWebInfo(cmpOtherWebInfo);
		}
	}

	public static void main(String[] args) {
		File file = new File("d:/aabb");
		P.println(file.length());
	}
}