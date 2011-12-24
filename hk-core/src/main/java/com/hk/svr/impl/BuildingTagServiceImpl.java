package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hk.bean.BuildingTag;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.BuildingTagService;

public class BuildingTagServiceImpl implements BuildingTagService {
	@Autowired
	private QueryManager manager;

	public boolean createBuildingTag(BuildingTag buildingTag) {
		Query query = this.manager.createQuery();
		query.setTable(BuildingTag.class);
		query.where("name=? and cityid=?").setParam(buildingTag.getName())
				.setParam(buildingTag.getCityId());
		if (query.count() == 0) {
			query.addField("name", buildingTag.getName());
			query.addField("cityid", buildingTag.getCityId());
			query.addField("provinceid", buildingTag.getProvinceId());
			query.insert(BuildingTag.class);
			return true;
		}
		return false;
	}

	public void deleteBuildingTag(int tagId) {
		Query query = this.manager.createQuery();
		query.deleteById(BuildingTag.class, tagId);
	}

	public List<BuildingTag> getBuildingTagList(String name, int cityId,
			int provinceId, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from buildingtag where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (cityId > 0) {
			sql.append(" and cityid=?");
			olist.add(cityId);
		}
		if (provinceId > 0) {
			sql.append(" and provinceid=?");
			olist.add(cityId);
		}
		sql.append(" order by tagid desc");
		Query query = this.manager.createQuery();
		query.setTable(BuildingTag.class);
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				BuildingTag.class, olist);
	}

	public List<BuildingTag> getBuildingTagList(String name, int cityId,
			int provinceId) {
		StringBuilder sql = new StringBuilder(
				"select * from buildingtag where 1=1");
		List<Object> olist = new ArrayList<Object>();
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (cityId > 0) {
			sql.append(" and cityid=?");
			olist.add(cityId);
		}
		else if (provinceId > 0) {
			sql.append(" and provinceid=?");
			olist.add(provinceId);
		}
		sql.append(" order by tagid desc");
		Query query = this.manager.createQuery();
		query.setTable(BuildingTag.class);
		return query.listBySqlParamList("ds1", sql.toString(), BuildingTag.class, olist);
	}

	public void updateBuildingTag(BuildingTag buildingTag) {
		Query query = this.manager.createQuery();
		query.setTable(BuildingTag.class);
		query.addField("name", buildingTag.getName());
		query.addField("cityid", buildingTag.getCityId());
		query.addField("provinceid", buildingTag.getProvinceId());
		query.where("tagid=?").setParam(buildingTag.getTagId());
		query.update();
	}

	public BuildingTag getBuildingTag(int tagId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(BuildingTag.class, tagId);
	}
}