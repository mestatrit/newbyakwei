package com.hk.svr.cache;

import com.hk.bean.Laba;

public interface LabaCache {
	void removeLaba(long labaId);

	Laba getLaba(long labaId);

	void putLaba(Laba laba);
	// UserRecentLaba getUserRecentLaba(long userId);
	// void removeUserRecentLaba(long userId);
}