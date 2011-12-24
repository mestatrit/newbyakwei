package com.hk.web.pub.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hk.bean.Laba;
import com.hk.bean.RefLaba;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.frame.util.ResourceConfig;
import com.hk.svr.LabaService;
import com.hk.svr.laba.parser.LabaAPIOutPutParser;
import com.hk.svr.laba.parser.LabaOutPutParser;
import com.hk.web.util.HkWebUtil;

public class LabaVo {
	private boolean replyMe;

	private boolean fav;

	private Laba laba;

	private String sendFromData;

	private String content;

	private String longContent;

	private List<User> userList;

	private List<Tag> tagList;

	private String source;

	private String sourceurl;

	private boolean hasMoreContent;

	private String mainContent;

	private String refContent;

	private String mainLongContent;

	private String refLongContent;

	private boolean notshow;

	/**
	 * 是否被当前用户引用过
	 */
	private boolean refed;

	public boolean isRefed() {
		return refed;
	}

	public void setRefed(boolean refed) {
		this.refed = refed;
	}

	/**
	 * 用于少于n个字符，就不显示
	 * 
	 * @return
	 */
	public boolean isNotshow() {
		return notshow;
	}

	public void setNotshow(boolean notshow) {
		this.notshow = notshow;
	}

	public String getMainLongContent() {
		return mainLongContent;
	}

	public void setMainLongContent(String mainLongContent) {
		this.mainLongContent = mainLongContent;
	}

	public String getRefLongContent() {
		return refLongContent;
	}

	public void setRefLongContent(String refLongContent) {
		this.refLongContent = refLongContent;
	}

	public String getMainContent() {
		return mainContent;
	}

	public void setMainContent(String mainContent) {
		this.mainContent = mainContent;
	}

	public String getRefContent() {
		return refContent;
	}

	public void setRefContent(String refContent) {
		this.refContent = refContent;
	}

	public boolean isHasMoreContent() {
		return hasMoreContent;
	}

