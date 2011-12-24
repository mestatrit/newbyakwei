package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业相关文章
 * 
 * @author akwei
 */
@Table(name = "cmparticle")
public class CmpArticle {

	public static final byte HOMEPINK_N = 0;

	/**
	 * 推荐到首页(普通企业版使用)
	 */
	public static final byte HOMEPINK_Y = 1;

	public static final byte HIDETITLEFLG_N = 0;

	public static final byte HIDETITLEFLG_Y = 1;

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private long cmpNavOid;

	@Column
	private String title;

	@Column
	private Date createTime;

	@Column
	private String filepath;

	@Column
	private long groupId;

	/**
	 * 排序号，越大越靠前
	 */
	@Column
	private int orderflg;

	/**
	 * 企业推荐
	 * 
	 * @see {@link CmpUtil#CMPPINK_N}
	 * @see {@link CmpUtil#CMPPINK_N}
	 */
	@Column
	private byte cmppink;

	/**
	 * 推荐的时间
	 */
	@Column
	private Date cmppinkTime;

	/**
	 * 推荐到首页
	 */
	@Column
	private byte homepink;

	/**
	 * 可挂接的产品id
	 */
	@Column
	private long productId;

	/**
	 * 产品所属分类id
	 */
	@Column
	private int sortId;

	@Column
	private byte hideTitleflg;

	/**
	 * 如果推荐到页面模块中，就有模块id，否则，为0
	 */
	@Column
	private long page1BlockId;

	private CmpPageBlock page1Block;

	private CmpArticleGroup cmpArticleGroup;

	private CmpArticleContent cmpArticleContent;

	public void setCmpArticleGroup(CmpArticleGroup cmpArticleGroup) {
		this.cmpArticleGroup = cmpArticleGroup;
	}

	public CmpArticleGroup getCmpArticleGroup() {
		return cmpArticleGroup;
	}

	public void setPage1Block(CmpPageBlock page1Block) {
		this.page1Block = page1Block;
	}

	public CmpPageBlock getPage1Block() {
		return page1Block;
	}

	public void setPage1BlockId(long page1BlockId) {
		this.page1BlockId = page1BlockId;
	}

	public long getPage1BlockId() {
		return page1BlockId;
	}

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getCmpNavOid() {
		return cmpNavOid;
	}

	public void setCmpNavOid(long cmpNavOid) {
		this.cmpNavOid = cmpNavOid;
	}

	public int validate() {
		String s = DataUtil.toText(this.title);
		if (DataUtil.isEmpty(s) || s.length() > 50) {
			return Err.CMPARTICLE_TITLE_ERROR;
		}
		return Err.SUCCESS;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public byte getCmppink() {
		return cmppink;
	}

	public void setCmppink(byte cmppink) {
		this.cmppink = cmppink;
	}

	public Date getCmppinkTime() {
		return cmppinkTime;
	}

	public void setCmppinkTime(Date cmppinkTime) {
		this.cmppinkTime = cmppinkTime;
	}

	public boolean isPinkForCmp() {
		if (this.cmppink == CmpUtil.CMPPINK_Y) {
			return true;
		}
		return false;
	}

	public byte getHomepink() {
		return homepink;
	}

	public void setHomepink(byte homepink) {
		this.homepink = homepink;
	}

	public boolean isPinkToHome() {
		if (this.homepink == HOMEPINK_Y) {
			return true;
		}
		return false;
	}

	public String getCmpFilePic60() {
		return ImageConfig.getCmpFilePic60Url(this.filepath);
	}

	public String getCmpFilePic120() {
		return ImageConfig.getCmpFilePic120Url(this.filepath);
	}

	public String getCmpFilePic320() {
		return ImageConfig.getCmpFilePic320Url(this.filepath);
	}

	public String getCmpFilePic120_2() {
		return ImageConfig.getCmpFilePic120Url2(this.filepath);
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public boolean isHideTitle() {
		if (this.hideTitleflg == HIDETITLEFLG_Y) {
			return true;
		}
		return false;
	}

	public byte getHideTitleflg() {
		return hideTitleflg;
	}

	public void setHideTitleflg(byte hideTitleflg) {
		this.hideTitleflg = hideTitleflg;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public CmpArticleContent getCmpArticleContent() {
		return cmpArticleContent;
	}

	public void setCmpArticleContent(CmpArticleContent cmpArticleContent) {
		this.cmpArticleContent = cmpArticleContent;
	}
}