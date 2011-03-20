package tuxiazi.webapi;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.User;
import tuxiazi.svr.iface.PhotoCmtService;
import tuxiazi.svr.iface.PhotoService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;

import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;

@Component("/api/photo/cmt")
public class CmtAction extends BaseApiAction {

	@Autowired
	private PhotoCmtService photoCmtService;

	@Autowired
	private PhotoService photoService;

	public String prvcreate(HkRequest req, HkResponse resp) {
		User user = this.getUser(req);
		long photoid = req.getLong("photoid");
		Photo photo = this.photoService.getPhoto(photoid);
		if (photo == null) {
			APIUtil.writeErr(req, resp, Err.PHOTO_NOTEXIST);
			return null;
		}
		PhotoCmt photoCmt = new PhotoCmt();
		photoCmt.setPhotoid(photoid);
		photoCmt.setUserid(user.getUserid());
		photoCmt.setCreate_time(new Date());
		photoCmt.setContent(DataUtil.limitTextRow(req.getHtmlRow("content"),
				140));
		photoCmt.setUser(user);
		int code = photoCmt.validate();
		if (code != Err.SUCCESS) {
			APIUtil.writeErr(req, resp, code);
			return null;
		}
		int withweibo = req.getInt("withweibo");
		this.photoCmtService.createPhotoCmt(photo, photoCmt, user, withweibo,
				this.getApiUserSina(req));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("photoCmt", photoCmt);
		APIUtil.writeData(resp, map, "vm/photocmt_create.vm");
		return null;
	}

	public String prvdelete(HkRequest req, HkResponse resp) {
		User user = this.getUser(req);
		long cmtid = req.getLong("cmtid");
		long photoid = req.getLong("photoid");
		PhotoCmt photoCmt = this.photoCmtService.getPhotoCmt(photoid, cmtid);
		if (photoCmt == null) {
			APIUtil.writeErr(req, resp, Err.PHOTOCMT_NOTEXIST);
			return null;
		}
		if (photoCmt.getUserid() != user.getUserid()) {
			APIUtil.writeErr(req, resp, Err.OP_NOPOWER);
			return null;
		}
		this.photoCmtService.deletePhotoCmt(photoCmt);
		APIUtil.writeSuccess(req, resp);
		return null;
	}

	/**
	 * 某图片的评论列表
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-11-27
	 */
	public String list(HkRequest req, HkResponse resp) {
		long photoid = req.getLong("photoid");
		int page = req.getInt("page", 1);
		int size = req.getInt("size", 10);
		SimplePage simplePage = new SimplePage(size, page);
		List<PhotoCmt> list = this.photoCmtService.getPhotoCmtListByPhotoid(
				photoid, true, simplePage.getBegin(), simplePage.getSize());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/photocmtlist.vm");
		return null;
	}
}