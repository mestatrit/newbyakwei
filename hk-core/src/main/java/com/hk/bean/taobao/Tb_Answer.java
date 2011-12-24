package com.hk.bean.taobao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.frame.util.JsonUtil;
import com.hk.svr.pub.Err;

@Table(name = "tb_answer")
public class Tb_Answer {

	@Id
	private long ansid;

	@Column
	private long userid;

	/**
	 * 问题id
	 */
	@Column
	private long aid;

	/**
	 * 答案内容(内容3000，格式化后3300)
	 */
	@Column
	private String content;

	/**
	 * 解决方案内容，使用json格式化，最多支持10个淘宝商品
	 */
	@Column
	private String resolve_content;

	/**
	 * 支持者数量
	 */
	@Column
	private int support_num;

	/**
	 * 反对者数量
	 */
	@Column
	private int discmd_num;

	/**
	 * 创建时间
	 */
	@Column
	private Date create_time;

	private Tb_User tbUser;

	public long getAnsid() {
		return ansid;
	}

	public void setAnsid(long ansid) {
		this.ansid = ansid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public long getAid() {
		return aid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setResolve_content(String resolveContent) {
		resolve_content = resolveContent;
	}

	public String getResolve_content() {
		return resolve_content;
	}

	public int getSupport_num() {
		return support_num;
	}

	public void setSupport_num(int supportNum) {
		support_num = supportNum;
	}

	public int getDiscmd_num() {
		return discmd_num;
	}

	public void setDiscmd_num(int discmdNum) {
		discmd_num = discmdNum;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public Tb_User getTbUser() {
		return tbUser;
	}

	public void setTbUser(Tb_User tbUser) {
		this.tbUser = tbUser;
	}

	public List<String> getResolveList() {
		if (DataUtil.isEmpty(this.resolve_content)) {
			return null;
		}
		List<String> vlist = JsonUtil.getListFromJson(this.resolve_content);
		return vlist;
	}

	private List<Tb_Answer_Item> tbAnswerItemList;

	private void inintTbAnswerItemList() {
		if (DataUtil.isEmpty(this.resolve_content)) {
			return;
		}
		if (tbAnswerItemList != null) {
			return;
		}
		tbAnswerItemList = new ArrayList<Tb_Answer_Item>();
		List<String> vlist = JsonUtil.getListFromJson(this.resolve_content);
		Map<String, String> map = null;
		Tb_Answer_Item tbAnswerItem = null;
		for (String v : vlist) {
			map = JsonUtil.getMapFromJson(v);
			tbAnswerItem = new Tb_Answer_Item();
			tbAnswerItem.setClick_url(map.get("u"));
			tbAnswerItem.setTitle(map.get("t"));
			tbAnswerItem.setPic_url(map.get("i"));
			tbAnswerItem.setPrice(map.get("p"));
			tbAnswerItem.setNum_iid(map.get("tb_id"));
			tbAnswerItem.setId(Integer.valueOf(map.get("id")));
			tbAnswerItemList.add(tbAnswerItem);
		}
	}

	public List<Tb_Answer_Item> getTbAnswerItemList() {
		this.inintTbAnswerItemList();
		return this.tbAnswerItemList;
	}

	public void deleteItem(String num_iid) {
		this.inintTbAnswerItemList();
		for (Tb_Answer_Item o : this.tbAnswerItemList) {
			if (o.getNum_iid().equals(num_iid)) {
				this.tbAnswerItemList.remove(o);
				break;
			}
		}
		this.saveResolve_contentFromList();
	}

	private void saveResolve_contentFromList() {
		List<String> list = new ArrayList<String>();
		Map<String, String> map = null;
		for (Tb_Answer_Item o : this.tbAnswerItemList) {
			map = new HashMap<String, String>();
			map.put("t", o.getTitle());
			map.put("p", o.getPrice());
			map.put("i", o.getPic_url());
			map.put("u", o.getClick_url());
			map.put("id", o.getNum_iid());
			list.add(JsonUtil.toJson(map));
		}
		if (list.size() > 0)
			this.resolve_content = JsonUtil.toJson(list);
		else
			this.resolve_content = null;
	}

	public String getExpression() {
		StringBuilder sb = new StringBuilder();
		this.inintTbAnswerItemList();
		double sum = 0;
		if (this.tbAnswerItemList.size() == 1) {
			return this.tbAnswerItemList.get(0).getPrice();
		}
		for (Tb_Answer_Item o : this.tbAnswerItemList) {
			sb.append(o.getPrice()).append(" + ");
			sum += Double.valueOf(o.getPrice());
		}
		sb.delete(sb.length() - 4, sb.length()).append(" = ").append(sum);
		return sb.toString();
	}

	public int validate() {
		if (!HkValidate.validateEmptyAndLength(this.content, true, 3000)) {
			return Err.TB_ANSWER_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}