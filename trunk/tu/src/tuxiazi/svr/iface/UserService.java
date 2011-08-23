package tuxiazi.svr.iface;

import java.util.List;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.SinaUser;
import tuxiazi.bean.SinaUserFromAPI;
import tuxiazi.bean.User;
import tuxiazi.svr.exception.UserAlreadyExistException;

public interface UserService {

	User createUserFromSina(SinaUserFromAPI sinaUserFromAPI, boolean sendJms)
			throws UserAlreadyExistException;

	void updateApi_user_sina(Api_user_sina apiUserSina);

	/**
	 * 更新用户对象
	 * 
	 * @param user
	 *            2010-11-6
	 */
	void update(User user);

	/**
	 * 获得用户在新浪微博的粉丝列表，如果好友在系统中已经注册，就会有系统的信息在数据中
	 * 
	 * @param apiUserSina
	 * @param begin
	 * @param size
	 * @return
	 */
	List<SinaUser> getSinaFansList(Api_user_sina apiUserSina, int page, int size);

	List<SinaUser> getSinaFriendList(Api_user_sina apiUserSina, int page,
			int size);
}