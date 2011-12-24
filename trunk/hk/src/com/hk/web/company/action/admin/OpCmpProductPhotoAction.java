package com.hk.web.company.action.admin;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.CmpProduct;
import com.hk.bean.CmpProductPhoto;
import com.hk.bean.Photo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpProductService;
import com.hk.svr.PhotoService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

/**
 * 管理产品图片，具有足迹管理权限的人可管理
 * 
 * @author akwei
 */
@Component("/e/op/cmpproductphoto")
public class OpCmpProductPhotoAction extends BaseAction {

	@Autowired
	private CmpProductService cmpProductService;

	@Autowired
	private PhotoService photoService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long productId = req.getLongAndSetAttr("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		req.setAttribute("head", cmpProduct.getHeadPath());
		req.setAttribute("cmpProduct", cmpProduct);
		List<CmpProductPhoto> list = this.cmpProductService
				.getCmpProductPhotoListByProductId(productId, 0, 30);
		req.setAttribute("list", list);
		req.reSetAttribute("companyId");
		return this.getWeb3Jsp("e/product/photo/op/list.jsp");
	}

	/**
	 * 最多只能上传30张
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String upload(HkRequest req, HkResponse resp) {
		long companyId = req.getLong("companyId");
		long productId = req.getLong("productId");
		CmpProduct cmpProduct = this.cmpProductService.getCmpProduct(productId);
		if (cmpProduct == null) {// 不存在的产品
			return null;
		}
		File[] files = req.getFiles();
		User loginUser = this.getLoginUser(req);
		int successnum = 0;
		int errornum = 0;
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] == null) {
					continue;
				}
				List<CmpProductPhoto> list = this.cmpProductService
						.getCmpProductPhotoListByProductId(productId, 0, 30);
				if (list.size() == 30) {// 多于30张，就不能上传了
					return this.initError(req,
							Err.CMPPRODUCTPHOTO_OUT_OF_LIMIT, -1, null,
							"upload", "onuploaderror", successnum);
				}
				// 生成用户图片库记录
				Photo photo = new Photo();
				photo.setUserId(loginUser.getUserId());
				try {
					this.photoService.createPhoto(photo, files[i], 2);
					// 生成产品图片库记录
					CmpProductPhoto cmpProductPhoto = new CmpProductPhoto();
					cmpProductPhoto.setCompanyId(companyId);
					cmpProductPhoto.setUserId(loginUser.getUserId());
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
					successnum++;
				}
				catch (Exception e) {
					errornum++;
					e.printStackTrace();
				}
			}
		}
		return this.initSuccess(req, "upload", "onuploadok", errornum);
	}

	/**
	 * 设置产品头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sethead(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getInt("oid");
		CmpProductPhoto photo = this.cmpProductService.getCmpProductPhoto(oid);
		long productId = req.getLong("productId");
		this.cmpProductService.updateCmpProductHeadPath(productId, photo
				.getPath());
		req.setSessionText("op.setheadok");
		return "r:/e/op/cmpproductphoto.do?companyId="
				+ req.getLong("companyId") + "&productId=" + productId;
	}

	/**
	 * 从产品图集中删除图片 如果被删除的图片是头图，就更新产品头图
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getInt("oid");
		long productId = req.getLong("productId");
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
		}
		req.setSessionText("op.delinfook");
		return "r:/e/op/cmpproductphoto.do?companyId="
				+ req.getLong("companyId") + "&productId=" + productId;
	}
}