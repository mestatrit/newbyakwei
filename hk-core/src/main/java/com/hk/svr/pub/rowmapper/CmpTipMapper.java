package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpTip;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpTipMapper extends HkRowMapper<CmpTip> {
	@Override
	public Class<CmpTip> getMapperClass() {
		return CmpTip.class;
	}

	public CmpTip mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpTip o = new CmpTip();
		o.setTipId(rs.getLong("tipid"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setUserId(rs.getLong("userid"));
		o.setContent(rs.getString("content"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setShowflg(rs.getByte("showflg"));
		o.setDoneflg(rs.getByte("doneflg"));
		return o;
	}
}