package com.hk.svr;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.hk.bean.AdminHkb;
import com.hk.bean.AdminUser;
import com.hk.bean.DefFollowUser;
import com.hk.bean.HkbLog;
import com.hk.bean.IpCityRangeUser;
import com.hk.bean.IpCityUser;
import com.hk.bean.IpUser;
import com.hk.bean.ProUser;
import com.hk.bean.Randnum;
import com.hk.bean.RegCode;
import com.hk.bean.ScoreLog;
import com.hk.bean.User;
import com.hk.bean.UserBindInfo;
import com.hk.bean.UserFgtMail;
import com.hk.bean.UserNoticeInfo;
import com.hk.bean.UserOtherInfo;
import com.hk.bean.UserProtect;
import com.hk.bean.UserRecentUpdate;
import com.hk.bean.UserTool;
import com.hk.bean.UserUpdate;
import com.hk.bean.UserWebBind;
import com.hk.bean.WelProUser;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.pub.DataSort;
import com.hk.svr.user.exception.CreateRandnumException;
import com.hk.svr.user.exception.EmailDuplicateException;
import com.hk.svr.user.exception.LoginException;
import com.hk.svr.user.exception.MobileDuplicateException;
import com.hk.svr.user.exception.MsnDuplicateException;
import com.hk.svr.user.exception.SendOutOfLimitException;
import com.hk.svr.user.exception.UserNotExistException;

public interface UserService {

	/**
	 * 注册用户,email唯一,昵称唯一(考虑昵称保护),加积分和荣誉 手机号码和email任意选填
	 * 
	 * @param input
	 * @param password
	 * @return
	 * @throws EmailDuplicateException
	 */
	long createUser(String input, String password, String ip)
			throws EmailDuplicateException, MobileDuplicateException;

	long createUserWithRegCode(String input, String password, String ip,
			RegCode regCode) throws EmailDuplicateException,
			MobileDuplicateException;

	long createUser(String email, String mobile, String password, String ip,
			RegCode regCode) throws EmailDuplicateException,
			MobileDuplicateException;

	void processRegCodeUser(RegCode regCode, long userId);

	/**
	 * 更改昵称，如果所写昵称已经被保护，则抛出异常,判断是否是第一次修改,如果是第一次修改,就加积分和荣誉
	 * 
	 * @param userId
	 * @param nickName
	 * @return false 重名修改失败,true 修改成功
	 */
	boolean updateNickName(long userId, String nickName);

	/**
	 * 增加或者减少积分
	 * 
	 * @param scoreLog
	 */
	void addScore(ScoreLog scoreLog);

	/**
	 * 增加积分和荣誉
	 * 
	 * @param userId
	 * @param scoreAdd
	 * @param honorAdd
	 * @throws UserNotExistException
	 */
	void addScoreAndHonor(long userId, int scoreAdd, int honorAdd);

	/**
	 * 用户更改头像,判断是否是第一次修改,如果是第一次修改,就加积分和荣誉
	 * 
	 * @param userId
	 * @param headFile
	 */
	void updateHead(long userId, File headFile) throws ImageException,
			NotPermitImageFormatException, OutOfSizeException;

	void updateHeadWithCut(long userId, File headFile, int x1, int y1, int x2,
			int y2) throws ImageException, NotPermitImageFormatException,
			OutOfSizeException;

	/**
	 * 更新性别,判断是否是第一次修改,如果是第一次修改,就加积分和荣誉
	 * 
	 * @param userId
	 * @param sex
	 */
	void updateSex(long userId, byte sex);

	/**
	 * 修改个人介绍,判断是否是第一次修改,如果是第一次修改,就加积分和荣誉
	 * 
	 * @param userId
	 * @param intro
	 * @throws UserNotExistException
	 */
	void updateIntro(long userId, String intro);

	/**
	 * 获得一个user实体，可以从缓存中获得，也可以从数据库中获得
	 * 
	 * @param userId
	 * @return
	 */
	User getUser(long userId);

	/**
	 * 根据昵称获得一个user实体，可以从缓存中获得，也可以从数据库中获得
	 * 
	 * @param nickName
	 * @return
	 */
	User getUserByNickName(String nickName);

	/**
	 * 获得用户详细信息
	 * 
	 * @param userId
	 * @return
	 */
	UserOtherInfo getUserOtherInfo(long userId);

