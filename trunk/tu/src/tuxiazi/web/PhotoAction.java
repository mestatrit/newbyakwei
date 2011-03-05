package tuxiazi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Photo;
import tuxiazi.svr.iface.PhotoService;

import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/photo")
public class PhotoAction extends BaseAction {

	@Autowired
	private PhotoService photoService;

	@Override
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long photoid = req.getLongAndSetAttr("photoid");
		Photo photo = this.photoService.getPhoto(photoid);
		if (photo == null) {
			return null;
		}
		req.setAttribute("photo", photo);
		return this.getWebPath("photo/photo.jsp");
	}
}
