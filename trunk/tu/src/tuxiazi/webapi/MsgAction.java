package tuxiazi.webapi;

import halo.util.DataUtil;
import halo.web.action.HkRequest;
import halo.web.action.HkResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import tuxiazi.bean.Api_user_sina;
import tuxiazi.util.Err;
import tuxiazi.web.util.APIUtil;
import tuxiazi.web.util.SinaUtil;
import weibo4j.WeiboException;

@Component("/api/msg")
public class MsgAction extends BaseApiAction {

	private final Log log = LogFactory.getLog(MsgAction.class);

	/**
	 * 提交bug信息到微薄 @akweiwei
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String sendbug(HkRequest req, HkResponse resp) {
		String content = req.getString("content");
		if (DataUtil.isEmpty(content)) {
			APIUtil.writeErr(resp, Err.API_BUG_CONTENT_ERROR);
			return null;
		}
		content = DataUtil.limitLength(content, 100);
		content = "@akweiwei " + content;
		Api_user_sina api_user_sina = this.getApiUserSina(req);
		try {
			SinaUtil.updateStatus(api_user_sina.getAccess_token(),
					api_user_sina.getToken_secret(), content);
			APIUtil.writeSuccess(resp);
		}
		catch (WeiboException e) {
			log.error(e.toString());
			APIUtil.writeErr(resp, Err.API_SINA_ERR);
		}
		return null;
	}
}
