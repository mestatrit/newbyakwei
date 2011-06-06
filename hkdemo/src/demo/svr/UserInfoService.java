package demo.svr;

import demo.bean.UserInfo;

public interface UserInfoService {

	void createUserInfo(UserInfo userInfo);

	void updateUserInfo(UserInfo userInfo);

	void deleteUserInfo(long userid);

	UserInfo getUserInfo(long userid);
}
