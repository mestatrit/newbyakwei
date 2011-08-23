package tuxiazi.svr.iface;

import halo.util.image.ImageException;

import java.io.IOException;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.UploadPhoto;
import tuxiazi.bean.User;
import tuxiazi.svr.exception.ImageSizeOutOfLimitException;

public interface PhotoService {

	Photo createPhoto(UploadPhoto uploadPhoto, boolean withweibo, User user,
			Api_user_sina apiUserSina) throws ImageSizeOutOfLimitException,
			ImageException, IOException;

	/**
	 * 更新图片数据
	 * 
	 * @param photo
	 */
	void updatePhoto(Photo photo);

	/**
	 * 删除图片
	 * 
	 * @param photo
	 */
	void deletePhoto(Photo photo, User user);

	/**
	 * 用户喜欢的图片，对图片进行收藏
	 * 
	 * @param user
	 * @param photo
	 */
	PhotoUserLike createPhotoUserLike(User user, Photo photo);

	/**
	 * 删除某用户喜欢的某图片数据
	 * 
	 * @param user
	 * @param photo
	 */
	void deletePhotoUserLike(User user, Photo photo);

	/**
	 * 计算热门图片
	 */
	void createHotPhotos();
}