	public void setHasMoreContent(boolean hasMoreContent) {
		this.hasMoreContent = hasMoreContent;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceurl() {
		return sourceurl;
	}

	public void setSourceurl(String sourceurl) {
		this.sourceurl = sourceurl;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLongContent() {
		return longContent;
	}

	public void setLongContent(String longContent) {
		this.longContent = longContent;
	}

	public void setSendFromData(String sendFromData) {
		this.sendFromData = sendFromData;
	}

	public String getSendFromData() {
		return sendFromData;
	}

	public LabaVo(Laba laba) {
		this.laba = laba;
	}

	public void setFav(boolean fav) {
		this.fav = fav;
	}

	public boolean isFav() {
		return fav;
	}

	public boolean isReplyMe() {
		return replyMe;
	}

	public void setReplyMe(boolean replyMe) {
		this.replyMe = replyMe;
	}

	public Laba getLaba() {
		return laba;
	}

	public void setLaba(Laba laba) {
		this.laba = laba;
	}

	private static void build(LabaVo vo) {
		Laba laba = vo.getLaba();
		LabaOutPutParser parser = new LabaOutPutParser();
		vo.setContent(parser.getText(laba.getContent()));
	}

	private static void parseTextContent(LabaVo vo, String content,
			String longContent, LabaParserCfg cfg) {
		LabaOutPutParser parser = null;
		if (cfg.isParseContent()) {
			if (cfg.isForapi()) {// api解析
				parser = new LabaAPIOutPutParser();
			}
			else {
				parser = new LabaOutPutParser();
			}
			String s = null;
			if (cfg.isRemoveRefFlg()) {// 是否删除引用标记，饮用后面的内容忽略
				int idx = content.indexOf(HkWebUtil.labarefflg);
				if (idx != -1) {
					s = content.substring(0, idx);
				}
				else {
					s = content;
				}
			}
			else {
				s = content;
			}
			vo.setContent(parser.getText(s));
		}
		if (cfg.isParseLongContent()) {
			parser = new LabaOutPutParser();
			if (!DataUtil.isEmpty(longContent)) {
				vo.setLongContent(parser.getText(longContent));
			}
		}
	}

	private static void parseHtmlContent(LabaVo vo, String content,
			String longContent, LabaParserCfg cfg, long userId) {
		LabaOutPutParser parser = null;
		if (cfg.isParseContent()) {
			int idx = content.indexOf(HkWebUtil.labarefflg);
			String s = null;
			if (cfg.isRemoveRefFlg()) {
				if (idx != -1) {
					s = content.substring(0, idx);
				}
				else {
					s = content;
				}
			}
			else {
				s = content;
			}
			parser = new LabaOutPutParser();
			vo.setContent(parser.getHtml(cfg.getUrlInfo(), s, userId));
		}
		if (cfg.isParseLongContent()) {
			int idx = content.indexOf(HkWebUtil.labarefflg);
			String s = null;
			if (cfg.isRemoveRefFlg()) {
				if (idx != -1) {
					s = longContent.substring(0, idx);
				}
				else {
					s = longContent;
				}
			}
			else {
				s = longContent;
			}
			parser = new LabaOutPutParser();
			if (!DataUtil.isEmpty(longContent)) {
				vo.setLongContent(parser.getHtml(cfg.getUrlInfo(), s, userId));
			}
		}
	}

	/**
	 * 格式化喇叭内容，把rt后的内容放到一个新的变量中，并转化为用户的个性化引用标记
	 * 
	 * @param content
	 * @param cfg
	 * @return
	 */
	private static String[] formatContent(String content, LabaParserCfg cfg) {
		String[] result = null;
		// 格式化喇叭内容，把rt后的内容放到一个新的变量中，并转化为用户的引用标记
		if (cfg.isParseContent() && cfg.isFormatRef() && content != null) {
			int idx = content.indexOf(HkWebUtil.labarefflg);
			if (idx != -1) {
				result = new String[2];
				result[0] = content.substring(0, idx);
				int end = idx + HkWebUtil.labarefflg.length();
				result[1] = content.substring(end);
				if (cfg.getLabartflg() > 0) {
					result[1] = result[1].replaceAll(HkWebUtil.labarefflg,
							ResourceConfig.getText("view.usertool.labartflg_"
									+ cfg.getLabartflg())
									+ " ");
				}
			}
		}
		return result;
	}

	private static void build(LabaVo vo, LabaParserCfg cfg, boolean canCheckFav) {
		Laba laba = vo.getLaba();
		if (laba.getLongContent() != null) {
			vo.setHasMoreContent(true);// 有更多内容
		}
		if (cfg.getUrlInfo() == null) {
			parseTextContent(vo, laba.getContent(), laba.getLongContent(), cfg);
		}
		else {
			parseHtmlContent(vo, laba.getContent(), laba.getLongContent(), cfg,
					laba.getUserId());
		}
		String[] result = formatContent(vo.getContent(), cfg);
		if (result != null) {
			vo.setMainContent(result[0]);
			if (result[1] != null) {
				vo.setRefContent(result[1]);
			}
		}
		result = formatContent(vo.getLongContent(), cfg);
		if (result != null) {
			vo.setMainLongContent(result[0]);
			if (result[1] != null) {
				vo.setRefLongContent(result[1]);
			}
		}
		if (canCheckFav && cfg.isCheckFav()) {
			LabaService labaService = (LabaService) HkUtil
					.getBean("labaService");
			vo.setFav(labaService
					.isCollected(cfg.getUserId(), laba.getLabaId()));
		}
	}

	public static LabaVo create(Laba laba, LabaParserCfg cfg) {
		LabaVo vo = new LabaVo(laba);
		if (cfg == null) {
			build(vo);
		}
		else {
			build(vo, cfg, true);
		}
		return vo;
	}

	public static List<LabaVo> createVoList(List<Laba> list, LabaParserCfg cfg) {
		List<LabaVo> volist = new ArrayList<LabaVo>();
		List<Long> idList = new ArrayList<Long>();
		List<Long> productIdList = new ArrayList<Long>();
		for (Laba o : list) {
			LabaVo vo = new LabaVo(o);
			volist.add(vo);
			idList.add(o.getLabaId());
			if (o.getProductId() > 0) {
				productIdList.add(o.getProductId());
			}
		}
		LabaService labaService = (LabaService) HkUtil.getBean("labaService");
		Map<Long, RefLaba> refmap = null;
		if (cfg != null && cfg.isCheckUserRef() && cfg.getUserId() > 0) {
			refmap = labaService.getRefLabaMapInLabaIdByRefUserId(cfg
					.getUserId(), idList);
		}
		List<Long> favLabaIdList = null;
		if (cfg != null && cfg.isCheckFav() && cfg.getUserId() > 0) {
			favLabaIdList = labaService.getCollectedLabaIdList(cfg.getUserId(),
					idList);
		}
		else {
			favLabaIdList = new ArrayList<Long>();
		}
		List<LabaVo> delList = new ArrayList<LabaVo>();
		for (LabaVo vo : volist) {
			vo.setFav(favLabaIdList.contains(vo.getLaba().getLabaId()));
			if (cfg != null && refmap != null && cfg.isCheckUserRef()) {
				vo.setRefed(refmap.containsKey(vo.getLaba().getLabaId()));
			}
			if (cfg == null) {
				build(vo);
			}
			else {
				build(vo, cfg, false);
				if (vo.isNotshow()) {
					delList.add(vo);
				}
			}
		}
		if (delList.size() > 0) {
			volist.removeAll(delList);
		}
		return volist;
	}
}