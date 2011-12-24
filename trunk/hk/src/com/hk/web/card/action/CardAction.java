package com.hk.web.card.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.MyUserCard;
import com.hk.bean.User;
import com.hk.bean.UserCard;
import com.hk.bean.UserOtherInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.page.SimplePage;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.UserCardService;
import com.hk.svr.UserService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/card/card")
public class CardAction extends BaseAction {
	@Autowired
	private UserCardService userCardService;

	@Autowired
	private UserService userService;

	private int size = 20;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User user = this.userService.getUser(userId);
		UserOtherInfo info = this.userService.getUserOtherInfo(userId);
		UserCard o = this.userCardService.getUserCard(userId);
		User loginUser = this.getLoginUser(req);
		if (userId != loginUser.getUserId()) {// 如果不是自己，就查看是否是已经交换名片,没有交换过名片不能查看
			MyUserCard myUserCard = this.userCardService.getMyUserCard(
					loginUser.getUserId(), userId);
			if (myUserCard == null) {
				return null;
			}
		}
		// if (DataUtil.isEmpty(o.getMainMobile())) {
		// if (!DataUtil.isEmpty(info.getMobile())) {
		// o.setMainMobile(info.getMobile());
		// this.userCardService.updateUserCard(o);// 如果手机号为空,手机号同步
		// }
		// }
		req.setAttribute("o", o);
		req.setAttribute("user", user);
		req.setAttribute("info", info);
		req.setAttribute("userId", userId);
		String queryString = req.getQueryString();
		req.setAttribute("queryString", queryString);
		return "/WEB-INF/page/card/card.jsp";
	}

	/**
	 * 询问是否删除对方中你的信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deluser(HkRequest req, HkResponse resp) throws Exception {
		req.reSetAttribute("userId");
		return "/WEB-INF/page/card/cfrmdelall.jsp";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String deluser2(HkRequest req, HkResponse resp) throws Exception {
		long userId = req.getLong("userId");
		User loginUser = this.getLoginUser(req);
		this.userCardService.deleteMyUserCard(loginUser.getUserId(), userId);
		if (req.getString("delall") != null) {
			this.userCardService
					.deleteMyUserCard(userId, loginUser.getUserId());
		}
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/card/card_list.do";
	}

	/**
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String back(HkRequest req, HkResponse resp) throws Exception {
		long actId = req.getLong("actId");
		if (actId > 0) {
			return "r:/chgcard/act_view.do?actId=" + actId;
		}
		int card = req.getInt("card");
		if (card == 1) {
			return "r:/card/card_list.do";
		}
		return "r:/card/card_list.do";
	}

	/**
	 * 我的名片簿
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String list(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String key = req.getString("key", "");
		SimplePage page = req.getSimplePage(size);
		List<MyUserCard> list = this.userCardService.getMyUserCardList(key,
				loginUser.getUserId(), page.getBegin(), size);
		List<Long> idList = new ArrayList<Long>();
		for (MyUserCard o : list) {
			idList.add(o.getCardUserId());
		}
		Map<Long, UserCard> map = this.userCardService
				.getUserCardMapInUserId(idList);
		for (MyUserCard o : list) {
			o.setUserCard(map.get(o.getCardUserId()));
		}
		page.setListSize(list.size());
		req.setAttribute("list", list);
		req.setEncodeAttribute("key", key);
		return "/WEB-INF/page/card/list.jsp";
	}

	/**
	 * 到编辑页面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String toedit(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		UserCard o = (UserCard) req.getAttribute("o");
		if (o == null) {
			o = this.userCardService.getUserCard(loginUser.getUserId());
		}
		if (o == null) {// 如果用户没有创建过名片,就生成一个
			UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
					.getUserId());
			User user = this.userService.getUser(info.getUserId());
			o = new UserCard(info, user);
			this.userCardService.createUserCard(o);
		}
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		req.setAttribute("info", info);
		req.setAttribute("o", o);
		if (req.getInt("editmore") == 1) {
			return "/WEB-INF/page/card/editmore.jsp";
		}
		return "/WEB-INF/page/card/edit.jsp";
	}

	/**
	 * 编辑名片
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String edit(HkRequest req, HkResponse resp) throws Exception {
		int editmore = req.getInt("editmore");
		User loginUser = this.getLoginUser(req);
		String name = req.getString("name");
		byte chgflg = req.getByte("chgflg");
		String homeAddr = req.getString("homeAddr");
		String homePostcode = req.getString("homePostcode");
		String homeTelphone = req.getString("homeTelphone");
		String workAddr = req.getString("workAddr");
		String workplace = req.getString("workplace");
		String workPlaceWeb = req.getString("workPlaceWeb");
		workPlaceWeb = DataUtil.filterHttp(workPlaceWeb);
		String prvWeb = req.getString("prvWeb");
		prvWeb = DataUtil.filterHttp(prvWeb);
		String workPostcode = req.getString("workPostcode");
		String qq = req.getString("qq");
		String msn = req.getString("msn");
		String gtalk = req.getString("gtalk");
		String skype = req.getString("skype");
		String intro = req.getString("intro");
		String jobRank = req.getString("jobRank");
		String anotherMobile = req.getString("anotherMobile");
		String email = req.getString("email");
		UserCard o = this.userCardService.getUserCard(loginUser.getUserId());
		if (editmore == 1) {// 如果是编辑更多
			o.setHomeAddr(DataUtil.toHtmlRow(homeAddr));
			o.setHomePostcode(DataUtil.toHtmlRow(homePostcode));
			o.setHomeTelphone(DataUtil.toHtmlRow(homeTelphone));
			o.setWorkAddr(DataUtil.toHtmlRow(workAddr));
			o.setWorkplace(DataUtil.toHtmlRow(workplace));
			o.setWorkPostcode(DataUtil.toHtmlRow(workPostcode));
			o.setWorkPlaceWeb(DataUtil.toHtmlRow(workPlaceWeb));
			o.setQq(DataUtil.toHtmlRow(qq));
			o.setMsn(DataUtil.toHtmlRow(msn));
			o.setGtalk(DataUtil.toHtmlRow(gtalk));
			o.setSkype(DataUtil.toHtmlRow(skype));
			o.setIntro(DataUtil.toHtml(intro));
			o.setJobRank(DataUtil.toHtmlRow(jobRank));
			o.setAnotherMobile(DataUtil.toHtmlRow(anotherMobile));
			o.setEmail(DataUtil.toHtmlRow(email));
		}
		o.setName(DataUtil.toHtmlRow(name));
		o.setNickName(loginUser.getNickName());
		o.setChgflg(chgflg);
		int code = o.validate();
		req.setAttribute("o", o);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/card/card_toedit.do";
		}
		this.userCardService.updateUserCard(o);
		UserOtherInfo info = this.userService.getUserOtherInfo(loginUser
				.getUserId());
		info.setName(DataUtil.toHtmlRow(name));
		if (editmore == 1) {
			info.setPrvWeb(prvWeb);
		}
		this.userService.updateUserOtherInfo(info);
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/card/card.do?userId=" + loginUser.getUserId();
	}
}