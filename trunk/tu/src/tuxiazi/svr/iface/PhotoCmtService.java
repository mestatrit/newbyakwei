package tuxiazi.svr.iface;

import java.util.List;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.User;

public interface PhotoCmtService {

	/**
	 * 创建图片评论
	 * 
	 * @param photoCmt
	 * @param user
	 */
	void createPhotoCmt(Photo photo, PhotoCmt photoCmt, User user);

	void createPhotoCmt(Photo photo, PhotoCmt photoCmt, User user, int weibo,
			Api_user_sina apiUserSina);

	/**
	 * 删除图片评论
	 * 
	 * @param photoCmt
	 *            2010-11-27
	 */
	void deletePhotoCmt(PhotoCmt photoCmt);

	/**
	 * 删除某图片的所有评论
	 * 
	 * @param photoid
	 *            2010-11-27
	 */
	void deletePhotoCmtByPhotoid(long photoid);

	/**
	 * 获得图片评论对象
	 * 
	 * @param photoid
	 * @param cmtid
	 * @return
	 *         2010-11-27
	 */
	PhotoCmt getPhotoCmt(long photoid, long cmtid);

	/**
	 * 获得某图片的评论集合
	 * 
	 * @param photoid
	 * @param buildUser 是否构造user对象
	 * @param begin
	 * @param size
	 * @return
	 *         2010-11-27
	 */
	List<PhotoCmt> getPhotoCmtListByPhotoid(long photoid, boolean buildUser,
			int begin, int size);
}