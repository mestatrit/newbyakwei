package web.pub.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

public class LabaParser {
	private static PatternCompiler nickNameCompiler = new Perl5Compiler();

	private static PatternCompiler companyCompiler = new Perl5Compiler();

	private static PatternCompiler tagCompiler = new Perl5Compiler();

	private static PatternCompiler urlCompiler = new Perl5Compiler();

	private static PatternCompiler urlCompiler2 = new Perl5Compiler();

	private static String res_url = "{0}<a href=\"{1}\">{2}</a>";

	private static final String nickNameRegx = "#@([^\\s,，.。:]+)#";

	private static final String tagRegx = "#(#[^\\s,，.。:]+)#";

	private static final String cmpRegx = "#\\[([^\\[^\\]]+)\\]#";

	public static String urlRegx = "(http://|https://){1}[\\w\\.\\-/:\\?=\\%\\$\\~\\&\\+]+";// url表达式

	public static String urlRegx2 = "(www\\.){1}[\\w\\.\\-/:\\?=\\%\\$\\~\\&\\+]+";

	public static Pattern nickNamePattern;

	public static Pattern cmpPattern;

	public static Pattern tagPattern;

	public static Pattern urlPattern;

	protected static Pattern urlPattern2;

	public static java.util.regex.Pattern anchorpattern = java.util.regex.Pattern
			.compile("<.+?>", java.util.regex.Pattern.DOTALL);
	static {
		try {
			nickNamePattern = nickNameCompiler.compile(nickNameRegx);
			tagPattern = tagCompiler.compile(tagRegx);
			urlPattern = urlCompiler.compile(urlRegx);
			urlPattern2 = urlCompiler2.compile(urlRegx2);
			cmpPattern = companyCompiler.compile(cmpRegx);
		}
		catch (MalformedPatternException e) {
			e.printStackTrace();
		}
	}

	public static String parseContent(UrlInfo urlInfo, String content) {
		List<String> nickNameList = parse(content, nickNamePattern);
		List<String> tagList = parse(content, tagPattern);
		List<String> cmpList = parse(content, cmpPattern);
		List<String> urlList = parseUrl(content);
		String res = content;
		for (String s : nickNameList) {
			if (urlInfo == null) {
				res = res.replaceAll("#@" + s + "#", "@" + s);
			}
			else {
				String url = MessageFormat.format(urlInfo.getUserUrl(),
						urlEncoder(s));
				res = res.replaceAll("#@" + s + "#", MessageFormat.format(
						res_url, "@", url, s));
			}
		}
		for (String s : tagList) {
			if (urlInfo == null) {
				res = res.replaceAll("#" + s + "#", s);
			}
			else {
				String url = MessageFormat.format(urlInfo.getTagUrl(),
						urlEncoder(s.substring(1)));
				res = res.replaceAll("#" + s + "#", MessageFormat.format(
						res_url, "", url, s));
			}
		}
		for (String s : cmpList) {
			if (urlInfo == null) {
				res = res.replaceAll("#\\[" + s + "\\]#", "\\[" + s + "\\]");
			}
			else {
				String url = MessageFormat.format(urlInfo.getCompanyUrl(),
						urlEncoder(s));
				res = res.replaceAll("#\\[" + s + "\\]#", MessageFormat.format(
						res_url, "", url, "\\[" + s + "\\]"));
			}
		}
		for (String s : urlList) {
			if (urlInfo != null) {
				String url = null;
				if (urlInfo.isNeedGwt()) {
					url = "http://google.com/gwt/n?u=" + urlEncoder(s);
				}
				else {
					url = s;
				}
				String gg = s.toLowerCase();
				if (gg.startsWith("http://") || gg.startsWith("https://")) {
					res = res.replaceAll(s, MessageFormat.format(res_url, "",
							url, s));
				}
				else {
					res = res.replaceAll("[^://a-z_A-Z0-9]" + s, MessageFormat
							.format(res_url, "", url, s));
				}
			}
		}
		return res;
	}

	protected static List<String> parseUrl(String content) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		String s = content.replaceAll("&amp;", "&");
		Matcher matcher2 = anchorpattern.matcher(s);
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
		matcher2 = anchorpattern.matcher(s);
		res = matcher2.replaceAll("");
		matcher = new Perl5Matcher();
		input = new PatternMatcherInput(res);
		result = null;
		while (matcher.contains(input, urlPattern2)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = null;
				t = result.group(0);
				map.put(t, t);
				// if (!map.containsKey("http://" + t)) {
				// if (!map.containsKey("https://" + t)) {
				// map.put(t, t);
				// }
				// }
			}
		}
		List<String> list = new ArrayList<String>();
		list.addAll(map.values());
		return list;
	}

	private static List<String> parse(String content, Pattern pattern) {
		List<String> list = new ArrayList<String>(3);
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		while (matcher.contains(input, pattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = result.group(1);
				list.add(t);
			}
		}
		return list;
	}

	public static String urlEncoder(String value) {
		if (value != null) {
			try {
				return URLEncoder.encode(value, "utf-8");
			}
			catch (UnsupportedEncodingException e) {
				System.out.println(e);
			}
		}
		return "";
	}

	public static void main(String[] args) {
		String s = "hahhaha #@akwei#  ##段子段子# ##段子# ##段子段子# #[情有独钟咖啡厅]# #[情有独钟]# http://www.163.com http://163.com";
		UrlInfo urlInfo = new UrlInfo();
		urlInfo.setUserUrl("user/{0}");
		urlInfo.setTagUrl("tag/{0}");
		urlInfo.setCompanyUrl("cmp/{0}");
		urlInfo.setNeedGwt(true);
		System.out.println(LabaParser.parseContent(urlInfo, s));
	}
}