package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpJoinInApply;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpJoinInApplyMapper extends HkRowMapper<CmpJoinInApply> {

	@Override
	public Class<CmpJoinInApply> getMapperClass() {
		return CmpJoinInApply.class;
	}

	public CmpJoinInApply mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpJoinInApply o = new CmpJoinInApply();
		o.setOid(rs.getLong("oid"));
		o.setName(rs.getString("name"));
		o.setCompanyId(rs.getLong("companyid"));
		o.setTel(rs.getString("tel"));
		o.setMobile(rs.getString("mobile"));
		o.setContent(rs.getString("content"));
		o.setCmpname(rs.getString("cmpname"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setReaded(rs.getByte("readed"));
		o.setIp(rs.getString("ip"));
		return o;
	}
}