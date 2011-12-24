package com.hk.svr.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.Photo;
import com.hk.bean.PhotoCmt;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.PhotoService;
import com.hk.svr.pub.ImageConfig;

public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private QueryManager manager;

	public void createPhoto(Photo photo, File file, double singleFileLimitSize)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException {
		String photoName = System.currentTimeMillis() + ""
				+ DataUtil.getRandom(2) + DataUtil.getRandom(2);
		String dbPath = ImageConfig.getPicSaveToDBPath(photo.getUserId() + "",
				photoName);
		String filePath = ImageConfig.getPicUploadPath(dbPath);
		photo.setPath(dbPath);
		photo.setCreateTime(new Date());
		try {
			if (!DataUtil.isImage(file)) {
				throw new ImageException("not img file");
			}
		}
		catch (IOException e) {
			throw new ImageException("not img file");
		}
		JMagickUtil util = new JMagickUtil(file, singleFileLimitSize);
		util.makeImage(filePath, ImageConfig.IMG_H60, JMagickUtil.IMG_SQUARE,
				60);
		util.makeImage(filePath, ImageConfig.IMG_H120, JMagickUtil.IMG_OBLONG,
				120);
		util.makeImage(filePath, ImageConfig.IMG_H240, JMagickUtil.IMG_OBLONG,
				240);
		util.makeImage(filePath, ImageConfig.IMG_H320, JMagickUtil.IMG_OBLONG,
				320);
		// 文件名称为640.jpg,实际大小为600
		util.makeImage(filePath, ImageConfig.IMG_H640, JMagickUtil.IMG_OBLONG,
				600);
		util.makeImage(filePath, ImageConfig.IMG_H800, JMagickUtil.IMG_OBLONG,
				800);
		Query query = manager.createQuery();
		long id = query.insertObject(photo).longValue();
		photo.setPhotoId(id);
	}

	public void deltePhoto(long photoId) {
		Query query = manager.createQuery();
		query.deleteById(Photo.class, photoId);
	}

	public Photo getPhoto(long photoId) {
		Query query = manager.createQuery();
		return query.getObjectById(Photo.class, photoId);
	}

	public List<Photo> getPhotoListByUserId(long userId, int begin, int size) {
		Query query = manager.createQuery();
		query.setTable(Photo.class);
		query.where("userid=?").setParam(userId);
		query.orderByDesc("photoid");
		return query.list(begin, size, Photo.class);
	}

	public List<Photo> getPhotoListInId(List<Long> idList) {
		StringBuilder sql = new StringBuilder(
				"select * from photo where photoid in (");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1).append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), Photo.class);
	}

	public void updateName(long photoId, String name) {
		Query query = manager.createQuery();
		query.addField("name", name);
		query.updateById(Photo.class, photoId);
	}

	public void createPhotoCmt(PhotoCmt photoCmt) {
		if (photoCmt.getCreateTime() == null) {
			photoCmt.setCreateTime(new Date());
		}
		Query query = manager.createQuery();
		long id = query.insertObject(photoCmt).longValue();
		photoCmt.setCmtId(id);
	}

	public void deletePhotoCmt(long cmtId) {
		Query query = manager.createQuery();
		query.deleteById(PhotoCmt.class, cmtId);
	}

	public List<PhotoCmt> getPhotoCmtListByPhotoId(long photoId, int begin,
			int size) {
		Query query = manager.createQuery();
		return query.listEx(PhotoCmt.class, "photoid=?",
				new Object[] { photoId }, "cmtid desc", begin, size);
	}

	public void updatePhotoCmt(PhotoCmt photoCmt) {
		Query query = manager.createQuery();
		query.updateObject(photoCmt);
	}

	public PhotoCmt getPhotoCmt(long cmtId) {
		Query query = manager.createQuery();
		return query.getObjectById(PhotoCmt.class, cmtId);
	}
}