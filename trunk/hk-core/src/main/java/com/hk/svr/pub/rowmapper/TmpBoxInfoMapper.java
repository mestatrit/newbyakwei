package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.hk.bean.TmpBoxInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class TmpBoxInfoMapper extends HkRowMapper<TmpBoxInfo> {
	@Override
	public Class<TmpBoxInfo> getMapperClass() {
		return TmpBoxInfo.class;
	}

	public TmpBoxInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TmpBoxInfo o = new TmpBoxInfo();
		o.setBoxId(rs.getLong("boxid"));
		o.setContent(rs.getString("content"));
		return o;
	}
}