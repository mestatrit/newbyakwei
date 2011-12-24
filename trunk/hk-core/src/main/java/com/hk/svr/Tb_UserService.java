package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.taobao.Tb_Sina_User;
import com.hk.bean.taobao.Tb_User;
import com.hk.bean.taobao.Tb_User_Api;
import com.hk.svr.pub.Err;

/**
 * 用户相关逻辑
 * 
 * @author akwei
 */
public interface Tb_UserService {

	/**
	 * 创建用户。如果密码不为空，用户数据中密码的值会被换成加密后的值
	 * 
	 * @param tbUser
	 * @return {@link Err#SUCCESS} 创建成功,{@link Err#TB_USER_NICK_DUPLICATE},
	 *         {@link Err#TB_USER_EMAIL_DUPLICATE} 2010-8-27
	 */
	int createTb_User(Tb_User tbUser);

	int createTb_User(Tb_User tbUser, Tb_User_Api tbUserApi);

	/**
	 * @param tbUser
	 * @return {@link Err#SUCCESS} 更新成功,{@link Err#TB_USER_NICK_DUPLICATE},
	 *         {@link Err#TB_USER_EMAIL_DUPLICATE} 2010-8-27
	 */
	int updateTb_User(Tb_User tbUser);

	void deleteTb_User(long userid);

	/**
	 * 根据id查询user，如果没有返回null
	 * 
	 * @param userid
	 * @return
	 *         2010-8-27
	 */
	Tb_User getTb_User(long userid);

	/**
	 * 根据昵称查询user，如果没有返回null
	 * 
	 * @param nick
	 * @return
	 *         2010-8-29
	 */
	Tb_User getTb_UserByNick(String nick);

	/**
	 * 根据E-mail查询user，如果没有返回null
	 * 
	 * @param email
	 * @return
	 *         2010-8-29
	 */
	Tb_User getTb_UserByEmail(String email);

	void updatePwd(long userid, String pwd);

	/**
	 * 创建数据。对于新浪，豆瓣等第三方有单独的表来处理，例如：新浪用户创建到新浪用户表中，已新浪用户id为主键，火酷id为对应
	 * 
	 * @param tbUserApi
	 *            2010-8-30
	 */
	void createTb_User_Api(Tb_User_Api tbUserApi);

	void updateTb_User_Api(Tb_User_Api tbUserApi);

	/**
	 * 删除用户绑定的第三方api。对于新浪，豆瓣等第三方有单独的表来处理，例如：新浪用户，就需要删除新浪表当中对应数据
	 * 
	 * @param tbUserApi
	 *            2010-8-30
	 */
	void deleteTb_User_Api(Tb_User_Api tbUserApi);

	/**
	 * 更新或创建数据。 根据数据id查看，如果是<=0就创建，>0就更新
	 * 
	 * @param tbUserApi
	 *            2010-8-30
	 */
	void saveTb_User_Api(Tb_User_Api tbUserApi);

	Tb_User_Api getTb_User_Api(long userid, byte reg_source);

	Tb_User_Api getTb_User_ApiByUidAndReg_source(String uid, byte reg_source);

	List<Tb_User_Api> getTb_User_ApiByUserid(long userid);

	List<Tb_Sina_User> getTb_Sina_UserListInId(List<Long> idList,
			boolean buildUser);

	Tb_Sina_User getTb_Sina_User(long uid);

	Map<Long, Tb_User> getTb_UserMapInId(List<Long> idList);

	List<Tb_User> getTb_UserListForNew(int begin, int size);

	/**
	 * 发布商品数量高到低
	 * 
	 * @param begin
	 * @param size
	 * @return
	 *         2010-9-6
	 */
	List<Tb_User> getTb_UserListForSuperMan(int begin, int size);

	void addItem_count(long userid, int add);

	void updateLoginInfoUserid(long userid, String api_pic_path,
			String apiinfo, byte login_flg);

	void updateItem_want_countByUserid(long userid, int count);

	void updateItem_hold_countByUserid(long userid, int count);

	void updateItem_cmt_countByUserid(long userid, int count);
}