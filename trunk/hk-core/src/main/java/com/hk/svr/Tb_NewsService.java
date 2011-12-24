package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.taobao.Tb_Friend_News;
import com.hk.bean.taobao.Tb_News;
import com.hk.bean.taobao.Tb_User_News;

public interface Tb_NewsService {

	void createTb_News(Tb_News tbNews);

	void deleteTb_User_News(long userid, long nid);

	Tb_News getTb_News(long nid);

	Map<Long, Tb_News> getTb_NewsMapInId(List<Long> idList);

	/**
	 * 每个用户最多保留100条，把最后多于的数据删除
	 * 
	 * @param tbFriendNews
	 *            2010-9-16
	 */
	void createTb_Friend_News(Tb_Friend_News tbFriendNews);

	void deleteTb_Friend_News(long oid);

	void deleteTb_Friend_NewsByUseridAndNews_userid(long userid,
			long news_userid);

	/**
	 * 保存用户自己的动态
	 * 
	 * @param userid
	 * @param begin
	 * @param size 当size<0时，取所有数据
	 * @return
	 *         2010-9-16
	 */
	List<Tb_User_News> getTb_User_NewsListByUserid(long userid,
			boolean buildNews, int begin, int size);

	List<Tb_Friend_News> getTb_Friend_NewsListByUserid(long userid,
			boolean buildNews, int begin, int size);

	void deleteTb_NewsByNtypeAndOid(int ntype, long oid);
}