package com.hk.frame.util.search;

import org.apache.lucene.document.Document;

public interface ResultMapper<T> {

	T mapRow(Document doc);
}
