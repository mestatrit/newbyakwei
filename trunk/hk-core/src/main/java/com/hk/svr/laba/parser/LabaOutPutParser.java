package com.hk.svr.laba.parser;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;

import com.hk.bean.UrlInfo;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.P;

/**
 * 从数据库取出数据后解析其中用户,url,频道,给其添加链接
 * 
 * @author yuanwei
 */
public class LabaOutPutParser {

	public static String companyRegx = "\\{\\[([0-9]+,[^\\{\\}]+)\\}";

	private static PatternCompiler nickNameCompiler = new Perl5Compiler();

	private static PatternCompiler companyCompiler = new Perl5Compiler();

	private static PatternCompiler tagCompiler = new Perl5Compiler();

	private static Pattern nickNamePattern;

	private static Pattern companyPattern;

	private static Pattern tagPattern;

	private Set<String> nickNameSet = new HashSet<String>();

	private Set<String> tagSet = new HashSet<String>();

	private Set<String> companySet = new HashSet<String>();

	java.util.regex.Pattern anchorpattern = java.util.regex.Pattern.compile(
			"<.+?>", java.util.regex.Pattern.DOTALL);

	protected Set<String> urlList = new HashSet<String>(2);
	static {
		try {
			nickNamePattern = nickNameCompiler.compile(DataUtil.nickNameRegx);
			companyPattern = companyCompiler.compile(companyRegx);
			tagPattern = tagCompiler.compile(DataUtil.tagRegx);
		}
		catch (MalformedPatternException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把其中的url解析到urllist
	 * 
	 * @param content
	 * @return
	 */
	protected void parseUrl(String content) {
		List<String> list = DataUtil.parseUrl(content);
		Set<String> set = new HashSet<String>();
		set.addAll(list);
		urlList.addAll(set);
	}

	// /**
	// * 把其中的url解析到urllist
	// *
	// * @param content
	// * @return
	// */
	// protected String parseUrl(String content) {
	// LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	// Matcher matcher2 = anchorpattern.matcher(content);
	// String res = matcher2.replaceAll("");
	// PatternMatcher matcher = new Perl5Matcher();
	// PatternMatcherInput input = new PatternMatcherInput(res);
	// MatchResult result = null;
	// while (matcher.contains(input, urlPattern)) {
	// result = matcher.getMatch();
	// if (result != null) {
	// String t = null;
	// t = result.group(0);
	// map.put(t, t);
	// }
	// }
	// urlList.addAll(map.values());
	// return res;
	// }
	/**
	 * 解析的内容不带有url信息,不可点击
	 * 
	 * @param content
	 * @return
	 */
	public String getText(String content) {
		// this.parseNickName(content);
		// this.parseCompany(content);
		// this.parseUrl(content);
		String cnt = content;
		StringBuilder pat = new StringBuilder();
		StringBuilder v = new StringBuilder();
		for (String cmps : companySet) {
			pat.append("\\{\\[").append(DataUtil.getFilterRegValue(cmps)).append(
					"\\}");
			String[] tmp = cmps.split(",");
			String cmpname = null;
			if (tmp.length > 1) {
				cmpname = tmp[1];
			}
			v.append("\\[").append(cmpname).append("\\]");
			cnt = cnt.replaceAll(pat.toString(), v.toString());
			pat.delete(0, pat.length());
			v.delete(0, v.length());
		}
		return cnt;
	}

	/**
	 * 带有url的喇叭内容信息
	 * 
	 * @param urlInfo
	 * @see {@link UrlInfo}
	 * @param laba 传送laba对象作为参数是因为对于tag的解析中,要带有发表者userid,用来统计
	 * @return
	 */
	public String getHtml(UrlInfo urlInfo, String content, long userId) {
		String content2 = content.replaceAll(" www\\.", " http://www\\.");
		this.parseNickName(content2);
		this.parseTag(content2);
		this.parseCompany(content2);
		this.parseUrl(content2);
		String cnt = content2;
		StringBuilder pat = new StringBuilder();// 要替换的内容
		StringBuilder v = new StringBuilder();// 转换后的内容
		for (String cmps : companySet) {
			try {
				pat.append("\\{\\[").append(DataUtil.getFilterRegValue(cmps))
						.append("\\}");
				String[] tmp = cmps.split(",");
				String cmpname = null;
				if (tmp.length > 1) {
					cmpname = tmp[1];
				}
				String url = null;
				if (tmp[0].equals("0")) {
					url = MessageFormat.format(urlInfo.getCmpListUrl(),
							DataUtil.urlEncoder(cmpname));
				}
				else {
					url = MessageFormat.format(urlInfo.getCompanyUrl(), tmp[0]);
				}
				v.append("<a href=\"").append(url).append("\">");
				v.append("[").append(cmpname).append("]</a>");
				String pp = pat.toString();
				cnt = cnt.replaceAll(pp, v.toString());// 替换频道内容
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				pat.delete(0, pat.length());// 为了重复利用清空pat
				v.delete(0, v.length());// 为了重复利用清空v
			}
		}
		String pattern = urlInfo.getTagUrl();
		for (String tag : tagSet) {
			pat.append(tag);
			String param = DataUtil.urlEncoder(tag.substring(1));
			String url = null;
			if (urlInfo.isRewriteTagUrl()) {// rewrite后地址可能不一样,所以需要区分
				url = MessageFormat.format(pattern, param, String
						.valueOf(userId));
			}
			else {
				url = MessageFormat.format(pattern, param, String
						.valueOf(userId));
			}
			// 把url设置为html格式
			v.append("<a href=\"").append(url).append("\"");
			if (urlInfo.getTagUrlClass() != null) {// 可以设置 url css央视
				v.append(" class=\"").append(urlInfo.getTagUrlClass()).append(
						"\"");
			}
			if (urlInfo.isTagNewWin()) {// 可以设置为打开新窗口
				v.append(" target=\"_blank\"");
			}
			v.append(">").append(tag).append("</a>");
			cnt = cnt.replaceAll(pat.toString(), v.toString());// 替换频道内容
			pat.delete(0, pat.length());// 为了重复利用清空pat
			v.delete(0, v.length());// 为了重复利用清空v
		}
		for (String nickName : nickNameSet) {
			pat.append("@").append(nickName).append(
					"([^a-zA-Z0-9\\x00-\\xff  ]*)");
			String url = MessageFormat.format(urlInfo.getUserUrl(), DataUtil
					.urlEncoder(nickName));
			v.append("@<a href=\"").append(url).append("\"");
			if (urlInfo.getUserUrlClass() != null) {
				v.append(" class=\"").append(urlInfo.getUserUrlClass()).append(
						"\"");
			}
			if (urlInfo.isUserNewWin()) {
				v.append(" target=\"_blank\"");
			}
			v.append(">").append(nickName).append("</a>");
			cnt = cnt.replaceAll(pat.toString(), v.toString());
			pat.delete(0, pat.length());
			v.delete(0, v.length());
		}
		for (String url : urlList) {
			String localurl = url;
			if (urlInfo.isNeedGwt()) {// 是否需要转化为google手机页面打开
				String gwturl = "http://google.com/gwt/n?u="
						+ DataUtil.urlEncoder(localurl);
				v.append("<a href=\"").append(gwturl).append("\"");
			}
			else {
				v.append("<a href=\"").append(localurl).append("\"");
			}
			if (urlInfo.getShortUrlClass() != null) {// 设置css
				v.append(" class=\"" + urlInfo.getShortUrlClass() + "\"");
			}
			v.append(" target=\"_blank\"");
			v.append(">").append(url).append("</a>");
			String tmp_url = DataUtil.getFilterRegValue(url);
			cnt = cnt.replaceAll(tmp_url, v.toString());
			v.delete(0, v.length());
		}
		return cnt;
	}

	/**
	 * 把内容中的昵称都解析到userlist中
	 * 
	 * @param content
	 */
	protected void parseNickName(String content) {
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		while (matcher.contains(input, nickNamePattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = result.group(1);
				nickNameSet.add(t);
			}
		}
	}

	/**
	 * 解析企业
	 * 
	 * @param content
	 */
	protected void parseCompany(String content) {
		companySet.addAll(parseCompanyName(content));
	}

	public static Set<String> parseCompanyName(String content) {
		Set<String> companySet = new HashSet<String>();
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		while (matcher.contains(input, companyPattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = result.group(1);
				companySet.add(t);
			}
		}
		return companySet;
	}

	/**
	 * 把内容中的昵称都解析到userlist中
	 * 
	 * @param content
	 */
	protected void parseTag(String content) {
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		while (matcher.contains(input, tagPattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = result.group(1);
				tagSet.add(t);
			}
		}
	}

	/**
	 * 在字符串中替换字符串。
	 * 
	 * @param str 原始字符串
	 * @param reg 正则式
	 * @param str2 替换成为字符串
	 * @param num 替换次数,为0则replaceAll
	 */
	public static String replace(String str, String reg, String str2, int num) {
		String result = str;
		int replaceCount = Util.SUBSTITUTE_ALL;
		if (num > 0) {
			replaceCount = num;
		}
		try {
			String content = str;
			String ps1 = reg;
			PatternCompiler orocom = new Perl5Compiler();
			Pattern pattern1 = orocom.compile(ps1);
			PatternMatcher matcher = new Perl5Matcher();
			result = Util.substitute(matcher, pattern1, new Perl5Substitution(
					str2), content, replaceCount);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	public static void main(String[] args) {
		String s = "我在 @akwei @akweiwei @心飞扬扬 @心飞扬 @心飞扬扬 {[37,上岛咖啡 (现代城店)} {[1,情有独钟咖啡厅} 我来测试一个小喇叭 @心飞扬 @akwei #测试 #a #b http://u.huoku.com/aaa9 #测试 ";
		LabaOutPutParser parser = new LabaOutPutParser();
		P.println(parser.getText(s));
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setRewriteUserUrl(true);
		urlInfo.setUserUrl("/n?v={0}");
		urlInfo.setTagUrl("/laba/taglabalist_viewbyname.do?v={0}&reuserId={1}");
		urlInfo.setCompanyUrl("/venue/{0}");
		urlInfo.setCmpListUrl("/e/cmp_s.do?name={0}");
		P.println(parser.getHtml(urlInfo, s, 0));
	}
}