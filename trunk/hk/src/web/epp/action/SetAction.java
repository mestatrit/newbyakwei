package web.epp.action;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;
import web.pub.util.WebUtil;

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
import com.hk.svr.processor.UserProcessor;
import com.hk.svr.pub.Err;
import com.hk.svr.pub.ImageConfig;

@Component("/epp/web/op/user/set")
public class SetAction extends EppBaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private UserProcessor userProcessor;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		return "r:/epp/web/op/user/set_setinfo.do?companyId="
				+ req.getLong("companyId");
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
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == 0) {
			return this.setheadpro0(req, resp);
		}
		if (cmpflg == 2) {
			return this.setheadpro2(req, resp);
		}
		return null;
	}

	/**
	 * 设置头像
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setheadpro2(HkRequest req, HkResponse resp) throws Exception {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.setheadpro20(req, resp);
		}
		return null;
	}

	/**
	 * 设置头像
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setheadpro0(HkRequest req, HkResponse resp) throws Exception {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.setheadpro00(req, resp);
		}
		return null;
	}

	/**
	 * 设置头像
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setheadpro20(HkRequest req, HkResponse resp)
			throws Exception {
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.setAttribute("op_func", 2);
			return this.getWebPath("mod/2/0/user/set/head.jsp");
		}
		User loginUser = this.getLoginUser2(req);
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
	 * 设置头像
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setheadpro00(HkRequest req, HkResponse resp)
			throws Exception {
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.setAttribute("op_func", 2);
			return this.getWebPath("user/set/head.jsp");
		}
		User loginUser = this.getLoginUser2(req);
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
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == 0) {
			return this.sethead2pro0(req, resp);
		}
		if (cmpflg == 2) {
			return this.sethead2pro2(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	private String sethead2pro0(HkRequest req, HkResponse resp) {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.sethead2pro00(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	private String sethead2pro2(HkRequest req, HkResponse resp) {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.sethead2pro20(req, resp);
		}
		return null;
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 */
	private String sethead2pro00(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.reSetAttribute("picurl");
			req.reSetAttribute("width");
			req.reSetAttribute("height");
			req.setAttribute("op_func", 2);
			return this.getWebPath("user/set/head2.jsp");
		}
		User loginUser = this.getLoginUser2(req);
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
			loginUser = this.userService.getUser(loginUser.getUserId());
			req.setAttribute(WebUtil.LOGINUSER, loginUser);
			req.getSession().setAttribute(WebUtil.LOGINUSER, loginUser);
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
	 * @param req
	 * @param resp
	 * @return
	 */
	private String sethead2pro20(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		if (ch == 0) {
			req.reSetAttribute("picurl");
			req.reSetAttribute("width");
			req.reSetAttribute("height");
			req.setAttribute("op_func", 2);
			return this.getWebPath("mod/2/0/user/set/head2.jsp");
		}
		User loginUser = this.getLoginUser2(req);
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
			loginUser = this.userService.getUser(loginUser.getUserId());
			req.setAttribute(WebUtil.LOGINUSER, loginUser);
			req.getSession().setAttribute(WebUtil.LOGINUSER, loginUser);
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
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == 0) {
			return this.setinfo0(req, resp);
		}
		if (cmpflg == 2) {
			return this.setinfo2(req, resp);
		}
		return null;
	}

	/**
	 * 设置个人信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setinfo0(HkRequest req, HkResponse resp) {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.setinfo00(req, resp);
		}
		return null;
	}

	/**
	 * 设置个人信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setinfo2(HkRequest req, HkResponse resp) {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.setinfo20(req, resp);
		}
		return null;
	}

	/**
	 * 设置个人信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setinfo00(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		User loginUser = this.getLoginUser2(req);
		User user = this.userService.getUser(loginUser.getUserId());
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		req.setAttribute("user", user);
		req.setAttribute("info", info);
		if (ch == 0) {
			req.setAttribute("op_func", 1);
			return this.getWebPath("user/set/info.jsp");
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
	 * 设置个人信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setinfo20(HkRequest req, HkResponse resp) {
		int ch = req.getInt("ch");
		User loginUser = this.getLoginUser2(req);
		User user = this.userService.getUser(loginUser.getUserId());
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		req.setAttribute("user", user);
		req.setAttribute("info", info);
		if (ch == 0) {
			req.setAttribute("op_func", 1);
			return this.getWebPath("mod/2/0/user/set/info.jsp");
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
	 * 设置密码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setpwd(HkRequest req, HkResponse resp) {
		int cmpflg = this.getCmpflg(req);
		if (cmpflg == 0) {
			return this.setpwd0(req, resp);
		}
		if (cmpflg == 2) {
			return this.setpwd2(req, resp);
		}
		return null;
	}

	/**
	 * 设置密码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setpwd0(HkRequest req, HkResponse resp) {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.setpwd00(req, resp);
		}
		return null;
	}

	/**
	 * 设置密码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setpwd2(HkRequest req, HkResponse resp) {
		int tmlflg = this.getTmlflg(req);
		if (tmlflg == 0) {
			return this.setpwd20(req, resp);
		}
		return null;
	}

	/**
	 * 设置密码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setpwd00(HkRequest req, HkResponse resp) {
		if (req.getInt("ch") == 0) {
			req.setAttribute("op_func", 3);
			return this.getWebPath("user/set/pwd.jsp");
		}
		User loginUser = this.getLoginUser2(req);
		String old_pwd = req.getString("old_pwd");
		String new_pwd = req.getString("new_pwd");
		String renew_pwd = req.getString("renew_pwd");
		int code = this.userProcessor.updatePwd(loginUser.getUserId(), old_pwd,
				new_pwd, renew_pwd);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		req.setSessionText("func.user.updatepwdok");
		return this.onSuccess2(req, "updateok", null);
	}

	/**
	 * 设置密码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private String setpwd20(HkRequest req, HkResponse resp) {
		if (req.getInt("ch") == 0) {
			req.setAttribute("op_func", 3);
			return this.getWebPath("mod/2/0/user/set/pwd.jsp");
		}
		User loginUser = this.getLoginUser2(req);
		String old_pwd = req.getString("old_pwd");
		String new_pwd = req.getString("new_pwd");
		String renew_pwd = req.getString("renew_pwd");
		int code = this.userProcessor.updatePwd(loginUser.getUserId(), old_pwd,
				new_pwd, renew_pwd);
		if (code != Err.SUCCESS) {
			return this.onError(req, code, "updateerror", null);
		}
		req.setSessionText("func.user.updatepwdok");
		return this.onSuccess2(req, "updateok", null);
	}
}