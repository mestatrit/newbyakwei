package com.hk.svr.company.parser;

import com.hk.bean.UrlInfo;
import com.hk.svr.laba.parser.LabaInPutParser;
import com.hk.svr.laba.parser.LabaInfo;
import com.hk.svr.laba.parser.LabaOutPutParser;

public class CompanyCmtParser {
	public static String parseInput(String shortUrlDomain, String content) {
		LabaInPutParser labaInPutParser = new LabaInPutParser(shortUrlDomain);
		LabaInfo labaInfo = labaInPutParser.parse(content);
		return labaInfo.getParsedContent();
	}

	public static String parseOutPut(UrlInfo urlInfo, String content) {
		LabaOutPutParser labaOutPutParser = new LabaOutPutParser();
		if (urlInfo == null) {
			return labaOutPutParser.getText(content);
		}
		return labaOutPutParser.getHtml(urlInfo, content, 0);
	}
}