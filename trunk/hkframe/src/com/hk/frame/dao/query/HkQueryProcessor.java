package com.hk.frame.dao.query;

public interface HkQueryProcessor {

	<T> T execute();
}