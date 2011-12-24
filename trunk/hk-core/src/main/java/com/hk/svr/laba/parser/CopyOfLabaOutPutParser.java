package com.hk.svr.laba.parser;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.hk.bean.Company;
import com.hk.bean.Tag;
import com.hk.bean.UrlInfo;
import com.hk.bean.User;
import com.hk.frame.util.DataUtil;
import com.hk.frame.util.HkUtil;
import com.hk.svr.CompanyService;
import com.hk.svr.TagService;
import com.hk.svr.UserService;

/**
 * 从数据库取出数据后解析其中用户,url,频道,给其添加链接
 * 
 * @author yuanwei
 */
public class CopyOfLabaOutPutParser {

	public static String nickNameRegx = "\\{@([0-9]+)\\}";// 昵称表达式

	public static String companyRegx = "\\{\\[([0-9]+)\\}";// 企业表达式

	public static String tagValue = "\\{##([^#\\{\\}\\s,:，.。;\\(\\)]+)\\}";

	public static String tagRegx = "\\{#([0-9]+)\\}(" + tagValue + ")?";// 频道表达式

	public static String urlRegx = "(http://|https://){1}[\\w\\.\\-/:\\?=\\%\\$\\~\\&\\+;_]+";// url表达式

	public static String productRegx = "\\{%(\\S+:[0-9]+)\\}";// 产品表达式

	protected PatternCompiler nickNameCompiler = new Perl5Compiler();

	protected PatternCompiler companyCompiler = new Perl5Compiler();

	protected PatternCompiler tagCompiler = new Perl5Compiler();

	protected PatternCompiler urlCompiler = new Perl5Compiler();

	protected Pattern nickNamePattern;

	protected Pattern companyPattern;

	protected Pattern tagPattern;

	protected Pattern urlPattern;

	java.util.regex.Pattern anchorpattern = java.util.regex.Pattern.compile(
			"<.+?>", java.util.regex.Pattern.DOTALL);

	protected List<User> userList = new ArrayList<User>(2);

	protected List<Tag> tagList = new ArrayList<Tag>(2);

	protected List<Company> companyList = new ArrayList<Company>(2);

	protected Set<String> urlList = new HashSet<String>(2);

	public CopyOfLabaOutPutParser() {
		try {
			nickNamePattern = nickNameCompiler.compile(nickNameRegx);
			companyPattern = companyCompiler.compile(companyRegx);
			tagPattern = tagCompiler.compile(tagRegx);
			urlPattern = urlCompiler.compile(urlRegx);
		}
		catch (MalformedPatternException e) {
			e.printStackTrace();
		}
	}

