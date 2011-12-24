package com.hk.bean;

public interface DelInfo {
	int LABA = 0;

	int FAV_LABA = 1;

	int PINK_LABA = 2;

	int getType();

	long getOpuserId();

	long getOptime();

	String getOtherParam();
}