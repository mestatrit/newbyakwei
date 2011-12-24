package svrtest;

import com.hk.bean.CompanyPhoto;
import com.hk.svr.CompanyPhotoService;

public class CompanyPhotoServiceTest extends HkServiceTest {
	private CompanyPhotoService companyPhotoService;

	public void setCompanyPhotoService(CompanyPhotoService companyPhotoService) {
		this.companyPhotoService = companyPhotoService;
	}

	public void testCreatePhoto() {
		long userId = 1;
		long companyId = 11;
		CompanyPhoto photo = new CompanyPhoto();
		photo.setCompanyId(companyId);
		photo.setUserId(userId);
		this.companyPhotoService.createPhoto(photo);
		this.commit();
	}
}