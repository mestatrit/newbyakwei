package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.AuthorApply;
import com.hk.bean.AuthorTag;
import com.hk.bean.UserAuthorTag;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.AuthorTagService;

public class AuthorTagServiceImpl implements AuthorTagService {

	@Autowired
	private QueryManager manager;

	public long createUserAuthorTag(long userId, String name) {
		Query query = manager.createQuery();
		AuthorTag tag = query.getObjectEx(AuthorTag.class, "name=?",
				new Object[] { name });
		if (tag == null) {
			tag = new AuthorTag();
			tag.setName(name);
			query.addField("name", name);
			long id = query.insert(AuthorTag.class).longValue();
			tag.setTagId(id);
		}
		query.delete(UserAuthorTag.class, "userid=?", new Object[] { userId });
		// 不存在userid tagid的数据就创建
		if (query.count(UserAuthorTag.class, "userid=? and tagid=?",
				new Object[] { userId, tag.getTagId() }) == 0) {
			query.addField("userid", userId);
			query.addField("tagid", tag.getTagId());
			query.insert(UserAuthorTag.class);
		}
		return tag.getTagId();
	}

	public boolean createAuthorApply(AuthorApply authorApply) {
		authorApply.setCreateTime(new Date());
		authorApply.setCheckflg(AuthorApply.CHECKFLG_N);
		Query query = manager.createQuery();
		query.addField("userid", authorApply.getUserId());
		query.addField("name", authorApply.getName());
		query.addField("tel", authorApply.getTel());
		query.addField("email", authorApply.getEmail());
		query.addField("blog", authorApply.getBlog());
		query.addField("checkflg", authorApply.getCheckflg());
		query.addField("createtime", authorApply.getCreateTime());
		query.addField("content", authorApply.getContent());
		long id = query.insert(AuthorApply.class).longValue();
		authorApply.setOid(id);
		return true;
	}

	public AuthorApply getAuthorApplyByUserId(long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(AuthorApply.class, "userid=?",
				new Object[] { userId });
	}

	public List<AuthorApply> getAuthorApplyList(String name, byte checkflg,
			int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from authorapply where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (checkflg >= 0) {
			olist.add(checkflg);
			sql.append(" and checkflg=?");
		}
		if (!DataUtil.isEmpty(name)) {
			olist.add("%" + name + "%");
			sql.append(" and name like ?");
		}
		sql.append(" order by oid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				AuthorApply.class, olist);
	}

	public AuthorTag getAuthorTag(long tagId) {
		Query query = manager.createQuery();
		return query.getObjectById(AuthorTag.class, tagId);
	}

	public AuthorTag getAuthorTagByName(String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(AuthorTag.class, "name=?",
				new Object[] { name });
	}

	public UserAuthorTag getUserAuthorTagByTagIdAndUserId(long tagId,
			long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(UserAuthorTag.class, "tagid=? and userid=?",
				new Object[] { tagId, userId });
	}

	public void updateAuthorApply(AuthorApply authorApply) {
		Query query = manager.createQuery();
		query.setTable(AuthorApply.class);
		query.addField("userid", authorApply.getUserId());
		query.addField("name", authorApply.getName());
		query.addField("tel", authorApply.getTel());
		query.addField("email", authorApply.getEmail());
		query.addField("blog", authorApply.getBlog());
		query.addField("checkflg", authorApply.getCheckflg());
		query.addField("createtime", authorApply.getCreateTime());
		query.addField("content", authorApply.getContent());
		query.update(AuthorApply.class, "oid=?", new Object[] { authorApply
				.getOid() });
	}

	public void checkAuthorApply(long oid, byte checkflg, String name) {
		Query query = manager.createQuery();
		query.addField("checkflg", checkflg);
		query.update(AuthorApply.class, "oid=?", new Object[] { oid });
		AuthorApply apply = this.getAuthorApply(oid);
		if (checkflg == AuthorApply.CHECKFLG_Y) {
			if (!DataUtil.isEmpty(name)) {
				this.createUserAuthorTag(apply.getUserId(), name);
			}
		}
	}

	public AuthorApply getAuthorApply(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(AuthorApply.class, oid);
	}

	public UserAuthorTag getUserAuthorTagByUserId(long userId) {
		Query query = manager.createQuery();
		return query.getObjectEx(UserAuthorTag.class, "userid=?",
				new Object[] { userId });
	}

	public void deleteUserAuthorTag(long tagId, long userId) {
		Query query = manager.createQuery();
		query.delete(UserAuthorTag.class, "tagid=? and userid=?", new Object[] {
				tagId, userId });
	}

	public void deleteUserAuthorTag(long userId) {
		Query query = manager.createQuery();
		query.delete(UserAuthorTag.class, "userid=?", new Object[] { userId });
	}
}