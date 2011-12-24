package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpAdminUser;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpAdminUserService;
import com.hk.svr.UserService;
import com.hk.svr.processor.CmpAdminUserProcessor;

/**
 * 只有超级管理员才可以操作
 * 
 * @author akwei
 */
@Component("/epp/mgr/web/adminuser")
public class MgrAdminUserAction extends EppBaseAction {

	@Autowired
	private CmpAdminUserService cmpAdminUserService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpAdminUserProcessor cmpAdminUserProcessor;

	/**
	 * 管理员列表
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long companyId = req.getLong("companyId");
		List<CmpAdminUser> list = this.cmpAdminUserProcessor
				.getCmpAdminUserByCompanyId(companyId, true);
		req.setAttribute("list", list);
		req.setAttribute("active_0", 1);
		return this.getWebPath("admin/adminuser/list.jsp");
	}

	/**
	 * 根据昵称，查找用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String find(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_0", 1);
		long companyId = req.getLong("companyId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/adminuser/find.jsp");
		}
		String nickName = req.getStringAndSetAttr("nickName");
		if (DataUtil.isEmpty(nickName)) {
			return "r:/epp/mgr/web/adminuser_find.do?companyId=" + companyId;
		}
		User user = this.userService.getUserByNickName(nickName);
		if (user != null) {
			UserOtherInfo userOtherInfo = this.userService
					.getUserOtherInfo(user.getUserId());
			req.setAttribute("userOtherInfo", userOtherInfo);
		}
		req.setAttribute("user", user);
		return this.getWebPath("admin/adminuser/find.jsp");
	}

	/**
	 * 添加管理员
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_0", 1);
		long companyId = req.getLong("companyId");
		long userId = req.getLong("userId");
		CmpAdminUser cmpAdminUser = new CmpAdminUser();
		cmpAdminUser.setCompanyId(companyId);
		cmpAdminUser.setUserId(userId);
		this.cmpAdminUserService.createCmpAdminUser(cmpAdminUser);
		this.setOpFuncSuccessMsg(req);
		return "r:/epp/mgr/web/adminuser.do?companyId=" + companyId;
	}

	/**
	 * 删除管理员
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpAdminUserService.deleteCmpAdminUser(oid);
		req.setSessionText("view2.data.delete.ok");
		return null;
	}
}