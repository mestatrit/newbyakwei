package tuxiazi.svr.iface;

import java.util.List;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.bean.Invitelog;

public interface InvitelogService {

	boolean inviteSinaFans(Api_user_sina apiUserSina, long sinaUserid,
			String content);

	List<Invitelog> getInvitelogListByUseridAndApi_typeAndInOtherid(
			long userid, int api_type, List<String> otheridList);
}