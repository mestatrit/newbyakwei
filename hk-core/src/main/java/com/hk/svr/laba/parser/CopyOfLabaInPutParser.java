package com.hk.svr.laba.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.hk.bean.Company;
import com.hk.bean.ShortUrl;
import com.hk.bean.Tag;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.ShortUrlService;
import com.hk.svr.TagService;
import com.hk.svr.UserService;

public class CopyOfLabaInPutParser {
	public static String nickNameRegx = "@([^\\s,，.。:]+)[,|，|.|。|:]*";

	public static String urlRegx = "(http://|https://){1}[\\w\\.\\-/:\\?=\\%\\$\\~\\&\\+]+";

	public static String urlRegx2 = "(www\\.){1}[\\w\\.\\-/:\\?=\\%\\$\\~\\&\\+]+";

	public static String tagRegx = "[#｜＃]([^\\{\\}\\[\\]\\s,:，.。;\\(\\)]+)[,|，|.|。|;|:]*";

	public static String companyRegx = "\\[([^\\[^\\]]+)\\]";

	private static PatternCompiler companyCompiler = new Perl5Compiler();

	private static PatternCompiler nickNameCompiler = new Perl5Compiler();

	private static PatternCompiler tagCompiler = new Perl5Compiler();

	private static Pattern nickNamePattern;

	private static Pattern companyPattern;

	private static Pattern tagPattern;

	private String shortUrlDomain;
	static {
		try {
			nickNamePattern = nickNameCompiler.compile(nickNameRegx);
			tagPattern = tagCompiler.compile(tagRegx);
			companyPattern = companyCompiler.compile(companyRegx);
		}
		catch (MalformedPatternException e) {
			e.printStackTrace();
		}
	}

	public CopyOfLabaInPutParser(String shortUrlDomain) {
		this.shortUrlDomain = shortUrlDomain;
	}

	private String removeSpChar(String s) {
		return s.replaceAll("[,|，|.|。|:]*", "");
	}

	private List<String> parseUrlByPattern(String content) {
		return DataUtil.parseUrl(content);
	}

	private List<String> parseByPattern(Pattern pattern, String content,
			String removeString1, String removeString2) {
		List<String> list = new ArrayList<String>(5);
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		while (matcher.contains(input, pattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = null;
				if (!pattern.getPattern().equals(urlRegx)) {
					t = result.group(1);
					t = this.removeSpChar(t);
				}
				else {
					t = result.group(0);
				}
				if (removeString1 != null) {
					t = t.replaceFirst(removeString1, "");
				}
				if (removeString2 != null) {
					t = t.replaceFirst(removeString2, "");
				}
				list.add(t);
			}
		}
		return list;
	}

