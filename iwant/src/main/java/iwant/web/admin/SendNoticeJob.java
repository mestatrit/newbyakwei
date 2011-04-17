package iwant.web.admin;

import iwant.bean.User;
import iwant.bean.UserNotice;
import iwant.svr.NoticeSvr;
import iwant.svr.UserSvr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.DataUtil;

public class SendNoticeJob {

	@Autowired
	private NoticeSvr noticeSvr;

	@Autowired
	private UserSvr userSvr;

	private boolean processing;

	public void invoke() {
		if (this.processing) {
			return;
		}
		this.processing = true;
		List<UserNotice> list = this.noticeSvr.getUserNoticeList(true, 0, 100);
		User user = null;
		for (UserNotice o : list) {
			user = this.userSvr.getUserByUserid(o.getUserid());
			if (user != null && DataUtil.isNotEmpty(user.getDevice_token())) {
				// send apns notice
				this.noticeSvr.sendApnsNotice(o.getNotice().getContent(), user
						.getDevice_token());
			}
			// 删除已发送的通知记录
			this.noticeSvr.deleteUserNotice(o);
		}
		this.processing = false;
	}
}