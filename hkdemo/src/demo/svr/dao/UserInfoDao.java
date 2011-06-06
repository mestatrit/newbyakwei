package demo.svr.dao;

import java.util.List;

import com.dev3g.cactus.dao.query.IDao;

import demo.bean.UserInfo;

public interface UserInfoDao extends IDao<UserInfo> {

	List<UserInfo> getListByGender(int gender, int begin, int size);
}