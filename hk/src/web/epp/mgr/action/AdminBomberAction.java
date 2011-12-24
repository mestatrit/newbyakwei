package web.epp.mgr.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.pub.action.EppBaseAction;

import com.hk.bean.CmpBomber;
import com.hk.bean.User;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.CmpBomberService;
import com.hk.svr.UserService;
import com.hk.svr.processor.CmpBomberProcessor;

/**
 * 管理员可操作的功能
 * 
 * @author akwei
 */
@Component("/epp/web/op/webadmin/adminbomber")
public class AdminBomberAction extends EppBaseAction {

	@Autowired
	private CmpBomberService cmpBomberService;

	@Autowired
	private UserService userService;

	@Autowired
	private CmpBomberProcessor cmpBomberProcessor;

	/**
	 * 炸弹人列表
	 */
	public String execute(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_7", 1);
		SimplePage page = req.getSimplePage(20);
		long companyId = req.getLong("companyId");
		List<CmpBomber> list = this.cmpBomberProcessor
				.getCmpBomberListByCompanyId(companyId, true, page.getBegin(),
						page.getSize() + 1);
		this.processListForPage(page, list);
		req.setAttribute("list", list);
		return this.getWebPath("admin/cmpbomber/list.jsp");
	}

	/**
	 * 查找用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String find(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_7", 1);
		long companyId = req.getLong("companyId");
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/cmpbomber/find.jsp");
		}
		String nickName = req.getString("nickName");
		if (DataUtil.isEmpty(nickName)) {
			return "r:/epp/web/op/webadmin/adminbomber_find.do?companyId="
					+ companyId;
		}
		User user = this.userService.getUserByNickName(nickName);
		if (user != null) {
			UserOtherInfo userOtherInfo = this.userService
					.getUserOtherInfo(user.getUserId());
			req.setAttribute("userOtherInfo", userOtherInfo);
		}
		req.setAttribute("user", user);
		return this.getWebPath("admin/cmpbomber/find.jsp");
	}

	/**
	 * 分配炸弹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String create(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_7", 1);
		long companyId = req.getLong("companyId");
		long userId = req.getLong("userId");
		int bombcount = req.getInt("bombcount");
		CmpBomber cmpBomber = new CmpBomber();
		cmpBomber.setCompanyId(companyId);
		cmpBomber.setUserId(userId);
		cmpBomber.setBombcount(bombcount);
		this.cmpBomberService.saveCmpBomber(cmpBomber);
		this.setOpFuncSuccessMsg(req);
		return "r:/epp/web/op/webadmin/adminbomber.do?companyId=" + companyId;
	}

	/**
	 * 分配炸弹
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String update(HkRequest req, HkResponse resp) throws Exception {
		req.setAttribute("active_7", 1);
		long companyId = req.getLong("companyId");
		long oid = req.getLongAndSetAttr("oid");
		CmpBomber cmpBomber = this.cmpBomberService.getCmpBomber(oid);
		if (cmpBomber == null) {
			return null;
		}
		User user = this.userService.getUser(cmpBomber.getUserId());
		req.setAttribute("user", user);
		req.setAttribute("cmpBomber", cmpBomber);
		int ch = req.getInt("ch");
		if (ch == 0) {
			return this.getWebPath("admin/cmpbomber/update.jsp");
		}
		int bombcount = req.getInt("bombcount");
		cmpBomber.setBombcount(bombcount);
		this.cmpBomberService.saveCmpBomber(cmpBomber);
		this.setOpFuncSuccessMsg(req);
		return "r:/epp/web/op/webadmin/adminbomber.do?companyId=" + companyId;
	}

	/**
	 * 删除炸弹人
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 *             2010-5-10
	 */
	public String del(HkRequest req, HkResponse resp) throws Exception {
		long oid = req.getLong("oid");
		this.cmpBomberService.deleteCmpBomber(oid);
		this.setDelSuccessMsg(req);
		return null;
	}

	public static void main(String[] args) {
		P.println(Integer.MAX_VALUE);
	}
}