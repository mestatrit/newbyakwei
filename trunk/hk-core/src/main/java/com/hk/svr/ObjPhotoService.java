package com.hk.svr;

import java.io.File;
import java.util.List;

import com.hk.bean.ObjPhoto;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;

/**
 * 优惠券与宝箱奖品的图片
 * 
 * @author akwei
 */
public interface ObjPhotoService {

	void createObjPhoto(ObjPhoto objPhoto, File file) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException;

	List<ObjPhoto> getObjPhotoListByUserId(long userId, int begin, int size);

	List<ObjPhoto> getObjPhotoListByCompanyId(long companyId, int begin,
			int size);

	ObjPhoto getObjPhoto(long photoId);
}