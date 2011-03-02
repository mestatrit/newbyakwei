package com.hk.frame.util.search;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public class HkDefStandardAnalyzer extends StandardAnalyzer {

	private static final Set<?> HK_STOP_WORDS_SET;
	static {
		List<String> list = Arrays.asList("的", "不", "么", "呢", "是", "爱", "拿",
				"了", "，", "。", "！", "：", "？");
		final CharArraySet stopSet = new CharArraySet(list.size(), false);
		stopSet.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		stopSet.addAll(list);
		HK_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
	}

	public HkDefStandardAnalyzer(Version matchVersion) {
		this(matchVersion, HK_STOP_WORDS_SET);
	}

	public HkDefStandardAnalyzer(Version matchVersion, Set<?> stopWords) {
		super(matchVersion, stopWords);
	}
}