package com.hk.web.pub.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.DelInfo;
import com.hk.bean.Laba;
import com.hk.bean.LabaDelInfo;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.BombService;
import com.hk.svr.LabaService;
import com.hk.svr.laba.exception.LabaNotExistException;
import com.hk.svr.laba.validate.LabaValidate;

@Component("/undo")
public class UnDoAction extends BaseAction {
	@Autowired
	private LabaService labaService;

	@Autowired
	private BombService bombService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		int type = req.getInt("type");
		long opuserId = req.getLong("opuserId");
		long optime = req.getLong("optime");
		if (type == DelInfo.LABA) {
			LabaDelInfo labaDelInfo = new LabaDelInfo();
			labaDelInfo.setOptime(optime);
			labaDelInfo.setOpuserId(opuserId);
			labaDelInfo.setLabaId(req.getLong("labaId"));
			Laba laba = this.labaService.reRemoveLaba(labaDelInfo);
			req.setSessionMessage("喇叭已恢复");
			return "r:/laba/laba.do?labaId=" + laba.getLabaId();
		}
		if (type == DelInfo.FAV_LABA) {
			long labaId = req.getLong("labaId");
			long userId = this.getLoginUser(req).getUserId();
			try {
				LabaValidate.validateLaba(labaId);
				this.labaService.collectLaba(userId, labaId);
				req.setSessionMessage("成功恢复收藏");
			}
			catch (LabaNotExistException e) {//
			}
			return "r:/laba/fav.do";
		}
		if (type == DelInfo.PINK_LABA) {//
			long labaId = req.getLong("labaId");
			this.bombService.deletePinkLaba(labaId);
			req.setSessionMessage(req.getText("op.exeok"));
			return "r:/laba/laba.do?labaId=" + labaId;
		}
		return null;
	}
}