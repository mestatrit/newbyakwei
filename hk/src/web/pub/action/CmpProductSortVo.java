package web.pub.action;

import java.util.List;

import com.hk.bean.CmpArticle;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpProductSortFile;

public class CmpProductSortVo {

	private CmpProductSort cmpProductSort;

	/**
	 * 推荐的产品
	 */
	private List<CmpProduct> pinkList;

	/**
	 * 推荐的分类文章
	 */
	private List<CmpArticle> pinkCmpArticleList;

	/**
	 * 分类的广告图片
	 */
	private List<CmpProductSortFile> cmpProductSortFileList;

	public CmpProductSort getCmpProductSort() {
		return cmpProductSort;
	}

	public void setCmpProductSort(CmpProductSort cmpProductSort) {
		this.cmpProductSort = cmpProductSort;
	}

	public List<CmpProduct> getPinkList() {
		return pinkList;
	}

	public void setPinkList(List<CmpProduct> pinkList) {
		this.pinkList = pinkList;
	}

	public List<CmpArticle> getPinkCmpArticleList() {
		return pinkCmpArticleList;
	}

	public void setPinkCmpArticleList(List<CmpArticle> pinkCmpArticleList) {
		this.pinkCmpArticleList = pinkCmpArticleList;
	}

	public List<CmpProductSortFile> getCmpProductSortFileList() {
		return cmpProductSortFileList;
	}

	public void setCmpProductSortFileList(
			List<CmpProductSortFile> cmpProductSortFileList) {
		this.cmpProductSortFileList = cmpProductSortFileList;
	}
}