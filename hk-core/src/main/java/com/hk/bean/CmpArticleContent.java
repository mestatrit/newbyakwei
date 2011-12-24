package com.hk.bean;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

/**
 * 企业相关文章
 * 
 * @author akwei
 */
@Table(name = "cmparticlecontent")
public class CmpArticleContent {

	@Id
	private long oid;

	@Column
	private String content;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSimpleContent80() {
		return DataUtil.limitLength(DataUtil.toText(this.content), 80);
	}

	public String getSimpleContent480() {
		return DataUtil.toHtml(DataUtil.limitLength(DataUtil
				.toText(this.content), 480));
	}

	public String getSimpleContent140() {
		return DataUtil.limitLength(DataUtil.toText(this.content), 140);
	}

	public String getFirstParagraph() {
		String _content = this.getContent();
		_content = _content.replaceAll("(<br/>)+{2,}", "<br/>");
		int fromIndex = 0;
		for (int i = 0; i < 1; i++) {
			fromIndex = _content.indexOf("<br/>", fromIndex);
			if (fromIndex != -1) {
				fromIndex += 5;
			}
		}
		if (fromIndex <= 0) {
			return _content;
		}
		if (fromIndex <= _content.length() - 1) {
			_content = _content.substring(0, fromIndex);
			int last_idx = _content.lastIndexOf("<br/>");
			if (last_idx + 5 == _content.length()) {
				return _content.substring(0, last_idx);
			}
			return _content;
		}
		return _content;
	}

	public int validate() {
		String s = DataUtil.toText(this.content);
		if (DataUtil.isEmpty(s) || s.length() > 20000) {
			return Err.CMPARTICLE_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}