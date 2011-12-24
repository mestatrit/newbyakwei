package com.hk.svr.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductPhoto;
import com.hk.bean.CmpProductSort;
import com.hk.bean.CmpProductSortFile;
import com.hk.bean.CmpUnion;
import com.hk.bean.CmpUnionFeed;
import com.hk.bean.Company;
import com.hk.bean.Photo;
import com.hk.frame.util.DataUtil;
import com.hk.svr.CmpProductService;
import com.hk.svr.CmpProductSortFileService;
import com.hk.svr.CmpUnionMessageService;
import com.hk.svr.CmpUnionService;
import com.hk.svr.CompanyService;
import com.hk.svr.PhotoService;
import com.hk.svr.pub.CmpUnionMessageUtil;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

public class CmpProductProcessor {

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CmpUnionService cmpUnionService;

	@Autowired
	private CmpUnionMessageService cmpUnionMessageService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private CmpProductSortFileService cmpProductSortFileService;

	public boolean createProduct(CmpProduct cmpProduct) {
		boolean result = this.cmpProductService.createCmpProduct(cmpProduct);
		if (result) {
			this.productCreated(cmpProduct);
		}
		return result;
	}

	/**
	 * 上传产品图片，如果产品没有头图，就以上传的第一张为头图，如果图片超过30张，就不再上传
	 * 
	 * @param companyId
	 * @param productId
	 * @param userId
	 * @param files
	 *            2010-5-16
	 */
	public UploadCmpProductPhotoResult uploadCmpProductPhoto(
			CmpProduct cmpProduct, long userId, File[] files) {
		long companyId = cmpProduct.getCompanyId();
		long productId = cmpProduct.getProductId();
		UploadCmpProductPhotoResult uploadCmpProductPhotoResult = new UploadCmpProductPhotoResult();
		List<CmpProductPhoto> list = this.cmpProductService
				.getCmpProductPhotoListByProductId(productId, 0, 30);
		if (list.size() == 30) {// 多于30张，就不能上传了
			uploadCmpProductPhotoResult.setCanUpload(false);
			return uploadCmpProductPhotoResult;
		}
		uploadCmpProductPhotoResult.setCanUpload(true);
		List<CmpProductPhoto> cmpProductPhotoList = new ArrayList<CmpProductPhoto>();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] == null) {
					continue;
				}
				// 生成用户图片库记录
				Photo photo = new Photo();
				photo.setUserId(userId);
				try {
					this.photoService.createPhoto(photo, files[i], 2);
					// 生成产品图片库记录
					CmpProductPhoto cmpProductPhoto = new CmpProductPhoto();
					cmpProductPhoto.setCompanyId(companyId);
					cmpProductPhoto.setUserId(userId);
					cmpProductPhoto.setPhotoId(photo.getPhotoId());
					cmpProductPhoto.setPath(photo.getPath());
					cmpProductPhoto.setProductId(productId);
					this.cmpProductService
							.createCmpProductPhoto(cmpProductPhoto);
					if (DataUtil.isEmpty(cmpProduct.getHeadPath())) {
						// 更新产品头图
						this.cmpProductService.updateCmpProductHeadPath(
								productId, photo.getPath());
					}
					cmpProductPhotoList.add(cmpProductPhoto);
				}
				catch (Exception e) {
					uploadCmpProductPhotoResult.addErrnum(1);
				}
			}
			uploadCmpProductPhotoResult
					.setCmpProductPhotoList(cmpProductPhotoList);
		}
		return uploadCmpProductPhotoResult;
	}

	private void productCreated(CmpProduct cmpProduct) {
		this.createCmpUnionFeed(cmpProduct);
	}

	private void createCmpUnionFeed(CmpProduct cmpProduct) {
		long companyId = cmpProduct.getCompanyId();
		if (companyId <= 0) {
			return;
		}
		Company company = this.companyService.getCompany(companyId);
		if (company == null || company.getUid() <= 0) {
			return;
		}
		CmpUnion cmpUnion = this.cmpUnionService.getCmpUnion(company.getUid());
		if (cmpUnion == null) {
			return;
		}
		CmpUnionFeed feed = new CmpUnionFeed();
		feed.setUid(cmpUnion.getUid());
		feed.setFeedflg(CmpUnionMessageUtil.FEED_CREATEPRODUCT);
		Map<String, String> map = new HashMap<String, String>();
		map.put("cmp", company.getName());
		map.put("pid", cmpProduct.getProductId() + "");
		map.put("name", cmpProduct.getName());
		feed.setData(DataUtil.toJson(map));
		feed.setObjId(companyId);
		cmpUnionMessageService.createCmpUnionFeed(feed);
	}

	public List<CmpProduct> getCmpProductListForAdmin(long companyId,
			String name, int sortId, byte sellStatus, boolean buildSort,
			int begin, int size) {
		List<CmpProduct> list = this.cmpProductService.getCmpProductList(
				companyId, name, sortId, sellStatus, begin, size);
		if (buildSort) {
			List<Integer> idList = new ArrayList<Integer>();
			for (CmpProduct o : list) {
				idList.add(o.getSortId());
			}
			Map<Integer, CmpProductSort> map = new HashMap<Integer, CmpProductSort>();
			List<CmpProductSort> sortlist = this.cmpProductService
					.getCmpProductSortList(companyId);
			for (CmpProductSort o : sortlist) {
				map.put(o.getSortId(), o);
			}
			for (CmpProduct o : list) {
				o.setCmpProductSort(map.get(o.getSortId()));
			}
		}
		return list;
	}

	/**
	 * 删除产品图片，如果产品头图为当前图片，就更新头图
	 * 
	 * @param productId
	 * @param oid
	 *            2010-5-16
	 */
	public void deletePhoto(long productId, long oid) {
		CmpProduct product = this.cmpProductService.getCmpProduct(productId);
		CmpProductPhoto photo = this.cmpProductService.getCmpProductPhoto(oid);
		this.cmpProductService.deleteCmpProductPhoto(oid);
		if (photo.getPath().equals(product.getHeadPath())) {
			List<CmpProductPhoto> list = this.cmpProductService
					.getCmpProductPhotoListByProductId(productId, 0, 1);
			if (list.size() > 0) {
				this.cmpProductService.updateCmpProductHeadPath(productId, list
						.iterator().next().getPath());
			}
			else {
				this.cmpProductService
						.updateCmpProductHeadPath(productId, null);
			}
		}
	}

	public int createCmpProductSortFile(CmpProductSortFile cmpProductSortFile,
			File file) throws IOException {
		if (file == null) {
			return Err.MUST_PIC_UPLOAD;
		}
		if (!DataUtil.isImage(file)) {
			return Err.IMG_FMT_ERROR;
		}
		if (DataUtil.isBigger(file, 100)) {
			return Err.IMG_OUTOFSIZE_ERROR;
		}
		String photoName = String.valueOf(System.nanoTime());
		String dbPath = ImageConfig.getCmpProductSortPicSaveToDBPath(
				cmpProductSortFile.getSortId(), photoName);
		DataUtil.copyFile(file, ImageConfig
				.getcmpProductSortPicFilePath(dbPath), "h.jpg");
		cmpProductSortFile.setPath(dbPath);
		this.cmpProductSortFileService
				.createCmpProductSortFile(cmpProductSortFile);
		return Err.SUCCESS;
	}

	public int updateCmpProductSortFile(CmpProductSortFile cmpProductSortFile,
			File file) throws IOException {
		if (file != null) {
			// 删除先前的图片,再把新图片路径写上
			if (!DataUtil.isEmpty(cmpProductSortFile.getPath())) {
				String filePath = ImageConfig
						.getcmpProductSortPicFilePath(cmpProductSortFile
								.getPath());
				DataUtil.deleteFile(new File(filePath + "h.jpg"));
				DataUtil.deleteFile(new File(filePath));
			}
			if (!DataUtil.isImage(file)) {
				return Err.IMG_FMT_ERROR;
			}
			if (DataUtil.isBigger(file, 100)) {
				return Err.IMG_OUTOFSIZE_ERROR;
			}
			String photoName = String.valueOf(System.nanoTime());
			String dbPath = ImageConfig.getCmpProductSortPicSaveToDBPath(
					cmpProductSortFile.getSortId(), photoName);
			DataUtil.copyFile(file, ImageConfig
					.getcmpProductSortPicFilePath(dbPath), "h.jpg");
			cmpProductSortFile.setPath(dbPath);
		}
		this.cmpProductSortFileService
				.updateCmpProductSortFile(cmpProductSortFile);
		return Err.SUCCESS;
	}

	public void deleteCmpProductSortFile(long oid) {
		CmpProductSortFile cmpProductSortFile = this.cmpProductSortFileService
				.getCmpProductSortFile(oid);
		if (cmpProductSortFile == null) {
			return;
		}
		if (!DataUtil.isEmpty(cmpProductSortFile.getPath())) {
			String filePath = ImageConfig
					.getcmpProductSortPicFilePath(cmpProductSortFile.getPath());
			DataUtil.deleteFile(new File(filePath + "h.jpg"));
			DataUtil.deleteFile(new File(filePath));
		}
		this.cmpProductSortFileService.deleteCmpProductSortFile(oid);
	}
}