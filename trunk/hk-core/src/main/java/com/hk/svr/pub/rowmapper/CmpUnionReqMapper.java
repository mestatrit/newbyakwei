package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.CmpUnionReq;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class CmpUnionReqMapper extends HkRowMapper<CmpUnionReq> {
	@Override
	public Class<CmpUnionReq> getMapperClass() {
		return CmpUnionReq.class;
	}

	public CmpUnionReq mapRow(ResultSet rs, int rowNum) throws SQLException {
		CmpUnionReq o = new CmpUnionReq();
		o.setReqflg(rs.getInt("reqflg"));
		o.setReqid(rs.getLong("reqid"));
		o.setObjId(rs.getLong("objid"));
		o.setUid(rs.getLong("uid"));
		o.setCreateTime(rs.getTimestamp("createtime"));
		o.setData(rs.getString("data"));
		return o;
	}
}