	private String parseContent(String content, LabaInfo labaInfo) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		TagService tagService = (TagService) HkUtil.getBean("tagService");
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		String parsedContent = content;
		for (String ss : labaInfo.getNickNameList()) {
			User user = userService.getUserByNickName(ss);
			if (user != null) {
				parsedContent = parsedContent.replaceAll("@" + ss, "{@"
						+ user.getUserId() + "}");// 把昵称替换为{@id}
			}
		}
		// 标签内容不做替换，在使用的时候解析
		for (String tagName : labaInfo.getTagValueList()) {
			String name = null;
			if (tagName.length() > 16) {// 频道最长为16个字符,多于截断
				name = tagName.substring(0, 16);
			}
			else {
				name = tagName;
			}
			tagService.createTag(name);
			// Tag tag = tagService.createTag(name);
			// if (name.equals(tag.getName())) {
			// parsedContent = parsedContent.replaceAll("[#|＃]" + name, "\\{#"
			// + tag.getTagId() + "\\}");// 吧频道内容替换为{#id}
			// }
			// else {
			// parsedContent = parsedContent.replaceAll("[#|＃]" + name, "\\{#"
			// + tag.getTagId() + "\\}" + "\\{##" + tagName + "\\}");//
			// 吧频道内容替换为{#id}{#name}
			// }
		}
		for (String name : labaInfo.getCompanyNameList()) {
			Company o = companyService.getCompanyByName(name);
			if (o != null) {
				parsedContent = parsedContent.replaceAll("\\[" + name + "\\]",
						"\\{\\[" + o.getCompanyId() + "," + name + "\\}");// 把企业名称替换为{[id}
			}
		}
		// parsedContent = parsedContent.replaceAll(" \\{#", "\\{#");
		// parsedContent = parsedContent.replaceAll("\\}\\{#", "\\} \\{#");
		// parsedContent = parsedContent.replaceAll("\\} \\{##", "\\}\\{##");
		// int idx1 = parsedContent.indexOf("{#");
		// int idx2 = parsedContent.indexOf(" {#");
		// if (idx1 == idx2) {
		// parsedContent = parsedContent.replaceFirst(" \\{#", "\\{#");
		// }
		int idx1 = parsedContent.indexOf("#");
		int idx2 = parsedContent.indexOf(" #");
		if (idx1 == idx2) {
			parsedContent = parsedContent.replaceFirst(" #", "#");
		}
		parsedContent = parsedContent.replaceAll("&", "&amp;").replaceAll("\"",
				"&quot").replaceAll("<", "&lt;").replaceAll(">", "&gt;")
				.replaceAll("\r", "").replaceAll("\n", "");// 特殊字符替换,由于“转化后带有&,所以放到最后转化
		return parsedContent;
	}

	private LabaInfo parseLabaInfo(String content) {
		String con = content;
		LabaInfo labaInfo = new LabaInfo();
		labaInfo.setNickNameList(this.parseByPattern(nickNamePattern, con, "@",
				null));
		labaInfo.setTagValueList(this.parseByPattern(tagPattern, con
				.replaceAll("#", " #"), "#", null));
		labaInfo.setCompanyNameList(this.parseByPattern(companyPattern, con,
				null, null));
		this.sortListByLength(labaInfo.getNickNameList());
		this.sortListByLength(labaInfo.getTagValueList());
		this.sortListByLength(labaInfo.getCompanyNameList());
		// String tmp_con = con.replaceAll("&amp;", "&").replaceAll("&quot",
		// "\"");// 特殊字符替换,由于“转化后带有&,所有放到最后转化
		labaInfo.setUrlList(this.parseUrlByPattern(content));
		UserService userService = (UserService) HkUtil.getBean("userService");
		TagService tagService = (TagService) HkUtil.getBean("tagService");
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		for (String ss : labaInfo.getCompanyNameList()) {
			Company company = companyService.getCompanyByName(ss);
			if (company != null) {
				labaInfo.addCompnayId(company.getCompanyId());
			}
		}
		for (String ss : labaInfo.getNickNameList()) {
			User user = userService.getUserByNickName(ss);
			if (user != null) {
				labaInfo.addUserId(user.getUserId());
			}
		}
		for (String tagName : labaInfo.getTagValueList()) {
			String name = null;
			if (tagName.length() > 16) {// 频道最长为16个字符,多于截断
				name = tagName.substring(0, 16);
			}
			else {
				name = tagName;
			}
			Tag tag = tagService.createTag(name);
			labaInfo.addTagId(tag.getTagId());
		}
		return labaInfo;
	}

	private void sortListByLength(List<String> list) {
		Collections.sort(list, c);
	}

	private Comparator<String> c = new Comparator<String>() {
		public int compare(String o1, String o2) {
			if (o1.length() > o2.length()) {
				return -1;
			}
			if (o1.length() < o2.length()) {
				return 1;
			}
			return 0;
		}
	};

	public LabaInfo parse(String content) {
		String parsedUrlContent = DataUtil.formatUrl(content);
		String scontent = null;// 短信息
		String lcontent = null;// 长信息
		String fmturl = "<http://u.huoku.com/xxxx>";
		String con = this.filterSpString(parsedUrlContent);
		String t1 = DataUtil.replaceAllUrl(con, "<http://u.huoku.com/xxxx>");
		if (t1.length() > 140) {
			t1 = t1.substring(0, 140);
			int fmturlcount = DataUtil.countString(t1, fmturl);
			int chcount = DataUtil.countString(t1, "<");
			if (fmturlcount != chcount) {// 截断了url
				int idx = t1.lastIndexOf('<');
				t1 = t1.substring(0, idx);
			}
			List<String> urllist = DataUtil.parseUrl(con);
			for (String url : urllist) {
				ShortUrlService shortUrlService = (ShortUrlService) HkUtil
						.getBean("shortUrlService");
				if (url.length() > 26 && url.indexOf(this.shortUrlDomain) == -1) {
					ShortUrl o = shortUrlService.createShortUrl(url);
					String shortUrlData = this.shortUrlDomain + "/"
							+ o.getShortKey();
					t1 = DataUtil.replaceRirstUrl(t1, fmturl, shortUrlData);
				}
				else {
					t1 = DataUtil.replaceRirstUrl(t1, fmturl, url);
				}
			}
			scontent = t1;
			lcontent = con;
		}
		else {
			scontent = con;
			List<String> urllist = DataUtil.parseUrl(scontent);
			String tmpurl = null;
			ShortUrlService shortUrlService = (ShortUrlService) HkUtil
					.getBean("shortUrlService");
			for (String url : urllist) {
				if (url.length() > 26 && url.indexOf(this.shortUrlDomain) == -1) {
					ShortUrl o = shortUrlService.createShortUrl(url);
					String shortUrlData = this.shortUrlDomain + "/"
							+ o.getShortKey();
					tmpurl = url.replaceAll("\\?", "\\\\?").replaceAll("\\$",
							"\\\\$");
					scontent = scontent.replaceAll(tmpurl, shortUrlData);
				}
			}
		}
		if (lcontent != null) {
			List<String> urllist = DataUtil.parseUrl(lcontent);
			String tmpurl = null;
			ShortUrlService shortUrlService = (ShortUrlService) HkUtil
					.getBean("shortUrlService");
			for (String url : urllist) {
				if (url.length() > 26 && url.indexOf(this.shortUrlDomain) == -1) {
					ShortUrl o = shortUrlService.createShortUrl(url);
					String shortUrlData = this.shortUrlDomain + "/"
							+ o.getShortKey();
					tmpurl = url.replaceAll("\\?", "\\\\?").replaceAll("\\$",
							"\\\\$");
					lcontent = lcontent.replaceAll(tmpurl, shortUrlData);
				}
			}
		}
		LabaInfo labaInfo = this.parseLabaInfo(con);
		if (lcontent != null) {
			labaInfo
					.setLongParsedContent(this.parseContent(lcontent, labaInfo));
		}
		labaInfo.setParsedContent(this.parseContent(scontent, labaInfo));
		return labaInfo;
	}

	private String filterSpString(String s) {
		String v = s.replaceAll("#|＃", " #").replaceAll("；", ";").replaceAll(
				"：", ":").replaceAll("　", " ").replaceAll("＠", "@").replaceAll(
				"，", ",").replaceAll("。", ".").replaceAll("【", "\\[")
				.replaceAll("】", "\\]").replaceAll("［", "\\[").replaceAll("］",
						"\\]").trim();
		return v;
	}
}