package tuxiazi.webapi;

import halo.util.DataUtil;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;
import halo.web.util.SimplePage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Photo;
import tuxiazi.bean.PhotoCmt;
import tuxiazi.bean.User;
import tuxiazi.dao.PhotoCmtDao;
import tuxiazi.dao.PhotoDao;
import tuxiazi.svr.iface.PhotoCmtService;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;

@Component("/api/photo/cmt")
public class CmtAction extends BaseApiAction {

	@Autowired
	private PhotoCmtService photoCmtService;

	@Autowired
	private PhotoCmtDao photoCmtDao;

	@Autowired
	private PhotoDao photoDao;

	public String prvcreate(HkRequest req, HkResponse resp) {
		User user = this.getUser(req);
		long photoid = req.getLong("photoid");
		Photo photo = this.photoDao.getById(photoid);
		if (photo == null) {
			APIUtil.writeErr(resp, Err.PHOTO_NOTEXIST);
			return null;
		}
		PhotoCmt photoCmt = new PhotoCmt();
		photoCmt.setPhotoid(photoid);
		photoCmt.setUserid(user.getUserid());
		photoCmt.setCreate_time(new Date());
		photoCmt.setContent(DataUtil.limitLength(req.getString("content"), 140));
		photoCmt.setUser(user);
		int code = photoCmt.validate();
		if (code != Err.SUCCESS) {
			APIUtil.writeErr(resp, code);
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
		PhotoCmt photoCmt = this.photoCmtDao.getById(cmtid);
		if (photoCmt == null) {
			APIUtil.writeErr(resp, Err.PHOTOCMT_NOTEXIST);
			return null;
		}
		if (photoCmt.getUserid() != user.getUserid()) {
			APIUtil.writeErr(resp, Err.OP_NOPOWER);
			return null;
		}
		this.photoCmtService.deletePhotoCmt(photoCmt);
		APIUtil.writeSuccess(resp);
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
		List<PhotoCmt> list = this.photoCmtDao.getListByPhotoid(photoid, true,
				simplePage.getBegin(), simplePage.getSize());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		APIUtil.writeData(resp, map, "vm/photocmtlist.vm");
		return null;
	}
}