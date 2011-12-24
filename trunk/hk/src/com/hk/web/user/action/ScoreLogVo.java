package com.hk.web.user.action;

import java.util.ArrayList;
import java.util.List;
import com.hk.bean.ScoreLog;
import com.hk.frame.util.ResourceConfig;

public class ScoreLogVo {
	private ScoreLog scoreLog;

	private String content;

	public ScoreLog getScoreLog() {
		return scoreLog;
	}

	public void setScoreLog(ScoreLog scoreLog) {
		this.scoreLog = scoreLog;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static List<ScoreLogVo> createVoList(List<ScoreLog> list) {
		List<ScoreLogVo> volist = new ArrayList<ScoreLogVo>();
		for (ScoreLog o : list) {
			ScoreLogVo vo = new ScoreLogVo();
			vo.setScoreLog(o);
			vo.setContent(ResourceConfig.getText("hklog.type"
					+ o.getScoretype()));
			volist.add(vo);
		}
		return volist;
	}
}