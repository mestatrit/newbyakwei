package com.hk.web.pub.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.RegCode;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.RegCodeService;
import com.hk.svr.user.exception.RegCodeNameDuplicateException;

@Component("/pub/regcode")
public class RegCodeAction extends BaseAction {
	@Autowired
	private RegCodeService regCodeService;

	private final Log log = LogFactory.getLog(RegCodeAction.class);

	private char[] wchar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public String execute(HkRequest req, HkResponse resp) {
		int sum = 0;
		for (int i = 0; i < 1000; i++) {
			StringBuilder sb = new StringBuilder();
			for (int k = 0; k < 5; k++) {
				sb.append(wchar[DataUtil.getRandomNumber(wchar.length)]);
			}
			RegCode regCode = new RegCode();
			regCode.setName(sb.toString());
			try {
				this.regCodeService.createRegCode(regCode);
				sum++;
			}
			catch (RegCodeNameDuplicateException e) {
				log.warn(e.getMessage());
			}
		}
		resp.sendHtml("<h1>" + sum + " create</h1>");
		return null;
	}
}