package iwant.dao;

import iwant.bean.User;

import com.hk.frame.dao.query2.IDao;

public interface UserDao extends IDao<User> {

	/**
	 * 根据设备id查看是否已经存在此用户
	 * 
	 * @param device_token
	 * @return true:存在,false:不存在
	 */
	boolean isExistByDevice_token(String device_token);

	User getByDevice_tokenAnsNotUserid(String device_token, long userid);
}