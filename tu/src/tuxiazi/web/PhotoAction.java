package tuxiazi.web;

import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Photo;
import tuxiazi.dao.PhotoDao;

@Component("/photo")
public class PhotoAction extends BaseAction {

	@Autowired
	private PhotoDao photoDao;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long photoid = req.getLongAndSetAttr("photoid");
		Photo photo = this.photoDao.getById(photoid);
		if (photo == null) {
			return null;
		}
		req.setAttribute("photo", photo);
		return this.getWebPath("photo/photo.jsp");
	}
}
