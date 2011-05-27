package iwant.svr.impl;

import iwant.bean.User;
import iwant.bean.UseridCreator;
import iwant.dao.UserDao;
import iwant.dao.UseridCreatorDao;
import iwant.svr.UserSvr;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.dev3g.cactus.util.DateUtil;
import com.dev3g.cactus.util.NumberUtil;

public class UserSvrImpl implements UserSvr {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UseridCreatorDao useridCreatorDao;

	@Override
	public boolean createUser(User user) {
		if (this.userDao.isExistByDevice_token(user.getDevice_token())) {
			return false;
		}
		if (user.getCreatetime() == null) {
			user.setCreatetime(DateUtil.createNoMillisecondTime(new Date()));
		}
		long userid = NumberUtil.getLong(this.useridCreatorDao
				.save(new UseridCreator()));
		user.setUserid(userid);
		this.userDao.save(user);
		return true;
	}

	@Override
	public User getUserByUserid(long userid) {
		return this.userDao.getById(null, userid);
	}

	@Override
	public boolean updateUser(User user) {
		if (this.userDao.getByDevice_tokenAnsNotUserid(user.getDevice_token(),
				user.getUserid()) != null) {
			return false;
		}
		this.userDao.update(user);
		return true;
	}

	@Override
	public User getUserByDevice_token(String deviceToken) {
		return this.userDao.getByDevice_token(deviceToken);
	}
}