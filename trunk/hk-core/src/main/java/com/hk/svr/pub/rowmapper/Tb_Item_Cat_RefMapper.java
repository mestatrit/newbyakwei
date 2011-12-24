package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Item_Cat_Ref;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Item_Cat_RefMapper extends HkRowMapper<Tb_Item_Cat_Ref> {

	@Override
	public Class<Tb_Item_Cat_Ref> getMapperClass() {
		return Tb_Item_Cat_Ref.class;
	}

	public Tb_Item_Cat_Ref mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Item_Cat_Ref o = new Tb_Item_Cat_Ref();
		o.setOid(rs.getLong("oid"));
		o.setCid(rs.getLong("cid"));
		o.setItemid(rs.getLong("itemid"));
		return o;
	}
}