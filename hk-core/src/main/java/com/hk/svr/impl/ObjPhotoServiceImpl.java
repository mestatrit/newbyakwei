package com.hk.svr.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.ObjPhoto;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.ObjPhotoService;
import com.hk.svr.pub.ImageConfig;

public class ObjPhotoServiceImpl implements ObjPhotoService {

	@Autowired
	private QueryManager manager;

	public void createObjPhoto(ObjPhoto objPhoto, File file)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException {
		String photoName = System.currentTimeMillis() + ""
				+ DataUtil.getRandom(2) + DataUtil.getRandom(2);
		String dbPath = ImageConfig.getBoxPrizePicSaveToDBPath(objPhoto
				.getUserId()
				+ "", photoName);
		String filePath = ImageConfig.getBoxPrizePicUploadPath(dbPath);
		objPhoto.setPath(dbPath);
		try {
			if (!DataUtil.isImage(file)) {
				throw new ImageException("no img file");
			}
		}
		catch (IOException e) {
			throw new ImageException("no img file");
		}
		JMagickUtil util = new JMagickUtil(file, 2);
		util.makeImage(filePath, "h_0.jpg", JMagickUtil.IMG_SQUARE, 60);
		util.makeImage(filePath, "h_1.jpg", JMagickUtil.IMG_OBLONG, 240);
		util.makeImage(filePath, "h_2.jpg", JMagickUtil.IMG_OBLONG, 320);
		Query query = manager.createQuery();
		long id = query.insertObject(objPhoto).longValue();
		objPhoto.setPhotoId(id);
	}

	public List<ObjPhoto> getObjPhotoListByCompanyId(long companyId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(ObjPhoto.class, "companyid=?",
				new Object[] { companyId }, "photoid desc", begin, size);
	}

	public List<ObjPhoto> getObjPhotoListByUserId(long userId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(ObjPhoto.class, "userid=?",
				new Object[] { userId }, "photoid desc", begin, size);
	}

	public ObjPhoto getObjPhoto(long photoId) {
		Query query = manager.createQuery();
		return query.getObjectById(ObjPhoto.class, photoId);
	}
}