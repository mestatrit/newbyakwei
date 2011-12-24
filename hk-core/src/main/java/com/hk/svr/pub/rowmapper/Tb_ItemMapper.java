package com.hk.svr.pub.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hk.bean.taobao.Tb_Item;
import com.hk.frame.dao.rowmapper.HkRowMapper;

public class Tb_ItemMapper extends HkRowMapper<Tb_Item> {

	@Override
	public Class<Tb_Item> getMapperClass() {
		return Tb_Item.class;
	}

	public Tb_Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tb_Item o = new Tb_Item();
		o.setItemid(rs.getLong("itemid"));
		o.setNum_iid(rs.getLong("num_iid"));
		o.setDetail_url(rs.getString("detail_url"));
		o.setTitle(rs.getString("title"));
		o.setNick(rs.getString("nick"));
		o.setItem_type(rs.getString("item_type"));
		o.setCid(rs.getLong("cid"));
		o.setPic_url(rs.getString("pic_url"));
		o.setNum(rs.getInt("num"));
		o.setValid_thru(rs.getInt("valid_thru"));
		o.setList_time(rs.getTimestamp("list_time"));
		o.setDelist_time(rs.getTimestamp("delist_time"));
		o.setStuff_status(rs.getByte("stuff_status"));
		o.setLocation(rs.getString("location"));
		o.setPrice(rs.getDouble("price"));
		o.setFreight_payer(rs.getByte("freight_payer"));
		o.setHas_invoice(rs.getByte("has_invoice"));
		o.setHas_warranty(rs.getByte("has_warranty"));
		o.setModified(rs.getTimestamp("modified"));
		o.setScore(rs.getLong("score"));
		o.setVolume(rs.getLong("volume"));
		o.setCreate_time(rs.getTimestamp("create_time"));
		o.setProduct_id(rs.getLong("product_id"));
		o.setLast_modified(rs.getTimestamp("last_modified"));
		o.setOne_station(rs.getByte("one_station"));
		o.setCmt_num(rs.getInt("cmt_num"));
		o.setUserid(rs.getLong("userid"));
		o.setHkscore(rs.getInt("hkscore"));
		o.setHkscore_num(rs.getInt("hkscore_num"));
		o.setSid(rs.getLong("sid"));
		o.setClick_url(rs.getString("click_url"));
		o.setShop_click_url(rs.getString("shop_click_url"));
		o.setCommission(rs.getDouble("commission"));
		o.setCommission_rate(rs.getDouble("commission_rate"));
		o.setHuo_status(rs.getInt("huo_status"));
		o.setHome_cmd_flg(rs.getByte("home_cmd_flg"));
		o.setCommission_num(rs.getInt("commission_num"));
		return o;
	}
}