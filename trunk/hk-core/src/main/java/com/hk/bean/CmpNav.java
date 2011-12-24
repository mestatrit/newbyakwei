package com.hk.bean;

import java.util.ArrayList;
import java.util.List;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

/**
 * 企业网站导航（只支持2级）
 * 
 * @author akwei
 */
@Table(name = "cmpnav")
public class CmpNav {

	public static final int REFFUNC_NONE = 1;

	public static final int REFFUNC_SINGLECONTENT = 2;

	public static final int REFFUNC_LISTCONTENT = 3;

	public static final int REFFUNC_BOX = 4;

	public static final int REFFUNC_COUPON = 5;

	public static final int REFFUNC_BBS = 6;

	public static final int REFFUNC_MSG = 7;

	public static final int REFFUNC_CONTACT = 8;

	public static final int REFFUNC_MAP = 9;

	public static final int REFFUNC_SELNET = 10;

	public static final int REFFUNC_HOME = 11;

	public static final int REFFUNC_PRODUCT = 12;

	public static final int REFFUNC_CMPDOWNFILE = 13;

	public static final int REFFUNC_VIDEO = 14;

	/**
	 * 招生简章
	 */
	public static final int REFFUNC_STUDYAD = 15;

	/**
	 * 预约服务
	 */
	public static final int REFFUNC_RESERVE = 16;

	/**
	 * 文字链接
	 */
	public static final int REFFUNC_LINK = 17;

	/**
	 * 登录和注册
	 */
	public static final int REFFUNC_LOGINANDREG = 18;

	/**
	 * 回到首页，显示在栏目中
	 */
	public static final int REFFUNC_RETURNHOME = 19;

	/**
	 * 自定义表格数据
	 */
	public static final int REFFUNC_TABLE_DATA = 20;

	public static final byte SHOWFLG_TITLE = 1;

	public static final byte SHOWFLG_IMG = 2;

	public static final byte NLEVEL_1 = 1;

	public static final byte NLEVEL_2 = 2;

	public static final byte HOMEPOS_LEFT = 1;

	public static final byte HOMEPOS_MIDDLE = 2;

	public static final byte HOMEPOS_RIGHT = 3;

	public static final byte APPLYFORMFLG_N = 0;

	public static final byte APPLYFORMFLG_Y = 1;

	@Id
	private long oid;

	@Column
	private long companyId;

	@Column
	private String name;

	@Column
	private long parentId;

	/**
	 * 是是否显示到首页
	 */
	@Column
	private int showInHome;

	/**
	 * 在首页的现实位置
	 */
	@Column
	private byte homepos;

	/**
	 * 导航级别
	 */
	@Column
	private int nlevel;

	/**
	 * 文章是否挂接加盟表单的标志
	 */
	@Column
	private byte applyformflg;

	/**
	 * 挂接某个功能
	 */
	@Column
	private int reffunc;

	/**
	 * 一级导航带有的图片
	 */
	@Column
	private String filepath;

	@Column
	private byte fileflg;

	/**
	 * 展示方式@see {@link CmpNav#SHOWFLG_IMG} {@link CmpNav#SHOWFLG_TITLE}
	 */
	@Column
	private byte showflg;

	/**
	 * 显示顺序，小数字在前
	 */
	@Column
	private int orderflg;

	/**
	 * 右边部分显示
	 */
	public static final byte FILESHOWFLG_RIGHT = 0;

	/**
	 * 通栏显示
	 */
	public static final byte FILESHOWFLG_ALL = 1;

	/**
	 * 图片或flash文件显示方式
	 */
	@Column
	private byte fileShowflg;

	/**
	 * 文件链接，只支持图片方式
	 */
	@Column
	private String fileShowLink;

	/**
	 * 专业分类id
	 */
	@Column
	private long kindId;

	/**
	 * 栏目标题(企业模板1使用)
	 */
	@Column
	private String title;

	/**
	 * 栏目说明(企业模板1使用)
	 */
	@Column
	private String intro;

	/**
	 * css数据json格式(文字链接颜色)
	 */
	@Column
	private String cssData;

	/**
	 * 背景图路径
	 */
	@Column
	private String bgPicPath;

	/**
	 * 使用默认背景图
	 */
	public static final byte BGFLG_DEFAULT = 0;

	/**
	 * 使用自设背景图
	 */
	public static final byte BGFLG_SET = 1;

	/**
	 * 不使用背景图
	 */
	public static final byte BGFLG_NONE = 2;

	/**
	 * 背景图使用开关0:使用默认背景图,1:使用自设背景图,2:不使用背景图
	 */
	@Column
	private byte bgflg;

