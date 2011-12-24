package com.hk.web.card.action;

import java.util.ArrayList;
import java.util.List;
import com.hk.bean.UserCard;

public class UserCardVo {
	private UserCard userCard;

	public UserCardVo(UserCard userCard) {
		this.userCard = userCard;
	}

	public void setUserCard(UserCard userCard) {
		this.userCard = userCard;
	}

	public UserCard getUserCard() {
		return userCard;
	}

	public static List<UserCardVo> createVoList(List<UserCard> list) {
		List<UserCardVo> volist = new ArrayList<UserCardVo>();
		for (UserCard o : list) {
			UserCardVo vo = new UserCardVo(o);
			volist.add(vo);
		}
		return volist;
	}
}