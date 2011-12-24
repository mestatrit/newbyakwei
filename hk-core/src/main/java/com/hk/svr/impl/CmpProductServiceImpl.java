package com.hk.svr.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.hk.bean.CmpUtil;
import com.hk.bean.UserCmpProductReview;
import com.hk.bean.UserProduct;
import com.hk.frame.dao.query.Query;
import com.hk.frame.dao.query.QueryManager;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpProductService;
import com.hk.svr.pub.ChineseSpelling;

public class CmpProductServiceImpl implements CmpProductService {

	@Autowired
	private QueryManager manager;

	public boolean createCmpProduct(CmpProduct cmpProduct) {
		cmpProduct.setCmppinkTime(new Date());
		cmpProduct.setSellStatus(CmpProduct.SELLSTATUS_Y);
		if (cmpProduct.getShortName() == null) {
			cmpProduct.setShortName(ChineseSpelling.getShortSpell(cmpProduct
					.getName()));
			if (cmpProduct.getShortName() != null) {
				if (cmpProduct.getShortName().length() > 50) {
					cmpProduct.setShortName(DataUtil.subString(cmpProduct
							.getShortName(), 50));
				}
			}
		}
		Query query = manager.createQuery();
		CmpProduct o = this.getCmpProductByName(cmpProduct.getCompanyId(),
				cmpProduct.getName());
		if (o != null) {
			return false;
		}
		long id = query.insertObject(cmpProduct).longValue();
		cmpProduct.setProductId(id);
		// 聚合网站产品名标签
		CmpProductTag tag = this.createTag(cmpProduct.getName());
		this.createTagRef(cmpProduct.getCompanyId(), tag.getTagId());
		// this.updateCmpProductCount(cmpProduct.getSortId());
		return true;
	}

	// private void updateCmpProductCount(int sortId) {
	// if (sortId <= 0) {
	// return;
	// }
	// Query query = manager.createQuery();
	// int count = query.count(CmpProduct.class, "sortid=?",
	// new Object[] { sortId });
	// query.addField("productcount", count);
	// query.updateById(CmpProductSort.class, sortId);
	// }
	private CmpProductTag createTag(String name) {
		Query query = manager.createQuery();
		CmpProductTag o = query.getObjectEx(CmpProductTag.class, "name=?",
				new Object[] { name });
		if (o == null) {
			o = new CmpProductTag();
			o.setName(name);
			query.addField("name", o.getName());
			long tagId = query.insert(CmpProductTag.class).longValue();
			o.setTagId(tagId);
		}
		return o;
	}

	private boolean createTagRef(long companyId, long tagId) {
		Query query = manager.createQuery();
		if (query.count(CmpProductTagRef.class, "companyid=? and tagid=?",
				new Object[] { companyId, tagId }) == 0) {
			query.addField("companyid", companyId);
			query.addField("tagid", tagId);
			query.insert(CmpProductTagRef.class);
			return true;
		}
		return false;
	}

