package com.hk.svr.processor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.bean.CmpPhotoSet;
import com.hk.bean.CmpPhotoSetRef;
import com.hk.bean.Company;
import com.hk.bean.CompanyPhoto;
import com.hk.bean.Photo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.svr.CompanyPhotoService;
import com.hk.svr.CompanyService;
import com.hk.svr.PhotoService;
import com.hk.svr.pub.Err;

public class CompanyPhotoProcessor {

	@Autowired
	private CompanyPhotoService companyPhotoService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private PhotoService photoService;

	public UploadCompanyPhotoResult createCompanyPhoto(long companyId,
			long userId, File[] files) {
		UploadCompanyPhotoResult uploadCompanyPhotoResult = new UploadCompanyPhotoResult();
		List<CompanyPhoto> list = new ArrayList<CompanyPhoto>();
		Company company = this.companyService.getCompany(companyId);
		int photoCount = this.companyPhotoService
				.countCompanyPhotoByCompanyId(companyId);
		if (photoCount >= 300) {
			uploadCompanyPhotoResult
					.setErrorCode(Err.COMPANYPHOTO_OUT_OF_COUNT);
			return uploadCompanyPhotoResult;
		}
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i] == null) {
					continue;
				}
				Photo photo = new Photo();
				photo.setName(null);
				photo.setUserId(userId);
				try {
					this.photoService.createPhoto(photo, files[i], 2);
					CompanyPhoto companyPhoto = new CompanyPhoto(photo);
					companyPhoto.setCompanyId(companyId);
					this.companyPhotoService.createPhoto(companyPhoto);
					if (DataUtil.isEmpty(company.getHeadPath())) {
						this.companyService.updateHeadPath(companyId,
								companyPhoto.getPath());
					}
					list.add(companyPhoto);
				}
				catch (ImageException e) {
					uploadCompanyPhotoResult.addErrCount(1);
				}
				catch (NotPermitImageFormatException e) {
					uploadCompanyPhotoResult.addFmtErrCount(1);
				}
				catch (OutOfSizeException e) {
					uploadCompanyPhotoResult.addOutOfSizeCount(1);
				}
			}
		}
		uploadCompanyPhotoResult.setList(list);
		return uploadCompanyPhotoResult;
	}

	public void deleteCompanyPhoto(long photoId) {
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(photoId);
		if (companyPhoto == null) {
			return;
		}
		this.companyPhotoService.deleteCompanhPhoto(photoId);
		this.changeHead(companyPhoto);
	}

	private void changeHead(CompanyPhoto companyPhoto) {
		long companyId = companyPhoto.getCompanyId();
		Company company = this.companyService.getCompany(companyId);
		if (companyPhoto.getPath().equals(company.getHeadPath())) {
			CompanyPhoto p = this.companyPhotoService
					.getFirstCompanyPhoto(companyId);
			if (p != null) {
				company.setHeadPath(p.getPath());
			}
			else {
				company.setHeadPath(null);
			}
			this.companyService.updateCompany(company);
		}
	}

	public List<CmpPhotoSetRef> getCmpPhotoSetRefListByCompanyIdAndSetId(
			long companyId, long setId, boolean buildPhoto, int begin, int size) {
		List<CmpPhotoSetRef> list = this.companyPhotoService
				.getCmpPhotoSetRefListByCompanyIdAndSetId(companyId, setId,
						begin, size);
		if (buildPhoto) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpPhotoSetRef o : list) {
				idList.add(o.getPhotoId());
			}
			Map<Long, CompanyPhoto> map = this.companyPhotoService
					.getCompanyPhotoMapInId(companyId, idList);
			for (CmpPhotoSetRef o : list) {
				o.setCompanyPhoto(map.get(o.getPhotoId()));
			}
		}
		return list;
	}

	public List<CmpPhotoSetRef> getCmpPhotoSetRefListByCompanyIdAndPhotoId(
			long companyId, long photoId, boolean buildSet) {
		List<CmpPhotoSetRef> list = this.companyPhotoService
				.getCmpPhotoSetRefListByCompanyIdAndPhotoId(companyId, photoId);
		if (buildSet) {
			List<Long> idList = new ArrayList<Long>();
			for (CmpPhotoSetRef o : list) {
				idList.add(o.getSetId());
			}
			Map<Long, CmpPhotoSet> map = this.companyPhotoService
					.getCmpPhotoSetMapByCompanyIdAndInId(companyId, idList);
			for (CmpPhotoSetRef o : list) {
				o.setCmpPhotoSet(map.get(o.getSetId()));
			}
		}
		return list;
	}

	/**
	 * 把图片从图集中移除，如果被移除的图片正好为图集头图，则更换c头图
	 * 
	 * @param companyId
	 * @param oid
	 *            2010-7-22
	 */
	public void deleteCmpPhotosetRef(long companyId, long oid) {
		CmpPhotoSetRef cmpPhotoSetRef = this.companyPhotoService
				.getCmpPhotoSetRef(companyId, oid);
		if (cmpPhotoSetRef == null) {
			return;
		}
		this.companyPhotoService.deleteCmpPhotosetRef(companyId, oid);
		CmpPhotoSet cmpPhotoSet = this.companyPhotoService.getCmpPhotoSet(
				companyId, cmpPhotoSetRef.getSetId());
		if (cmpPhotoSet == null) {
			return;
		}
		CompanyPhoto companyPhoto = this.companyPhotoService
				.getCompanyPhoto(cmpPhotoSetRef.getPhotoId());
		if (companyPhoto == null) {
			return;
		}
		// 如果移除的图片为图集头图，则更换头图为下一张
		if (!DataUtil.isEmpty(cmpPhotoSet.getPicPath())
				&& cmpPhotoSet.getPicPath().equals(companyPhoto.getPath())) {
			List<CmpPhotoSetRef> reflist = this.companyPhotoService
					.getCmpPhotoSetRefListByCompanyIdAndSetId(companyId,
							cmpPhotoSetRef.getSetId(), 0, 1);
			if (reflist.size() > 0) {
				CompanyPhoto companyPhoto2 = this.companyPhotoService
						.getCompanyPhoto(reflist.get(0).getPhotoId());
				cmpPhotoSet.setPicPath(companyPhoto2.getPath());
			}
			else {
				cmpPhotoSet.setPicPath(null);
			}
			this.companyPhotoService.updateCmpPhotoSet(cmpPhotoSet);
		}
	}
}