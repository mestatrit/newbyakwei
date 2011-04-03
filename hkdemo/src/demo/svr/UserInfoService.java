package demo.svr;

import demo.bean.UserInfo;

public interface UserInfoService {

	void createUserInfo(UserInfo userInfo);

	void updateUserInfo(UserInfo userInfo);

	void deleteUserInfo(UserInfo userInfo);

	UserInfo getUserInfo(long userid);
}
