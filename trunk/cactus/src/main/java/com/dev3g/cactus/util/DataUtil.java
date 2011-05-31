package com.dev3g.cactus.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DataUtil implements ApplicationContextAware {

	private DataUtil() {
	}

	private static Pattern p = Pattern.compile("[\\u4E00-\\u9FA5]");

	public static ApplicationContext applicationContext;

	public static final String DEFAULTBASE64CHARSET = "iso-8859-1";

	public static final String DEFAULTCHARSET = "UTF-8";

	private static final Pattern emailPattern = Pattern
			.compile("(\\w+(.\\w+)*)@(\\w+(.\\w+)*)");

	private static final Pattern mobileNumberPattern = Pattern
			.compile("(15[0-9]|13[0-9]|18[0-9])\\d{8}");

	private static PatternCompiler htmlCompiler = new Perl5Compiler();

	private static org.apache.oro.text.regex.Pattern htmlPattern;
	static {
		try {
			htmlPattern = htmlCompiler.compile("( ){2,}");
		}
		catch (MalformedPatternException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isOutOfLength(String value, int minLen, int maxLen) {
		if (value == null) {
			return true;
		}
		if (value.length() > maxLen) {
			return true;
		}
		if (value.length() < minLen) {
			return true;
		}
		return false;
	}

	public static boolean checkOutOfLength(String value, int len) {
		if (value == null) {
			return false;
		}
		if (value.length() > len) {
			return true;
		}
		return false;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static boolean isChinese(char ch) {
		Matcher m = p.matcher(String.valueOf(ch));
		return m.find() ? true : false;
	}

	public static boolean isEmpty(String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String value) {
		if (value != null && value.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isLegalEmail(String value) {
		if (value == null || value.trim().length() == 0) {
			return false;
		}
		if (value.length() > 50) {
			return false;
		}
		if (value.indexOf('.') == -1) {
			return false;
		}
		if (value.indexOf('@') != value.lastIndexOf('@')) {
			return false;
		}
		if (value.indexOf(',') != -1) {
			return false;
		}
		return emailPattern.matcher(value).matches();
	}

	public static boolean isLegalMobile(long value) {
		String v = value + "";
		if (isEmpty(v)) {
			return false;
		}
		return mobileNumberPattern.matcher(v).matches();
	}

	public static boolean isLegalMobile(String value) {
		if (isEmpty(value)) {
			return false;
		}
		return mobileNumberPattern.matcher(value).matches();
	}

	public static boolean isNumberOrCharOrChinese(String value) {
		if (isEmpty(value)) {
			return false;
		}
		char[] ch = value.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			boolean a = isChinese(ch[i]);
			boolean b = isNumberAndChar(String.valueOf(ch[i]));
			if (!a && !b) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumberOrCharOrChineseWithSapce(String value) {
		if (isEmpty(value)) {
			return false;
		}
		char[] ch = value.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (!isChinese(ch[i]) && !isNumberAndChar(String.valueOf(ch[i]))
					&& ch[i] != ' ') {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumberAndChar(String value) {
		org.apache.oro.text.regex.Pattern numberAndCharPattern = null;
		PatternMatcher numberAndCharPatternMatcher = null;
		PatternCompiler compiler = null;
		try {
			compiler = new Perl5Compiler();
			numberAndCharPattern = compiler.compile("^[a-zA-Z0-9\\.]+",
					Perl5Compiler.CASE_INSENSITIVE_MASK);
			numberAndCharPatternMatcher = new Perl5Matcher();
		}
		catch (MalformedPatternException e) {
			throw new RuntimeException(e);
		}
		return numberAndCharPatternMatcher.matches(value, numberAndCharPattern);
	}

	public static boolean isNumber(String value) {
		org.apache.oro.text.regex.Pattern numberPattern = null;
		PatternMatcher numberPatternMatcher = null;
		PatternCompiler compiler = null;
		try {
			compiler = new Perl5Compiler();
			numberPattern = compiler.compile("^[0-9]+",
					Perl5Compiler.CASE_INSENSITIVE_MASK);
			numberPatternMatcher = new Perl5Matcher();
		}
		catch (MalformedPatternException e) {
			throw new RuntimeException(e);
		}
		return numberPatternMatcher.matches(value, numberPattern);
	}

	public static boolean isSingleNumber(String value) {
		org.apache.oro.text.regex.Pattern numberPattern = null;
		PatternMatcher numberPatternMatcher = null;
		PatternCompiler compiler = null;
		try {
			compiler = new Perl5Compiler();
			numberPattern = compiler.compile("^[0-9]{1}",
					Perl5Compiler.CASE_INSENSITIVE_MASK);
			numberPatternMatcher = new Perl5Matcher();
		}
		catch (MalformedPatternException e) {
			throw new RuntimeException(e);
		}
		return numberPatternMatcher.matches(value, numberPattern);
	}

	public static String limitLength(String str, int len) {
		if (str == null) {
			return null;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	/**
	 * 识别英文的获取特定长度的字符串
	 * 
	 * @param s
	 * @param len
	 * @return 2010-8-5
	 */
	public static String limitLengthEx(String s, int len) {
		// 逻辑为转换为字符数组遇到英文字符，长度+1，然后判断是否超出总长度，如果超出长度，就取到当前判断长度的位置的字符串
		if (isEmpty(s)) {
			return null;
		}
		if (s.length() <= len) {
			return s;
		}
		double _len = len;
		char[] ch = s.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (isEnChar(ch[i])) {
				_len = _len + 0.5;
				if (_len >= ch.length) {
					break;
				}
			}
		}
		if (_len >= ch.length) {
			return s;
		}
		return s.substring(0, (int) _len);
	}

	private static boolean isEnChar(char ch) {
		if (ch >= 'A' && ch <= 'z') {
			return true;
		}
		return false;
	}

	public static String subString(String str, int len) {
		if (str == null) {
			return null;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	/**
	 * 过滤换行符
	 * 
	 * @param str
	 * @return
	 */
	public static String toRowValue(String str) {
		if (str != null) {
			return str.replaceAll("<br/>", "").replaceAll("<br>", "")
					.replaceAll("\n", "").replaceAll("\r", "")
					.replaceAll("&nbsp;", " ").replaceAll("\"", "&quot;")
					.replaceAll("'", "&#39;");
		}
		return null;
	}

	/**
	 * 把html数据转为转义字符，并消除换行
	 * 
	 * @param str
	 * @return
	 */
	public static String toHtmlRow(String str) {
		if (str == null) {
			return null;
		}
		String v = str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\n", "")
				.replaceAll("\r", "").replaceAll("\"", "&quot;")
				.replaceAll("'", "&#39;");
		return v;
	}

	public static String filterHtmlRowValue(String str) {
		if (str == null) {
			return null;
		}
		String v = str.replaceAll("&", "&amp;").replaceAll("<", "")
				.replaceAll(">", "").replaceAll("\n", "").replaceAll("\r", "")
				.replaceAll("\\s", "&nbsp;").replaceAll("\\s+", "")
				.replaceAll("\"", "&quot;");
		return v;
	}

	/**
	 * 把html数据转为转义字符
	 * 
	 * @param str
	 * @return
	 */
	public static String toHtml(String str) {
		return toHtmlEx(str);
	}

	private static List<String> parseHtmlSpace(String content) {
		HashSet<String> htmlset = new HashSet<String>();
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		while (matcher.contains(input, htmlPattern)) {
			result = matcher.getMatch();
			if (result != null) {
				htmlset.add(result.group(0));
			}
		}
		List<String> list = new ArrayList<String>();
		list.addAll(htmlset);
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				if (o1.length() < o2.length()) {
					return 1;
				}
				return 0;
			}
		});
		return list;
	}

	private static String getLenSpace(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append("&nbsp;");
		}
		return sb.toString();
	}

	public static String toHtmlSimple(String str) {
		if (str == null) {
			return null;
		}
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
				.replaceAll("\r", "").replaceAll("\"", "&quot;")
				.replaceAll("'", "&#39;");
	}

	public static String toHtmlSimpleOneRow(String str) {
		if (str == null) {
			return null;
		}
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\n", "")
				.replaceAll("\r", "").replaceAll("\"", "&quot;")
				.replaceAll("'", "&#39;");
	}

	/**
	 * 把html数据转为转义字符，可是使用多空格进行页面布局，对于每段开头内容进行空格
	 * 
	 * @param str
	 * @return
	 */
	public static String toHtmlEx(String str) {
		if (str == null) {
			return null;
		}
		String _str = str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
				.replaceAll("\r", "").replaceAll("\"", "&quot;")
				.replaceAll("'", "&#39;");
		List<String> list = parseHtmlSpace(str);
		for (String s : list) {
			int len = s.length();
			if (len > 1) {
				String rp = getLenSpace(len - 1);
				_str = _str.replaceAll(s, " " + rp);
			}
		}
		return _str;
	}

	/**
	 * 把html转义字符转为普通文本
	 * 
	 * @param str
	 * @return
	 */
	public static String toText(String str) {
		String v = null;
		if (str != null) {
			v = str.replaceAll("\r", "").replaceAll("\n", "")
					.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
					.replaceAll("<br/>", "\n").replaceAll("&amp;", "&")
					.replaceAll("&nbsp;", " ").replaceAll("&quot;", "\"")
					.replaceAll("&#39;", "'");
		}
		return v;
	}

	/**
	 * 把html转义字符转为普通文本
	 * 
	 * @param str
	 * @return
	 */
	public static String toTextRow(String str) {
		String v = null;
		if (str != null) {
			v = str.replaceAll("\r", "").replaceAll("\n", "")
					.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
					.replaceAll("<br/>", "").replaceAll("&amp;", "&")
					.replaceAll("&nbsp;", " ").replaceAll("&quot;", "\"")
					.replaceAll("&#39;", "'");
		}
		return v;
	}

	public static String urlDecoder(String value) {
		if (value != null) {
			try {
				return URLDecoder.decode(value, DEFAULTCHARSET);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return "";
	}

	public static String urlDecoder(String value, String charset) {
		if (value != null) {
			try {
				return URLDecoder.decode(value, charset);
			}
			catch (UnsupportedEncodingException e) {
				System.out.println(e);
			}
		}
		return "";
	}

	public static String urlEncoder(String value) {
		if (value != null) {
			try {
				return URLEncoder.encode(value, DEFAULTCHARSET);
			}
			catch (UnsupportedEncodingException e) {
				System.out.println(e);
			}
		}
		return "";
	}

	public static String urlEncoder(String value, String charset) {
		if (value != null) {
			try {
				return URLEncoder.encode(value, charset);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return "";
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		DataUtil.applicationContext = applicationContext;
	}

	public static String getRandom(int len) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}

	public static <T> List<T> subList(List<T> list, int begin, int size) {
		if (list.size() - 1 < begin) {
			return new ArrayList<T>();
		}
		if (list.size() <= size) {
			return list.subList(begin, begin + list.size());
		}
		return list.subList(begin, begin + size);
	}

	public static int getRandomPageBegin(int count, int size) {
		int ncount = count;
		Random r = new Random();
		int begin = 0;
		if (ncount > size) {
			ncount = ncount - size + 1;
			while ((begin = r.nextInt(ncount)) < 0) {
			}
		}
		return begin;
	}

	/**
	 * 验证2个字符串是否相等，当字符串都为null时，返回不相等
	 * 
	 * @param s1
	 * @param s2
	 * @return 2010-7-12
	 */
	public static boolean eqNotNull(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return false;
		}
		if (s1.equals(s2)) {
			return true;
		}
		return false;
	}
}