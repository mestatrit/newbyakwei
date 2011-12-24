package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Item_User_Ref;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Item_User_RefMapper extends HkRowMapper<Tb_Item_User_Ref> {

	@Override
	public Class<Tb_Item_User_Ref> getMapperClass() {
		return Tb_Item_User_Ref.class;
	}

	public Tb_Item_User_Ref mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		Tb_Item_User_Ref o = new Tb_Item_User_Ref();
		o.setOid(rs.getLong("oid"));
		o.setUserid(rs.getLong("userid"));
		o.setItemid(rs.getLong("itemid"));
		o.setFlg(rs.getByte("flg"));
		o.setCmtid(rs.getLong("cmtid"));
		return o;
	}
}