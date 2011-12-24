package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.CmpZoneTag;
import com.hk.bean.CompanyTag;
import com.hk.bean.CompanyTagRef;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CompanyTagService;

public class CompanyTagServiceImpl implements CompanyTagService {
	@Autowired
	private QueryManager manager;

	public synchronized boolean createCompanyTag(String name, int kindId) {
		Query query = this.manager.createQuery();
		query.setTable(CompanyTag.class);
		query.where("name=? and kindid=?").setParam(name).setParam(kindId);
		if (query.count() == 0) {
			query.addField("name", name);
			query.addField("kindid", kindId);
			query.addField("cmpcount", 0);
			query.insert(CompanyTag.class);
			return true;
		}
		return false;
	}

	public void deleteCompanyTag(int tagId) {
		Query query = this.manager.createQuery();
		query.deleteById(CompanyTag.class, tagId);
		// 删除关系数据
		query.delete(CompanyTagRef.class, "tagid=?", new Object[] { tagId });
		// 删除地区关系数据
		query.delete(CmpZoneTag.class, "tagid=?", new Object[] { tagId });
	}

	public List<CompanyTag> getCompanyTagList(String name, int kindId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select * from companytag where 1=1");
		List<Object> plist = new ArrayList<Object>();
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			plist.add("%" + name + "%");
		}
		if (kindId > 0) {
			sql.append(" and kindid=?");
			plist.add(kindId);
		}
		sql.append(" order by tagid desc");
		return query.listBySql("ds1", sql.toString(), begin, size,
				CompanyTag.class, plist.toArray(new Object[plist.size()]));
	}

	public CompanyTag getCompanyTag(int tagId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CompanyTag.class, tagId);
	}

	public boolean updateCompanyTag(CompanyTag companyTag) {
		CompanyTag o = this.getCompanyTag(companyTag.getTagId());
		if (o.getName().equals(companyTag.getName())) {
			return true;
		}
		Query query = this.manager.createQuery();
		query.setTable(CompanyTag.class);
		query.where("name=? and kindid=?").setParam(companyTag.getName())
				.setParam(companyTag.getKindId());
		if (query.count() == 0) {
			query.setTable(CompanyTag.class);
			query.addField("name", companyTag.getName());
			query.where("tagid=?").setParam(companyTag.getTagId());
			query.update();
			return true;
		}
		return false;
	}

	public synchronized boolean createCompanyTagRef(long companyId, int tagId,
			int cityId) {
		Query query = this.manager.createQuery();
		if (query.count(CompanyTagRef.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId }) > 0) {
			return false;
		}
		query.addField("companyid", companyId);
		query.addField("tagid", tagId);
		query.addField("cityId", cityId);
		query.insert(CompanyTagRef.class);
		this.updateCmpCountByTagIdAndCityId(tagId, cityId);
		return true;
	}

	public void deleteCompanyTagRef(long companyId, int tagId, int cityId) {
		Query query = this.manager.createQuery();
		query.setTable(CompanyTagRef.class);
		query.where("companyid=? and tagid=?").setParam(companyId).setParam(
				tagId);
		query.delete();
		this.updateCmpCountByTagIdAndCityId(tagId, cityId);
	}

	public void deleteCompanyTagRef(long companyId) {
		Query query = this.manager.createQuery();
		String sql = "select distinct tagid,cityid from companytagref where companyid=?";
		List<Object[]> list = query.listdata("ds1", sql, companyId);
		query.delete(CompanyTagRef.class, "companyid=?",
				new Object[] { companyId });
		for (Object[] o : list) {
			int tagId = Integer.parseInt(o[0].toString());
			int cityId = Integer.parseInt(o[1].toString());
			this.updateCmpCountByTagIdAndCityId(tagId, cityId);
		}
	}

	public List<CompanyTag> getCompanyTagListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		String sql = "select t.* from companytag t,companytagref r where t.tagid=r.tagid and companyid=? order by r.oid desc";
		return query.listBySqlEx("ds1", sql, CompanyTag.class, companyId);
	}

	public List<CompanyTag> getCompanyTagListExcept(int kindId,
			List<Integer> idList) {
		Query query = this.manager.createQuery();
		if (idList.size() == 0) {
			return query.listEx(CompanyTag.class, "kindid=?",
					new Object[] { kindId }, "tagid desc");
		}
		StringBuilder sb = new StringBuilder();
		for (Integer i : idList) {
			sb.append(i).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		String sql = "select * from companytag where kindid=? and tagid not in ( "
				+ sb.toString() + ") order by tagid desc";
		return query.listBySqlEx("ds1", sql, CompanyTag.class, kindId);
	}

	/**
	 * @param tagId
	 * @param cityId
	 * @return false 已经存在，不再创建
	 */
	public synchronized boolean createCmpZoneTag(CmpZoneTag cmpZoneTag) {
		Query query = this.manager.createQuery();
		if (query.count(CmpZoneTag.class, "tagid=? and cityid=?", new Object[] {
				cmpZoneTag.getTagId(), cmpZoneTag.getCityId() }) > 0) {
			return false;
		}
		query.addField("tagid", cmpZoneTag.getTagId());
		query.addField("cityid", cmpZoneTag.getCityId());
		query.addField("kindid", cmpZoneTag.getKindId());
		query.addField("cmpcount", cmpZoneTag.getCmpCount());
		query.insert(CmpZoneTag.class);
		return true;
	}

	/**
	 * 当足迹的地区改变时，调用此方法，更新相应标签的数量
	 * 
	 * @param tagId
	 * @param cityId
	 */
	private void updateCmpCountByTagIdAndCityId(int tagId, int cityId) {
		CompanyTag o = this.getCompanyTag(tagId);
		CmpZoneTag cmpZoneTag = new CmpZoneTag();
		cmpZoneTag.setCityId(cityId);
		cmpZoneTag.setTagId(tagId);
		cmpZoneTag.setKindId(o.getKindId());
		this.createCmpZoneTag(cmpZoneTag);
		Query query = this.manager.createQuery();
		int count = query.count(CompanyTagRef.class, "tagid=? and cityid=?",
				new Object[] { tagId, cityId });
		query.addField("cmpcount", count);
		query.update(CmpZoneTag.class, "tagid=? and cityid=?", new Object[] {
				tagId, cityId });
	}

	public void updateCompanyTagRefByCompanyId(long companyId, int old_cityid,
			int new_cityId) {
		Query query = this.manager.createQuery();
		// 更新足迹关联数据中的cityId
		query.addField("cityid", new_cityId);
		query.update(CompanyTagRef.class, "companyid=?",
				new Object[] { companyId });
		// 更新old new cityId 的地区足迹标签数量
		String sql = "select distinct tagid from companytagref where companyid=?";
		List<Object[]> list = query.listdata("ds1", sql, companyId);
		for (Object[] o : list) {
			int tagId = Integer.parseInt(o[0].toString());
			this.updateCmpCountByTagIdAndCityId(tagId, old_cityid);
			this.updateCmpCountByTagIdAndCityId(tagId, new_cityId);
		}
	}

	public List<CompanyTag> getCompanyTagListForHot(int kindId, int cityId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select t.* from companytag t,cmpzonetag zt where zt.kindid=? and zt.cityid=? and cmpcount>0 and t.tagid=zt.tagid order by cmpcount desc";
		return query.listBySql("ds1", sql, begin, size, CompanyTag.class,
				kindId, cityId);
	}

	public int countCompanyTagRefByTagIdAndCityId(int tagId, int cityId) {
		Query query = this.manager.createQuery();
		return query.count(CompanyTagRef.class, "tagid=? and cityid=?",
				new Object[] { tagId, cityId });
	}

	public List<CompanyTagRef> getCompanyTagRefListByTagIdAndCityId(int tagId,
			int cityId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CompanyTagRef.class, "tagid=? and cityid=?",
				new Object[] { tagId, cityId }, "companyid desc", begin, size);
	}
}