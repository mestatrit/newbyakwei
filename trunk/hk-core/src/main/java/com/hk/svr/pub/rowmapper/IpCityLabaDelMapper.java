package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.IpCityLabaDel;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class IpCityLabaDelMapper extends HkRowMapper<IpCityLabaDel> {
	@Override
	public Class<IpCityLabaDel> getMapperClass() {
		return IpCityLabaDel.class;
	}

	public IpCityLabaDel mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpCityLabaDel o = new IpCityLabaDel();
		o.setCityId(rs.getInt("cityid"));
		o.setLabaId(rs.getLong("labaid"));
		o.setOpuserId(rs.getLong("opuserid"));
		o.setOptime(rs.getLong("optime"));
		return o;
	}
}
