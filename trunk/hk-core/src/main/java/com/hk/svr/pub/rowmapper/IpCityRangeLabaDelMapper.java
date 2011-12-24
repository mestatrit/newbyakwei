package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.IpCityRangeLabaDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class IpCityRangeLabaDelMapper extends HkRowMapper<IpCityRangeLabaDel> {
	@Override
	public Class<IpCityRangeLabaDel> getMapperClass() {
		return IpCityRangeLabaDel.class;
	}

	public IpCityRangeLabaDel mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		IpCityRangeLabaDel o = new IpCityRangeLabaDel();
		o.setRangeId(rs.getInt("rangeid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}