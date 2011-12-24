package com.hk.web.tag.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.web.http.HkRequest;
import com.hk.frame.web.http.HkResponse;
import com.hk.svr.TagService;
import com.hk.svr.pub.Err;
import com.hk.web.pub.action.BaseAction;

@Component("/tag/op/op")
public class OpTagAction extends BaseAction {
	@Autowired
	private TagService tagService;

	public String execute(HkRequest req, HkResponse resp) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 创建频道，并加入频道
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String add(HkRequest req, HkResponse resp) throws Exception {
		User loginUser = this.getLoginUser(req);
		String name = req.getString("name");
		int code = Tag.validateName(name);
		req.setAttribute("name", name);
		if (code != Err.SUCCESS) {
			req.setMessage(req.getText(code + ""));
			return "/tag/utlist_mt.do";
		}
		Tag tag = this.tagService.createTag(DataUtil.toHtmlRow(name));
		this.tagService.addUserTag(loginUser.getUserId(), tag.getTagId());
		req.setSessionMessage(req.getText("op.exeok"));
		return "r:/laba/taglabalist.do?tagId=" + tag.getTagId();
	}
}