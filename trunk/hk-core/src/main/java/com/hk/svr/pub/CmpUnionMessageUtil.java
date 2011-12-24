package com.hk.svr.pub;

import java.util.HashMap;
import java.util.Map;

public class CmpUnionMessageUtil {
	public static final int FEED_CREATEBOX = 1;

	public static final int FEED_CREATECOUPON = 2;

	public static final int FEED_CREATEPRODUCT = 3;

	public static final int FEED_CREATECMPACT = 4;

	public static final int REQ_JOIN_IN_CMPUNION = 1;

	public static final Map<Integer, Boolean> COMBINED_MAP = new HashMap<Integer, Boolean>();
	static {
		COMBINED_MAP.put(FEED_CREATEBOX, true);
		COMBINED_MAP.put(FEED_CREATECOUPON, true);
		COMBINED_MAP.put(FEED_CREATEPRODUCT, true);
		COMBINED_MAP.put(FEED_CREATECMPACT, false);
	}

	public static boolean isFeedCombined(int feedflg) {
		Boolean o = COMBINED_MAP.get(feedflg);
		if (o == null) {
			return false;
		}
		return o;
	}
}