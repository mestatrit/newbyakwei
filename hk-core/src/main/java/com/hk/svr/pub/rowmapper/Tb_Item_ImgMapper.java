package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Item_Img;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_Item_ImgMapper extends HkRowMapper<Tb_Item_Img> {

	@Override
	public Class<Tb_Item_Img> getMapperClass() {
		return Tb_Item_Img.class;
	}

	public Tb_Item_Img mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Item_Img o = new Tb_Item_Img();
		o.setImgid(rs.getLong("imgid"));
		o.setNum_iid(rs.getLong("num_iid"));
		o.setUrl(rs.getString("url"));
		o.setPosition(rs.getLong("position"));
		return o;
	}
}