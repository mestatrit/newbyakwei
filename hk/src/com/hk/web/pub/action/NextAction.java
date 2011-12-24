package com.hk.web.pub.action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hk.bean.City;
import com.hk.bean.CityCode;
import com.hk.bean.Country;
import com.hk.bean.HkGroup;
import com.hk.bean.IpCityRange;
import com.hk.bean.IpCityRangeUser;
import com.hk.bean.IpCityUser;
import com.hk.bean.IpUser;
import com.hk.bean.Province;
import com.hk.bean.User;
import com.hk.bean.UserCard;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.FollowService;
import com.hk.svr.GroupService;
import com.hk.svr.IpCityService;
import com.hk.svr.UserCardService;
import com.hk.svr.UserService;
import com.hk.svr.ZoneService;
import com.hk.svr.friend.exception.AlreadyBlockException;
import com.hk.svr.friend.validate.FollowValidate;
import com.hk.svr.pub.Err;
import com.hk.svr.user.exception.UserNotExistException;

/**
 * 注册后的个人向导
 * 
 * @author akwei
 */
@Component("/next/op/op")
public class NextAction extends BaseAction {

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService followService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private IpCityService ipCityService;

	@Autowired
	private ZoneService zoneService;

	@Autowired
	private UserCardService userCardService;

	public String execute(HkRequest request, HkResponse response)
			throws Exception {
		response.sendHtml("next 1");
		return null;
	}

