package com.hk.frame.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class DataUtil implements ApplicationContextAware {

	private DataUtil() {
		// default
	}

	private static String urlRegx = "(http://|https://){1}[a-zA-Z0-9\\.\\-/:\\?=\\%\\$\\~\\&\\+;_,#]+";

	public static String filtervalue = "\\s\\(\\)\\|\\{\\}\\[\\]\\*\\$\\^\\+\\?\\.,:;!@~`#%&\"\'/，。？！~、；→…￥@-_";

	public static String nickNameRegx = "@([^" + filtervalue
			+ "]+)[,|，|.|。|:]*";

	private static PatternCompiler nickNameCompiler = new Perl5Compiler();

	// public static String nickNameRegx2 = "@([^" + filtervalue
	// + "]+)[^a-zA-Z0-9 ]+";
	public static String tagRegx = "(#[^" + filtervalue + "]+)[,|，|.|。|;|:]*";

	private static Pattern p = Pattern.compile("[\\u4E00-\\u9FA5]");

	public static final String SYS_DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static ApplicationContext applicationContext;

	public static final String DEFAULTBASE64CHARSET = "iso-8859-1";

	public static final String DEFAULTCHARSET = "UTF-8";

	private static final Pattern emailPattern = Pattern
			.compile("(\\w+(.\\w+)*)@(\\w+(.\\w+)*)");

	private static final Pattern mobileNumberPattern = Pattern
			.compile("(15[0-9]|13[0-9]|18[0-9])\\d{8}");

	// private static final String urlRegx =
	// "(http://|https://){1}[\\w\\.\\-/:\\?=\\%\\$\\~\\&]+";
	private static org.apache.oro.text.regex.Pattern urlPattern;

	private static PatternCompiler urlCompiler = new Perl5Compiler();

	private static PatternCompiler htmlCompiler = new Perl5Compiler();

	private static org.apache.oro.text.regex.Pattern htmlPattern;

	private static org.apache.oro.text.regex.Pattern nickNamePattern;
	static {
		try {
			nickNamePattern = nickNameCompiler.compile(nickNameRegx);
			htmlPattern = htmlCompiler.compile("( ){2,}");
			urlPattern = urlCompiler.compile(urlRegx);
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

	/**
	 * 用户昵称验证,不超过8个汉字，或者是16个英文字符数字下划线
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isLegalNickName(String value) {
		if (isEmpty(value)) {
			return false;
		}
		int charCount = 0;
		char[] ch = value.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (isChinese(ch[i])) {
				charCount = charCount + 2;
			}
			else if (isNumberAndChar(String.valueOf(ch[i]))) {
				charCount = charCount + 1;
			}
			else if (ch[i] == '_') {
				charCount = charCount + 1;
			}
			else {
				return false;
			}
		}
		if (charCount <= 16) {
			return true;
		}
		return false;
	}

	public static boolean isLegalPassword(String value) {
		if (value == null || value.trim().length() == 0) {
			return false;
		}
		if (isOutOfLength(value, 4, 10)) {
			return false;
		}
		return isNumberAndChar(value);
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

	public static String limitHtmlRow(String html, int len) {
		return DataUtil.toHtmlRow(DataUtil.limitLength(
				DataUtil.toTextRow(html), len));
	}

	public static String limitTextRow(String html, int len) {
		return DataUtil.limitLength(DataUtil.toTextRow(html), len);
	}

	/**
	 * 识别英文的获取特定长度的字符串
	 * 
	 * @param s
	 * @param len
	 * @return
	 *         2010-8-5
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
					.replaceAll("\n", "").replaceAll("\r", "").replaceAll(
							"&nbsp;", " ").replaceAll("\"", "&quot;")
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
				.replaceAll(">", "&gt;").replaceAll("\n", "").replaceAll("\r",
						"").replaceAll("　", " ").replaceAll(" +", " ")
				.replaceAll("\"", "&quot;").replaceAll("'", "&#39;");
		return v;
	}

	public static String filterHtmlRowValue(String str) {
		if (str == null) {
			return null;
		}
		String v = str.replaceAll("&", "&amp;").replaceAll("<", "").replaceAll(
				">", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll(
				"\\s", "&nbsp;").replaceAll("\\s+", "").replaceAll("\"",
				"&quot;");
		return v;
	}

	/**
	 * 把html数据转为转义字符
	 * 
	 * @param str
	 * @return
	 */
	public static String toHtml(String str) {
		// if (str == null) {
		// return null;
		// }
		// String v = str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
		// .replaceAll(">", "&gt;").replaceAll("\n", "<br/>").replaceAll(
		// "\r", "").replaceAll("\\s", "&nbsp;").replaceAll(
		// "\\s+", "").replaceAll("\"", "&quot;").replaceAll("'",
		// "&#39;");
		// return v;
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
				// int len = result.groups();
				// for (int i = 0; i < len; i++) {
				// P.println(result.group(i).length());
				// }
			}
		}
		// for(String s:htmlset){
		// P.println(s.length());
		// }
		List<String> list = new ArrayList<String>();
		list.addAll(htmlset);
		Collections.sort(list, new Comparator<String>() {

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
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
				">", "&gt;").replaceAll("\n", "<br/>").replaceAll("\r", "")
				.replaceAll("\"", "&quot;").replaceAll("'", "&#39;");
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
				.replaceAll(">", "&gt;").replaceAll("\n", "<br/>").replaceAll(
						"\r", "").replaceAll("\"", "&quot;").replaceAll("'",
						"&#39;");
		List<String> list = parseHtmlSpace(str);
		for (String s : list) {
			int len = s.length();
			if (len > 1) {
				String rp = getLenSpace(len - 1);
				_str = _str.replaceAll(s, " " + rp);
			}
		}
		// String[] arr = str.split("\n");
		// for (int i = 0; i < arr.length; i++) {
		// arr[i] = arr[i].replaceAll("( ){2,}", "");
		// }
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
			v = str.replaceAll("\r", "").replaceAll("\n", "").replaceAll(
					"&lt;", "<").replaceAll("&gt;", ">").replaceAll("<br/>",
					"\n").replaceAll("&amp;", "&").replaceAll("&nbsp;", " ")
					.replaceAll("&quot;", "\"").replaceAll("&#39;", "'");
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
			v = str.replaceAll("\r", "").replaceAll("\n", "").replaceAll(
					"&lt;", "<").replaceAll("&gt;", ">")
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

	public static Date parseTime(String v, String pattern) {
		if (DataUtil.isEmpty(v)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(v);
		}
		catch (ParseException e) {
			return null;
		}
	}

	public static Date parseTime(String timeString) {
		return parseTime(timeString, SYS_DEFAULT_TIME_FORMAT);
	}

	public static String getFormatTimeData(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String getFormatTimeData(Date date, String format,
			Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		return sdf.format(date);
	}

	public static String getFormatTimeData(Date date) {
		return getFormatTimeData(date, SYS_DEFAULT_TIME_FORMAT);
	}

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

	public static int getRandomNumber(int maxNumber) {
		Random r = new Random();
		return r.nextInt(maxNumber);
	}

	/**
	 * 如果end=-1,无法取结果
	 * 
	 * @param list
	 * @param begin
	 * @param size
	 * @return
	 */
	public static int getSelectedListEnd(List<?> list, int begin, int size) {
		int end = 0;
		if (begin > list.size() - 1) {
			return -1;
		}
		end = begin + size;
		if (end > list.size() - 1) {
			end = list.size();
		}
		return end;
	}

	public static long parseIpNumber(String ip) {
		String[] ips = ip.split("\\.");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ips.length; i++) {
			if (ips[i].length() < 3) {
				int zeron = 3 - ips[i].length();
				for (int k = 0; k < zeron; k++) {
					sb.append("0");
				}
			}
			sb.append(ips[i]);
		}
		try {
			return Long.parseLong(sb.toString());
		}
		catch (NumberFormatException e) {
			return 0;
		}
	}

	public static boolean isInElements(Object obj, Object[] objs) {
		for (Object o : objs) {
			if (o.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 过滤http
	 * 
	 * @param v
	 * @return
	 */
	public static String filterHttp(String v) {
		if (v == null) {
			return null;
		}
		if (v.toLowerCase().startsWith("http://")) {
			return v.substring(7);
		}
		return v;
	}

	public static boolean endWithInterpunction(String s) {
		if (s != null) {
			char ch = s.charAt(s.length() - 1);
			if (ch == ',' || ch == '，' || ch == '.' || ch == '。' || ch == '!'
					|| ch == '！' || ch == '?' || ch == '？') {
				return true;
			}
		}
		return false;
	}

	public static List<String> parseUrl(String content) {
		List<String> list = new ArrayList<String>(5);
		PatternMatcher matcher = new Perl5Matcher();
		PatternMatcherInput input = new PatternMatcherInput(content);
		MatchResult result = null;
		while (matcher.contains(input, urlPattern)) {
			result = matcher.getMatch();
			if (result != null) {
				String t = result.group(0);
				list.add(t);
			}
		}
		return list;
	}

	// public static List<String> parseUrl(String content) {
	// List<String> list = new ArrayList<String>(5);
	// PatternCompiler urlCompiler = new Perl5Compiler();
	// org.apache.oro.text.regex.Pattern urlPattern;
	// try {
	// urlPattern = urlCompiler.compile(urlRegx);
	// PatternMatcher matcher = new Perl5Matcher();
	// PatternMatcherInput input = new PatternMatcherInput(content);
	// MatchResult result = null;
	// while (matcher.contains(input, urlPattern)) {
	// result = matcher.getMatch();
	// if (result != null) {
	// String t = null;
	// t = result.group(0);
	// list.add(t);
	// }
	// }
	// }
	// catch (MalformedPatternException e) {
	// e.printStackTrace();
	// }
	// return list;
	// }
	/**
	 * 把所有url替换为特殊字符,
	 * 
	 * @param s
	 * @param replaceStr
	 * @return
	 */
	public static String replaceAllUrl(String s, String replaceStr) {
		List<String> urllist = DataUtil.parseUrl(s);
		String cont = s;
		for (String url : urllist) {
			String tmpurl = url.replaceAll("\\?", "\\\\?").replaceAll("\\$",
					"\\\\$");// 把其中特殊字符转化为普通字符
			cont = cont.replaceAll(tmpurl, replaceStr);// 去除url,然后计算总字数
		}
		return cont;
	}

	public static String formatUrl(String content) {
		if (content == null) {
			return null;
		}
		return content.replaceAll(" www.", " http://www.");
	}

	public static String replaceRirstUrl(String s, String url, String replaceStr) {
		String tmpurl = url.replaceAll("\\?", "\\\\?").replaceAll("\\$",
				"\\\\$");
		return s.replaceFirst(tmpurl, replaceStr);
	}

	public static int countString(String source, String v) {
		String str = " " + source;
		if (str.endsWith(v)) {
			return str.split(v).length;
		}
		return str.split(v).length - 1;
	}

	public static String filterZoneName(String name) {
		int idx = name.indexOf("省");
		String v = name;
		if (idx != -1) {
			v = v.substring(idx + 1);
		}
		idx = v.indexOf("市");
		if (idx != -1) {
			v = v.replaceAll("市", "");
		}
		idx = v.indexOf("区");
		if (idx != -1) {
			v = v.replaceAll("区", "");
		}
		return v;
		// return name.replaceAll("省", "").replaceAll("市", "").replaceAll("区",
		// "");
	}

	// public static <E, T> List<T> getFieldValueList(List<E> list,
	// FieldValueMapper<E, T> mapper) {
	// List<T> tlist = new ArrayList<T>();
	// Iterator<E> it = list.iterator();
	// while (it.hasNext()) {
	// T t = mapper.getValue(it.next());
	// tlist.add(t);
	// }
	// return tlist;
	// }
	public static Date createDate(int year, int month, int date) {
		if (year == 0 || month == 0 || date == 0) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 中国移动：
	// 2G号段：134、135、136、137、138、139、150、151、152、155、157、158、159；
	// 3G号段：187、188；
	// 中国联通：
	// 2G号段：130|131|132|156|185|186
	// 3G号段：185、186；
	// 中国电信：
	// 2G号段：133|153|180|189
	// 3G号段：180、189；
	private static final Pattern mobilePattern = Pattern
			.compile("(134|135|136|137|138|139|150|151|152|155|157|158|159|187|188)[0-9]{8}");

	private static final Pattern unicomPattern = Pattern
			.compile("(130|131|132|156|185|186)[0-9]{8}");

	private static final Pattern telecomPattern = Pattern
			.compile("(133|153|180|189)[0-9]{8}");

	/**
	 * 判断是否是移动号码
	 * 
	 * @param mobile
	 * @return
	 *         2010-4-11
	 */
	public static boolean isCmpMobile(String mobile) {
		return mobilePattern.matcher(mobile).matches();
	}

	public static String getMobileType(String mobile) {
		if (isCmpMobile(mobile)) {
			return "mobile";
		}
		if (isCmpUnicom(mobile)) {
			return "unicom";
		}
		return "telecom";
	}

	/**
	 * 判断是不是联通号码
	 * 
	 * @param mobile
	 * @return
	 *         2010-4-11
	 */
	public static boolean isCmpUnicom(String mobile) {
		return unicomPattern.matcher(mobile).matches();
	}

	/**
	 * 判断是否是电信号码
	 * 
	 * @param mobile
	 * @return
	 *         2010-4-11
	 */
	public static boolean isCmpTelecom(String mobile) {
		return telecomPattern.matcher(mobile).matches();
	}

	/**
	 * 把正则表达式特殊字符编码后的的值
	 * 
	 * @param s
	 * @return
	 *         2010-5-7
	 */
	public static String getFilterRegValue(String s) {
		if (isEmpty(s)) {
			return s;
		}
		return s.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]")
				.replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}")
				.replaceAll("\\*", "\\\\*").replaceAll("\\?", "\\\\?")
				.replaceAll("\\+", "\\\\+").replaceAll("\\.", "\\\\.")
				.replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)")
				.replaceAll("\\^", "\\\\^").replaceAll("\\|", "\\\\|")
				.replaceAll("\\$", "\\\\$");
	}

	public static String filerUnicodeString(String value) {
		char[] ss = value.toCharArray();
		for (int i = 0; i < ss.length; ++i) {
			if (ss[i] > 0xFFFD) {
				ss[i] = ' ';
			}
			else if (ss[i] < 0x20 && ss[i] != 't' & ss[i] != 'n' & ss[i] != 'r') {
				ss[i] = ' ';
			}
		}
		return new String(ss);
	}

	public static boolean validateIp(String ip) {
		if (ip == null) {
			return false;
		}
		String[] s = ip.split("\\.");
		if (s.length != 4) {
			return false;
		}
		for (int i = 0; i < s.length; i++) {
			int a = Integer.parseInt(s[i]);
			if (a < 0 || a > 255) {
				return false;
			}
			if (i == 3 && a == 0) {
				return false;
			}
		}
		return true;
	}

	public static String getLegalIp(String ip) {
		if (DataUtil.validateIp(ip)) {
			return ip;
		}
		return null;
	}

	public static String getSearchValue(String key) {
		if (key == null) {
			return null;
		}
		return key.replaceAll("\\+", "").replaceAll("&", "").replaceAll("NOT ",
				"").replaceAll("AND ", "").replaceAll("\\-", "").replaceAll(
				"[\\[\\]\\{\\}\\*\\(\\)\\-\\+&]+", "");
	}

	private static char[] base32_a = new char[] { 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5' };

	public static String[] getShortArray(String input) {
		int shortsize = 4;
		int subHexLen = 4;
		String[] output = new String[subHexLen];
		String hex = MD5Util.md5Encode32(input).toUpperCase();
		for (int i = 0; i < subHexLen; i++) {
			String subHex = hex.substring(i * 8, 8 * (i + 1));
			long t = Long.decode(("0x" + subHex));
			int tv = (int) (0x3FFFFFFF & (1 * t));
			String out = "";
			for (int j = 0; j < shortsize; j++) {
				int val = 0x0000001F & tv;
				out += base32_a[val];
				tv = tv >> 5;
			}
			output[i] = out;
		}
		return output;
	}

	public static String getShortValue(String input) {
		String[] o = getShortArray(input);
		int n = getRandomNumber(4);
		return o[n];
	}

	public static boolean isSameDay(Date a, Date b) {
		Calendar cal_a = Calendar.getInstance();
		Calendar cal_b = Calendar.getInstance();
		cal_a.setTime(a);
		cal_b.setTime(b);
		if (cal_a.get(Calendar.DATE) == cal_b.get(Calendar.DATE)
				&& cal_a.get(Calendar.MONTH) == cal_b.get(Calendar.MONTH)
				&& cal_a.get(Calendar.YEAR) == cal_b.get(Calendar.YEAR)) {
			return true;
		}
		return false;
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

	public static String getFmtTime(Date date) {
		long v = System.currentTimeMillis() - date.getTime();
		long sec = v / 1000;
		long min = sec / 60;
		long h = min / 60;
		if (sec == 0) {
			return "1秒前";
		}
		StringBuilder sb = new StringBuilder();
		if (h > 0) {
			if (h < 24) {
				sb.append(h).append("小时前");
				return sb.toString();
			}
			long day = h / 24;
			if (day < 30 && day > 0) {
				sb.append(day).append("天前");
				return sb.toString();
			}
			long month = day / 30;
			if (month < 12 && month > 0) {
				sb.append(month).append("个月前");
				return sb.toString();
			}
			sb.append(month / 12).append("年前");
			return sb.toString();
		}
		if (min > 0) {
			sb.append(min).append("分前");
			return sb.toString();
		}
		sb.append(sec).append("秒前");
		return sb.toString();
	}

	/**
	 * 按照中文汉字进行排序
	 * 
	 * @param array
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[] getSortedByName(String[] array) {
		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
		String[] arr = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			arr[i] = array[i];
		}
		Arrays.sort(arr, cmp);
		return arr;
	}

	/**
	 * 只保留日期数据，时间数据为0
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得某个日期的结束时间 HH:mm:ss:ms 为23:59:59:0
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
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

	public static String toJson(Map<String, String> map) {
		return JsonUtil.toJson(map);
	}

	public static String toJson(List<String> list) {
		return JsonUtil.toJson(list);
	}

	public static List<String> getListFromJson(String json) {
		return JsonUtil.getListFromJson(json);
	}

	public static Map<String, String> getMapFromJson(String json) {
		return JsonUtil.getMapFromJson(json);
	}

	public static JsonObj getJsonObj(String json) {
		return JsonUtil.getJsonObj(json);
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 *            原文家路径
	 * @param distm
	 *            要复制到的目标路径
	 *            2010-5-4
	 */
	public static void copyFile(String source, String dist, String fileName)
			throws IOException {
		copyFile(new File(source), dist, fileName);
	}

	public static void mkdir(String path) {
		File f = new File(path);
		if (!f.exists() || !f.isDirectory()) {
			f.mkdirs();
		}
	}

	public static boolean isFileDirectory(String path) {
		File f = new File(path);
		if (!f.exists()) {
			return false;
		}
		return f.isDirectory();
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 *            原文家路径
	 * @param distm
	 *            要复制到的目标路径
	 *            2010-5-4
	 */
	public static void copyFile(File file, String dist, String fileName)
			throws IOException {
		File f = new File(dist);
		if (!f.exists() || !f.isDirectory()) {
			f.mkdirs();
		}
		BufferedOutputStream buffos = null;
		BufferedInputStream buffis = null;
		byte[] by = new byte[1024];
		try {
			buffos = new BufferedOutputStream(new FileOutputStream(dist + "/"
					+ fileName));
			buffis = new BufferedInputStream(new FileInputStream(file));
			int len = -1;
			while ((len = buffis.read(by)) != -1) {
				buffos.write(by, 0, len);
			}
			buffos.flush();
		}
		catch (FileNotFoundException e) {
		}
		finally {
			try {
				if (buffos != null) {
					buffos.close();
				}
			}
			catch (IOException e) {
			}
			try {
				if (buffis != null) {
					buffis.close();
				}
			}
			catch (IOException e) {
			}
		}
	}

	/**
	 * 查看文件大小是否大于指定大小
	 * 
	 * @param file
	 * @param size
	 *            单位为k
	 * @return true:文件大小超过指定大小,false:文件大小在指定大小之内
	 *         2010-5-10
	 */
	public static boolean isBigger(File file, long size) {
		long fileSize = file.length();
		BigDecimal decimal = new BigDecimal(fileSize).divide(new BigDecimal(
				1024), 0, BigDecimal.ROUND_HALF_UP);
		long amount = decimal.longValue() - size;
		if (amount > 0) {
			return true;
		}
		return false;
	}

	public static final int FILE_SIZE_TYPE_K = 0;

	public static final int FILE_SIZE_TYPE_M = 1;

	public static long getFileSize(File file, int sizeType) {
		long fileSize = file.length();
		if (sizeType == FILE_SIZE_TYPE_K) {
			BigDecimal decimal = new BigDecimal(fileSize).divide(
					new BigDecimal(1024), 0, BigDecimal.ROUND_HALF_UP);
			return decimal.longValue();
		}
		if (sizeType == FILE_SIZE_TYPE_M) {
			BigDecimal decimal = new BigDecimal(fileSize).divide(
					new BigDecimal(1024 * 1024), 0, BigDecimal.ROUND_HALF_UP);
			return decimal.longValue();
		}
		return fileSize;
	}

	public static boolean isImage(File input) throws IOException {
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(input);
			Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
			if (readers.hasNext()) {
				return true;
			}
		}
		catch (IOException e) {
			throw e;
		}
		catch (Exception e) {
			return false;
		}
		finally {
			if (iis != null) {
				try {
					iis.close();
				}
				catch (IOException e) {
					throw e;
				}
				iis = null;
			}
		}
		return false;
	}

	public static void deleteFile(File file) {
		if (file == null) {
			return;
		}
		if (file.exists()) {
			file.delete();
		}
		File parent = file.getParentFile();
		if (parent != null) {
			parent.delete();
		}
	}

	public static void deleteAllFile(String dirPath) {
		File file = new File(dirPath);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] fs = file.listFiles();
				String path = null;
				for (File f : fs) {
					path = f.getAbsolutePath();
					deleteAllFile(path);
				}
				file.delete();
			}
			else {
				file.delete();
			}
		}
	}

	/**
	 * 验证2个字符串是否相等，当字符串都为null时，返回不相等
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 *         2010-7-12
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

	/**
	 * 英文长度为4-20,中文为2-10字符
	 * 
	 * @param nick
	 * @return
	 *         2010-8-29
	 */
	public static boolean checkEnAndChineseNick(String nick) {
		if (!isNumberOrCharOrChinese(nick)) {
			return false;
		}
		int sum = 0;
		char[] nick_char_arr = nick.toCharArray();
		for (char ch : nick_char_arr) {
			if (DataUtil.isChinese(ch)) {
				sum = sum + 2;
			}
			else {
				sum++;
			}
		}
		if (sum > 20 || sum < 4) {
			return false;
		}
		return true;
	}

	public static String getFormatNickLabaValue(String text) {
		return Util.substitute(new Perl5Matcher(), nickNamePattern,
				new Perl5Substitution("@<a href=\"/u/$1\">$1</a>",
						Perl5Substitution.INTERPOLATE_ALL), text,
				Util.SUBSTITUTE_ALL);
	}

	public static String fmtDouble(double number, String pattern) {
		DecimalFormat fmt = new DecimalFormat(pattern);
		return fmt.format(number);
	}

	public static Date createNoMillisecondTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static void main(String[] args) {
		// String text =
		// "马修刘 转发了 @剑语 的微博:在ipad上下载了个 读揽天下，发现其杂志99%都是收费的，用户习惯还需培养，若不先以免费方式吸引读者，恐怕很难做大 转发理由:iPad上这么做其实还是能理解的，大家“被收费“已习惯了。 //@冯文杰:厄，我也发现了";
		// P.println(getFormatNickLabaValue(text));
		// P.println(DataUtil.getFormatTimeData(new Date(),
		// "EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH));
		// P.println(DataUtil.getFormatTimeData(new Date(),
		// "EEE MMM d HH:mm:ss z yyyy"));
		DataUtil.deleteAllFile("d:/test/create");
		P.println("ok");
	}
}