	public CmpProduct getCmpProductByName(long companyId, String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpProduct.class, "companyid=? and name=?",
				new Object[] { companyId, name });
	}

	public void createCmpProductSort(CmpProductSort cmpProductSort) {
		Query query = manager.createQuery();
		if (cmpProductSort.getParentId() == 0) {
			cmpProductSort.setNlevel(1);
		}
		CmpProductSort parent = null;
		if (cmpProductSort.getParentId() > 0) {
			parent = this.getCmpProductSort(cmpProductSort.getParentId());
		}
		String parentData = null;
		if (parent != null) {
			cmpProductSort.setNlevel(parent.getNlevel() + 1);
			if (parent.getParentData() != null) {
				parentData = parent.getParentData() + "," + parent.getSortId();
			}
			else {
				parentData = String.valueOf(parent.getSortId());
			}
		}
		else {
			parentData = "0";
		}
		cmpProductSort.setParentData(parentData);
		int sortId = query.insertObject(cmpProductSort).intValue();
		cmpProductSort.setSortId(sortId);
		if (parent != null) {
			parent.setChildflg(CmpProductSort.CHILDFLG_Y);
			this.updateCmpProductSort(parent);
		}
	}

	public CmpProduct getCmpProduct(long productId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpProduct.class, productId);
	}

	public CmpProductSort getCmpProductSort(int sortId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpProductSort.class, sortId);
	}

	public List<CmpProductSort> getCmpProductSortList(long companyId) {
		Query query = manager.createQuery();
		String orderstr = null;
		String where = null;
		Object[] param = null;
		where = "companyid=?";
		orderstr = "sortid asc";
		param = new Object[] { companyId };
		return query.listEx(CmpProductSort.class, where, param, orderstr);
	}

	public List<CmpProductSort> getCmpProductSortListByParentId(long companyId,
			int parentId) {
		Query query = manager.createQuery();
		return query.listEx(CmpProductSort.class, "companyid=? and parentid=?",
				new Object[] { companyId, parentId }, "sortid desc");
	}

	public List<CmpProductSort> getCmpProductSortListByNlevel(long companyId,
			int nlevel) {
		Query query = manager.createQuery();
		return query.listEx(CmpProductSort.class, "companyid=? and nlevel=?",
				new Object[] { companyId, nlevel }, "sortid desc");
	}

	private void updateReviewCount(long productId) {
		Query query = manager.createQuery();
		int count = query.count(CmpProductReview.class, "productid=?",
				new Object[] { productId });
		query.addField("reviewcount", count);
		query.updateById(CmpProduct.class, productId);
	}

	public boolean updateProduct(CmpProduct cmpProduct) {
		CmpProduct o = this.getCmpProductByName(cmpProduct.getCompanyId(),
				cmpProduct.getName());
		if (o != null && o.getProductId() != cmpProduct.getProductId()) {
			return false;
		}
		if (cmpProduct.getShortName() == null) {
			cmpProduct.setShortName(ChineseSpelling.getShortSpell(cmpProduct
					.getName()));
		}
		Query query = manager.createQuery();
		// CmpProduct old = this.getCmpProduct(cmpProduct.getProductId());
		// this.updateCmpProductCount(old.getSortId());
		query.updateObject(cmpProduct);
		// 聚合网站产品名标签
		CmpProductTag tag = this.createTag(cmpProduct.getName());
		this.createTagRef(cmpProduct.getCompanyId(), tag.getTagId());
		// this.updateCmpProductCount(cmpProduct.getSortId());
		return true;
	}

	public void updateCmpProductSort(CmpProductSort cmpProductSort) {
		Query query = manager.createQuery();
		query.updateObject(cmpProductSort);
	}

	public void deleteCmpPorductSort(int sortId) {
		Query query = manager.createQuery();
		CmpProductSort sort = this.getCmpProductSort(sortId);
		if (sort == null) {
			return;
		}
		query.deleteById(CmpProductSort.class, sortId);
		// 更新父分类是否有子分类
		if (sort.getParentId() > 0) {
			CmpProductSort parent = this.getCmpProductSort(sort.getParentId());
			if (parent != null) {
				int sortCount = query.count(CmpProductSort.class,
						"companyid=? and parentid=?", new Object[] {
								sort.getCompanyId(), parent.getSortId() });
				if (sortCount > 0) {
					parent.setChildflg(CmpProductSort.CHILDFLG_Y);
				}
				else {
					parent.setChildflg(CmpProductSort.CHILDFLG_N);
				}
				this.updateCmpProductSort(parent);
			}
		}
		List<CmpProduct> list = query.listEx(CmpProduct.class, "sortid=?",
				new Object[] { sortId });
		for (CmpProduct o : list) {
			o.setSortId(0);
			this.updateProduct(o);
		}
	}

	public List<CmpProduct> getCmpProductList(long companyId, String name,
			int sortId, byte sellStatus, int begin, int size) {
		Query query = manager.createQuery();
		List<Object> olist = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"select * from cmpproduct where companyid=? and delflg=?");
		olist.add(companyId);
		olist.add(CmpProduct.DELFLG_N);
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (sortId > 0) {
			sql.append(" and sortid=?");
			olist.add(sortId);
		}
		if (sellStatus > -1) {
			sql.append(" and sellstatus=?");
			olist.add(sellStatus);
		}
		sql.append(" order by orderflg desc, productid desc");
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpProduct.class, olist);
	}

	public int countCmpProduct(long companyId, String name, int sortId,
			byte sellStatus) {
		Query query = manager.createQuery();
		List<Object> olist = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"select count(*) from cmpproduct where companyid=? and delflg=?");
		olist.add(companyId);
		olist.add(CmpProduct.DELFLG_N);
		if (!DataUtil.isEmpty(name)) {
			sql.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		if (sortId > 0) {
			sql.append(" and sortid=?");
			olist.add(sortId);
		}
		if (sellStatus > -1) {
			sql.append(" and sellstatus=?");
			olist.add(sellStatus);
		}
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public void deleteCmpProduct(long productId) {
		// CmpProduct o = this.getCmpProduct(productId);
		Query query = manager.createQuery();
		query.deleteById(CmpProduct.class, productId);
		// this.updateCmpProductCount(o.getSortId());
	}

	public List<CmpProduct> getCmpProductList(long companyId, int sortId,
			long filterProductId, int begin, int size) {
		StringBuilder sql = new StringBuilder(
				"select * from cmpproduct where companyid=? and sellstatus=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		olist.add(CmpProduct.SELLSTATUS_Y);
		if (sortId > 0) {
			sql.append(" and sortid=?");
			olist.add(sortId);
		}
		if (filterProductId > 0) {
			sql.append(" and productid !=?");
			olist.add(filterProductId);
		}
		sql.append(" order by orderflg desc,productid desc");
		Query query = manager.createQuery();
		return query.listBySqlParamList("ds1", sql.toString(), begin, size,
				CmpProduct.class, olist);
	}

	public int countCmpProduct(long companyId, int sortId, long filterProductId) {
		StringBuilder sql = new StringBuilder(
				"select count(*) from cmpproduct where companyid=?");
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (sortId > 0) {
			sql.append(" and sortid=?");
			olist.add(sortId);
		}
		if (filterProductId > 0) {
			sql.append(" and productid !=?");
			olist.add(filterProductId);
		}
		Query query = manager.createQuery();
		return query.countBySql("ds1", sql.toString(), olist);
	}

	public void createCmpProductPhoto(CmpProductPhoto cmpProductPhoto) {
		Query query = manager.createQuery();
		query.addField("photoid", cmpProductPhoto.getPhotoId());
		query.addField("productid", cmpProductPhoto.getProductId());
		query.addField("companyid", cmpProductPhoto.getCompanyId());
		query.addField("path", cmpProductPhoto.getPath());
		// query.addField("name", cmpProductPhoto.getName());
		query.addField("userid", cmpProductPhoto.getUserId());
		query.insert(CmpProductPhoto.class);
	}

	public void updateCmpProductPhoto(CmpProductPhoto cmpProductPhoto) {
		Query query = manager.createQuery();
		query.addField("photoid", cmpProductPhoto.getPhotoId());
		query.addField("productid", cmpProductPhoto.getProductId());
		query.addField("companyid", cmpProductPhoto.getCompanyId());
		query.addField("path", cmpProductPhoto.getPath());
		// query.addField("name", cmpProductPhoto.getName());
		query.addField("userid", cmpProductPhoto.getUserId());
		query.updateById(CmpProductPhoto.class, cmpProductPhoto.getOid());
	}

	public void deleteCmpProductPhoto(long oid) {
		Query query = manager.createQuery();
		query.deleteById(CmpProductPhoto.class, oid);
	}

	public List<CmpProductPhoto> getCmpProductPhotoListByProductId(
			long productId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpProductPhoto.class, "productid=?",
				new Object[] { productId }, "oid desc", begin, size);
	}

	public CmpProductPhoto getCmpProductPhoto(long oid) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpProductPhoto.class, oid);
	}

	public void updateCmpProductHeadPath(long productId, String path) {
		Query query = manager.createQuery();
		query.addField("headpath", path);
		query.updateById(CmpProduct.class, productId);
	}

	public void updateCmpProductPhotoName(long oid, String name) {
		Query query = manager.createQuery();
		query.addField("name", name);
		query.updateById(CmpProduct.class, oid);
	}

	public void runSell(long productId) {
		this.updateSelStatus(productId, CmpProduct.SELLSTATUS_Y);
	}

	public void stopSell(long productId) {
		this.updateSelStatus(productId, CmpProduct.SELLSTATUS_N);
	}

	public void updateSelStatus(long productId, byte sellStatus) {
		Query query = manager.createQuery();
		query.addField("sellstatus", sellStatus);
		query.updateById(CmpProduct.class, productId);
	}

	public void createCmpProductUserScore(long userId, long productId, int score) {
		if (userId <= 0) {
			throw new RuntimeException("userId can not <=0");
		}
		if (productId <= 0) {
			throw new RuntimeException("productId can not <=0");
		}
		Query query = manager.createQuery();
		CmpProductUserScore o = query.getObjectEx(CmpProductUserScore.class,
				"userid=? and productid=?", new Object[] { userId, productId });
		if (o == null) {// 如果没有评分，创建评分
			o = new CmpProductUserScore();
			o.setUserId(userId);
			o.setProductId(productId);
			o.setScore(score);
			query.addField("userid", o.getUserId());
			query.addField("productid", o.getProductId());
			query.addField("score", o.getScore());
			query.insert(CmpProductUserScore.class);
		}
		else {// 更新评分
			o.setScore(score);
			query.addField("userid", o.getUserId());
			query.addField("productid", o.getProductId());
			query.addField("score", o.getScore());
			query.update(CmpProductUserScore.class, "userid=? and productid=?",
					new Object[] { userId, productId });
		}
		// 更新用户点评中的打分字段
		query.addField("score", score);
		query.update(CmpProductReview.class, "userid=? and productid=?",
				new Object[] { userId, productId });
		// 重新计算总分
		String sql = "select sum(score),count(*) from cmpproductuserscore where productid=?";
		List<Object[]> list = query.listdata("ds1", sql, productId);
		int totalScore = 0;
		int userCount = 0;
		if (list.size() > 0) {
			Object[] objs = list.iterator().next();
			totalScore = Integer.parseInt(objs[0].toString());
			userCount = Integer.parseInt(objs[1].toString());
		}
		// 更新产品打分记录
		query.addField("score", totalScore);
		query.addField("scoreusercount", userCount);
		query.updateById(CmpProduct.class, productId);
	}

	public void createCmpProductUserStatus(long userId, long productId,
			byte status) {
		Query query = manager.createQuery();
		CmpProductUserStatus o = query.getObjectEx(CmpProductUserStatus.class,
				"userid=? and productid=?", new Object[] { userId, productId });
		if (o == null) {// 没有记录过状态，创建状态
			o = new CmpProductUserStatus();
			o.setProductId(productId);
			o.setUserId(userId);
			o.setUserStatus(status);
			query.addField("productid", o.getProductId());
			query.addField("userid", o.getUserId());
			query.addField("userstatus", o.getUserStatus());
			query.insert(CmpProductUserStatus.class);
		}
		else {// 更新状态
			o.setUserStatus(status);
			query.addField("productid", o.getProductId());
			query.addField("userid", o.getUserId());
			query.addField("userstatus", o.getUserStatus());
			query.updateById(CmpProductUserStatus.class, o.getSysId());
		}
	}

	private CmpProductUserScore getCmpProductUserScore(long userId,
			long productId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpProductUserScore.class,
				"userid=? and productid=?", new Object[] { userId, productId });
	}

	public void createReview(CmpProductReview cmpProductReview) {
		cmpProductReview.setCheckflg(CmpProductReview.CHECKFLG_NORMAL);
		if (cmpProductReview.getCreateTime() == null) {
			cmpProductReview.setCreateTime(new Date());
		}
		// 如果用户没有打分，就调用以前的打分
		if (cmpProductReview.getScore() == 0) {
			CmpProductUserScore o = this.getCmpProductUserScore(
					cmpProductReview.getUserId(), cmpProductReview
							.getProductId());
			if (o != null) {
				cmpProductReview.setScore(o.getScore());
			}
		}
		Query query = manager.createQuery();
		query.addField("labaid", cmpProductReview.getLabaId());
		query.addField("userid", cmpProductReview.getUserId());
		query.addField("productid", cmpProductReview.getProductId());
		query.addField("score", cmpProductReview.getScore());
		query.addField("content", cmpProductReview.getContent());
		query.addField("longcontent", cmpProductReview.getLongContent());
		query.addField("createtime", cmpProductReview.getCreateTime());
		query.addField("sendfrom", cmpProductReview.getSendFrom());
		query.addField("checkflg", cmpProductReview.getCheckflg());
		query.addField("companyid", cmpProductReview.getCompanyId());
		query.insert(CmpProductReview.class);
		// 如果用户没有打分，不更新产品打分字段
		if (cmpProductReview.getScore() != 0) {// 用户重新打分
			this.createCmpProductUserScore(cmpProductReview.getUserId(),
					cmpProductReview.getProductId(), cmpProductReview
							.getScore());
		}
		// 更新评论总人数
		this.updateCmpProductReviewCount(cmpProductReview.getProductId());
		// 创建用户最新产品点评
		this.createUserCmpProductReview(cmpProductReview.getUserId(),
				cmpProductReview.getLabaId(), cmpProductReview.getProductId(),
				cmpProductReview.getCompanyId());
		this.updateReviewCount(cmpProductReview.getProductId());
	}

	private void updateCmpProductReviewCount(long productId) {
		Query query = manager.createQuery();
		int count = query.count(CmpProductReview.class, "productid=?",
				new Object[] { productId });
		query.addField("reviewcount", count);
		query.updateById(CmpProduct.class, productId);
	}

	private void createUserCmpProductReview(long userId, long labaId,
			long productId, long companyId) {
		Query query = manager.createQuery();
		UserCmpProductReview o = query.getObjectEx(UserCmpProductReview.class,
				"userid=? and productid=?", new Object[] { userId, productId });
		if (o != null) {
			query.addField("labaid", labaId);
			query.update(UserCmpProductReview.class,
					"userid=? and productid=?", new Object[] { userId,
							productId });
		}
		else {
			query.addField("userid", userId);
			query.addField("labaid", labaId);
			query.addField("productid", productId);
			query.addField("companyid", companyId);
			query.insert(UserCmpProductReview.class);
		}
	}

	public void deleteReview(long labaId) {
		CmpProductReview o = this.getCmpProductReview(labaId);
		if (o == null) {
			return;
		}
		Query query = manager.createQuery();
		query.deleteById(CmpProductReview.class, labaId);
		this.updateCmpProductReviewCount(labaId);
		// 更新usercmpreview
		CmpProductReview o2 = query.getObject(CmpProductReview.class,
				"productid=? and userid=?", new Object[] { o.getProductId(),
						o.getUserId() }, "labaid desc");
		if (o2 == null) {
			this.deleteUserCmpProductReview(o.getUserId(), o.getProductId());
		}
		else {
			this.createUserCmpProductReview(o.getUserId(), o2.getLabaId(), o
					.getProductId(), o.getCompanyId());
		}
		this.updateReviewCount(o.getProductId());
	}

	public CmpProductReview getCmpProductReview(long labaId) {
		Query query = manager.createQuery();
		return query.getObjectById(CmpProductReview.class, labaId);
	}

	public List<CmpProductReview> getCmpProductReviewListByProductId(
			long productId, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpProductReview.class, "productid=?",
				new Object[] { productId }, "labaid desc", begin, size);
	}

	public void deleteUserCmpProductReview(long userId, long productId) {
		Query query = manager.createQuery();
		query.delete(UserCmpProductReview.class, "userid=? and productid=?",
				new Object[] { userId, productId });
	}

	public void deleteFavProduct(long userId, long productId) {
		Query query = manager.createQuery();
		query.delete(CmpProductFav.class, "userid=? and productid=?",
				new Object[] { userId, productId });
	}

	public boolean favProduct(long userId, long productId, long companyId) {
		Query query = manager.createQuery();
		if (query.count(CmpProductFav.class, "userid=? and productid=?",
				new Object[] { userId, productId }) == 0) {
			query.addField("userid", userId);
			query.addField("productid", productId);
			query.addField("companyid", companyId);
			query.insert(CmpProductFav.class);
			return true;
		}
		return false;
	}

	public CmpProductTag getCmpProductTagByName(String name) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpProductTag.class, "name=?",
				new Object[] { name });
	}

	public List<CmpProductTagRef> getCmpProductTagRefListByTagId(long tagId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpProductTagRef.class, "tagid=?",
				new Object[] { tagId }, "companyid desc", begin, size);
	}

	public List<CmpProductUserScore> getCmpProductUserScoreGoodListByProductId(
			long productId, int begin, int size) {
		Query query = manager.createQuery();
		String sql = "select * from cmpproductuserscore where productid=? and score in (2,3) order by oid desc";
		return query.listBySql("ds1", sql, begin, size,
				CmpProductUserScore.class, productId);
	}

	public boolean isFavProduct(long userId, long productId) {
		Query query = manager.createQuery();
		if (query.count(CmpProductFav.class, "userid=? and productid=?",
				new Object[] { userId, productId }) == 0) {
			return false;
		}
		return true;
	}

	public CmpProductUserStatus getCmpProductUserStatus(long userId,
			long productId) {
		Query query = manager.createQuery();
		return query.getObjectEx(CmpProductUserStatus.class,
				"userid=? and productid=?", new Object[] { userId, productId });
	}

	public Map<Long, CmpProduct> getCmpProductMapInId(List<Long> idList) {
		Map<Long, CmpProduct> map = new HashMap<Long, CmpProduct>();
		List<CmpProduct> list = this.getCmpProductListInId(idList);
		for (CmpProduct o : list) {
			map.put(o.getProductId(), o);
		}
		return map;
	}

	public List<CmpProduct> getCmpProductListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpProduct>();
		}
		Query query = manager.createQuery();
		StringBuilder sql = new StringBuilder(
				"select * from cmpproduct where productid in (");
		for (Long i : idList) {
			sql.append(i).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return query.listBySqlEx("ds1", sql.toString(), CmpProduct.class);
	}

	public void updateReview(CmpProductReview cmpProductReview) {
		Query query = manager.createQuery();
		query.addField("labaid", cmpProductReview.getLabaId());
		query.addField("userid", cmpProductReview.getUserId());
		query.addField("productid", cmpProductReview.getProductId());
		query.addField("score", cmpProductReview.getScore());
		query.addField("content", cmpProductReview.getContent());
		query.addField("longcontent", cmpProductReview.getLongContent());
		query.addField("createtime", cmpProductReview.getCreateTime());
		query.addField("sendfrom", cmpProductReview.getSendFrom());
		query.addField("checkflg", cmpProductReview.getCheckflg());
		query.addField("companyid", cmpProductReview.getCompanyId());
		query.updateById(CmpProductReview.class, cmpProductReview.getLabaId());
		// 如果用户没有打分，不更新产品打分字段
		if (cmpProductReview.getScore() != 0) {// 用户重新打分
			this.createCmpProductUserScore(cmpProductReview.getUserId(),
					cmpProductReview.getProductId(), cmpProductReview
							.getScore());
		}
	}

	public boolean hasReviewed(long userId, long productId) {
		Query query = manager.createQuery();
		if (query.count(CmpProductReview.class, "userid=? and productid=?",
				new Object[] { userId, productId }) > 0) {
			return true;
		}
		return false;
	}

	public List<UserCmpProductReview> getUserCmpProductReviewList(long userId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(UserCmpProductReview.class, "userid=?",
				new Object[] { userId }, "labaid desc", begin, size);
	}

	public List<CmpProductReview> getCmpProductReviewListInId(List<Long> idList) {
		if (idList.size() == 0) {
			return new ArrayList<CmpProductReview>();
		}
		StringBuilder sql = new StringBuilder(
				"select * from cmpproductreview where labaid in(");
		for (Long l : idList) {
			sql.append(l).append(",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		Query query = manager.createQuery();
		return query.listBySqlEx("ds1", sql.toString(), CmpProductReview.class);
	}

	public List<CmpProductReview> getCmpProductReviewListByUserId(long userId,
			int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpProductReview.class, "userid=?",
				new Object[] { userId }, "labaid desc", begin, size);
	}

	public List<CmpProduct> getCmpProductListByCompanyIdAndNameForSell(
			long companyId, String name, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpProduct.class,
				"companyid=? and name like ? and sellstatus=?", new Object[] {
						companyId, name + "%", CmpProduct.SELLSTATUS_Y },
				"productid desc", begin, size);
	}

	public List<CmpProduct> getCmpProductListByCompanyIdAndPnumForSell(
			long companyId, String pnum, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpProduct.class,
				"companyid=? and pnum like ? and sellstatus=?", new Object[] {
						companyId, pnum + "%", CmpProduct.SELLSTATUS_Y },
				"productid desc", begin, size);
	}

	public List<CmpProduct> getCmpProductListByCompanyIdAndShortNameForSell(
			long companyId, String shortName, int begin, int size) {
		Query query = manager.createQuery();
		return query.listEx(CmpProduct.class,
				"companyid=? and shortname like ? and sellstatus=?",
				new Object[] { companyId, shortName + "%",
						CmpProduct.SELLSTATUS_Y }, "productid desc", begin,
				size);
	}

	public void updateUid(long companyId, long uid) {
		Query query = this.manager.createQuery();
		query.addField("uid", uid);
		query.update(CmpProduct.class, "companyid=?",
				new Object[] { companyId });
	}

	public List<CmpProduct> getCmpProductListByUid(long uid, String name,
			int begin, int size) {
		Query query = this.manager.createQuery();
		if (name == null) {
			return query.listEx(CmpProduct.class, "uid=? and sellstatus=?",
					new Object[] { uid, CmpProduct.SELLSTATUS_Y },
					"productid desc", begin, size);
		}
		return query.listEx(CmpProduct.class,
				"uid=? and sellstatus=? and name like ?", new Object[] { uid,
						CmpProduct.SELLSTATUS_Y, "%" + name + "%" },
				"productid desc", begin, size);
	}

	public List<CmpProduct> getCmpProductListByCmpUnionKindId(
			long cmpUninKindId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProduct.class,
				"cmpunionkindid=? and sellstatus=?", new Object[] {
						cmpUninKindId, CmpProduct.SELLSTATUS_Y },
				"productid desc", begin, size);
	}

	public List<CmpProductFav> getCmpProductFavListByUserId(long userId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProductFav.class, "userid=?",
				new Object[] { userId }, "oid desc", begin, size);
	}

	public List<UserProduct> getUserProductListByUserId(long userId, int begin,
			int size) {
		Query query = this.manager.createQuery();
		return query.listEx(UserProduct.class, "userid=?",
				new Object[] { userId }, "oid desc", begin, size);
	}

	public CmpProductFav getCmpProductFav(long userId, long productId) {
		Query query = this.manager.createQuery();
		return query.getObject(CmpProductFav.class, "userid=? and productid=?",
				new Object[] { userId, productId }, null);
	}

	public void createUserProduct(long userId, List<Long> idList, long companyId) {
		Query query = this.manager.createQuery();
		for (Long l : idList) {
			if (query.count(UserProduct.class, "userid=? and productid=?",
					new Object[] { userId, l }) == 0) {
				UserProduct userProduct = new UserProduct();
				userProduct.setUserId(userId);
				userProduct.setProductId(l);
				userProduct.setCompanyId(companyId);
				query.insertObject(userProduct);
			}
		}
	}

	public boolean createCmdProduct(CmdProduct cmdProduct) {
		Query query = this.manager.createQuery();
		if (query.count(CmdProduct.class, "productid=?",
				new Object[] { cmdProduct.getProductId() }) > 0) {
			return false;
		}
		cmdProduct.setOid(query.insertObject(cmdProduct).longValue());
		return true;
	}

	public void deleteCmdProduct(long oid) {
		Query query = this.manager.createQuery();
		query.deleteById(CmdProduct.class, oid);
	}

	public List<CmdProduct> getCmdProductList(int pcityId, int begin, int size) {
		Query query = this.manager.createQuery();
		if (pcityId > 0) {
			return query.listEx(CmdProduct.class, "pcityid=?",
					new Object[] { pcityId }, "oid desc", begin, size);
		}
		return query.listEx(CmdProduct.class, "oid desc", begin, size);
	}

	public void updateCmpProductPcityIdByCompanyId(long companyId, int pcityId) {
		Query query = this.manager.createQuery();
		query.addField("pcityid", pcityId);
		query.update(CmpProduct.class, "companyid=?",
				new Object[] { companyId });
	}

	public int countCmpProductByPcityIdAndGood(int pcityId) {
		Query query = this.manager.createQuery();
		String sql = "select count(*) from cmpproduct where pcityid=? and score>0 and score/scoreUserCount>=2";
		return query.countBySql("ds1", sql, pcityId);
	}

	public List<CmpProduct> getCmpProductListByPcityIdAndGood(int pcityId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		String sql = "select * from cmpproduct where pcityid=? and score>0 and score/scoreUserCount>=2";
		return query.listBySql("ds1", sql, begin, size, CmpProduct.class,
				pcityId);
	}

	public CmdProduct getCmdProduct(long oid) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmdProduct.class, oid);
	}

	public int countCmdProduct(int pcityId) {
		Query query = this.manager.createQuery();
		if (pcityId > 0) {
			return query.count(CmdProduct.class, "pcityid=?",
					new Object[] { pcityId });
		}
		return query.count(CmdProduct.class);
	}

	public void updateCmpProductCmppink(long productId, byte cmppink) {
		Query query = this.manager.createQuery();
		query.addField("cmppink", cmppink);
		query.addField("cmppinktime", new Date());
		query.updateById(CmpProduct.class, productId);
	}

	public List<CmpProduct> getCmpProductListByCompanyId(long companyId,
			int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProduct.class, "companyid=?",
				new Object[] { companyId }, "orderflg desc,productid desc",
				begin, size);
	}

	public int countCmpProductListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.count(CmpProduct.class, "companyid=?",
				new Object[] { companyId });
	}

	public List<CmpProduct> getCmpProductListByCompanyIdForCmppink(
			long companyId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProduct.class, "companyid=? and cmppink=?",
				new Object[] { companyId, CmpUtil.CMPPINK_Y },
				"cmppinktime desc", begin, size);
	}

	public List<CmpProduct> getCmpProductListByCompanyIdAndSortIdForCmppink(
			long companyId, int sortId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProduct.class,
				"companyid=? and sortid=? and cmppink=?", new Object[] {
						companyId, sortId, CmpUtil.CMPPINK_Y },
				"cmppinktime desc", begin, size);
	}

	public List<CmpProduct> getCmpProductListByCompanyIdAndSortId(
			long companyId, int sortId, int begin, int size) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProduct.class, "companyid=? and sortid=?",
				new Object[] { companyId, sortId },
				"orderflg desc,productid desc", begin, size);
	}

	public int countCmpProductListByCompanyIdAndSortId(long companyId,
			int sortId) {
		Query query = this.manager.createQuery();
		return query.count(CmpProduct.class, "companyid=? and sortid=?",
				new Object[] { companyId, sortId });
	}

	public List<CmpProduct> getCmpProductListByCompanyIdForRange(
			long companyId, long productId, int orderflg, int rangefix, int size) {
		Query query = this.manager.createQuery();
		if (rangefix <= 0) {
			List<CmpProduct> list = query.listEx(CmpProduct.class,
					"companyid=? and  productid>? and orderflg=?",
					new Object[] { companyId, productId, orderflg },
					"orderflg asc, productid asc", 0, size);
			list
					.addAll(query.listEx(CmpProduct.class,
							"companyid=? and orderflg>?", new Object[] {
									companyId, orderflg },
							"orderflg asc, productid asc", 0, size));
			return DataUtil.subList(list, 0, 3);
		}
		List<CmpProduct> list = query.listEx(CmpProduct.class,
				"companyid=? and productid<? and orderflg=?", new Object[] {
						companyId, productId, orderflg },
				"orderflg desc, productid desc", 0, size);
		list.addAll(query.listEx(CmpProduct.class,
				"companyid=? and orderflg<?", new Object[] { companyId,
						orderflg }, "orderflg desc, productid desc", 0, size));
		return DataUtil.subList(list, 0, 3);
	}

	public List<CmpProduct> getCmpProductListByCompanyIdAndSortIdForRange(
			long companyId, int sortId, long productId, int orderflg,
			int rangefix, int size) {
		Query query = this.manager.createQuery();
		if (rangefix <= 0) {
			List<CmpProduct> list = query.listEx(CmpProduct.class,
					"companyid=? and sortid=? and productid>? and orderflg=?",
					new Object[] { companyId, sortId, productId, orderflg },
					"orderflg asc, productid asc", 0, size);
			list.addAll(query.listEx(CmpProduct.class,
					"companyid=? and sortid=? and orderflg>?", new Object[] {
							companyId, sortId, orderflg },
					"orderflg asc, productid asc", 0, size));
			return DataUtil.subList(list, 0, 3);
		}
		List<CmpProduct> list = query.listEx(CmpProduct.class,
				"companyid=? and sortid=? and productid<? and orderflg=?",
				new Object[] { companyId, sortId, productId, orderflg },
				"orderflg desc, productid desc", 0, size);
		list.addAll(query.listEx(CmpProduct.class,
				"companyid=? and sortid=? and orderflg<?", new Object[] {
						companyId, sortId, orderflg },
				"orderflg desc, productid desc", 0, size));
		return DataUtil.subList(list, 0, 3);
	}

	public void updateCmpProductOrderflg(long productId, int orderflg) {
		Query query = this.manager.createQuery();
		query.addField("orderflg", orderflg);
		query.updateById(CmpProduct.class, productId);
	}

	public List<CmpProductSort> getCmpProductSortInId(long companyId,
			List<Integer> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(CmpProductSort.class, "companyid=?",
				new Object[] { companyId }, "sortid", idList, "sortid asc");
	}

	public void createCmpProductSortAttr(CmpProductSortAttr cmpProductSortAttr) {
		Query query = this.manager.createQuery();
		query.insertObject(cmpProductSortAttr);
	}

	public void deleteCmpProductSortAttr(long attrId) {
		Query query = this.manager.createQuery();
		query.deleteById(CmpProductSortAttr.class, attrId);
	}

	public CmpProductSortAttr getCmpProductSortAttr(long attrId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpProductSortAttr.class, attrId);
	}

	public List<CmpProductSortAttr> getCmpProductSortAttrListInId(
			List<Long> idList) {
		Query query = this.manager.createQuery();
		// return query.listInId(CmpProductSortAttr.class, idList);
		return query.listInField(CmpProductSortAttr.class, null, null,
				"attrid", idList, null);
	}

	public Map<Long, CmpProductSortAttr> getCmpProductSortAttrMapInId(
			List<Long> idList) {
		List<CmpProductSortAttr> list = this
				.getCmpProductSortAttrListInId(idList);
		Map<Long, CmpProductSortAttr> map = new HashMap<Long, CmpProductSortAttr>();
		for (CmpProductSortAttr o : list) {
			map.put(o.getAttrId(), o);
		}
		return map;
	}

	public CmpProductSortAttrModule getCmpProductSortAttrModule(int sortId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpProductSortAttrModule.class, sortId);
	}

	public void updateCmpProductSortAttr(CmpProductSortAttr cmpProductSortAttr) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpProductSortAttr);
	}

	public CmpProductAttr getCmpProductAttr(long productId) {
		Query query = this.manager.createQuery();
		return query.getObjectById(CmpProductAttr.class, productId);
	}

	public void createCmpProductAttr(CmpProductAttr cmpProductAttr) {
		Query query = this.manager.createQuery();
		query.insertObject(cmpProductAttr);
	}

	public void createCmpProductSortAttrModule(
			CmpProductSortAttrModule cmpProductSortAttrModule) {
		Query query = this.manager.createQuery();
		query.insertObject(cmpProductSortAttrModule);
	}

	public void updateCmpProductAttr(CmpProductAttr cmpProductAttr) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpProductAttr);
	}

	public void updateCmpProductSortAttrModule(
			CmpProductSortAttrModule cmpProductSortAttrModule) {
		Query query = this.manager.createQuery();
		query.updateObject(cmpProductSortAttrModule);
	}

	public List<CmpProductSortAttr> getCmpProductSortAttrListBySortIdAndAttrflg(
			int sortId, int attrflg) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProductSortAttr.class, "sortid=? and attrflg=?",
				new Object[] { sortId, attrflg }, "attrid asc");
	}

	public List<CmpProductSortAttr> getCmpProductSortAttrListBySortId(int sortId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProductSortAttr.class, "sortid=?",
				new Object[] { sortId }, "attrid asc");
	}

	public List<CmpProductSort> getCmpProductSortListByCompanyId(long companyId) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProductSort.class, "companyid=?",
				new Object[] { companyId }, "sortid asc");
	}

	public List<CmpProductSort> getCmpProductSortListByCompanyIdAndChildflg(
			long companyId, byte childflg) {
		Query query = this.manager.createQuery();
		return query.listEx(CmpProductSort.class, "companyid=? and childflg=?",
				new Object[] { companyId, childflg }, "sortid asc");
	}

	public List<CmpProduct> getCmpProductListByCompanyIdEx(long companyId,
			int sortId, String name, int begin, int size) {
		StringBuilder sb = new StringBuilder(
				"select * from cmpproduct where companyid=?");
		Query query = this.manager.createQuery();
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (sortId > 0) {
			sb.append(" and sortid=?");
			olist.add(sortId);
		}
		if (!DataUtil.isEmpty(name)) {
			sb.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		sb.append(" order by productid desc");
		return query.listBySqlParamList("ds1", sb.toString(), begin, size,
				CmpProduct.class, olist);
	}

	public int countCmpProductListByCompanyIdEx(long companyId, int sortId,
			String name) {
		StringBuilder sb = new StringBuilder(
				"select count(*) from cmpproduct where companyid=?");
		Query query = this.manager.createQuery();
		List<Object> olist = new ArrayList<Object>();
		olist.add(companyId);
		if (sortId > 0) {
			sb.append(" and sortid=?");
			olist.add(sortId);
		}
		if (!DataUtil.isEmpty(name)) {
			sb.append(" and name like ?");
			olist.add("%" + name + "%");
		}
		return query.countBySql("ds1", sb.toString(), olist);
	}

	public Map<Long, CmpProductSortAttr> getCmpProductSortAttrMapByCompanyIdAndSortIdAndInId(
			long companyId, int sortId, List<Long> idList) {
		Query query = this.manager.createQuery();
		List<CmpProductSortAttr> list = query.listInField(
				CmpProductSortAttr.class, "companyid=? and sortid=?",
				new Object[] { companyId, sortId }, "attrid", idList, null);
		Map<Long, CmpProductSortAttr> map = new HashMap<Long, CmpProductSortAttr>();
		for (CmpProductSortAttr o : list) {
			map.put(o.getAttrId(), o);
		}
		return map;
	}

	public List<CmpProductSortAttr> getCmpProductSortAttrListByCompanyIdAndSortIdAndInId(
			long companyId, int sortId, List<Long> idList) {
		Query query = this.manager.createQuery();
		return query.listInField(CmpProductSortAttr.class,
				"companyid=? and sortid=?", new Object[] { companyId, sortId },
				"attrid", idList, null);
	}
}