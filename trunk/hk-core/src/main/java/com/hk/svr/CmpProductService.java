package com.hk.svr;

import java.util.List;
import java.util.Map;

import com.hk.bean.CmdProduct;
import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductAttr;
import com.hk.bean.CmpProductFav;
import com.hk.bean.CmpProductPhoto;
import com.hk.bean.CmpProductReview;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpProductSortAttr;
import com.hk.bean.CmpProductSortAttrModule;
import com.hk.bean.CmpProductTag;
import com.hk.bean.CmpProductTagRef;
import com.hk.bean.CmpProductUserScore;
import com.hk.bean.CmpProductUserStatus;
import com.hk.bean.UserCmpProductReview;
import com.hk.bean.UserProduct;

public interface CmpProductService {

	/**
	 * 创建产品
	 * 
	 * @param cmpProduct
	 * @return false:有重名,true:创建成功
	 */
	boolean createCmpProduct(CmpProduct cmpProduct);

	boolean updateProduct(CmpProduct cmpProduct);

	/**
	 * 创建分类数据，更新现有父分类的是否有子分类标识
	 * 
	 * @param cmpProductSort
	 *            2010-6-2
	 */
	void createCmpProductSort(CmpProductSort cmpProductSort);

	/**
	 * 保存分类数据，当前数据的原父分类如果没有子分类时，更新原父分类的是否有子分类的标识，更新现有父分类的是否有子分类标识
	 * 
	 * @param old_parentId 原父分类id
	 * @param cmpProductSort
	 */
	void updateCmpProductSort(CmpProductSort cmpProductSort);

	/**
	 * @param companyId
	 * @param orderByProductCount false 时，显示所有数据，并且按照id倒排 ,true时直线式有产品的分类
	 * @return
	 */
	List<CmpProductSort> getCmpProductSortList(long companyId);

	List<CmpProductSort> getCmpProductSortListByParentId(long companyId,
			int parentId);

	List<CmpProductSort> getCmpProductSortListByNlevel(long companyId,
			int nlevel);

	List<CmpProductSort> getCmpProductSortListByCompanyId(long companyId);

	CmpProduct getCmpProduct(long productId);

	CmpProductSort getCmpProductSort(int sortId);

	List<CmpProductSort> getCmpProductSortInId(long companyId,
			List<Integer> idList);

	/**
	 * 删除分类，分类中的产品sortid设置为0，删除相关产品分类属性模板以及属性值
	 * 
	 * @param sortId
	 */
	void deleteCmpPorductSort(int sortId);

	void deleteCmpProduct(long productId);

	/**
	 * @param companyId
	 * @param name 为null时忽略此条件
	 * @param sortId 为0时忽略此条件
	 * @param sellStatus -1可忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpProduct> getCmpProductList(long companyId, String name, int sortId,
			byte sellStatus, int begin, int size);

	int countCmpProduct(long companyId, String name, int sortId, byte sellStatus);

	/**
	 * 正在销售中的产品
	 * 
	 * @param companyId
	 * @param sortId 为0时忽略此条件
	 * @param filterProductId 去除的productId，为0时，忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpProduct> getCmpProductList(long companyId, int sortId,
			long filterProductId, int begin, int size);

	/**
	 * @param companyId
	 * @param sortId 为0时忽略此条件
	 * @param filterProductId 去除的productId，为0时，忽略此条件
	 * @return
	 */
	int countCmpProduct(long companyId, int sortId, long filterProductId);

	void createCmpProductPhoto(CmpProductPhoto cmpProductPhoto);

	void updateCmpProductPhoto(CmpProductPhoto cmpProductPhoto);

	void deleteCmpProductPhoto(long oid);

	List<CmpProductPhoto> getCmpProductPhotoListByProductId(long productId,
			int begin, int size);

	CmpProductPhoto getCmpProductPhoto(long oid);

	void updateCmpProductHeadPath(long productId, String path);

	void stopSell(long productId);

	void runSell(long productId);

	void createReview(CmpProductReview cmpProductReview);

	void updateReview(CmpProductReview cmpProductReview);

	void deleteReview(long labaId);

	void createCmpProductUserScore(long userId, long productId, int score);

