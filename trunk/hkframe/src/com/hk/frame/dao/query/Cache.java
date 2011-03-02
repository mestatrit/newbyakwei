package com.hk.frame.dao.query;

public interface Cache {
	public void put(Object key, Object obj);

	public Object get(Object key);

	public void remove(Object key);

	public void clearAll();
}