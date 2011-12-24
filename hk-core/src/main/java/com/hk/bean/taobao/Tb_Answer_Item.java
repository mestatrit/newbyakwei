package com.hk.bean.taobao;

public class Tb_Answer_Item {

	private int id;

	private String title;

	private String pic_url;

	private String price;

	private String num_iid;

	private String click_url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPic_url() {
		return pic_url;
	}

	public void setPic_url(String picUrl) {
		pic_url = picUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNum_iid() {
		return num_iid;
	}

	public void setNum_iid(String numIid) {
		num_iid = numIid;
	}

	public String getClick_url() {
		return click_url;
	}

	public void setClick_url(String clickUrl) {
		click_url = clickUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}