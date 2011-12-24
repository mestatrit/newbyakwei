package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpOtherInfo;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpOtherInfoMapper extends HkRowMapper<CmpOtherInfo> {

	@Override
	public Class<CmpOtherInfo> getMapperClass() {
		return CmpOtherInfo.class;
	}

	public CmpOtherInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpOtherInfo o = new CmpOtherInfo();
		o.setCompanyId(rs.getLong("companyid"));
		o.setDurationdata(rs.getString("durationdata"));
		o.setSvrrate(rs.getInt("svrrate"));
		o.setAdclose(rs.getByte("adclose"));
		return o;
	}
}