	/**
	 * 用户输入链接(如果栏目类型为文字链接类型时，此值会有效)
	 */
	@Column
	private String url;

	private CmpStudyKind cmpStudyKind;

	private boolean hasChild;

	public void setCmpStudyKind(CmpStudyKind cmpStudyKind) {
		this.cmpStudyKind = cmpStudyKind;
	}

	public CmpStudyKind getCmpStudyKind() {
		return cmpStudyKind;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNlevel() {
		return nlevel;
	}

	public void setNlevel(int nlevel) {
		this.nlevel = nlevel;
	}

	public int getReffunc() {
		return reffunc;
	}

	public void setReffunc(int reffunc) {
		this.reffunc = reffunc;
	}

	public byte getShowflg() {
		return showflg;
	}

	public void setShowflg(byte showflg) {
		this.showflg = showflg;
	}

	public int getOrderflg() {
		return orderflg;
	}

	public void setOrderflg(int orderflg) {
		this.orderflg = orderflg;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public int validate() {
		String s = DataUtil.toText(this.name);
		if (DataUtil.isEmpty(s) || s.length() > 50) {
			return Err.CMPNAV_NAME_ERROR;
		}
		if (this.reffunc <= 0) {
			return Err.CMPNAV_REFFUNC_ERROR;
		}
		if (this.reffunc == REFFUNC_LISTCONTENT) {
			if (this.showflg == 0) {
				return Err.CMPNAV_SHOWFLG_ERROR;
			}
		}
		if (this.fileShowLink != null && this.fileShowLink.length() > 200) {
			return Err.CMPNAV_FILESHOWLINK_ERROR;
		}
		if (!HkValidate.validateLength(this.title, true, 100)) {
			return Err.CMPNAV_TITLE_ERROR;
		}
		if (!HkValidate.validateLength(this.intro, true, 200)) {
			return Err.CMPNAV_INTRO_ERROR;
		}
		if (!HkValidate.validateLength(this.url, true, 200)) {
			return Err.CMPNAV_URL_ERROR;
		}
		return Err.SUCCESS;
	}

	public boolean isDirectory() {
		if (this.reffunc == REFFUNC_NONE) {
			return true;
		}
		return false;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public byte getFileflg() {
		return fileflg;
	}

	public void setFileflg(byte fileflg) {
		this.fileflg = fileflg;
	}

	public boolean isTopNLevel() {
		if (this.nlevel == NLEVEL_1) {
			return true;
		}
		return false;
	}

	public boolean isSecondNLevel() {
		if (this.nlevel == NLEVEL_2) {
			return true;
		}
		return false;
	}

	public boolean isImageShow() {
		if (this.fileflg == CmpFile.FILEFLG_IMG) {
			return true;
		}
		return false;
	}

	public boolean isFlashShow() {
		if (this.fileflg == CmpFile.FILEFLG_SWF) {
			return true;
		}
		return false;
	}

	public boolean isArticleList() {
		if (this.reffunc == REFFUNC_LISTCONTENT) {
			return true;
		}
		return false;
	}

	public boolean isArticleSingle() {
		if (this.reffunc == REFFUNC_SINGLECONTENT) {
			return true;
		}
		return false;
	}

	public String getPicHUrl() {
		return ImageConfig.getCmpNavPicHUrl(this.filepath);
	}

	public String getPicSUrl() {
		return ImageConfig.getCmpNavPicSUrl(this.filepath);
	}

	public String getBgPicUrl() {
		return ImageConfig.getCmpNavBgPicUrl(this.bgPicPath);
	}

	public String getFlashHUrl() {
		return ImageConfig.getCmpNavFlashHUrl(this.filepath);
	}

	public int getShowInHome() {
		return showInHome;
	}

	public void setShowInHome(int showInHome) {
		this.showInHome = showInHome;
	}

	public List<CmpNav> children;

	public void addChild(CmpNav cmpNav) {
		if (this.children == null) {
			this.children = new ArrayList<CmpNav>();
		}
		this.children.add(cmpNav);
	}

	public List<CmpNav> getChildren() {
		return children;
	}

	public boolean isCanShowInHome() {
		if (this.reffunc == REFFUNC_HOME || this.reffunc == REFFUNC_NONE
				|| this.reffunc == REFFUNC_CONTACT
				|| this.reffunc == REFFUNC_SELNET
				|| this.reffunc == REFFUNC_PRODUCT
				|| this.reffunc == REFFUNC_MSG) {
			return false;
		}
		return true;
	}

	public boolean isShowOnLeft() {
		if (this.homepos == HOMEPOS_LEFT) {
			return true;
		}
		return false;
	}

	public boolean isShowOnMiddle() {
		if (this.homepos == HOMEPOS_MIDDLE) {
			return true;
		}
		return false;
	}

	public boolean isShowOnRight() {
		if (this.homepos == HOMEPOS_RIGHT) {
			return true;
		}
		return false;
	}

	/**
	 * 功能是否是首页的导航
	 * 
	 * @return
	 *         2010-5-17
	 */
	public boolean isHomeNav() {
		if (this.reffunc == REFFUNC_HOME) {
			return true;
		}
		return false;
	}

	public byte getHomepos() {
		return homepos;
	}

	public void setHomepos(byte homepos) {
		this.homepos = homepos;
	}

	/**
	 * 文章是否是以图片列表方式展示
	 * 
	 * @return
	 *         2010-5-19
	 */
	public boolean isArticleListWithImgShow() {
		if (this.showflg == SHOWFLG_IMG) {
			return true;
		}
		return false;
	}

	public byte getApplyformflg() {
		return applyformflg;
	}

	public void setApplyformflg(byte applyformflg) {
		this.applyformflg = applyformflg;
	}

	/**
	 * 文章是否需要挂接加盟申请表单
	 * 
	 * @return
	 *         2010-5-23
	 */
	public boolean isHasApplyForm() {
		if (this.applyformflg == APPLYFORMFLG_Y) {
			return true;
		}
		return false;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public byte getFileShowflg() {
		return fileShowflg;
	}

	public void setFileShowflg(byte fileShowflg) {
		this.fileShowflg = fileShowflg;
	}

	public String getFileShowLink() {
		return fileShowLink;
	}

	public void setFileShowLink(String fileShowLink) {
		this.fileShowLink = fileShowLink;
	}

	public boolean isFileShowRow() {
		if (this.fileShowflg == FILESHOWFLG_ALL) {
			return true;
		}
		return false;
	}

	public boolean isCmpBbsFunc() {
		if (this.reffunc == REFFUNC_BBS) {
			return true;
		}
		return false;
	}

	public long getKindId() {
		return kindId;
	}

	public void setKindId(long kindId) {
		this.kindId = kindId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCssData() {
		return cssData;
	}

	public void setCssData(String cssData) {
		this.cssData = cssData;
	}

	public String getBgPicPath() {
		return bgPicPath;
	}

	public void setBgPicPath(String bgPicPath) {
		this.bgPicPath = bgPicPath;
	}

	public CmpNavPageCssObj getCmpNavPageCssObj() {
		if (DataUtil.isEmpty(this.cssData)) {
			return null;
		}
		CmpNavPageCssObj cmpNavPageCssObj = new CmpNavPageCssObj(this.cssData);
		return cmpNavPageCssObj;
	}

	public byte getBgflg() {
		return bgflg;
	}

	public void setBgflg(byte bgflg) {
		this.bgflg = bgflg;
	}

	/**
	 * 是否使用默认背景图片
	 * 
	 * @return
	 *         2010-8-2
	 */
	public boolean isBgDefault() {
		if (this.bgflg == BGFLG_DEFAULT) {
			return true;
		}
		return false;
	}

	/**
	 * 是否使用自设背景图片
	 * 
	 * @return
	 *         2010-8-2
	 */
	public boolean isBgSet() {
		if (this.bgflg == BGFLG_SET) {
			return true;
		}
		return false;
	}

	/**
	 * 是否不使用背景图片
	 * 
	 * @return
	 *         2010-8-2
	 */
	public boolean isBgNone() {
		if (this.bgflg == BGFLG_NONE) {
			return true;
		}
		return false;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isUrlLink() {
		if (this.reffunc == REFFUNC_LINK) {
			return true;
		}
		return false;
	}

	public boolean isLoginAndRefFunc() {
		if (this.reffunc == REFFUNC_LOGINANDREG) {
			return true;
		}
		return false;
	}

	public boolean isReturnHome() {
		if (this.reffunc == REFFUNC_RETURNHOME) {
			return true;
		}
		return false;
	}

	public boolean isNoAdminFunc() {
		if (this.isDirectory()) {
			return true;
		}
		if (this.isHomeNav()) {
			return true;
		}
		if (this.isUrlLink()) {
			return true;
		}
		if (this.isLoginAndRefFunc()) {
			return true;
		}
		if (this.isReturnHome()) {
			return true;
		}
		return false;
	}
}