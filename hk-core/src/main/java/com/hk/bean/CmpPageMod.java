package com.hk.bean;

/**
 * 企业网站页面模块(现普通企业模板没有此功能)
 * 由于 不同的模板中模块的位置可能会不相同，所以模块可能就是不同的模块
 * 例如企业模板1中有焦点图，教育模板2中有焦点图，那么2个焦点图模块是2个不同的模块，而不是同一个模块
 * 
 * @author akwei
 */
public class CmpPageMod {

	public static final byte MODFLG_AD = 0;

	public static final byte MODFLG_ARTICLE = 1;

	public static final byte MODFLG_FR = 2;

	public static final byte MODFLG_LINK = 3;

	public static final byte ADMODFLG_NOLIMIT = 0;

	public static final byte ADMODFLG_TEXT = 1;

	public static final byte ADMODFLG_PIC = 2;

	private int pageModId;

	/**
	 * 模块名称
	 */
	private String name;

	/**
	 * 页面中的位置
	 */
	private byte pos;

	/**
	 * 描述
	 */
	private String intro;

	/**
	 * >0表示数量限制,-1表示不受限制
	 */
	private int dataSize;

	/**
	 * 模块类型
	 */
	private byte modflg;

	/**
	 * 广告类型,0:文字,1:图片
	 */
	private byte admodflg;

	/**
	 * 是否限制>0时，为限制数量
	 */
	private int cmpNavArticleCount;

	public void setModflg(byte modflg) {
		this.modflg = modflg;
	}

	public byte getModflg() {
		return modflg;
	}

	public boolean isModAd() {
		if (this.modflg == MODFLG_AD) {
			return true;
		}
		return false;
	}

	public boolean isModAdText() {
		if (this.admodflg == ADMODFLG_TEXT) {
			return true;
		}
		return false;
	}

	public boolean isModArticle() {
		if (this.modflg == MODFLG_ARTICLE) {
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getPos() {
		return pos;
	}

	public void setPos(byte pos) {
		this.pos = pos;
	}

	public int getPageModId() {
		return pageModId;
	}

	public void setPageModId(int pageModId) {
		this.pageModId = pageModId;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getDataSize() {
		return dataSize;
	}

	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}

	public byte getAdmodflg() {
		return admodflg;
	}

	public void setAdmodflg(byte admodflg) {
		this.admodflg = admodflg;
	}

	public int getCmpNavArticleCount() {
		return cmpNavArticleCount;
	}

	public void setCmpNavArticleCount(int cmpNavArticleCount) {
		this.cmpNavArticleCount = cmpNavArticleCount;
	}
}