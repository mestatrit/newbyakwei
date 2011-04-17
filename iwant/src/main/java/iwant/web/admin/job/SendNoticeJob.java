package iwant.web.admin.job;

import iwant.bean.User;
import iwant.bean.UserNotice;
import iwant.svr.NoticeSvr;
import iwant.svr.UserSvr;

import java.util.List;

import javapns.data.PayLoad;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hk.frame.util.DataUtil;

/**
 * 发送apns通知
 * 
 * @author akwei
 */
public class SendNoticeJob {

	@Autowired
	private NoticeSvr noticeSvr;

	@Autowired
	private UserSvr userSvr;

	private boolean processing;

	private final Log log = LogFactory.getLog(SendNoticeJob.class);

	public void invoke() {
		if (this.processing) {
			return;
		}
		this.processing = true;
		log.info("begin invoke");
		// 发送通知
		List<UserNotice> list = this.noticeSvr.getUserNoticeList(true, 0, 100);
		User user = null;
		PayLoad payLoad = null;
		for (UserNotice o : list) {
			user = this.userSvr.getUserByUserid(o.getUserid());
			if (user != null && DataUtil.isNotEmpty(user.getDevice_token())) {
				payLoad = new PayLoad();
				try {
					payLoad.addBadge(1);
					payLoad.addAlert(o.getNotice().getContent());
					payLoad.addSound("default");
					this.noticeSvr.sendApnsNotice(user.getDevice_token(),
							payLoad);
				}
				catch (Exception e) {
					log.error(e.getMessage());
				}
			}
			// 删除已发送的通知记录
			this.noticeSvr.deleteUserNotice(o);
		}
		this.processing = false;
	}
}