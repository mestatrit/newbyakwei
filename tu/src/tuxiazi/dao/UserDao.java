package tuxiazi.dao;

import halo.dao.query.IDao;

import java.util.List;
import java.util.Map;

import tuxiazi.bean.User;

public interface UserDao extends IDao<User> {

	public int addPi_num(long userid, int add);

	public Map<Long, User> getMapInId(List<Long> idList);

	public List<User> getListInId(List<Long> idList);
}