package com.hk.web.hk4.user;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.image.ImageException;
import com.hk.frame.util.image.JMagickUtil;
import com.hk.frame.util.image.NotPermitImageFormatException;
import com.hk.frame.util.image.OutOfSizeException;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;
import com.hk.web.pub.action.BaseAction;

@Component("/h4/op/user/set")
public class SetAction extends BaseAction {

	@Autowired
	private UserService userService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return "r:/h4/op/user/set_setinfo.do";
	}

	/**
	 * 设置头像
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sethead(HkRequest req, HkResponse resp) throws Exception {
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.setAttribute("op_func", 2);
			return this.getWeb4Jsp("user/set/head.jsp");
		}
		User loginUser = this.getLoginUser(req);
		File f = req.getFile("f");
		if (f == null) {
			return this.onError(req, Err.IMG_FMT_ERROR, "onheaderror", null);
		}
		try {
			JMagickUtil util = new JMagickUtil(f, 2);
			String filePath = ImageConfig.getTempUploadFilePath();
			String fileName = loginUser.getUserId()
					+ req.getRequestedSessionId() + DataUtil.getRandom(5);
			util.makeImage(filePath, fileName + ".jpg", JMagickUtil.IMG_OBLONG,
					500);
			String name = filePath + fileName + ".jpg";
			File file = new File(name);
			String picurl = ImageConfig.getTempUploadPic(fileName) + ".jpg";
			util = new JMagickUtil(file, 1);
			int width = util.getWidth();
			int height = util.getHeight();
			return this.onSuccess2(req, "onheadok", picurl + ";" + width + ";"
					+ height);
		}
		catch (ImageException e) {
			return this.onError(req, Err.IMG_UPLOAD_ERROR, "onheaderror", null);
		}
		catch (NotPermitImageFormatException e) {
			return this.onError(req, Err.IMG_FMT_ERROR, "onheaderror", null);
		}
		catch (OutOfSizeException e) {
			return this.onError(req, Err.IMG_OUTOFSIZE_ERROR,
					new Object[] { "2M" }, "onheaderror", null);
		}
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	public String sethead2(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.reSetAttribute("picurl");
			req.reSetAttribute("width");
			req.reSetAttribute("height");
			req.setAttribute("op_func", 2);
			return this.getWeb4Jsp("user/set/head2.jsp");
		}
		User loginUser = this.getLoginUser(req);
		int x1 = req.getInt("x1");
		int x2 = req.getInt("x2");
		int y1 = req.getInt("y1");
		int y2 = req.getInt("y2");
		String picurl = req.getString("picurl");
		String name = ImageConfig.getFileInServerPath(picurl);
		File f = new File(name);
		if (!f.exists()) {
			return null;
		}
		try {
			this.userService.updateHeadWithCut(loginUser.getUserId(), f, x1,
					y1, x2, y2);
			this.setOpFuncSuccessMsg(req);
			return this.onSuccess2(req, "setheadok", null);
		}
		catch (Exception e) {
			return this
					.onError(req, Err.IMG_UPLOAD_ERROR, "setheaderror", null);
		}
		finally {
			f.delete();
		}
	}

	/**
	 * 设置个人信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setinfo(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		User loginUser = this.getLoginUser(req);
		User user = this.userService.getUser(loginUser.getUserId());
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		req.setAttribute("user", user);
		req.setAttribute("info", info);
		if (ch == 0) {
			req.setAttribute("op_func", 1);
			return this.getWeb4Jsp("user/set/info.jsp");
		}
		String nickName = req.getString("nickName");
		String name = req.getString("name");
		String intro = req.getString("intro");
		byte sex = req.getByte("sex");
		user.setNickName(DataUtil.toHtmlRow(nickName));
		user.setSex(sex);
		info.setName(DataUtil.toHtmlRow(name));
		info.setIntro(DataUtil.toHtmlRow(intro));
		List<Integer> errlist = user.validateList();
		List<Integer> errlist2 = info.validatelist();
		errlist.addAll(errlist2);
		if (errlist.size() > 0) {
			return this.onErrorList(req, errlist, "editinfoerror");
		}
		this.userService.updateSex(loginUser.getUserId(), sex);
		if (!this.userService.updateNickName(loginUser.getUserId(), DataUtil
				.toHtmlRow(nickName))) {
			return this.onError(req, Err.NICKNAME_DUPLICATE, "editinfoerror2",
					null);
		}
		this.userService.updateUserOtherInfo(info);
		req.setSessionText("view2.user_updateinfo_ok");
		return this.onSuccess2(req, "editinfook", null);
	}

	/**
	 * 设置个性化域名
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String setdomain(HkRequest req, HkResponse resp) {
		return null;
		// int ch = req.getInt("ch");
		// User loginUser = this.getLoginUser(req);
		// User user = this.userService.getUser(loginUser.getUserId());
		// req.setAttribute("user", user);
		// if (ch == 0) {
		// req.setAttribute("op_func", 3);
		// return this.getWeb4Jsp("user/set/domain.jsp");
		// }
		// String domain = req.getString("domain");
		// if (DataUtil.isEmpty(domain)) {
		// return this.onError(req, Err.USER_DOMAIN_ERROR2, "domainerror",
		// null);
		// }
		// if (domain.length() < 4 || domain.length() > 20) {
		// return this.onError(req, Err.USER_DOMAIN_ERROR2, "domainerror",
		// null);
		// }
		// if (DataUtil.isNumber(domain)) {
		// return this.onError(req, Err.USER_DOMAIN_ERROR2, "domainerror",
		// null);
		// }
		// if (!DataUtil.isNumberAndChar(domain)) {
		// return this.onError(req, Err.USER_DOMAIN_ERROR2, "domainerror",
		// null);
		// }
		// if (HkWebConfig.isUserDomainIncludeKey(domain)) {
		// return this.onError(req, Err.USER_DOMAIN_ERROR3, "domainerror",
		// null);
		// }
		// if (this.userService.updateUserDomain(user.getUserId(), DataUtil
		// .toHtmlRow(domain))) {
		// loginUser.setDomain(domain);
		// req.setSessionText("view2.user.domain.settingok");
		// return this.onSuccess2(req, "domainok", null);
		// }
		// return this
		// .onError(req, Err.USER_DOMAIN_DUPLICATE, "domainerror", null);
	}
}