	void createCmpProductUserStatus(long userId, long productId, byte status);

	List<CmpProductReview> getCmpProductReviewListByProductId(long productId,
			int begin, int size);

	CmpProductReview getCmpProductReview(long labaId);

	void deleteUserCmpProductReview(long userId, long productId);

	/**
	 * 根据产品名字生成的标签，可查询其他还有同名产品的足迹
	 * 
	 * @param tagId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpProductTagRef> getCmpProductTagRefListByTagId(long tagId,
			int begin, int size);

	/**
	 * 匹配名字的标签
	 * 
	 * @param name 精确查询
	 * @return
	 */
	CmpProductTag getCmpProductTagByName(String name);

	/**
	 * 喜欢这里的用户
	 * 
	 * @param productId
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpProductUserScore> getCmpProductUserScoreGoodListByProductId(
			long productId, int begin, int size);

	/**
	 * 收藏产品
	 * 
	 * @param userId
	 * @param productId
	 * @return true 收藏成功，false,已经收藏过，不再次收藏
	 */
	boolean favProduct(long userId, long productId, long companyId);

	/**
	 * 是否收藏过此产品
	 * 
	 * @param userId
	 * @param productId
	 * @return true:已经收藏 false:没有收藏
	 */
	boolean isFavProduct(long userId, long productId);

	/**
	 * 取消收藏
	 * 
	 * @param userId
	 * @param productId
	 */
	void deleteFavProduct(long userId, long productId);

	CmpProductUserStatus getCmpProductUserStatus(long userId, long productId);

	Map<Long, CmpProduct> getCmpProductMapInId(List<Long> idList);

	/**
	 * 用户是否点评过
	 */
	boolean hasReviewed(long userId, long productId);

	List<UserCmpProductReview> getUserCmpProductReviewList(long userId,
			int begin, int size);

	List<CmpProductReview> getCmpProductReviewListByUserId(long userId,
			int begin, int size);

	List<CmpProductReview> getCmpProductReviewListInId(List<Long> idList);

	List<CmpProduct> getCmpProductListInId(List<Long> idList);

	List<CmpProduct> getCmpProductListByCompanyIdAndNameForSell(long companyId,
			String name, int begin, int size);

	List<CmpProduct> getCmpProductListByCompanyIdAndPnumForSell(long companyId,
			String pnum, int begin, int size);

	List<CmpProduct> getCmpProductListByCompanyIdAndShortNameForSell(
			long companyId, String shortName, int begin, int size);

	void updateUid(long companyId, long uid);

	/**
	 * @param uid
	 * @param name 模糊匹配条件，为空忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmpProduct> getCmpProductListByUid(long uid, String name, int begin,
			int size);

	List<CmpProduct> getCmpProductListByCmpUnionKindId(long cmpUninKindId,
			int begin, int size);

	List<UserProduct> getUserProductListByUserId(long userId, int begin,
			int size);

	List<CmpProductFav> getCmpProductFavListByUserId(long userId, int begin,
			int size);

	CmpProductFav getCmpProductFav(long userId, long productId);

	void createUserProduct(long userId, List<Long> idList, long companyId);

	/**
	 * 创建推荐产品
	 * 
	 * @param cmdProduct
	 * @return true:创建成功 false:已经存在
	 */
	boolean createCmdProduct(CmdProduct cmdProduct);

	/**
	 * 删除推荐产品
	 * 
	 * @param oid
	 */
	void deleteCmdProduct(long oid);

	/**
	 * 根据条件查询推荐产品
	 * 
	 * @param pcityId 为0时忽略此条件
	 * @param begin
	 * @param size
	 * @return
	 */
	List<CmdProduct> getCmdProductList(int pcityId, int begin, int size);

	/**
	 * @param pcityId 为0可忽略此条件
	 * @return
	 */
	int countCmdProduct(int pcityId);

	void updateCmpProductPcityIdByCompanyId(long companyId, int pcityId);

	List<CmpProduct> getCmpProductListByPcityIdAndGood(int pcityId, int begin,
			int size);

	int countCmpProductByPcityIdAndGood(int pcityId);

	CmdProduct getCmdProduct(long oid);

