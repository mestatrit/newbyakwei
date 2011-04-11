package iwant.svr;

import iwant.bean.User;

public interface UserSvr {

	/**
	 * 创建用户
	 * 
	 * @param user
	 * @return true:创建成功,false:用户已经存在
	 */
	boolean createUser(User user);

	/**
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);

	User getUserByUserid(long userid);
}