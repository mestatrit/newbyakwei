package tuxiazi.svr.iface;

import java.util.List;
import java.util.Map;

import tuxiazi.bean.Api_user;
import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.SinaUser;
import tuxiazi.bean.User;

public interface UserService {

	/**
	 * 通过新浪用户信息创建用户
	 * 
	 * @param apiUserSina
	 * @param nick 新浪昵称
	 * @param head_path 新浪头像
	 *            2010-11-7
	 */
	void createApi_user_sina(Api_user_sina apiUserSina, String nick,
			String head_path);

	/**
	 * 删除某个第三方网站的信息
	 * 
	 * @param apiUser
	 *            2010-11-7
	 */
	void deleteApi_user(Api_user apiUser);

	void updateApi_user_sina(Api_user_sina apiUserSina);

	/**
	 * 更新用户对象
	 * 
	 * @param user
	 *            2010-11-6
	 */
	void update(User user);

	/**
	 * 根据userid获得user
	 * 
	 * @param userid
	 * @return
	 *         2010-11-6
	 */
	User getUser(long userid);

	Api_user getApi_userByUseridAndApi_type(long userid, int api_type);

	Api_user_sina getApi_user_sinaBySina_userid(long sina_userid);

	Api_user_sina getApi_user_sinaByUserid(long userid);

	void addUserPic_numByUserid(long userid, int add);

	/**
	 * 获取已经注册的新浪好友
	 * 
	 * @param idList 新浪userid
	 * @return
	 *         2010-11-12
	 */
	Map<Long, Api_user_sina> getApi_user_sinaMapInSina_userid(
			List<Long> idList, boolean buildUser);

	/**
	 * @param idlist 新浪userid
	 * @param buildUser
	 * @return
	 *         2010-11-13
	 */
	List<Api_user_sina> getApi_user_sinaListInSina_userid(List<Long> idList,
			boolean buildUser);

	Map<Long, User> getUserMapInId(List<Long> idList);

	List<User> getUserListInId(List<Long> idList);

	void addFriend_num(long userid, int num);

	void addFans_num(long userid, int num);

	/**
	 * 获得用户在新浪微博的粉丝列表，如果好友在系统中已经注册，就会有系统的信息在数据中
	 * 
	 * @param apiUserSina
	 * @param begin
	 * @param size
	 * @return
	 */
	List<SinaUser> getSinaFansListBy(Api_user_sina apiUserSina, int page,
			int size);

	List<SinaUser> getSinaFriendListBy(Api_user_sina apiUserSina, int page,
			int size);
}