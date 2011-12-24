package com.hk.svr;

import java.util.List;
import com.hk.bean.AuthorApply;
import com.hk.bean.AuthorTag;
import com.hk.bean.UserAuthorTag;

public interface AuthorTagService {
	/**
	 * 创建个人栏目标签(也可能是新创建，也可能是已经存在，不进行创建操作)
	 * 
	 * @param userId
	 * @param name
	 * @return 返回标签id
	 */
	long createUserAuthorTag(long userId, String name);

	AuthorTag getAuthorTagByName(String name);

	AuthorTag getAuthorTag(long tagId);

	UserAuthorTag getUserAuthorTagByTagIdAndUserId(long tagId, long userId);

	UserAuthorTag getUserAuthorTagByUserId(long userId);

	boolean createAuthorApply(AuthorApply authorApply);

	/**
	 * 修改该用户专栏申请信息
	 * 
	 * @param authorApply
	 */
	void updateAuthorApply(AuthorApply authorApply);

	/**
	 * @param checkflg <0时查询所有
	 * @param begin
	 * @param size
	 * @return
	 */
	List<AuthorApply> getAuthorApplyList(String name, byte checkflg, int begin,
			int size);

	AuthorApply getAuthorApplyByUserId(long userId);

	AuthorApply getAuthorApply(long oid);

	void checkAuthorApply(long oid, byte checkflg, String name);

	void deleteUserAuthorTag(long tagId, long userId);

	void deleteUserAuthorTag(long userId);
}