package com.hk.frame.util;

public interface FieldValueMapper<E, T> {
	T getValue(E e);
}