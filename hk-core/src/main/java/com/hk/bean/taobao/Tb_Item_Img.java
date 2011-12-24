package com.hk.bean.taobao;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;

/**
 * 商品图片
 * 
 * @author akwei
 */
@Table(name = "tb_item_img")
public class Tb_Item_Img {

	/**
	 * 图片id
	 */
	@Id
	private long imgid;

	/**
	 * 商品num_iid;
	 */
	@Column
	private long num_iid;

	/**
	 * 图片url
	 */
	@Column
	private String url;

	/**
	 * 图片显示顺序
	 */
	@Column
	private long position;

	public long getImgid() {
		return imgid;
	}

	public void setImgid(long imgid) {
		this.imgid = imgid;
	}

	public void setNum_iid(long numIid) {
		num_iid = numIid;
	}

	public long getNum_iid() {
		return num_iid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}
}