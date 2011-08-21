package tuxiazi.svr.iface;

import halo.util.image.ImageException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.HotPhoto;
import tuxiazi.bean.Lasted_photo;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoLikeUser;
import tuxiazi.bean.PhotoUserLike;
import tuxiazi.bean.UploadPhoto;
import tuxiazi.bean.User;
import tuxiazi.bean.User_photo;
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
	 * 获得图片
	 * 
	 * @param photoid
	 * @return
	 */
	Photo getPhoto(long photoid);

	/**
	 * 获得图片
	 * 
	 * @param photoid
	 * @param favUserid
	 * @param buildUser
	 * @return
	 */
	Photo getPhoto(long photoid, long favUserid, boolean buildUser);

	/**
	 * 根据图片id 获得图片集合
	 * 
	 * @param idList
	 * @return
	 */
	Map<Long, Photo> getPhotoMapInId(List<Long> idList);

	/**
	 * @param userid
	 * @param buildPhoto
	 * @param favUserid
	 *            查看该用户是否有收藏当前集合的图片
	 * @param begin
	 * @param size
	 * @return 2010-11-13
	 */
	List<User_photo> getUser_photoListByUserid(long userid, boolean buildPhoto,
			long favUserid, int begin, int size);

	/**
	 * 获取最新图片
	 * 
	 * @param buildPhoto
	 *            true:组装图片数据
	 * @param buildPhotoUser
	 *            true:组装图片的用户数据 * @param favUserid 查看该用户是否有收藏当前集合的图片
	 * @param begin
	 * @param size
	 * @return
	 */
	List<Lasted_photo> getLasted_photoList(boolean buildPhoto,
			boolean buildPhotoUser, long favUserid, int begin, int size);

	/**
	 * 用户喜欢的图片，对图片进行收藏
	 * 
	 * @param user
	 * @param photo
	 */
	void createPhotoUserLike(User user, Photo photo);

	/**
	 * 删除某用户喜欢的某图片数据
	 * 
	 * @param userid
	 * @param photo
	 */
	void deletePhotoUserLike(long userid, Photo photo);

	/**
	 * 计算热门图片
	 */
	void createHotPhotos();

	/**
	 * 获取最新热门图片
	 * 
	 * @param begin
	 * @param size
	 * @return
	 */
	List<HotPhoto> getHotPhotoList(int begin, int size);

	List<PhotoUserLike> getPhotoUserLikeListByUserid(long userid,
			boolean buildPhoto, int begin, int size);

	PhotoUserLike getPhotoUserLikeByUseridAndPhotoid(long userid, long photoid);

	/**
	 * 查看喜欢某图片的用户，并且检测是否关注过该用户
	 * 
	 * @param photoid
	 * @param buildUser
	 * @param refuserid
	 * @param begin
	 * @param size
	 * @return
	 */
	List<PhotoLikeUser> getPhotoLikeUserListByPhotoid(long photoid,
			boolean buildUser, long refuserid, int begin, int size);
}