	void updateCmpProductCmppink(long productId, byte cmppink);

	List<CmpProduct> getCmpProductListByCompanyIdForCmppink(long companyId,
			int begin, int size);

	List<CmpProduct> getCmpProductListByCompanyIdAndSortIdForCmppink(
			long companyId, int sortId, int begin, int size);

	/**
	 * 企业的产品集合
	 * 
	 * @param companyId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-5-18
	 */
	List<CmpProduct> getCmpProductListByCompanyId(long companyId, int begin,
			int size);

	int countCmpProductListByCompanyId(long companyId);

	/**
	 * 企业某个分类的产品集合
	 * 
	 * @param companyId
	 * @param sortId
	 * @param begin
	 * @param size
	 * @return
	 *         2010-5-18
	 */
	List<CmpProduct> getCmpProductListByCompanyIdAndSortId(long companyId,
			int sortId, int begin, int size);

	int countCmpProductListByCompanyIdAndSortId(long companyId, int sortId);

	/**
	 * 获取在某个产品附近范围的其他产品集合
	 * 
	 * @param companyId
	 * @param productId 产品id
	 * @param rangefix >0时为某个产品之后, <0某个产品之前
	 * @param size
	 * @return
	 *         2010-5-23
	 */
	List<CmpProduct> getCmpProductListByCompanyIdForRange(long companyId,
			long productId, int orderflg, int rangefix, int size);

	/**
	 * 获取在某个分类的产品附近范围的其他产品集合
	 * 
	 * @param companyId
	 * @param productId 产品id
	 * @param rangefix >0时为某个产品之后, <0某个产品之前
	 * @param size
	 * @return
	 *         2010-5-23
	 */
	List<CmpProduct> getCmpProductListByCompanyIdAndSortIdForRange(
			long companyId, int sortId, long productId, int orderflg,
			int rangefix, int size);

	void updateCmpProductOrderflg(long productId, int orderflg);

	void createCmpProductSortAttrModule(
			CmpProductSortAttrModule cmpProductSortAttrModule);

	void updateCmpProductSortAttrModule(
			CmpProductSortAttrModule cmpProductSortAttrModule);

	void createCmpProductSortAttr(CmpProductSortAttr cmpProductSortAttr);

	void updateCmpProductSortAttr(CmpProductSortAttr cmpProductSortAttr);

	void deleteCmpProductSortAttr(long attrId);

	void createCmpProductAttr(CmpProductAttr cmpProductAttr);

	void updateCmpProductAttr(CmpProductAttr cmpProductAttr);

	CmpProductSortAttrModule getCmpProductSortAttrModule(int sortId);

	CmpProductSortAttr getCmpProductSortAttr(long attrId);

	CmpProductAttr getCmpProductAttr(long productId);

	List<CmpProductSortAttr> getCmpProductSortAttrListBySortIdAndAttrflg(
			int sortId, int attrflg);

	Map<Long, CmpProductSortAttr> getCmpProductSortAttrMapByCompanyIdAndSortIdAndInId(
			long companyId, int sortId, List<Long> idList);

	List<CmpProductSortAttr> getCmpProductSortAttrListByCompanyIdAndSortIdAndInId(
			long companyId, int sortId, List<Long> idList);

	List<CmpProductSortAttr> getCmpProductSortAttrListBySortId(int sortId);

	List<CmpProductSortAttr> getCmpProductSortAttrListInId(List<Long> idList);

	Map<Long, CmpProductSortAttr> getCmpProductSortAttrMapInId(List<Long> idList);

	List<CmpProductSort> getCmpProductSortListByCompanyIdAndChildflg(
			long companyId, byte childflg);

	/**
	 * 产品查询
	 * 
	 * @param companyId 必须为有效值，否则查询呢数据无效
	 * @param sortId 分类id 可为0，则不进入查询条件
	 * @param name 产品名称可为null，则不进入查询条件
	 * @param begin
	 * @param size
	 * @return
	 *         2010-6-17
	 */
	List<CmpProduct> getCmpProductListByCompanyIdEx(long companyId, int sortId,
			String name, int begin, int size);

	int countCmpProductListByCompanyIdEx(long companyId, int sortId, String name);
}