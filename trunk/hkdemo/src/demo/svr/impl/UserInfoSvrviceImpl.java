package demo.svr.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.bean.UserInfo;
import demo.svr.UserInfoService;
import demo.svr.dao.UserInfoDao;

public class UserInfoSvrviceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public void createUserInfo(UserInfo userInfo) {
		this.userInfoDao.save(userInfo);
	}

	@Override
	public void deleteUserInfo(long userid) {
		this.userInfoDao.deleteById(userid);
	}

	@Override
	public UserInfo getUserInfo(long userid) {
		return this.userInfoDao.getById(userid);
	}

	@Override
	public void updateUserInfo(UserInfo userInfo) {
		this.userInfoDao.update(userInfo);
	}
}