	/**
	 * email登录
	 * 
	 * @param email
	 * @param password
	 * @return 用户id
	 * @throws LoginException
	 */
	long loginByEmail(String email, String password, String ip)
			throws LoginException;

	/**
	 * 手机号登录
	 * 
	 * @param mobile
	 * @param password
	 * @return 用户id
	 * @throws LoginException
	 */
	long loginByMobile(String mobile, String password, String ip)
			throws LoginException;

	long loginByNickName(String nickName, String password, String ip)
			throws LoginException;

	List<User> getUserList(int begin, int size);

	List<User> getUserListByUserStatus(int begin, int size);

	List<User> getUserListExceptUserId(long userId, int begin, int size);

	void updateEmail(long userId, String email) throws EmailDuplicateException;

	void updateValidateEmail(long userId, byte validateEmail);

	/**
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @return true 修改成功 ,false:新旧密码不符，修改失败
	 */
	boolean updatePwd(long userId, String oldPwd, String newPwd);

	void updateNewPwd(long userId, String pwd);

	UserProtect getUserProtect(long userId);

	void updateUserProtect(long userId, int pconfig, String pvalue);

	UserOtherInfo getUserOtherInfoByeEmail(String email);

	String createDedValueForFgtPwd(long userId) throws SendOutOfLimitException;

	UserFgtMail getUserFgtMailByDesValue(String desValue);

	void removeUsrFgtMail(long userId);

	int countUser();

	List<IpUser> getIpUserList(String ip, int begin, int size);

	List<IpCityUser> getIpCityUserList(int cityId, int begin, int size);

	List<IpCityRangeUser> getIpCityRangeUserList(int rangeId, int begin,
			int size);

	UserOtherInfo getUserOtherInfoByMobile(String mobile);

	void updateMobile(long userId, String mobile)
			throws MobileDuplicateException;

	void bindMobile(long userId, String mobile);

	AdminUser getAdminUser(long userId);

	// void addHkb(long userId, int hkb);
	void addHkb(HkbLog hkbLog);

	void updateHkb(long userId, int hkb);

	/**
	 * 有效用户,关注度排序
	 */
	List<User> getUserListSortFriend(int begin, int size);

	List<IpCityRangeUser> getIpCityRangeUserListSortFriend(int rangeId,
			int begin, int size);

	List<IpCityUser> getIpCityUserListSortFriend(int cityId, int begin, int size);

	List<IpUser> getIpUserListSortFriend(String ip, int begin, int size);

	/**
	 * 为绑定手机好所使用的随机数字
	 * 
	 * @param userId
	 * @return
	 * @throws CreateRandnumException
	 */
	Randnum createUserRandnum(long userId) throws CreateRandnumException;

	Randnum getUserRandnum(long userId);

	void clearTimeoutRandnum();

	void clearTimeoutRandnum(int sysId);

	Randnum getRandnumByRandvalue(int randvalue);

	void cancelMobiebind(long userId);

	List<User> getAllUserList();

	void indexUser();

	List<User> getUserListForSearch(String key, int begin, int size);

	User getUserByDomain(String domain);

	void setUserStop(long userId);

	void setUserNormal(long userId);

	void updateUserOtherInfo(UserOtherInfo info);

	void updateUserBindInfo(UserBindInfo userBindInfo)
			throws MsnDuplicateException;

	UserBindInfo getUserBindInfo(long userId);

	UserBindInfo getUserBindInfoByMsn(String msn);

	UserNoticeInfo getUserNoticeInfo(long userId);

	void updateUserNoticeInfo(UserNoticeInfo userNoticeInfo);

	void updateFirstAddInfo(long userId, String firstAddInfo);

	void updateBirthday(long userId, int month, int date);

	/**
	 * 删除其他账号,把其他账号nickname置为userid,userStatu置为无效
	 * 
	 * @param input 可以是昵称,E-mail,手机号
	 * @param password
	 */
	User abolishUser(long ownnerId, String input, String password);

	List<User> getUserListInId(List<Long> idList, DataSort sort, int begin,
			int size);

	List<User> getUserListInId(List<Long> idList);

	List<User> getUserListInId(List<Long> idList, boolean id_desc);

	List<UserRecentUpdate> getUserRecentUpdateListInUserId(List<Long> idList,
			DataSort sort, int begin, int size);

	void updateUserRecentUpdate(UserRecentUpdate userRecentUpdate);

	UserRecentUpdate getUserRecentUpdate(long userId);

