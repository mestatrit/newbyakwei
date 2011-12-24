package com.hk.svr;

import java.io.File;
import java.util.List;

import com.hk.bean.Photo;
import com.hk.bean.PhotoCmt;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;

public interface PhotoService {

	/**
	 * 上传的图片文件不能超过2M
	 * 
	 * @param photo
	 * @param file
	 * @param singleFileLimitSize 单个文件最大容量限制 单位为M
	 * @throws ImageException
	 * @throws NotPermitImageFormatException
	 * @throws OutOfSizeException
	 *             2010-4-22
	 */
	void createPhoto(Photo photo, File file, double singleFileLimitSize)
			throws ImageException, NotPermitImageFormatException,
			OutOfSizeException;

	void deltePhoto(long photoId);

	List<Photo> getPhotoListByUserId(long userId, int begin, int size);

	Photo getPhoto(long photoId);

	List<Photo> getPhotoListInId(List<Long> idList);

	void updateName(long photoId, String name);

	void createPhotoCmt(PhotoCmt photoCmt);

	void updatePhotoCmt(PhotoCmt photoCmt);

	void deletePhotoCmt(long cmtId);

	List<PhotoCmt> getPhotoCmtListByPhotoId(long photoId, int begin, int size);

	PhotoCmt getPhotoCmt(long cmtId);
}