	public List<User> getUserList() {
		return userList;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	/**
	 * 解析的内容不带有url信息,不可点击
	 * 
	 * @param content
	 * @return
	 */
	public String getText(String content) {
		this.parseNickName(content);
		this.parseTag(content);
		this.parseCompany(content);
		String cnt = this.parseUrl(content);
		StringBuilder pat = new StringBuilder();
		StringBuilder v = new StringBuilder();
		for (Company o : this.companyList) {
			pat.append("\\{\\[").append(o.getCompanyId()).append("\\}");
			v.append("\\[").append(o.getName()).append("\\]");
			cnt = cnt.replaceAll(pat.toString(), v.toString());
			pat.delete(0, pat.length());
			v.delete(0, v.length());
		}
		for (Tag tag : tagList) {
			pat.append("\\{#").append(tag.getTagId()).append("\\}").append(
					"(\\{##" + tag.getName() + "\\})?");
			v.append("#").append(tag.getName());
			cnt = cnt.replaceAll(pat.toString(), v.toString());
			pat.delete(0, pat.length());
			v.delete(0, v.length());
		}
		cnt = cnt.replaceAll(tagValue, "");
		for (User user : userList) {
			pat.append("\\{@").append(user.getUserId()).append("\\}");
			v.append("@").append(user.getNickName());
			cnt = cnt.replaceAll(pat.toString(), v.toString());
			pat.delete(0, pat.length());
			v.delete(0, v.length());
		}
		return cnt;
	}

	/**
	 * 把其中的url解析到urllist
	 * 
	 * @param content
	 * @return
	 */
	protected String parseUrl(String content) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		// String s = content.replaceAll("&amp;", "&");
		Matcher matcher2 = anchorpattern.matcher(content);
		String res = matcher2.replaceAll("");
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(res);
		MatchResult result = null;
		while (matcher.contains(input, urlPattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = null;
				t = result.group(0);
				map.put(t, t);
			}
		}
		urlList.addAll(map.values());
		return res;
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
		String cnt = this.parseUrl(content2);
		StringBuilder pat = new StringBuilder();// 要替换的内容
		StringBuilder v = new StringBuilder();// 转换后的内容
		for (Company o : this.companyList) {
			if (o == null) {
				continue;
			}
			pat.append("\\{\\[").append(o.getCompanyId()).append("\\}");
			String url = MessageFormat.format(urlInfo.getCompanyUrl(), o
					.getCompanyId()
					+ "");
			v.append("<a href=\"").append(url).append("\">");
			v.append("[").append(o.getName()).append("]</a>");
			cnt = cnt.replaceAll(pat.toString(), v.toString());// 替换频道内容
			pat.delete(0, pat.length());// 为了重复利用清空pat
			v.delete(0, v.length());// 为了重复利用清空v
		}
		String pattern = urlInfo.getTagUrl();
		for (Tag tag : tagList) {
			pat.append("\\{#").append(tag.getTagId()).append("\\}").append(
					"(\\{##" + tag.getName() + "\\})?");// 由于{}为正则表达式特殊字符,必须转化为普通字符
			String url = null;
			if (urlInfo.isRewriteTagUrl()) {// rewrite后地址可能不一样,所以需要区分
				url = MessageFormat.format(pattern, tag.getTagId() + "", userId
						+ "");
			}
			else {
				url = MessageFormat.format(pattern, tag.getTagId() + "", userId
						+ "");
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
			v.append(">#").append(tag.getName()).append("</a>");
			cnt = cnt.replaceAll(pat.toString(), v.toString());// 替换频道内容
			pat.delete(0, pat.length());// 为了重复利用清空pat
			v.delete(0, v.length());// 为了重复利用清空v
		}
		cnt = cnt.replaceAll(tagValue, "");
		for (User user : userList) {
			pat.append("\\{@").append(user.getUserId()).append("\\}");
			String url = MessageFormat.format(urlInfo.getUserUrl(), user
					.getUserId()
					+ "");
			v.append("@<a href=\"").append(url).append("\"");
			if (urlInfo.getUserUrlClass() != null) {
				v.append(" class=\"").append(urlInfo.getUserUrlClass()).append(
						"\"");
			}
			if (urlInfo.isUserNewWin()) {
				v.append(" target=\"_blank\"");
			}
			v.append(">").append(user.getNickName()).append("</a>");
			cnt = cnt.replaceAll(pat.toString(), v.toString());
			pat.delete(0, pat.length());
			v.delete(0, v.length());
		}
		// cnt = cnt.replaceAll(" ", "&nbsp; ");
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
			String tmp_url = url.replaceAll("\\$", "\\\\$");// 由于?$属于特殊字符,需要转化为普通字符
			tmp_url = DataUtil.getFilterRegValue(tmp_url);
			// cnt = cnt.replaceAll("http://", " http://");
			cnt = cnt.replaceAll(tmp_url, v.toString());
			v.delete(0, v.length());
		}
		// cnt = cnt.replaceAll("&nbsp;", " ");
		return cnt;
	}

	/**
	 * 把内容中的昵称都解析到userlist中
	 * 
	 * @param content
	 */
	protected void parseNickName(String content) {
		UserService userService = (UserService) HkUtil.getBean("userService");
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		while (matcher.contains(input, nickNamePattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = result.group(1);
				User user = userService.getUser(Long.parseLong(t));
				this.userList.add(user);
			}
		}
	}

	/**
	 * 解析企业
	 * 
	 * @param content
	 */
	protected void parseCompany(String content) {
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		CompanyService companyService = (CompanyService) HkUtil
				.getBean("companyService");
		while (matcher.contains(input, companyPattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = result.group(1);
				Company o = companyService.getCompany(Long.parseLong(t));
				this.companyList.add(o);
			}
		}
	}

	/**
	 * 解析其中的频道
	 * 
	 * @param content
	 */
	protected void parseTag(String content) {
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		TagService tagService = (TagService) HkUtil.getBean("tagService");
		while (matcher.contains(input, tagPattern)) {
			result = matcher.getMatch();
			if (result != null) {
				int size = result.groups();
				String t = result.group(1);
				String t2 = null;
				if (size > 2) {
					t2 = result.group(3);
				}
				Tag tag = tagService.getTag(Long.parseLong(t));
				if (t2 != null) {
					if (t2.equals(tag.getName())) {
						this.tagList.add(tag);
					}
					else {
						Tag tag2 = new Tag();
						tag2.setTagId(tag.getTagId());
						tag2.setName(t2);
						this.tagList.add(tag2);
					}
				}
				else {
					this.tagList.add(tag);
				}
			}
		}
	}

	public static void main(String[] args) {
		String s = "http://www.huoku.com/laba/taglabalist.do?tagId={0}&reuserId={1}";
		System.out.println(MessageFormat.format(s, 73, 4490));
	}
}