	void createUserRecentUpdate(UserRecentUpdate userRecentUpdate);

	void createUserWebBind(UserWebBind userWebBind);

	void updateUserWebBind(UserWebBind userWebBind);

	List<User> getUserListSortUserRecentUpdate(int begin, int size);

	List<IpCityRangeUser> getIpCityRangeUserListSortUserRecentUpdate(
			int rangeId, int begin, int size);

	List<IpCityUser> getIpCityUserListSortUserRecentUpdate(int cityId,
			int begin, int size);

	List<IpUser> getIpUserListSortUserRecentUpdate(String ip, int begin,
			int size);

	boolean equalPwd(long userId, String pwd);

	// void updateUser(User user);
	void updateCityId(long userId, int cityId);

	void createDefFollowUser(long userId);

	void delteDefFollowUser(long userId);

	List<DefFollowUser> getDefFollowUserList(int begin, int size);

	DefFollowUser getDefFollowUser(long userId);

	void createUserTool(UserTool userTool);

	void updateuserTool(UserTool userTool);

	UserTool getUserTool(long userId);

	/**
	 * 如果用户没有使用过，就创建一条数据，并初始化
	 * 
	 * @param userId
	 * @return
	 */
	UserTool checkUserTool(long userId);

	Map<Long, User> getUserMapInId(List<Long> idList);

	Map<Long, User> getUserMapInId(String idStr);

	boolean hasEnoughHkb(long userId, int add);

	void createHkbLog(HkbLog hkbLog);

	List<HkbLog> getHkbLogList(long userId, int begin, int size);

	List<ScoreLog> getScoreLogList(long userId, int begin, int size);

	List<AdminHkb> getAdminHkbList(long userId, int begin, int size);

	void createAdminHkb(AdminHkb adminHkb);

	// List<UserOtherInfo> tmpgetMobileBindUserOtherInfoList();
	boolean isMobileAlreadyBind(long userId);

	void updateUserContactDegree(long userId, long contactUserId);

	List<UserOtherInfo> getUserOtherInfoListInId(List<Long> idList);

	Map<Long, UserOtherInfo> getUserOtherInfoMapInId(List<Long> idList);

	void createRegfromUser(long userId, int regfrom, long fromId);

	List<UserTool> getUserTooList(int begin, int size);

	List<UserTool> getUserToolListInId(List<Long> idList);

	/**
	 * 根据userId判断,在userId>0的情况下如果已经为用户创建过红地毯，就不能再次创建
	 * 
	 * @param proUser
	 * @return
	 */
	boolean createProUser(ProUser proUser);

	boolean updateProUser(ProUser proUser);

	void deleteProUser(long oid);

	List<ProUser> getProUserList(String nickName, int begin, int size);

	ProUser getProUser(long oid);

	ProUser getProUserByUserID(long userId);

	boolean createWelProUser(long userId, long prouserId);

	List<ProUser> getProUserListByOid(long oid, int begin, int size);

	List<WelProUser> getWelProuserListByProuserId(long prouserId, int begin,
			int size);

	List<User> getUserListInIdForBirthday(List<Long> idList, int begin, int size);

	List<User> getUserListForBirthday(int begin, int size);

	void updateUserStatus(long userId, byte userStatus);

	/**
	 * 更新用户地区数据
	 * 
	 * @param userId 用户id
	 * @param pcityId 地区id
	 */
	void updateUserPcityId(long userId, int pcityId);

	/**
	 * 增加用户点数
	 * 
	 * @param userId 用户id
	 * @param add 要增加的数量
	 */
	void addPoints(long userId, int add);

	List<UserUpdate> getUserUpdateListInIdList(List<Long> idList, int begin,
			int size);

	void updateUserUpdate(long userId);

	boolean updateUserDomain(long userId, String domain);
	// /**
	// * 通过新浪用户信息创建用户
	// *
	// * @param apiUserSina
	// * @param nick 新浪昵称
	// * 2010-11-7
	// */
	// void createApi_user_sina(Api_user_sina apiUserSina, String nick);
	//
	// void updateApi_user_sina(Api_user_sina apiUserSina);
	//
	// Api_user getApi_userByUseridAndApi_type(long userid, int api_type);
	//
	// Api_user_sina getApi_user_sinaBySina_userid(long sina_userid);
	//
	// Api_user_sina getApi_user_sinaByUserid(long userid);
}