package com.hk.bean;

import java.util.Date;

import com.hk.frame.dao.annotation.Column;
import com.hk.frame.dao.annotation.Id;
import com.hk.frame.dao.annotation.Table;
import com.hk.frame.util.DataUtil;
import com.hk.svr.pub.Err;

@Table(name = "cmpunionboard", id = "boardid")
public class CmpUnionBoard {
	@Id
	private long boardId;

	@Column
	private long uid;

	@Column
	private String title;

	@Column
	private String content;

	@Column
	private Date createTime;

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int validate() {
		String s = DataUtil.toTextRow(title);
		if (DataUtil.isEmpty(s) || s.length() > 30) {
			return Err.CMPUNIONBOARD_TITLE_ERROR;
		}
		s = DataUtil.toText(content);
		if (DataUtil.isEmpty(s) || s.length() > 500) {
			return Err.CMPUNIONBOARD_CONTENT_ERROR;
		}
		return Err.SUCCESS;
	}
}