	/**
	 * 到修改昵称页面(向导)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String toUpdateNickName(HkRequest request, HkResponse response)
			throws Exception {
		return "/WEB-INF/page/reg/updatenickname.jsp";
	}

	/**
	 * 修改昵称(向导)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateNickName(HkRequest request, HkResponse response)
			throws Exception {
		String nickName = request.getString("nickName", "");
		String jump = request.getString("jump");
		if (jump != null) {
			// return "r:/next/op/op_toAddFollow.do";
			return "r:/next/op/op_tosetzone0.do";
		}
		nickName = nickName.replaceAll("　", "");
		if (User.validateNickName(nickName) != Err.SUCCESS) {
			request.setMessage("请正确输入昵称");
			return "/next/op/op_toUpdateNickName.do";
		}
		User user = this.getLoginUser(request);
		if (this.userService.updateNickName(user.getUserId(), nickName)) {
			UserCard userCard = this.userCardService.getUserCard(user
					.getUserId());
			if (userCard != null) {
				userCard.setNickName(nickName);
				this.userCardService.updateUserCard(userCard);
			}
			user.setNickName(nickName);// 更新session中用户昵称
			request.setSessionMessage("修改昵称成功");
			// return "r:/next/op/op_toAddFollow.do";
			return "r:/next/op/op_tosetzone0.do";
		}
		request.setMessage("昵称已经被占用,请重新输入");
		return "/next/op/op_toUpdateNickName.do";
	}

	/**
	 * 推荐人来关注
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String toAddFollow(HkRequest request, HkResponse response)
			throws Exception {
		User loginUser = this.getLoginUser(request);
		// int beign = HkUtil.getRandomPageBegin(this.userService.countUser(),
		// 6);
		// List<User> list = this.userService.getUserListByUserStatus(beign, 6);
		// for (User user : list) {
		// if (user.getUserId() == this.getLoginUser(request).getUserId()) {
		// list.remove(user);
		// break;
		// }
		// }
		// if (list.size() == 6) {
		// list.remove(0);
		// }
		List<User> ulist = new ArrayList<User>();
		String ip = request.getRemoteAddr();
		// 获取ip地址相同的好友
		List<IpUser> ipuserlist = this.userService
				.getIpUserListSortUserRecentUpdate(ip, 0, 6);
		for (IpUser o : ipuserlist) {
			if (o.getUserId() != loginUser.getUserId()) {
				ulist.add(o.getUser());
			}
		}
		// 获取ip附件的好友
		IpCityRange range = this.ipCityService.getIpCityRange(ip);
		if (range != null) {
			List<IpCityRangeUser> ipcityrangeuserlist = this.userService
					.getIpCityRangeUserListSortUserRecentUpdate(range
							.getRangeId(), 0, 6);
			for (IpCityRangeUser o : ipcityrangeuserlist) {
				if (o.getUserId() != loginUser.getUserId()) {
					ulist.add(o.getUser());
				}
			}
			// 获取城市内的好友
			List<IpCityUser> ipcityuserlist = this.userService
					.getIpCityUserListSortUserRecentUpdate(range.getCityId(),
							0, 6);
			for (IpCityUser o : ipcityuserlist) {
				if (o.getUserId() != loginUser.getUserId()) {
					ulist.add(o.getUser());
				}
			}
		}
		// 获得所有的会员中最活跃的
		List<User> olist = this.userService.getUserListSortUserRecentUpdate(0,
				6);
		for (User o : olist) {
			if (o.getUserId() != loginUser.getUserId()) {
				ulist.add(o);
			}
		}
		Map<Long, User> map = new LinkedHashMap<Long, User>();
		for (User o : ulist) {// 去除重复
			map.put(o.getUserId(), o);
		}
		ulist = new ArrayList<User>();
		ulist.addAll(map.values());
		if (ulist.size() > 6) {
			ulist = ulist.subList(0, 6);// 最终取6个
		}
		request.setAttribute("userlist", ulist);
		return "/WEB-INF/page/reg/addfollow.jsp";
	}

	/**
	 * 批量关注
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String addFollow(HkRequest request, HkResponse response) {
		String jump = request.getString("jump");
		if (jump != null) {
			return "r:/next/op/op_toaddgroup.do";
		}
		User user = this.getLoginUser(request);
		String[] userIds = request.getStrings("userId");
		if (userIds != null) {
			for (int i = 0; i < userIds.length; i++) {
				try {
					FollowValidate.validateAddFollow(user.getUserId(), Long
							.parseLong(userIds[i]));
					this.followService.addFollow(user.getUserId(), Long
							.parseLong(userIds[i]), request.getRemoteAddr(),
							true);
				}
				catch (NumberFormatException e) {//
				}
				catch (UserNotExistException e1) {//
				}
				catch (AlreadyBlockException e) {//
				}
			}
		}
		request.setSessionMessage("操作成功");
		return "r:/next/op/op_toaddgroup.do";
	}

	/**
	 * 到加入圈子页面,人最多的圈子
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String toaddgroup(HkRequest request, HkResponse response) {
		List<HkGroup> list = this.groupService.getHkGroupListSortByUcount(6);
		request.setAttribute("list", list);
		return "/WEB-INF/page/reg/addgroup.jsp";
	}

	/**
	 * 加入圈子
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String addgroup(HkRequest request, HkResponse response) {
		String jump = request.getString("jump");
		if (jump != null) {
			return "r:/square.do";
		}
		int gid = request.getInt("gid");
		User user = this.getLoginUser(request);
		List<HkGroup> list = this.groupService.getGroupListByUserId(user
				.getUserId(), 0, 4);
		if (list.size() <= 3) {
			this.groupService.addGroupUser(gid, user.getUserId());
			request.setSessionMessage("加入圈子成功");
		}
		return "r:/group/gulist.do?gid=" + gid;
	}

	/**
	 * 到完成信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String toCompleteInfo(HkRequest request, HkResponse response)
			throws Exception {
		User user = this.getLoginUser(request);
		request.setAttribute("user", user);
		return "/WEB-INF/page/reg/updateNickName";
	}

	/**
	 * 选择所在国家,选择所在省或市
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosetzone0(HkRequest req, HkResponse resp) throws Exception {
		int countryId = req.getInt("countryId");
		List<Country> clist = this.zoneService.getCountryList();
		clist.remove(1);// 删除不限
		if (countryId == 0) {
			countryId = clist.iterator().next().getCountryId();
		}
		List<Province> plist = this.zoneService.getProvinceList(countryId);
		req.reSetAttribute("countryId");
		req.setAttribute("plist", plist);
		req.setAttribute("clist", clist);
		return "/WEB-INF/page/reg/setzone0.jsp";
	}

	/**
	 * 选择所在省或市
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosetzone1(HkRequest req, HkResponse resp) throws Exception {
		int provinceId = req.getInt("provinceId");
		List<City> clist = this.zoneService.getCityList(provinceId);
		req.setAttribute("clist", clist);
		req.reSetAttribute("countryId");
		req.reSetAttribute("provinceId");
		return "/WEB-INF/page/reg/setzone1.jsp";
	}

	/**
	 * 通过输入的地区代码查找省或者市,如果为省和直辖市,就到tosetzone1
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String tosetzone2(HkRequest req, HkResponse resp) throws Exception {
		String code = req.getString("code");
		CityCode cityCode = this.zoneService.getCityCodeByCode(code);
		if (cityCode == null) {
			// 区号不存在,请在下面列表中选择
			req.setSessionMessage("区号不存在,请在下面列表中选择");
			return "r:/next/op/op_tosetzone0.do";
		}
		List<City> clist = this.zoneService.getCityList(cityCode.getName());// 查看是否在地区中有
		if (clist.size() > 0) {
			return "r:/next/op/op_setzone.do?cityId="
					+ clist.iterator().next().getCityId();
		}
		List<Province> plist = this.zoneService.getProvinceList(cityCode
				.getName());// 查看是否在省市中有
		if (plist.size() > 0) {
			return "r:/next/op/op_tosetzone1.do?provinceId="
					+ plist.iterator().next().getProvinceId();
		}
		req.setSessionMessage("没有找到所在地区,请在下面列表中选择");
		return "r:/next/op/op_tosetzone0.do";
	}

	/**
	 * 选择所在省或市
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String setzone(HkRequest req, HkResponse resp) throws Exception {
		int cityId = req.getInt("cityId");
		User loginUser = this.getLoginUser(req);
		this.userService.updateCityId(loginUser.getUserId(), cityId);
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/next/op/op_toAddFollow.do";
	}
}